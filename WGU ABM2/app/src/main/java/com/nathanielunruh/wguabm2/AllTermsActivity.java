package com.nathanielunruh.wguabm2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AllTermsActivity extends AppCompatActivity {

    dBHandler DataBaseHandler;
    FloatingActionButton addTermButton;
    Cursor cursor;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);

        DataBaseHandler = new dBHandler(this);

        updateList();

        addTermButton = findViewById(R.id.addNewTermButton);

        addTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllTermsActivity.this, AddTermActivity.class));

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

        cursor = DataBaseHandler.getTerm();

        lvItems = findViewById(R.id.assessmentListView);
        TermCursorAdapter termCursorAdapter = new TermCursorAdapter(this, cursor);
        lvItems.setAdapter(termCursorAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String termTitle = ((TextView) view.findViewById(R.id.termTitleText)).getText().toString();
                String termStart = ((TextView) view.findViewById(R.id.termStartDate)).getText().toString();
                String termEnd = ((TextView) view.findViewById(R.id.termEndDate)).getText().toString();
                int termID = 0;

                //Selects the term and assigns it to a static variable to allow for linking of the id in course tables.
                cursor.moveToFirst();

                do {

                    if (termTitle.contentEquals(cursor.getString(1))) {

                        dBHandler.currentSelectedTerm = cursor.getInt(0);
                        System.out.println("Current term: " + dBHandler.currentSelectedTerm);

                    }

                } while (cursor.moveToNext());

                Intent intent = new Intent(AllTermsActivity.this, TermDetailsActivity.class);

                //Serialize data to open in the next activity
                /*intent.putExtra("termID", termID);
                intent.putExtra("termDetailsTitleText", termTitle);
                intent.putExtra("termDetailsStartDateText", termStart);
                intent.putExtra("termDetailsEndDateText", termEnd);*/

                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.all_terms_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.createSampleData:
                DataBaseHandler.createSampleData();
                updateList();
                return true;

            case R.id.deleteAllTerms:
                DataBaseHandler.deleteAllTerms();
                updateList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}