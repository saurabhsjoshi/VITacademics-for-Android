package com.collegecode.objects;

import android.content.ContentValues;
import android.content.Context;
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

                values.put(KEY_CLASNBR, sub.getString("classnbr"));
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

    public Subject getSubject(String clsnbr){
        return null;
    }

    public int getSubjectsCount(String subject){
        return 0;
    }

    public ArrayList<Subject> getAllSubjects(){
        return null;
    }

}
