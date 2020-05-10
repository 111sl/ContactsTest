package com.example.uncledrew.contactstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

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
       @RequiresApi(api = Build.VERSION_CODES.M)
       @Override
       public void onReceive(Context context, Intent intent) {
           /**
            * 获取短信内容这段代码是百度的
            */
           Bundle bundle = intent.getExtras();
           Object[]pdus =  (Object[]) bundle.get("pdus");
           //获取短信数组
           SmsMessage[] smsMessages = new SmsMessage[pdus.length];
           //高版本
           String format2 = intent.getStringExtra("format");
           //将pdus里内容转换成SmsMessage
           for(int i=0;i<pdus.length;i++){
               //高版本过时
               smsMessages[i] = SmsMessage.createFromPdu((byte[])pdus[i],format2);
           }
           //获取电话号
           String num = smsMessages[0].getOriginatingAddress();
           //获取发送短信的时间戳
           long timestampMillis = smsMessages[0].getTimestampMillis();
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           String time = format.format(new Date(timestampMillis));
           //获取短信的所有内容
           StringBuffer stringBuffer = new StringBuffer();
           for(int i=0;i<smsMessages.length;i++){
               stringBuffer.append(smsMessages[i].getDisplayMessageBody());
           }
           String body = stringBuffer.toString();
           Log.e("AAA", "==num=="+num+"==time=="+time+"==body=="+body);
           Toast.makeText(context, body, Toast.LENGTH_SHORT).show();
       }
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
