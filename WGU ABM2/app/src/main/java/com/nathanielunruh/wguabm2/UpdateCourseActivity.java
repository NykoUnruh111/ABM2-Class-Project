package com.nathanielunruh.wguabm2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateCourseActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
    dBHandler DataBaseHandler;
    Cursor cursor;

    EditText courseName;
    EditText startDate;
    EditText endDate;
    EditText mentorName;
    EditText mentorPhone;
    EditText mentorEmail;

    Button saveCourseButton;

    int currentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        DataBaseHandler = new dBHandler(this);
        cursor = DataBaseHandler.getCourse(dBHandler.currentSelectedTerm);

        courseName = findViewById(R.id.courseNameText);
        startDate = findViewById(R.id.courseStartDateText);
        endDate = findViewById(R.id.courseEndDateText);
        mentorName = findViewById(R.id.mentorNameText);
        mentorPhone = findViewById(R.id.mentorPhoneText);
        mentorEmail = findViewById(R.id.mentorEmailText);



        cursor.moveToFirst();

           do {

                //System.out.println(cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_ID)) );

                if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.COURSE_ID)) == dBHandler.currentSelectedCourse) {

                    courseName.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_NAME)));
                    startDate.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_STARTDATE)));
                    endDate.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_ENDDATE)));
                    mentorName.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTORNAME)));
                    mentorPhone.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTORPHONE)));
                    mentorEmail.setText(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTOREMAIL)));
                    currentStatus = cursor.getInt(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_STATUS));

                }

           } while (cursor.moveToNext());



        saveCourseButton = findViewById(R.id.saveCourseButton);

        saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean bCheck = Save();
                finish();


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    boolean Save() {

        //save stuff here
        String name = this.courseName.getText().toString();

        try {
            boolean bCheck = false;
            //Parse string into dates
            Date enddate = sdf.parse(this.endDate.getText().toString());
            Date startdate = sdf.parse(this.startDate.getText().toString());


            //Check to make sure startdate is before end date
            if (!startdate.before(enddate)) {

                Toast.makeText(this, "The end date must come after the start date!", Toast.LENGTH_SHORT).show();
                return false;

            } else {

                //If term name is not already created and taken saves the term

                    boolean bSaved = DataBaseHandler.updateCourse(dBHandler.currentSelectedCourse, name, sdf.format(startdate).toString(), sdf.format(enddate).toString(), mentorName.getText().toString(), mentorPhone.getText().toString(), mentorEmail.getText().toString(), currentStatus, dBHandler.currentSelectedTerm);

                    if (bSaved) {
                        Toast.makeText(UpdateCourseActivity.this, "Course has been updated.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(UpdateCourseActivity.this, "Course has not been updated.", Toast.LENGTH_SHORT).show();

                    return bSaved;

            }

        } catch (ParseException e) {
            Toast.makeText(UpdateCourseActivity.this, "Please make sure the date is formatted MM/DD/YYYY", Toast.LENGTH_SHORT).show();

        }

        return false;

    }

}