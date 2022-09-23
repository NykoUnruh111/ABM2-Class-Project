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

public class AddCourseActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
    dBHandler DataBaseHandler;

    EditText courseName;
    EditText startDate;
    EditText endDate;
    EditText mentorName;
    EditText mentorPhone;
    EditText mentorEmail;

    Button saveCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        DataBaseHandler = new dBHandler(this);

        courseName = findViewById(R.id.courseNameText);
        startDate = findViewById(R.id.courseStartDateText);
        endDate = findViewById(R.id.courseEndDateText);
        mentorName = findViewById(R.id.mentorNameText);
        mentorPhone = findViewById(R.id.mentorPhoneText);
        mentorEmail = findViewById(R.id.mentorEmailText);
        saveCourseButton = findViewById(R.id.saveCourseButton);

        saveCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean bCheck = Save();
                if (bCheck)
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

                //Check if course already exists
                Cursor cursor = DataBaseHandler.getCourse(dBHandler.currentSelectedTerm);

                if (cursor.getCount() >= 1) {

                    cursor.moveToFirst();

                    do {

                        //checks name entry and compares
                        if (!name.isEmpty() && name.contentEquals(cursor.getString(1))) {

                            bCheck = true;
                            Toast.makeText(AddCourseActivity.this, "This Course name is already taken.", Toast.LENGTH_SHORT).show();

                        }

                        System.out.println("Currently checked name: " + cursor.getString(1));

                    } while (cursor.moveToNext());

                }

                //If term name is not already created and taken saves the term
                if (!bCheck) {
                    boolean bSaved = DataBaseHandler.insertCourse(name, sdf.format(startdate).toString(), sdf.format(enddate).toString(), mentorName.getText().toString(), mentorPhone.getText().toString(), mentorEmail.getText().toString(), 0, dBHandler.currentSelectedTerm);

                    if (bSaved) {
                        Toast.makeText(AddCourseActivity.this, "Course has been saved.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(AddCourseActivity.this, "Course has not been saved.", Toast.LENGTH_SHORT).show();

                    return bSaved;
                }
            }

        } catch (ParseException e) {
            Toast.makeText(AddCourseActivity.this, "Please make sure the date is formatted MM/DD/YYYY", Toast.LENGTH_SHORT).show();

        }

        return false;

    }

}