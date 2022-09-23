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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CourseDetailsActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;
    Cursor cursor;
    TextView courseDetailsTitleText, courseDetailsStartDateText, courseDetailsEndDateText, courseMentorNameText, courseMentorPhoneText, courseMentorEmailText, courseStatusText;
    Button noteButton, assessmentButton;
    int currentStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        DataBaseHandler = new dBHandler(this);

        courseDetailsTitleText = findViewById(R.id.courseTitleText);
        courseDetailsStartDateText = findViewById(R.id.courseStartDateText);
        courseDetailsEndDateText = findViewById(R.id.courseEndDateText);
        courseMentorNameText = findViewById(R.id.courseMentorNameText);
        courseMentorPhoneText = findViewById(R.id.courseMentorPhoneText);
        courseMentorEmailText = findViewById(R.id.courseMentorEmailText);
        courseStatusText = findViewById(R.id.courseStatusText);

        updateText();

       /* courseDetailsTitleText.setText(this.getIntent().getSerializableExtra("courseDetailsTitleText").toString());
        courseDetailsStartDateText.setText(this.getIntent().getSerializableExtra("courseDetailsStartDateText").toString());
        courseDetailsEndDateText.setText(this.getIntent().getSerializableExtra("courseDetailsEndDateText").toString());
        courseMentorNameText.setText(this.getIntent().getSerializableExtra("courseMentorNameText").toString());
        courseMentorPhoneText.setText(this.getIntent().getSerializableExtra("courseMentorPhoneText").toString());
        courseMentorEmailText.setText(this.getIntent().getSerializableExtra("courseMentorEmailText").toString());
        currentStatus = (int) this.getIntent().getSerializableExtra("courseStatus");*/

        noteButton = findViewById(R.id.courseNotesButton);
        assessmentButton = findViewById(R.id.assessmentsButton);

        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CourseDetailsActivity.this, AllNotesActivity.class));

            }
        });

        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CourseDetailsActivity.this, AllAssessmentsActivity.class));

            }
        });

        updateStatus();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        updateText();
        super.onResume();
    }

    void updateText() {

        cursor = DataBaseHandler.getCourse(dBHandler.currentSelectedTerm);

        cursor.moveToFirst();

        do {

            //System.out.println(cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_ID)) );

            if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.COURSE_ID)) == dBHandler.currentSelectedCourse) {

                courseDetailsTitleText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_NAME)));
                courseDetailsStartDateText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_STARTDATE)));
                courseDetailsEndDateText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_ENDDATE)));
                courseMentorNameText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTORNAME)));
                courseMentorPhoneText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTORPHONE)));
                courseMentorEmailText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTOREMAIL)));
                currentStatus = cursor.getInt(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_STATUS));

            }


        } while(cursor.moveToNext());

    }

    void updateStatus() {

        switch (currentStatus) {
            case 0:
                courseStatusText.setText("Planned");
                break;
            case 1:
                courseStatusText.setText("In progress");
                break;
            case 2:
                courseStatusText.setText("Completed");
                break;
            case 3:
                courseStatusText.setText("Dropped");
                break;
            default:
                courseStatusText.setText("Somethings gone awfully wrong.");
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_details_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.setCourseStartAlarm:
            {
                //Split the date into separate strings to process it
                String[] splitdates = courseDetailsStartDateText.getText().toString().split("/", 5);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, (Integer.parseInt(splitdates[0]) - 1));
                calendar.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(splitdates[1])));
                calendar.set(Calendar.YEAR, Integer.parseInt(splitdates[2]));
                calendar.add(Calendar.SECOND, 5);

                System.out.println("Alarm month: " + calendar.get(Calendar.MONTH));
                System.out.println("Alarm Day of the month: " + calendar.get(Calendar.DAY_OF_MONTH));
                System.out.println("Alarm year: " + calendar.get(Calendar.YEAR));

                NotificationHandler notif = new NotificationHandler();

                notif.scheduleNotification(notif.getNotification(courseDetailsTitleText.getText().toString() + " is starting!", this), calendar.getTimeInMillis(), this);

                Toast.makeText(this, "Start course Alarm successfully set!", Toast.LENGTH_SHORT).show();
            }
            return true;
            case R.id.setEndCourseAlarm:
            {
                //Split the date into separate strings to process it
                String[] splitdates = courseDetailsStartDateText.getText().toString().split("/", 5);

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.MONTH, (Integer.parseInt(splitdates[0]) - 1));
                calendar.set(Calendar.DAY_OF_MONTH, (Integer.parseInt(splitdates[1])));
                calendar.set(Calendar.YEAR, Integer.parseInt(splitdates[2]));
                calendar.add(Calendar.SECOND, 5);

                System.out.println("Alarm month: " + calendar.get(Calendar.MONTH));
                System.out.println("Alarm Day of the month: " + calendar.get(Calendar.DAY_OF_MONTH));
                System.out.println("Alarm year: " + calendar.get(Calendar.YEAR));

                NotificationHandler notif = new NotificationHandler();

                notif.scheduleNotification(notif.getNotification(courseDetailsTitleText.getText().toString() + " is ending!", this), calendar.getTimeInMillis(), this);

                Toast.makeText(this, "End course Alarm successfully set!", Toast.LENGTH_SHORT).show();
            }
            return true;
            case R.id.menuDetailsStartCourse:
                currentStatus = 1;
                DataBaseHandler.updateCourse(dBHandler.currentSelectedCourse, courseDetailsTitleText.getText().toString(), courseDetailsStartDateText.getText().toString(), courseDetailsEndDateText.getText().toString(), courseMentorNameText.getText().toString(), courseMentorPhoneText.getText().toString(), courseMentorEmailText.getText().toString(), currentStatus, dBHandler.currentSelectedTerm);
                updateStatus();
                return true;
            case R.id.menuDetailsDropCourse:
                currentStatus = 3;
                DataBaseHandler.updateCourse(dBHandler.currentSelectedCourse, courseDetailsTitleText.getText().toString(), courseDetailsStartDateText.getText().toString(), courseDetailsEndDateText.getText().toString(), courseMentorNameText.getText().toString(), courseMentorPhoneText.getText().toString(), courseMentorEmailText.getText().toString(), currentStatus, dBHandler.currentSelectedTerm);
                updateStatus();
                return true;
            case R.id.menuDetailsEditCourse:
                //Do edit update stuff
            {

                Intent intent = new Intent(CourseDetailsActivity.this, UpdateCourseActivity.class);
                startActivity(intent);

            }
                return true;
            case R.id.menuDetailsDeleteCourse:
                if (DataBaseHandler.getCourse(dBHandler.currentSelectedTerm).getCount() >= 0) {
                    DataBaseHandler.deleteCourse(dBHandler.currentSelectedCourse);
                    finish();
                } else {
                    Toast.makeText(this, "Can not delete course!", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}