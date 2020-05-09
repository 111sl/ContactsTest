package com.example.uncledrew.contactstest;

import android.Manifest;
import android.content.ContentResolver;
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
 * 更新类
 */
public class Update extends AppCompatActivity {

    private EditText oldNameEdit;
    private EditText newNameEdit;
    private EditText newNumberEdit;
    private String oldName;
    private String newName;
    private String newNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        oldNameEdit = findViewById(R.id.old_name);
        newNameEdit = findViewById(R.id.new_name);
        newNumberEdit = findViewById(R.id.new_number);
        Button updateOne = findViewById(R.id.update_one);
        updateOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Update.this,Manifest.permission.WRITE_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Update.this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
                }else{
                    if(!TextUtils.isEmpty(oldNameEdit.getText())){
                        if(TextUtils.isEmpty(newNameEdit.getText())&&TextUtils.isEmpty(newNumberEdit.getText())){
                            Toast.makeText(Update.this,"请输入足够的信息",Toast.LENGTH_SHORT).show();
                        }else {
                            oldName = oldNameEdit.getText().toString();
                            newName = newNameEdit.getText().toString();
                            newNumber = newNumberEdit.getText().toString();
                            updateContact(oldName,newName,newNumber);
                        }
                    }else{
                        Toast.makeText(Update.this,"请输入足够的信息",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void updateContact(String oldName,String newName,String newNumber){
        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, "display_name=?", new String[]{oldName}, null);
        if (cursor == null)
            return;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            ContentValues values = new ContentValues();
            uri = Uri.parse("content://com.android.contacts/data");
            if(!TextUtils.isEmpty(newNameEdit.getText())){
                values.put("data2", newName);
                resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/name", id + ""});
            }
            if(!TextUtils.isEmpty(newNumberEdit.getText())){
                values.put("data1", newNumber);
                resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/phone_v2", id + ""});
            }
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "没有找到号码", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    if(!TextUtils.isEmpty(oldNameEdit.getText())){
                        if(TextUtils.isEmpty(newNameEdit.getText())&&TextUtils.isEmpty(newNumberEdit.getText())){
                            Toast.makeText(Update.this,"请输入足够的信息",Toast.LENGTH_SHORT).show();
                        }else {
                            oldName = oldNameEdit.getText().toString();
                            newName = newNameEdit.getText().toString();
                            newNumber = newNumberEdit.getText().toString();
                            updateContact(oldName,newName,newNumber);
                        }
                    }else{
                        Toast.makeText(Update.this,"请输入足够的信息",Toast.LENGTH_SHORT).show();
                    }
                }else {

                }
                break;
            default:
        }
    }
}
