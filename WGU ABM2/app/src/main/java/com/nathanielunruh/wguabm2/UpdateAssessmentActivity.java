package com.nathanielunruh.wguabm2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateAssessmentActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");

    dBHandler DataBaseHandler;
    Cursor cursor;

    EditText addAssessmentText;
    EditText startDateText;
    EditText endDateText;
    EditText addAssessmentContentText;
    RadioGroup radGroup;
    RadioButton radioPA, radioOA;

    Button saveAssessmentButton;
    int assessmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        DataBaseHandler = new dBHandler(this);

        addAssessmentText = findViewById(R.id.addAssessmentNameText);
        startDateText = findViewById(R.id.addAssessmentStartDateText);
        endDateText = findViewById(R.id.addAssessmentEndDateText);
        addAssessmentContentText = findViewById(R.id.addAssessmentContentText);
        radGroup = findViewById(R.id.addAssessmentRadGroup);
        radioPA = findViewById(R.id.radioButtonPA);
        radioOA = findViewById(R.id.radioButtonOA);
        assessmentID = (int) this.getIntent().getSerializableExtra("assessmentID");
        //System.out.println("Checked radio button is: " + radGroup.getCheckedRadioButtonId());

        cursor = DataBaseHandler.getAssessment(dBHandler.currentSelectedCourse);

        cursor.moveToFirst();

        do {

            //System.out.println(cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_ID)) );

            if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.ASSESSMENT_ID)) == assessmentID) {

                addAssessmentText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_NAME)));
                startDateText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_STARTDATE)));
                endDateText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_ENDDATE)));
                addAssessmentContentText.setText(cursor.getString(cursor.getColumnIndex(dBHandler.ASSESSMENT_COLUMN_CONTENT)));
            }


        } while(cursor.moveToNext());

        saveAssessmentButton = findViewById(R.id.saveAssessmentButton);

        saveAssessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean bCheck = Save();



            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    boolean Save() {

        //save stuff here
        String name = this.addAssessmentText.getText().toString();

        try {
            boolean bCheck = false;
            //Parse string into dates
            Date enddate = sdf.parse(this.endDateText.getText().toString());
            Date startdate = sdf.parse(this.startDateText.getText().toString());


            //Check to make sure startdate is before end date
            if (!startdate.before(enddate)) {

                Toast.makeText(this, "The end date must come after the start date!", Toast.LENGTH_SHORT).show();
                return false;

            } else {


                int assessmentType;
                if (radGroup.getCheckedRadioButtonId() == radioOA.getId()) {
                    assessmentType = 0;
                } else if (radGroup.getCheckedRadioButtonId() == radioPA.getId()){
                    assessmentType = 1;
                } else {
                    Toast.makeText(UpdateAssessmentActivity.this, "Please select an assessment type.", Toast.LENGTH_SHORT).show();
                    return false;
                }

                    boolean bSaved = DataBaseHandler.updateAssessment(assessmentID, name, sdf.format(startdate).toString(), sdf.format(enddate).toString(), assessmentType, addAssessmentContentText.getText().toString(),dBHandler.currentSelectedCourse);

                    if (bSaved) {
                        Toast.makeText(UpdateAssessmentActivity.this, "Assessment has been updated.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(UpdateAssessmentActivity.this, "Assessment has not been updated.", Toast.LENGTH_SHORT).show();

                    return bSaved;

            }

        } catch (ParseException e) {
            Toast.makeText(UpdateAssessmentActivity.this, "Please make sure the date is formatted MM/DD/YYYY", Toast.LENGTH_SHORT).show();
        }

        return false;

    }



}