package com.nathanielunruh.wguabm2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;

    Button currentTermButton, allTermsButton;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHandler = new dBHandler(this);

        currentTermButton = findViewById(R.id.CurrentTermButton);
        allTermsButton = findViewById(R.id.AllTermsButton);

        cursor = DataBaseHandler.getTerm();

        System.out.println(DatabaseUtils.dumpCursorToString(cursor));

        currentTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cursor = DataBaseHandler.getTerm();
                boolean bActiveTerm = false;

                //Check if term is currently active


                if (cursor != null && cursor.moveToFirst()) {


                    do {

                        //checks name entry and compares
                        if (cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_COLUMN_TERMACTIVE)) == 1) {

                            bActiveTerm = true;
                            dBHandler.currentlyActiveTerm = cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.TERM_ID));

                        }

                    } while (cursor.moveToNext());

                }

                //If active term then start new activity for the active term otherwise create toast message.
                if (bActiveTerm) {

                    dBHandler.currentSelectedTerm = dBHandler.currentlyActiveTerm;
                    startActivity(new Intent(MainActivity.this, TermDetailsActivity.class));

                } else {

                    Toast.makeText(MainActivity.this, "There is no active term!", Toast.LENGTH_SHORT).show();

                }




            }
        });

        allTermsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, AllTermsActivity.class));

            }
        });
    }
}