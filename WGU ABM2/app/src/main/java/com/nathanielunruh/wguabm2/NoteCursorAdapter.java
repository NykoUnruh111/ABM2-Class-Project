package com.nathanielunruh.wguabm2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class NoteCursorAdapter extends CursorAdapter {


    public NoteCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.note_list_item, parent,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView noteNameText = view.findViewById(R.id.noteNameText);
        TextView noteText = view.findViewById(R.id.noteText);


        String noteName = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.NOTE_COLUMN_NAME));
        String noteContentText = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.NOTE_COLUMN_CONTENT));

        noteNameText.setText(noteName);
        noteText.setText(noteContentText);

    }

}
