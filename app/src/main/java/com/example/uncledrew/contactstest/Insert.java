package com.example.uncledrew.contactstest;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 新增类
 */
public class Insert extends AppCompatActivity {

    private EditText nameEdit;
    private EditText numberEdit;
    private String name;
    private String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        nameEdit = findViewById(R.id.insert_name);
        numberEdit = findViewById(R.id.insert_number);
        Button insert = findViewById(R.id.insert_one);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Insert.this,Manifest.permission.WRITE_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Insert.this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
                }else{
                    if(!TextUtils.isEmpty(nameEdit.getText())&&!TextUtils.isEmpty(numberEdit.getText())){
                        name = nameEdit.getText().toString();
                        number = numberEdit.getText().toString();
                        writeContact(name,number);
                    }else{
                        Toast.makeText(Insert.this,"请输入完整的信息",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void writeContact(String name,String number) {

        //先查询要添加的号码是否已存在通讯录中, 不存在则添加. 存在则提示用户
        Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + number);
        ContentResolver resolver = getContentResolver();
        //从raw_contact表中返回display_name
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
        if (cursor == null)
            return;

        if (cursor.moveToFirst()) {
            Log.i("nn", "name=" + cursor.getString(0));
            Toast.makeText(this, "存在相同号码", Toast.LENGTH_SHORT).show();
        } else {
            uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentValues values = new ContentValues();
            long contact_id = ContentUris.parseId(resolver.insert(uri, values));
            //插入data表
            uri = Uri.parse("content://com.android.contacts/data");
            //add Name
            values.put("raw_contact_id", contact_id);
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/name");
            values.put("data2", name);
//            values.put("data1", name);
            resolver.insert(uri, values);
            values.clear();

            //add Phone
            values.put("raw_contact_id", contact_id);
            values.put(ContactsContract.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
            values.put("data2", "2");   //手机
            values.put("data1", number);
            resolver.insert(uri, values);
            values.clear();

            Toast.makeText(this, "插入号码成功", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(!TextUtils.isEmpty(nameEdit.getText())&&!TextUtils.isEmpty(numberEdit.getText())){
                        name = nameEdit.getText().toString();
                        number = numberEdit.getText().toString();
                        writeContact(name,number);
                    }else{
                        Toast.makeText(Insert.this,"请输入完整的信息",Toast.LENGTH_SHORT).show();
                    }
                }else {

                }
                break;
            default:
        }
    }
}
