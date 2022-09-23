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

public class AllNotesActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;
    FloatingActionButton addNoteButton;
    Cursor cursor;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        DataBaseHandler = new dBHandler(this);

        updateList();

        addNoteButton = findViewById(R.id.addNewNoteButton);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllNotesActivity.this, AddNoteActivity.class));

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

        cursor = DataBaseHandler.getNote(dBHandler.currentSelectedCourse);

        cursor.moveToFirst();

        lvItems = findViewById(R.id.noteListView);
        NoteCursorAdapter noteCursorAdapter = new NoteCursorAdapter(this, cursor);
        lvItems.setAdapter(noteCursorAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(AllNotesActivity.this, NoteDetailsActivity.class);

                String noteTitle;
                String noteContentText;
                int noteID = 0;

                //System.out.println(((TextView) view.findViewById(R.id.courseTitleText)).getText().toString().contentEquals(cursor.getString(cursor.getColumnIndex(dBHandler.COURSE_COLUMN_NAME))));

                // selected item
                do {

                    if (((TextView) view.findViewById(R.id.noteNameText)).getText().toString().contentEquals(cursor.getString(cursor.getColumnIndex(dBHandler.NOTE_COLUMN_NAME)))) {

                        noteID = cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.NOTE_ID));
                        intent.putExtra("noteID", noteID);

                        /*noteTitle = cursor.getString(cursor.getColumnIndex(dBHandler.NOTE_COLUMN_NAME));
                        noteContentText = cursor.getString(cursor.getColumnIndex(dBHandler.NOTE_COLUMN_CONTENT));


                        //Serialize data to open in the next activity
                        intent.putExtra("noteDetailsNameText", noteTitle);
                        intent.putExtra("noteDetailsText", noteContentText);*/



                    }



                } while (cursor.moveToNext());


                startActivity(intent);
            }
        });



    }

}
