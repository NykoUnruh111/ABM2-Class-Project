package com.nathanielunruh.wguabm2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AllCoursesActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;
    FloatingActionButton addCourseButton;
    Cursor cursor;
    ListView lvItems;
    int courseStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);

        DataBaseHandler = new dBHandler(this);

        updateList();

        addCourseButton = findViewById(R.id.addNewCoursesButton);

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllCoursesActivity.this, AddCourseActivity.class));

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onResume() {
        updateList();
        super.onResume();
    }

    void updateList() {

        //Update list view

        cursor = DataBaseHandler.getCourse(dBHandler.currentSelectedTerm);

        cursor.moveToFirst();

        lvItems = findViewById(R.id.courseListView);
        CourseCursorAdapter courseCursorAdapter = new CourseCursorAdapter(this, cursor);
        lvItems.setAdapter(courseCursorAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(AllCoursesActivity.this, CourseDetailsActivity.class);

                String courseTitle;
                String courseStart;
                String courseEnd;
                String courseMentorName;
                String courseMentorPhone;
                String courseMentorEmail;

                //System.out.println(((TextView) view.findViewById(R.id.courseTitleText)).getText().toString().contentEquals(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_NAME))));

                // selected item
                do {

                    if (((TextView) view.findViewById(R.id.courseTitleText)).getText().toString().contentEquals(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_NAME)))) {

                        courseTitle = cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_NAME));
                        courseStart = cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_STARTDATE));
                        courseEnd = cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_ENDDATE));
                        courseMentorName = cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTORNAME));
                        courseMentorPhone = cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTORPHONE));
                        courseMentorEmail = cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_MENTOREMAIL));

                        courseStatus = cursor.getInt(7);
                        dBHandler.currentSelectedCourse = cursor.getInt(0);

                        System.out.println("Current course according to DB: " + dBHandler.currentSelectedCourse);
                        System.out.println(courseStatus);

                        //Serialize data to open in the next activity
                        intent.putExtra("courseDetailsTitleText", courseTitle);
                        intent.putExtra("courseDetailsStartDateText", courseStart);
                        intent.putExtra("courseDetailsEndDateText", courseEnd);
                        intent.putExtra("courseMentorNameText", courseMentorName);
                        intent.putExtra("courseMentorPhoneText", courseMentorPhone);
                        intent.putExtra("courseMentorEmailText", courseMentorEmail);
                        intent.putExtra("courseStatus", courseStatus);


                    }

                } while (cursor.moveToNext());


                startActivity(intent);
            }
        });



    }

}