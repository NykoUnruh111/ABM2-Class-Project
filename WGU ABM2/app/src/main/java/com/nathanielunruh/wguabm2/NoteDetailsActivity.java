package com.nathanielunruh.wguabm2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class NoteDetailsActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;
    Cursor cursor;
    TextView noteDetailsNameText, noteDetailsText, sendSMSNumber;
    Button sendSMSButton;
    int noteID;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        DataBaseHandler = new dBHandler(this);
        cursor = DataBaseHandler.getNote(dBHandler.currentSelectedCourse);

        noteDetailsNameText = findViewById(R.id.noteDetailsNameText);
        noteDetailsText = findViewById(R.id.noteDetailsText);
        sendSMSNumber = findViewById(R.id.phoneNumberSMSText);
        noteID = (int) this.getIntent().getSerializableExtra("noteID");

        cursor.moveToFirst();

        do {


            if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.NOTE_ID)) == noteID) {

                noteDetailsNameText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.NOTE_COLUMN_NAME)));
                noteDetailsText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.NOTE_COLUMN_CONTENT)));


            }


        } while(cursor.moveToNext());

/*
        noteDetailsNameText.setText(this.getIntent().getSerializableExtra("noteDetailsNameText").toString());
        noteDetailsText.setText(this.getIntent().getSerializableExtra("noteDetailsText").toString());*/

        sendSMSButton = findViewById(R.id.sendSMSButton);

        sendSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendSMSMessage();
                //Toast.makeText(NoteDetailsActivity.this, "SMS has been sent!", Toast.LENGTH_SHORT).show();

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_details_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menuDetailsDeleteNote:
                if (DataBaseHandler.getNote(dBHandler.currentSelectedCourse).getCount() >= 0) {
                    DataBaseHandler.deleteNote(noteDetailsNameText.getText().toString());
                    finish();
                } else {
                    Toast.makeText(this, "Can not delete note!", Toast.LENGTH_SHORT).show();
                }
                    return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(sendSMSNumber.getText().toString(), null, noteDetailsText.getText().toString(), null, null);
                    Toast.makeText(this, "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}