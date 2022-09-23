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

public class AllAssessmentsActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;
    FloatingActionButton addAssessmentButton;
    Cursor cursor;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assessments);

        DataBaseHandler = new dBHandler(this);

        updateList();

        addAssessmentButton = findViewById(R.id.addNewAssessmentsButton);

        addAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllAssessmentsActivity.this, AddAssessmentActivity.class));

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

        cursor = DataBaseHandler.getAssessment(dBHandler.currentSelectedCourse);

        cursor.moveToFirst();

        lvItems = findViewById(R.id.assessmentListView);
        AssessmentCursorAdapter assessmentCursorAdapter = new AssessmentCursorAdapter(this, cursor);
        lvItems.setAdapter(assessmentCursorAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(AllAssessmentsActivity.this, AssessmentDetailsActivity.class);

                String assessmentTitle;
                String assessmentContentText;
                String assessmentStartDateText;
                String assessmentEndDateText;
                int assessmentType;
                int assessmentID = 0;

                //System.out.println(((TextView) view.findViewById(R.id.courseTitleText)).getText().toString().contentEquals(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_NAME))));

                assessmentTitle = cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_NAME));
                assessmentContentText = cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_CONTENT));
                assessmentStartDateText = cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_STARTDATE));
                assessmentEndDateText = cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_ENDDATE));
                assessmentType = cursor.getInt(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_TYPE));
                assessmentID = cursor.getInt(cursor.getColumnIndex(dBHandler.ASSESSMENT_ID));



                // selected item
                do {

                    if (((TextView) view.findViewById(R.id.assessmentNameText)).getText().toString().contentEquals(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_NAME)))) {
                        //assessmentID = cursor.getColumnIndex(dBHandler.ASSESSMENT_ID);
                        //Serialize data to open in the next activity


                    }



                } while (cursor.moveToNext());

                intent.putExtra("assessmentID", assessmentID);


                startActivity(intent);
            }
        });



    }
}