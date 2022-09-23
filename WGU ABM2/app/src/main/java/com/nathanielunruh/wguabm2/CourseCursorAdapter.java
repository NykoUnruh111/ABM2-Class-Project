package com.nathanielunruh.wguabm2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CourseCursorAdapter extends CursorAdapter {


    public CourseCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.course_list_item, parent,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView courseTitleText = view.findViewById(R.id.courseTitleText);
        TextView courseStartDate = view.findViewById(R.id.courseStartDate);
        TextView courseEndDate = view.findViewById(R.id.courseEndDate);
        TextView courseStatus = view.findViewById(R.id.currentCourseStatusText);

        String courseTitle = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.COURSE_COLUMN_NAME));
        String courseStart = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.COURSE_COLUMN_STARTDATE));
        String courseEnd = cursor.getString(cursor.getColumnIndexOrThrow(dBHandler.COURSE_COLUMN_ENDDATE));
        int currentStatus = cursor.getInt(cursor.getColumnIndexOrThrow(dBHandler.COURSE_COLUMN_STATUS));

        switch (currentStatus) {
            case 0:
                courseStatus.setText("Planned");
                break;
            case 1:
                courseStatus.setText("In progress");
                break;
            case 2:
                courseStatus.setText("Completed");
                break;
            case 3:
                courseStatus.setText("Dropped");
                break;
            default:
                courseStatus.setText("Somethings gone awfully wrong.");
                break;
        }

        courseTitleText.setText(courseTitle);
        courseStartDate.setText(courseStart);
        courseEndDate.setText(courseEnd);

    }

}
