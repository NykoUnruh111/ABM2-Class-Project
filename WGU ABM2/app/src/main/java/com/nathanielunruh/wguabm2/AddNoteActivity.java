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

public class AddNoteActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;

    EditText addNoteText;
    EditText addNoteContentText;

    Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        DataBaseHandler = new dBHandler(this);

        addNoteText = findViewById(R.id.addNoteText);
        addNoteContentText = findViewById(R.id.addNoteContentText);

        saveNoteButton = findViewById(R.id.saveNoteButton);

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
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
        String name = this.addNoteText.getText().toString();

        boolean bCheck = false;

        //Check if course already exists
        Cursor cursor = DataBaseHandler.getCourse(dBHandler.currentSelectedTerm);

        if (cursor.getCount() >= 1) {

            cursor.moveToFirst();

            do {

                //checks name entry and compares
                if (!name.isEmpty() && name.contentEquals(cursor.getString(1))) {

                    bCheck = true;
                    Toast.makeText(AddNoteActivity.this, "This Note name is already taken.", Toast.LENGTH_SHORT).show();

                }

                System.out.println("Currently checked name: " + cursor.getString(1));

            } while (cursor.moveToNext());

        }

        //If note name is not already created and taken saves the term
        if (!bCheck) {
            boolean bSaved = DataBaseHandler.insertNote(name, addNoteContentText.getText().toString(), dBHandler.currentSelectedCourse);

            if (bSaved) {
                Toast.makeText(AddNoteActivity.this, "Note has been saved.", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Toast.makeText(AddNoteActivity.this, "Note has not been saved.", Toast.LENGTH_SHORT).show();

            return bSaved;
        }


        return false;

    }

}