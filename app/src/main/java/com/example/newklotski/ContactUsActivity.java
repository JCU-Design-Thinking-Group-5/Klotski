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
        // Create an intent to start the MainActivity
        Intent intent = new Intent(ContactUsActivity.this, MainActivity.class);
        // Start the MainActivity
        startActivity(intent);
    }

public void send_contact(View v) {
        // Create an AlertDialog.Builder object
        final AlertDialog.Builder alterDialog = new AlertDialog.Builder(ContactUsActivity.this);
        // Set the title of the dialog
        alterDialog.setTitle("Success Send");
        // Set a negative button "OK" with an OnClickListener
        alterDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                        // Create an intent to start the MainActivity
                        Intent intent = new Intent(ContactUsActivity.this, MainActivity.class);
                        // Start the MainActivity
                        startActivity(intent);
                    }
                });
        // Create and show the AlertDialog
        alterDialog.create().show();
    }
