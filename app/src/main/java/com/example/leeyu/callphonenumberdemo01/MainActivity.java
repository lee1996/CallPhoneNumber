package com.example.leeyu.callphonenumberdemo01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test te = new test();
        TelephonyManager tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tManager.listen(te, PhoneStateListener.LISTEN_CALL_STATE);

    }

    class test extends PhoneStateListener {
        String incoming;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            switch (telephonyManager.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    getNumber(MainActivity.this, incomingNumber);
                    //  Toast.makeText(MainActivity.this,"来电号码为:"+incomingNumber,Toast.LENGTH_SHORT).show();
            }


        }

        public void getNumber(Context context, String number) {
            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, null, null);
            cursor.moveToFirst();
            int flag = 1;
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (num.equals(number)) {
                    Toast.makeText(context, "这是你通讯录中的好友，他是" + name, Toast.LENGTH_SHORT).show();
                    flag = 2;
                    break;
                }
            }
            if (flag == 1) {
                Toast.makeText(context, "陌生人号码", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }
    }

}
