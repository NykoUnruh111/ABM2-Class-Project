package com.nathanielunruh.wguabm2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CurrentTermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_term);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}