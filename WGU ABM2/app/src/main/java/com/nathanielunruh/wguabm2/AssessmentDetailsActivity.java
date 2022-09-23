package com.nathanielunruh.wguabm2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AssessmentDetailsActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;
    Cursor cursor;
    TextView assessmentTitleText, assessmentStartDateText, assessmentEndDateText, assessmentTypeText, descriptionText;
    int assessmentID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_assessment_details);

        DataBaseHandler = new dBHandler(this);

        //Assign the text fields
        assessmentTitleText = findViewById(R.id.assessmentTitleText);
        assessmentStartDateText = findViewById(R.id.assessmentStartDateText);
        assessmentEndDateText = findViewById(R.id.assessmentEndDateText);
        descriptionText = findViewById(R.id.descriptionText);
        assessmentTypeText = findViewById(R.id.assessmentTypeText);
        assessmentID = (int)this.getIntent().getSerializableExtra("assessmentID");

        updateText();

        /*//Set the text of each text field
        assessmentTitleText.setText(this.getIntent().getSerializableExtra("assessmentDetailsNameText").toString());
        assessmentStartDateText.setText(this.getIntent().getSerializableExtra("assessmentStartDateText").toString());
        assessmentEndDateText.setText(this.getIntent().getSerializableExtra("assessmentEndDateText").toString());
        descriptionText.setText(this.getIntent().getSerializableExtra("assessmentDetailsText").toString());*/



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        updateText();
        super.onResume();
    }

    void updateText() {

        cursor = DataBaseHandler.getAssessment(dBHandler.currentSelectedCourse);

        cursor.moveToFirst();

        do {

            if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.ASSESSMENT_ID)) == assessmentID) {

                assessmentTitleText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_NAME)));
                assessmentStartDateText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_STARTDATE)));
                assessmentEndDateText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_ENDDATE)));
                if (cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_CONTENT)) != null)
                    descriptionText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_CONTENT)));

                switch(cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.ASSESSMENT_COLUMN_TYPE))) {
                    case 0:
                        assessmentTypeText.setText("OA");
                        break;
                    case 1:
                        assessmentTypeText.setText("PA");
                        break;
                    default:
                        assessmentTypeText.setText("ERR");
                        break;
                }

            }


        } while(cursor.moveToNext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.assessment_details_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.setAssessmentStartAlarm:
            {
                //Split the date into separate strings to process it
                String[] splitdates = assessmentStartDateText.getText().toString().split("/", 5);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, (Integer.parseInt(splitdates[0]) - 1));
                calendar.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(splitdates[1])));
                calendar.set(Calendar.YEAR, Integer.parseInt(splitdates[2]));
                calendar.add(Calendar.SECOND, 5);

                System.out.println("Alarm month: " + calendar.get(Calendar.MONTH));
                System.out.println("Alarm Day of the month: " + calendar.get(Calendar.DAY_OF_MONTH));
                System.out.println("Alarm year: " + calendar.get(Calendar.YEAR));

                NotificationHandler notif = new NotificationHandler();

                notif.scheduleNotification(notif.getNotification(assessmentTitleText.getText().toString() + " is about to start!", this), calendar.getTimeInMillis(), this);

                Toast.makeText(this, "Start assessment Alarm successfully set!", Toast.LENGTH_SHORT).show();
            }
            return true;
            case R.id.setAssessmentEndAlarm:
            {
                //Split the date into separate strings to process it
                String[] splitdates = assessmentStartDateText.getText().toString().split("/", 5);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, (Integer.parseInt(splitdates[0]) - 1));
                calendar.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(splitdates[1])));
                calendar.set(Calendar.YEAR, Integer.parseInt(splitdates[2]));
                calendar.add(Calendar.SECOND, 5);

                System.out.println("Alarm month: " + calendar.get(Calendar.MONTH));
                System.out.println("Alarm Day of the month: " + calendar.get(Calendar.DAY_OF_MONTH));
                System.out.println("Alarm year: " + calendar.get(Calendar.YEAR));

                NotificationHandler notif = new NotificationHandler();

                notif.scheduleNotification(notif.getNotification(assessmentTitleText.getText().toString() + " is nearly over!", this), calendar.getTimeInMillis(), this);

                Toast.makeText(this, "End assessment Alarm successfully set!", Toast.LENGTH_SHORT).show();
            }
            return true;
            case R.id.menuDetailsEditAssessment:
            {

                Intent intent = new Intent(AssessmentDetailsActivity.this, UpdateAssessmentActivity.class);
                intent.putExtra("assessmentID", assessmentID);
                startActivity(intent);


            }
            return true;
            case R.id.menuDetailsDeleteAssessment:
                if (DataBaseHandler.getAssessment(assessmentID).getCount() >= 0) {
                    DataBaseHandler.deleteAssessment(assessmentID);
                    finish();
                } else {
                    Toast.makeText(this, "Can not delete assessment!", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}