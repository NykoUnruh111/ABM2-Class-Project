package com.nathanielunruh.wguabm2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.Serializable;

public class TermDetailsActivity extends AppCompatActivity implements Serializable {

    dBHandler DataBaseHandler;
    Cursor cursor;
    public TextView termDetailsTitleText, termDetailsStartDateText, termDetailsEndDateText;

    Button coursesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        DataBaseHandler = new dBHandler(this);

        coursesButton = findViewById(R.id.CoursesButton);

        termDetailsTitleText = findViewById(R.id.termDetailsTitleText);
        termDetailsStartDateText = findViewById(R.id.termDetailsStartDateText);
        termDetailsEndDateText = findViewById(R.id.termDetailsEndDateText);


        updateText();

       /* //Set text using serialized data from previous activity
        termDetailsTitleText.setText(this.getIntent().getSerializableExtra("termDetailsTitleText").toString());
        termDetailsStartDateText.setText(this.getIntent().getSerializableExtra("termDetailsStartDateText").toString());
        termDetailsEndDateText.setText(this.getIntent().getSerializableExtra("termDetailsEndDateText").toString());*/


        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(TermDetailsActivity.this, AllCoursesActivity.class));

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        updateText();
        super.onResume();
    }

    void updateText() {

        cursor = DataBaseHandler.getTerm();

        cursor.moveToFirst();

        do {

            if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_ID)) == dBHandler.currentSelectedTerm) {
                termDetailsTitleText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.TERM_COLUMN_NAME)));
                termDetailsStartDateText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.TERM_COLUMN_STARTDATE)));
                termDetailsEndDateText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.TERM_COLUMN_ENDDATE)));
            }


        } while(cursor.moveToNext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.term_details_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menuStartTerm:
            {
                cursor = DataBaseHandler.getTerm();

                cursor.moveToFirst();

                boolean bAlreadyActiveTerm = false;

                do {

                    if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_COLUMN_TERMACTIVE)) >= 1) {

                        bAlreadyActiveTerm = true;

                    }

                    if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_ID)) == dBHandler.currentSelectedTerm) {



                    }


                } while(cursor.moveToNext());

                if (!bAlreadyActiveTerm) {

                    cursor.moveToFirst();

                    do {


                        if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_ID)) == dBHandler.currentSelectedTerm) {

                            DataBaseHandler.updateTerm(cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_ID)), cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.TERM_COLUMN_NAME)), cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.TERM_COLUMN_STARTDATE)), cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.TERM_COLUMN_ENDDATE)), true);

                        }


                    } while(cursor.moveToNext());

                    Toast.makeText(this, "Term started!", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this, "You already have an active term!", Toast.LENGTH_SHORT).show();

                }

            }
                return true;
            case R.id.setStartTermAlarm:
            {
                //Split the date into separate strings to process it
                String[] splitdates = termDetailsStartDateText.getText().toString().split("/", 5);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, (Integer.parseInt(splitdates[0]) - 1));
                calendar.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(splitdates[1])));
                calendar.set(Calendar.YEAR, Integer.parseInt(splitdates[2]));
                calendar.add(Calendar.SECOND, 5);

                System.out.println("Alarm month: " + calendar.get(Calendar.MONTH));
                System.out.println("Alarm Day of the month: " + calendar.get(Calendar.DAY_OF_MONTH));
                System.out.println("Alarm year: " + calendar.get(Calendar.YEAR));

                NotificationHandler notif = new NotificationHandler();

                notif.scheduleNotification(notif.getNotification("Term is starting!", this), calendar.getTimeInMillis(), this);

                Toast.makeText(this, "Start term Alarm successfully set!", Toast.LENGTH_SHORT).show();


            }
                return true;
            case R.id.setEndTermAlarm:
            {
                //Split the date into separate strings to process it
                String[] splitdates = termDetailsStartDateText.getText().toString().split("/", 5);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, (Integer.parseInt(splitdates[0]) - 1));
                calendar.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(splitdates[1])));
                calendar.set(Calendar.YEAR, Integer.parseInt(splitdates[2]));
                calendar.add(Calendar.SECOND, 5);

                System.out.println("Alarm month: " + calendar.get(Calendar.MONTH));
                System.out.println("Alarm Day of the month: " + calendar.get(Calendar.DAY_OF_MONTH));
                System.out.println("Alarm year: " + calendar.get(Calendar.YEAR));

                NotificationHandler notif = new NotificationHandler();

                notif.scheduleNotification(notif.getNotification("Term is ending!", this), calendar.getTimeInMillis(), this);

                Toast.makeText(this, "End term Alarm successfully set!", Toast.LENGTH_SHORT).show();


            }
            return true;
            case R.id.menuDetailsEditTerm:
            {

                Intent intent = new Intent(TermDetailsActivity.this, UpdateTermActivity.class);

                intent.putExtra("termDetailsTitleText", termDetailsTitleText.getText().toString());
                intent.putExtra("termDetailsStartDateText", termDetailsStartDateText.getText().toString());
                intent.putExtra("termDetailsEndDateText", termDetailsEndDateText.getText().toString());
                intent.putExtra("termID", dBHandler.currentSelectedTerm);

                startActivity(intent);


            }
                return true;
            case R.id.menuDetailsDeleteTerm:
                if (DataBaseHandler.getCourse(dBHandler.currentSelectedTerm).getCount() <= 0) {
                    DataBaseHandler.deleteTerm(dBHandler.currentSelectedTerm);
                    finish();
                } else {
                    Toast.makeText(this, "Can not delete terms that have courses assigned!", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}