package com.nathanielunruh.wguabm2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateTermActivity extends AppCompatActivity implements Serializable {

    SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
    dBHandler DataBaseHandler;

    EditText termName;
    EditText startDate;
    EditText endDate;
    Button saveButton;
    int termID;

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

        termName.setText(this.getIntent().getSerializableExtra("termDetailsTitleText").toString());
        startDate.setText(this.getIntent().getSerializableExtra("termDetailsStartDateText").toString());
        endDate.setText(this.getIntent().getSerializableExtra("termDetailsEndDateText").toString());
        termID = (int) this.getIntent().getSerializableExtra("termID");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean bCheck = Save();

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


                    boolean bSaved = DataBaseHandler.updateTerm(termID, name, sdf.format(startdate).toString(), sdf.format(enddate).toString(), false);

                    if (bSaved)
                        Toast.makeText(UpdateTermActivity.this, "Term has been updated.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(UpdateTermActivity.this, "Term has not been updated.", Toast.LENGTH_SHORT).show();

                    return bSaved;

            }

        } catch (ParseException e) {
            Toast.makeText(UpdateTermActivity.this, "Please make sure the date is formatted MM/DD/YYYY", Toast.LENGTH_SHORT).show();

        }

        return false;

    }
}