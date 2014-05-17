package com.collegecode.objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saurabh on 5/17/14.
 */
public class sqDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "subjectsManager";

    // Contacts table name
    private static final String TABLE_SUBJECTS = "subjects";

    // Contacts Table Columns names
    private static final String KEY_CLASNBR = "clsnbr";
    private static final String KEY_TITLE = "title";
    private static final String KEY_SLOT = "slot";

    private static final String[] COLUMNS = {KEY_CLASNBR,KEY_TITLE,KEY_SLOT,"type","attended", "conducted", "regdate", "details"};

    public sqDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "("
                + KEY_CLASNBR + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_SLOT + " TEXT,"
                + "type TEXT, attended INTEGER, conducted INTEGER, regdate TEXT, details TEXT)";

        sqLiteDatabase.execSQL(CREATE_SUBJECTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        onCreate(sqLiteDatabase);
    }

    public void addSubjects(String SubjectJSON){
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            JSONArray root = new JSONArray(SubjectJSON);
            JSONObject sub;

            for(int i = 0; i < root.length(); i++){
                sub = root.getJSONObject(i);

                ContentValues values = new ContentValues();

                values.put(KEY_CLASNBR, Integer.parseInt(sub.getString("classnbr")));
                values.put(KEY_TITLE, sub.getString("title"));
                values.put(KEY_SLOT, sub.getString("slot"));
                values.put("type", sub.getString("type"));
                values.put("conducted", Integer.parseInt(sub.getString("conducted")));
                values.put("attended", Integer.parseInt(sub.getString("attended")));
                values.put("regdate", sub.getString("regdate"));
                values.put("details", sub.getJSONArray("details").toString());
                db.insertWithOnConflict(TABLE_SUBJECTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.close();
        }catch (Exception e){e.printStackTrace();}

    }

    public Subject getSubject(String clsnbr) {
        Subject sub = new Subject();
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.query(TABLE_SUBJECTS, COLUMNS, KEY_CLASNBR + "=?", new String[]{String.valueOf(clsnbr)}, null, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            sub.classnbr = Integer.toString(cursor.getInt(0));
            sub.title = cursor.getString(1);
            sub.slot = cursor.getString(2);
            sub.type = cursor.getString(3);
            sub.attended = cursor.getInt(4);
            sub.conducted = cursor.getInt(5);
            sub.regdate = cursor.getString(6);
            sub.detailsString = cursor.getString(7);

            sub.percentage = (int) DataHandler.getPer(sub.attended, sub.conducted);

            if (DataHandler.getPer(sub.attended,sub.conducted) > sub.percentage)
                sub.percentage += 1;

            cursor.close();
            db.close();

        }catch(Exception e){e.printStackTrace();}


        return sub;
    }

    /*public int getSubjectsCount(String subject){
        String countQuery = "SELECT  * FROM " + TABLE_SUBJECTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        return 0;
    }*/

    public ArrayList<Subject> getAllSubjects(){
        ArrayList<Subject> subs = new ArrayList<Subject>();
        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Subject sub = new Subject();
                sub.classnbr = Integer.toString(cursor.getInt(0));
                sub.title = cursor.getString(1);
                sub.slot = cursor.getString(2);
                sub.type = cursor.getString(3);
                sub.attended = cursor.getInt(4);
                sub.conducted = cursor.getInt(5);
                sub.regdate = cursor.getString(6);
                sub.detailsString = cursor.getString(7);

                sub.percentage = (int) DataHandler.getPer(sub.attended, sub.conducted);

                if (DataHandler.getPer(sub.attended,sub.conducted) > sub.percentage)
                    sub.percentage += 1;
                sub.putAttendanceDetails();
                subs.add(sub);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return subs;
    }

}
