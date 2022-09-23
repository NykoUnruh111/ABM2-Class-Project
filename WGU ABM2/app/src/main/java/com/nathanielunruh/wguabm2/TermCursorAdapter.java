package com.nathanielunruh.wguabm2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static androidx.core.content.ContextCompat.startActivity;

public class TermCursorAdapter extends CursorAdapter {


    public TermCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.term_list_item, parent,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView termTitleText = view.findViewById(R.id.termTitleText);
        TextView termStartDate = view.findViewById(R.id.termStartDate);
        TextView termEndDate = view.findViewById(R.id.termEndDate);

        String termTitle = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.TERM_COLUMN_NAME));
        String termStart = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.TERM_COLUMN_STARTDATE));
        String termEnd = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.TERM_COLUMN_ENDDATE));

        termTitleText.setText(termTitle);
        termStartDate.setText(termStart);
        termEndDate.setText(termEnd);
    }

}
