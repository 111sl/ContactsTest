package com.example.uncledrew.contactstest.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.uncledrew.contactstest.GetPhoneMessage;
import com.example.uncledrew.contactstest.Insert;

public class BroadReceiverTest extends BroadcastReceiver {
    String SMS_RECEIVED = "com.example.broadcasttest.TEST_BRODACAST" ;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(SMS_RECEIVED)){
            Toast.makeText(context,"自定义广播",Toast.LENGTH_SHORT).show();
        }else if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Toast.makeText(context,"开机启动",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context,GetPhoneMessage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
