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
        preferences.edit().putString(key, string).commit();
    }
    private void saveInt(String key, int num){
        preferences.edit().putInt(key, num).commit();
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

    public void saveRegNo(String regno){ saveString("REGNO", regno);}

    public void saveDob(int[] dob){for(int i = 0; i < 3; i++) saveInt("DOB"+i, dob[i]);}

    public void saveCampus(Boolean isVellore){preferences.edit().putBoolean("isVellore",isVellore).commit();}

    public String getRegNo(){
        return preferences.getString("REGNO", "");
    }

    public int[] getDOB(){int[] dob = new int[3]; for(int i = 0; i < 3; i++)dob[i] = preferences.getInt("DOB"+i, 0); return dob;}

    public String getDOBString(){int[] dob = getDOB(); return Integer.toString(dob[0]) + Integer.toString(dob[1]) + Integer.toString(dob[2]);}

    public boolean isVellore(){return preferences.getBoolean("isVellore", true);}

    public String getTimeTable(){return preferences.getString("TIMETABLEJSON", "");}
}
