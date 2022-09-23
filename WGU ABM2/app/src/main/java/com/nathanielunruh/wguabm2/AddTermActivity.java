package com.nathanielunruh.wguabm2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTermActivity extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
    dBHandler DataBaseHandler;

    EditText termName;
    EditText startDate;
    EditText endDate;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        DataBaseHandler = new dBHandler(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termName = findViewById(R.id.termNameText);
        startDate = findViewById(R.id.termStartDateText);
        endDate = findViewById(R.id.termEndDateText);
        saveButton = findViewById(R.id.saveTermButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean bCheck = Save();
                if (bCheck)
                    finish();


            }
        });

    }

    boolean Save() {

        //save stuff here
        String name = this.termName.getText().toString();

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

                //Check if term already exists
                Cursor cursor = DataBaseHandler.getTerm();

                if (cursor.getCount() >= 1) {

                    cursor.moveToFirst();

                    do {

                        //checks name entry and compares
                        if (!name.isEmpty() && name.contentEquals(cursor.getString(1))) {

                            bCheck = true;
                            Toast.makeText(AddTermActivity.this, "This term name is already taken.", Toast.LENGTH_SHORT).show();

                            System.out.println("Currently checked name: " + cursor.getString(1));
                        }

                    } while (cursor.moveToNext());

                }

                //If term name is not already created and taken saves the term
                if (!bCheck) {
                    boolean bSaved = DataBaseHandler.insertTerm(name, sdf.format(startdate).toString(), sdf.format(enddate).toString(), false);

                    if (bSaved)
                        Toast.makeText(AddTermActivity.this, "Term has been saved.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddTermActivity.this, "Term has not been saved.", Toast.LENGTH_SHORT).show();

                    return bSaved;
                }
            }

        } catch (ParseException e) {
            Toast.makeText(AddTermActivity.this, "Please make sure the date is formatted MM/DD/YYYY", Toast.LENGTH_SHORT).show();

        }

        return false;

    }
}