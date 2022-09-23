package com.nathanielunruh.wguabm2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AssessmentCursorAdapter extends CursorAdapter {


    public AssessmentCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.assessment_list_item, parent,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView assessmentNameText = view.findViewById(R.id.assessmentNameText);
        TextView assessmentStartDate = view.findViewById(R.id.assessmentStartDate);
        TextView assessmentEndDate = view.findViewById(R.id.assessmentEndDate);
        //TextView assessmentType = view.findViewById(R.id.assessmentTypeText);
        //TextView assessmentDescriptionText = view.findViewById(R.id.descriptionText);



        String assessmentName = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.ASSESSMENT_COLUMN_NAME));
        String assessmentStart = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.ASSESSMENT_COLUMN_STARTDATE));
        String assessmentEnd = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.ASSESSMENT_COLUMN_ENDDATE));
        //String assessmentDescription = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.ASSESSMENT_COLUMN_CONTENT));


        assessmentNameText.setText(assessmentName);
        assessmentStartDate.setText(assessmentStart);
        assessmentEndDate.setText(assessmentEnd);
        //assessmentDescriptionText.setText(assessmentDescription);


    }

}
