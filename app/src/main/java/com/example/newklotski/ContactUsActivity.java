package com.example.newklotski;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
    }

    public void return_contact(View v) {
        Intent intent = new Intent(ContactUsActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void send_contact(View v) {
        final AlertDialog.Builder alterDialog  = new AlertDialog.Builder(ContactUsActivity.this);
        alterDialog.setTitle("Success Send");
        alterDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(ContactUsActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
        alterDialog.create().show();
    }

}
