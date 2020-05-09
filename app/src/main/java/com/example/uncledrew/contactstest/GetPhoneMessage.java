package com.example.uncledrew.contactstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GetPhoneMessage extends AppCompatActivity {
    private GetPhoneMessageReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_message);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        mReceiver = new GetPhoneMessageReceiver();
        registerReceiver(mReceiver,intentFilter);
    }

   class  GetPhoneMessageReceiver extends BroadcastReceiver{
       @Override
       public void onReceive(Context context, Intent intent) {

       }
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
