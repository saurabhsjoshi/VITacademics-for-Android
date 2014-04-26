package com.collegecode.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by saurabh on 4/22/14.
 */
public class DataHandler {
    Context context;
    SharedPreferences preferences;

    String temp_TT = "{\"timetable\": {\"Friday\": [0, 1025, 1228, 0, 0, 0, 0, 0, 0, 0, 0, 0], \"Tuesday\": [1025, 1228, 0, 1015, 1022, 0, 0, 0, 0, 0, 0, 0], \"Thursday\": [0, 1015, 1022, 1988, 0, 0, 4183, 4183, 1117, 0, 0, 0], \"Wednesday\": [1988, 1022, 0, 0, 1228, 0, 0, 1117, 0, 0, 0, 0], \"Monday\": [1015, 1022, 1988, 0, 0, 0, 0, 1117, 0, 0, 0, 0]}, \"dob\": \"19091992\", \"subjects\": [{\"slot\": \"F1+TF1\", \"bl\": \"CBL\", \"code\": \"ECE102\", \"faculty\": \"GOVARDHAN K - SENSE\", \"ltpc\": \"3 1 0 4\", \"slno\": \"1\", \"cnum\": \"1022\", \"title\": \"Fundamentals of Electrical Engineering\", \"venue\": \"TT726\", \"billdate\": \"Registered, Receipt Generated and Approved by Academics\"}, {\"slot\": \"A1+TA1\", \"bl\": \"CBL\", \"code\": \"ECE307\", \"faculty\": \"THANIKAISELVAN V - SENSE\", \"ltpc\": \"3 0 0 3\", \"slno\": \"2\", \"cnum\": \"1015\", \"title\": \"Information Theory and Coding\", \"venue\": \"TT620\", \"billdate\": \"Registered and Approved\"}, {\"slot\": \"F2\", \"bl\": \"CBL\", \"code\": \"ECE308\", \"faculty\": \"RAVI KUMAR C V - SENSE\", \"ltpc\": \"3 0 0 3\", \"slno\": \"3\", \"cnum\": \"1117\", \"title\": \"Computer Communication\", \"venue\": \"TT724\", \"billdate\": \"Registered and Approved\"}, {\"slot\": \"L49+L50\", \"bl\": \"LBC\", \"code\": \"ECE308\", \"faculty\": \"RAVI KUMAR C V - SENSE\", \"ltpc\": \"0 0 2 1\", \"slno\": \"-\", \"cnum\": \"4183\", \"title\": \"Computer Communication\", \"venue\": \"TT134\", \"billdate\": \"Registered and Approved\"}, {\"slot\": \"B1\", \"bl\": \"PBL\", \"code\": \"ECE401\", \"faculty\": \"JABEENA A - SENSE\", \"ltpc\": \"2 0 0 2\", \"slno\": \"4\", \"cnum\": \"1025\", \"title\": \"Optical Communication and Networks\", \"venue\": \"TT619\", \"billdate\": \"Registered and Approved\"}, {\"slot\": \"G1+TG1\", \"bl\": \"CBL\", \"code\": \"ECE405\", \"faculty\": \"SANGEETHA N - SENSE\", \"ltpc\": \"3 0 0 3\", \"slno\": \"5\", \"cnum\": \"1228\", \"title\": \"Satellite Communication\", \"venue\": \"TT304\", \"billdate\": \"Registered and Approved\"}, {\"slot\": \"C1\", \"bl\": \"CBL\", \"code\": \"HUM118\", \"faculty\": \"SELVAKUMAR D S - SSL\", \"ltpc\": \"3 0 0 3\", \"slno\": \"6\", \"cnum\": \"1988\", \"title\": \"Fundamentals of Cyber Laws\", \"venue\": \"TT305\", \"billdate\": \"Registered and Approved\"}]}";
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

    public String getJSON(){return preferences.getString("ATTENDANCEJSON","");}

    public String getTimeTable(){return  temp_TT;}//return preferences.getString("TIMETABLEJSON", ""); }

    public Subject getSubject(String clsnbr){
        Subject att = new Subject();

        try {
            JSONArray root = new JSONArray(getJSON());
            JSONObject sub;
            String temp;

            for(int i = 0; i < root.length(); i++){
                sub = root.getJSONObject(i);
                temp = sub.getString("clsnbr");
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
                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}

        return att;
    }

    static float getPer(int num, int div)
    {
        return (float)((int)(((float)num/div)*1000))/10;
    }

}
