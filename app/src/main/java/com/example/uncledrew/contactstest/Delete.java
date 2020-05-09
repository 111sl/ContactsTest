package com.example.uncledrew.contactstest;

import android.Manifest;
import android.content.ContentResolver;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 删除类
 */
public class Delete extends AppCompatActivity {
    private EditText nameEdit;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        nameEdit = findViewById(R.id.delete_name);
        Button deleteButton = findViewById(R.id.delete_one);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(Delete.this,Manifest.permission.WRITE_CONTACTS)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Delete.this,new String[]{Manifest.permission.WRITE_CONTACTS},1);
                }else{
                    if(!TextUtils.isEmpty(nameEdit.getText())){
                        name = nameEdit.getText().toString();
                        deleteContact(name);
                    }else{
                        Toast.makeText(Delete.this,"请输入完整的信息",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void deleteContact(String name) {

        //根据姓名求id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data._ID}, "display_name=?", new String[]{name}, null);
        if (cursor == null)
            return;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            //根据id删除data中的相应数据
            resolver.delete(uri, "display_name=?", new String[]{name});
            uri = Uri.parse("content://com.android.contacts/data");
            resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
            Toast.makeText(this, "删除号码成功", Toast.LENGTH_SHORT).show();
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
                    if(!TextUtils.isEmpty(nameEdit.getText())){
                        name = nameEdit.getText().toString();
                        deleteContact(name);
                    }else{
                        Toast.makeText(Delete.this,"请输入完整的信息",Toast.LENGTH_SHORT).show();
                    }
                }else {

                }
                break;
            default:
        }
    }
}
