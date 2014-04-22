package com.collegecode.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by saurabh on 4/22/14.
 */
public class DataHandler {
    Context context;
    SharedPreferences preferences;

    public DataHandler(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private void saveString(String key, String string){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, string);
        editor.commit();
    }

    public void saveJSON(String jsonInput){
        jsonInput = jsonInput.substring(jsonInput.indexOf('%')+1);
        saveString("ATTENDANCEJSON", jsonInput);
    }

    public void saveMarks(String marks){
        saveString("MARKSJSON", marks);
    }

    public void saveTimeTable(String tt){
        saveString("TIMETABLEJSON", tt);
    }

    public String getRegNo(){
        return "11BEC0262";
    }

    public String getDOB(){
        return "19091992";
    }

    public boolean isVellore(){
        return true;
    }
}
