package com.collegecode.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public String getDOBString(){int[] dob = getDOB(); return check_dob(dob[0]) + check_dob(dob[1] + 1) + Integer.toString(dob[2]);}

    public boolean isVellore(){return preferences.getBoolean("isVellore", true);}

    public String getJSON(){return preferences.getString("ATTENDANCEJSON","");}

    public String getTimeTable(){return  preferences.getString("TIMETABLEJSON", "");}

    public int getDefUi(){return Integer.parseInt(preferences.getString("defUi","0"));}

    private String check_dob(int num){
        String t = Integer.toString(num);
        if(t.length() == 1)
            t = "0"+t;
        return t;
    }

    public ArrayList<Subject> getAllSubjects(){
        ArrayList<Subject> subs = new ArrayList<Subject>();
        Subject att;
        try{
            JSONArray root = new JSONArray(getJSON());
            JSONObject sub;

            for(int i = 0; i < root.length(); i++){
                sub = root.getJSONObject(i);
                att = new Subject();
                att.code = sub.getString("code");
                att.title = sub.getString("title");
                att.type = sub.getString("type");
                att.slot = sub.getString("slot");
                att.attended = Integer.parseInt(sub.getString("attended"));
                att.conducted = Integer.parseInt(sub.getString("conducted"));

                att.percentage = (int) getPer(att.attended, att.conducted);

                if (getPer(att.attended,att.conducted) > att.percentage)
                    att.percentage += 1;

                att.regdate = sub.getString("regdate");
                att.classnbr = sub.getString("classnbr");
                //No need to parse this here
                att.detailsString = sub.getJSONArray("details").toString();
                att.putAttendanceDetails();
                subs.add(att);
            }
        }catch (Exception e){e.printStackTrace();}
        return subs;
    }
    public Subject getSubject(String clsnbr){
        Subject att = new Subject();

        try {
            JSONArray root = new JSONArray(getJSON());
            JSONObject sub;
            String temp;

            for(int i = 0; i < root.length(); i++){
                sub = root.getJSONObject(i);
                temp = sub.getString("classnbr");
                if(temp.equals(clsnbr)){
                    att.code = sub.getString("code");
                    att.title = sub.getString("title");
                    att.type = sub.getString("type");
                    att.slot = sub.getString("slot");
                    att.attended = Integer.parseInt(sub.getString("attended"));
                    att.conducted = Integer.parseInt(sub.getString("conducted"));

                    att.percentage = (int) getPer(att.attended, att.conducted);

                    if (getPer(att.attended,att.conducted) > att.percentage)
                        att.percentage += 1;

                    att.regdate = sub.getString("regdate");
                    att.classnbr = sub.getString("classnbr");
                    //No need to parse this here
                    att.detailsString = sub.getJSONArray("details").toString();
                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}

        return att;
    }

    public boolean isNewUser(){return preferences.getBoolean("NewUser", true);}

    public void setNewUser(boolean bol){preferences.edit().putBoolean("NewUser", bol).commit();}

    public static float getPer(int num, int div)
    {
        return (float)((int)(((float)num/div)*1000))/10;
    }

    public static int getPerColor(int per){
        if (per < 80 && per >= 75)
            return Color.parseColor("#ffa500");
        else if (per < 75)
            return Color.parseColor("#ff0000");
        else
            return Color.parseColor("#008000");
    }

}
