package com.example.petfeeder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
//Database Handler Class
public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "scheduler_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "feeder_schedule";
    private static final String ID_COL = "_id";
    private static final String TIMER_NAME_COL = "timer_name";
    private static final String DATE_COL = "date";
    private static final String TIME_COL = "time";
    private static final String AMOUNT_COL = "amount";
    private static final String NOTE_COL= "notes";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Creates Database Columns
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " TEXT PRIMARY KEY,"
                + TIMER_NAME_COL + " TEXT,"
                + DATE_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + AMOUNT_COL +" TEXT,"
                + NOTE_COL + " TEXT)";
        db.execSQL(query);
    }

    //Adds input values into the database
    public void addNewTimer(String ID, String timerName, String date, String time, String amount, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL, ID);
        values.put(TIMER_NAME_COL, timerName);
        values.put(DATE_COL, date);
        values.put(TIME_COL, time);
        values.put(AMOUNT_COL, amount);
        values.put(NOTE_COL, notes);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //creates an arraylist viewTimer that allows the database to be viewed by the user
    public ArrayList<TimerModal> viewTimer() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<TimerModal> timerModelArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                timerModelArrayList.add(new TimerModal(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return timerModelArrayList;
    }

    //updateTimer Method updates values based upon their ID within the database, replacing any modifications
    public void updateTimer(String originalID, String ID, String timerName,
                               String date, String time, String amount, String notes) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL, ID);
        values.put(TIMER_NAME_COL, timerName);
        values.put(DATE_COL, date);
        values.put(TIME_COL, time);
        values.put(AMOUNT_COL, amount);
        values.put(NOTE_COL, notes);

        db.update(TABLE_NAME, values, "_id=?", new String[]{originalID});
        db.close();
    }

    //deleteTimer method removes database objects based upon their ID
    public void deleteTimer(String ID) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, "_id=?", new String[]{ID});
        db.close();
    }

    //Creates a boolean to compare current IDs with potential new IDs.
    public boolean checkID (String ID) {
        boolean rv = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor checkID = db.rawQuery("SELECT _id FROM " + TABLE_NAME +
                " where _id=?", new String[]{ID});
        if (checkID.moveToFirst()) {
            do {
                if (checkID.getString(0).equals(ID)){
                    rv = true;
                }
            } while (checkID.moveToNext());
        }
        checkID.close();
        return rv;
    }

    //Modifies the table version when databse is updated.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

