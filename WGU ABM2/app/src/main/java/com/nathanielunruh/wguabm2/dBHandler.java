package com.nathanielunruh.wguabm2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class dBHandler extends SQLiteOpenHelper {

    public static int currentSelectedTerm = 0, currentSelectedCourse = 0, currentlyActiveTerm = 0;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TESTDB.db";

    //Term database
    public static final String TERM_ID = "_id";
    public static final String TERM_TABLE_NAME = "termtable";
    public static final String TERM_COLUMN_NAME = "termname";
    public static final String TERM_COLUMN_STARTDATE = "termstartdate";
    public static final String TERM_COLUMN_ENDDATE = "termenddate";
    public static final String TERM_COLUMN_TERMACTIVE = "termactive";

    //Create entries for term database
    private static final String SQL_CREATE_ENTRIES_TERM =
            "CREATE TABLE " + TERM_TABLE_NAME + " (" +
                    TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TERM_COLUMN_NAME + " TEXT, " +
                    TERM_COLUMN_STARTDATE + " TEXT, " +
                    TERM_COLUMN_ENDDATE + " TEXT, " +
                    TERM_COLUMN_TERMACTIVE + " INTEGER)";

    //Delete term database
    private static final String SQL_DELETE_ENTRIES_TERM =
            "DROP TABLE IF EXISTS " + TERM_TABLE_NAME;

    //Course database
    public static final String COURSE_ID = "_id";
    public static final String COURSE_TABLE_NAME = "coursetable";
    public static final String COURSE_COLUMN_NAME = "coursename";
    public static final String COURSE_COLUMN_STARTDATE = "coursestartdate";
    public static final String COURSE_COLUMN_ENDDATE = "courseenddate";
    public static final String COURSE_COLUMN_MENTORNAME = "coursementorname";
    public static final String COURSE_COLUMN_MENTORPHONE = "coursementorphone";
    public static final String COURSE_COLUMN_MENTOREMAIL = "coursementoremail";
    public static final String COURSE_COLUMN_STATUS = "coursestatus";
    public static final String COURSE_TERM_ID = "coursetermid";

    //Create entries for course database
    private static final String SQL_CREATE_ENTRIES_COURSE =
            "CREATE TABLE " + COURSE_TABLE_NAME + " (" +
                    COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_COLUMN_NAME + " TEXT, " +
                    COURSE_COLUMN_STARTDATE + " TEXT, " +
                    COURSE_COLUMN_ENDDATE + " TEXT, " +
                    COURSE_COLUMN_MENTORNAME + " TEXT, " +
                    COURSE_COLUMN_MENTORPHONE + " TEXT, " +
                    COURSE_COLUMN_MENTOREMAIL + " TEXT, " +
                    COURSE_COLUMN_STATUS + " INTEGER, " +
                    COURSE_TERM_ID + " INTEGER, " +
                    "FOREIGN KEY ("+COURSE_TERM_ID+") REFERENCES " +TERM_TABLE_NAME+ "(" +TERM_ID+ "));";

    //Delete course database
    private static final String SQL_DELETE_ENTRIES_COURSE =
            "DROP TABLE IF EXISTS " + COURSE_TABLE_NAME;

    //Note database
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TABLE_NAME = "notetable";
    public static final String NOTE_COLUMN_NAME = "notename";
    public static final String NOTE_COLUMN_CONTENT = "notecontent";
    public static final String NOTE_COURSE_ID = "notecourseid";

    //Create entries for note database
    private static final String SQL_CREATE_ENTRIES_NOTE =
            "CREATE TABLE " + NOTE_TABLE_NAME + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_COLUMN_NAME + " TEXT, " +
                    NOTE_COLUMN_CONTENT + " TEXT, " +
                    NOTE_COURSE_ID + " INTEGER, " +
                    "FOREIGN KEY ("+NOTE_COURSE_ID+") REFERENCES " +COURSE_TABLE_NAME+ "(" +COURSE_ID+ "));";

    //Delete note database
    private static final String SQL_DELETE_ENTRIES_NOTE =
            "DROP TABLE IF EXISTS " + NOTE_TABLE_NAME;

    ///////////////////////////////////
    //Assessment database
    public static final String ASSESSMENT_ID = "_id";
    public static final String ASSESSMENT_TABLE_NAME = "assesmenttable";
    public static final String ASSESSMENT_COLUMN_NAME = "assessmentname";
    public static final String ASSESSMENT_COLUMN_STARTDATE = "assessmentstart";
    public static final String ASSESSMENT_COLUMN_ENDDATE = "assessmentend";
    public static final String ASSESSMENT_COLUMN_TYPE = "assessmenttype";
    public static final String ASSESSMENT_COLUMN_CONTENT = "assessmentcontent";
    public static final String ASSESSMENT_COURSE_ID = "assessmentcourseid";

    //Create entries for Assessment database
    private static final String SQL_CREATE_ENTRIES_ASSESSMENT =
            "CREATE TABLE " + ASSESSMENT_TABLE_NAME + " (" +
                    ASSESSMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ASSESSMENT_COLUMN_NAME + " TEXT, " +
                    ASSESSMENT_COLUMN_STARTDATE + " TEXT, " +
                    ASSESSMENT_COLUMN_ENDDATE + " TEXT, " +
                    ASSESSMENT_COLUMN_TYPE + " INTEGER, " +
                    ASSESSMENT_COLUMN_CONTENT + " TEXT, " +
                    ASSESSMENT_COURSE_ID + " INTEGER, " +
                    "FOREIGN KEY ("+ASSESSMENT_COURSE_ID+") REFERENCES " +COURSE_TABLE_NAME+ "(" +COURSE_ID+ "));";

    //Delete Assessment database
    private static final String SQL_DELETE_ENTRIES_ASSESSMENT =
            "DROP TABLE IF EXISTS " + NOTE_TABLE_NAME;

    public dBHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //Insert note into note database
    public boolean insertAssessment(String assessmentName, String startDate, String endDate, int assessmentType, String assessmentContent,  int courseID) {

        ContentValues values = new ContentValues();

        //Put values
        values.put(ASSESSMENT_COLUMN_NAME, assessmentName);
        values.put(ASSESSMENT_COLUMN_STARTDATE, startDate);
        values.put(ASSESSMENT_COLUMN_ENDDATE, endDate);
        values.put(ASSESSMENT_COLUMN_TYPE, assessmentType);
        values.put(ASSESSMENT_COLUMN_CONTENT, assessmentContent);
        values.put(ASSESSMENT_COURSE_ID, courseID);

        long result = this.getWritableDatabase().insert(ASSESSMENT_TABLE_NAME, null, values);

        if (result == -1)
            return false;
        else
            return true;

    }

    //Update note in note table
    public boolean updateAssessment(int assessmentID, String assessmentName, String startDate, String endDate, int assessmentType, String assessmentContent,  int courseID) {

        ContentValues values = new ContentValues();

        //Put values
        values.put(ASSESSMENT_COLUMN_NAME, assessmentName);
        values.put(ASSESSMENT_COLUMN_STARTDATE, startDate);
        values.put(ASSESSMENT_COLUMN_ENDDATE, endDate);
        values.put(ASSESSMENT_COLUMN_TYPE, assessmentType);
        values.put(ASSESSMENT_COLUMN_CONTENT, assessmentContent);
        values.put(ASSESSMENT_COURSE_ID, courseID);

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + ASSESSMENT_TABLE_NAME + " WHERE _id =?",  new String[] {Integer.toString(assessmentID)});
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().update(ASSESSMENT_TABLE_NAME, values, "_id =?", new String[]{Integer.toString(assessmentID)});

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    //Delete note in course table
    public boolean deleteAssessment(int assessmentID) {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + ASSESSMENT_TABLE_NAME + " WHERE " + ASSESSMENT_ID + " =?",  new String[] {Integer.toString(assessmentID)});
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().delete(ASSESSMENT_TABLE_NAME, ASSESSMENT_ID + " =?", new String[]{Integer.toString(assessmentID)});

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    //Delete all notes in note table
    public boolean deleteAllAssessments() {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + ASSESSMENT_TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().delete(ASSESSMENT_TABLE_NAME, null, null);

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }

    }

    //Get note
    public Cursor getAssessment(int assessmentID) {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + ASSESSMENT_TABLE_NAME + " WHERE assessmentcourseid =?",  new String[] {Integer.toString(assessmentID)});

        return cursor;

    }
    //////////////////////////////////////////////////
    //Insert note into note database
    public boolean insertNote(String noteName, String noteContent,  int courseID) {

        ContentValues values = new ContentValues();

        //Put values
        values.put(NOTE_COLUMN_NAME, noteName);
        values.put(NOTE_COLUMN_CONTENT, noteContent);
        values.put(NOTE_COURSE_ID, courseID);

        long result = this.getWritableDatabase().insert(NOTE_TABLE_NAME, null, values);

        if (result == -1)
            return false;
        else
            return true;

    }

    //Update note in note table
    public boolean updateNote(String noteName, String noteContent,  int courseID) {

        ContentValues values = new ContentValues();

        values.put(NOTE_COLUMN_NAME, noteName);
        values.put(NOTE_COLUMN_CONTENT, noteContent);
        values.put(NOTE_COURSE_ID, courseID);

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + NOTE_TABLE_NAME + " WHERE notename =?",  new String[] {noteName});
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().update(NOTE_TABLE_NAME, values, "notename =?", new String[]{noteName});

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    //Delete note in course table
    public boolean deleteNote(String noteName) {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + NOTE_TABLE_NAME + " WHERE " + NOTE_COLUMN_NAME + " =?",  new String[] {noteName});
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().delete(NOTE_TABLE_NAME, NOTE_COLUMN_NAME + " =?", new String[]{noteName});

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    //Delete all notes in note table
    public boolean deleteAllNotes() {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + NOTE_TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().delete(NOTE_TABLE_NAME, null, null);

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }

    }

    //Get note
    public Cursor getNote(int noteID) {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + NOTE_TABLE_NAME + " WHERE notecourseid =?",  new String[] {Integer.toString(noteID)});

        return cursor;

    }

    //Insert new course into course table
    public boolean insertCourse(String courseName, String startDate, String endDate, String mentorName, String mentorPhone, String mentorEmail, int courseStatus, int termID) {

        ContentValues values = new ContentValues();

        //Put values
        values.put(COURSE_COLUMN_NAME, courseName);
        values.put(COURSE_COLUMN_STARTDATE, startDate);
        values.put(COURSE_COLUMN_ENDDATE, endDate);
        values.put(COURSE_COLUMN_MENTORNAME, mentorName);
        values.put(COURSE_COLUMN_MENTORPHONE, mentorPhone);
        values.put(COURSE_COLUMN_MENTOREMAIL, mentorEmail);
        values.put(COURSE_COLUMN_STATUS, courseStatus);
        values.put(COURSE_TERM_ID, termID);

        long result = this.getWritableDatabase().insert(COURSE_TABLE_NAME, null, values);

        if (result == -1)
            return false;
        else
            return true;

    }

    //Update course in course table
    public boolean updateCourse(int courseID, String courseName, String startDate, String endDate, String mentorName, String mentorPhone, String mentorEmail, int courseStatus, int termID) {

        ContentValues values = new ContentValues();

        values.put(COURSE_COLUMN_NAME, courseName);
        values.put(COURSE_COLUMN_STARTDATE, startDate);
        values.put(COURSE_COLUMN_ENDDATE, endDate);
        values.put(COURSE_COLUMN_MENTORNAME, mentorName);
        values.put(COURSE_COLUMN_MENTORPHONE, mentorPhone);
        values.put(COURSE_COLUMN_MENTOREMAIL, mentorEmail);
        values.put(COURSE_COLUMN_STATUS, courseStatus);
        values.put(COURSE_TERM_ID, termID);
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + COURSE_TABLE_NAME + " WHERE _id =?",  new String[] {Integer.toString(courseID)});
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().update(COURSE_TABLE_NAME, values, "_id =?", new String[]{Integer.toString(courseID)});

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    //Delete course in course table
    public boolean deleteCourse(int courseID) {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + COURSE_TABLE_NAME + " WHERE _id =?",  new String[] {Integer.toString(courseID)});
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().delete(COURSE_TABLE_NAME, "_id =?", new String[]{Integer.toString(courseID)});

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    //Delete all courses in course table
    public boolean deleteAllCourses() {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + COURSE_TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().delete(COURSE_TABLE_NAME, null, null);

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }

    }

    //Get courses
    public Cursor getCourse(int termID) {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + COURSE_TABLE_NAME + " WHERE coursetermid =?",  new String[] {Integer.toString(termID)});

        return cursor;

    }

    //Insert terms into term table
    public boolean insertTerm(String termname, String startDate, String endDate, boolean termActive) {

        ContentValues values = new ContentValues();

        //values.put(TERM_ID, termID);
        values.put(TERM_COLUMN_NAME, termname);
        values.put(TERM_COLUMN_STARTDATE, startDate);
        values.put(TERM_COLUMN_ENDDATE, endDate);
        values.put(TERM_COLUMN_TERMACTIVE, termActive);

        long result = this.getWritableDatabase().insert(TERM_TABLE_NAME, null, values);

        if (result == -1)
            return false;
        else
            return true;
    }

    //Update term in term table
    public boolean updateTerm(int termID, String termname, String startDate, String endDate, boolean termActive) {

        ContentValues values = new ContentValues();

        values.put(TERM_COLUMN_NAME, termname);
        values.put(TERM_COLUMN_STARTDATE, startDate);
        values.put(TERM_COLUMN_ENDDATE, endDate);
        values.put(TERM_COLUMN_TERMACTIVE, termActive);
        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TERM_TABLE_NAME + " WHERE _id =?",  new String[] { Integer.toString(termID) });
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().update(TERM_TABLE_NAME, values, "_id =?", new String[]{Integer.toString(termID)});

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    //Delete term from term table
    public boolean deleteTerm(int termID) {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TERM_TABLE_NAME + " WHERE _id =?",  new String[] { Integer.toString(termID)});
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().delete(TERM_TABLE_NAME, "_id =?", new String[]{Integer.toString(termID)});

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }
    }

    //Delete all terms in term table
    public boolean deleteAllTerms() {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TERM_TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            long result = this.getWritableDatabase().delete(TERM_TABLE_NAME, null, null);

            if (result == -1)
                return false;
            else
                return true;
        } else {
            return false;
        }

    }

    //Get term
    public Cursor getTerm() {

        Cursor cursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + TERM_TABLE_NAME, null);

        return cursor;

    }

    //Create sample data for debugging
    public void createSampleData() {

        insertTerm("Term 1", "1/1/2021", "6/1/2021", false);
        insertTerm("Term 2", "6/1/2021", "1/1/2022", false);
        insertTerm("Term 3", "1/1/2022", "6/1/2022", false);

        insertCourse("Intro to programming 101", "1/1/2021", "3/1/2021", "John ManGuy", "8008884444", "test@test.com", 0, 1);
        insertCourse("Programming 102", "3/1/2021", "6/1/2021", "John ManGuy", "8008884444", "test@test.com", 0, 1);

        insertNote("Test note", "This is a test note to see if the note system is working!", 1);

        insertAssessment("Intro OA", "1/30/2021", "2/1/2021", 0, "a short description of this assessment",  1);
        insertAssessment("Intro PA", "2/30/2021", "3/1/2021", 1, "a short description of this assessment",  1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES_TERM);
        db.execSQL(SQL_CREATE_ENTRIES_COURSE);
        db.execSQL(SQL_CREATE_ENTRIES_NOTE);
        db.execSQL(SQL_CREATE_ENTRIES_ASSESSMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES_TERM);
        db.execSQL(SQL_DELETE_ENTRIES_COURSE);
        db.execSQL(SQL_DELETE_ENTRIES_NOTE);
        db.execSQL(SQL_DELETE_ENTRIES_ASSESSMENT);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onUpgrade(db, oldVersion, newVersion);

    }
}
