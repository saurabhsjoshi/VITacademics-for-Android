package com.collegecode.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by saurabh on 4/22/14.
 */
public class DataHandler {
    public Context context;
    SharedPreferences preferences;

    public DataHandler(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void DELETE_ALL_DATA(Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
        context.deleteDatabase("subjectsManager");
    }

    private void saveString(String key, String string){
        preferences.edit().putString(key, string).commit();
    }

    private void saveInt(String key, int num){
        preferences.edit().putInt(key, num).commit();
    }

    public void saveJSON(String jsonInput){
        jsonInput = jsonInput.substring(jsonInput.indexOf('%')+1);
        sqDatabase db = new sqDatabase(context);
        db.addSubjects(jsonInput);
    }

    public void addPushMessage(PushMessage pm){
        int cur = preferences.getInt("PUSHMESSAGEJSONSIZE", 0);
        Gson gson = new Gson();

        String t = gson.toJson(pm);
        saveString("PUSHMESSAGEJSON"+cur,t);

        saveInt("PUSHMESSAGEJSONSIZE", cur + 1);
    }

    public void savePushMessages(ArrayList<PushMessage> msgs){
        Gson gson = new Gson();
        for(int i = 0; i < msgs.size(); i++)
            saveString("PUSHMESSAGEJSON"+i, gson.toJson(msgs));

        saveInt("PUSHMESSAGEJSONSIZE", msgs.size());
    }

    public void addFriend(Friend f){
        int cur = preferences.getInt("FRIENDJSONSIZE", 0);
        Gson gson = new Gson();
        String t = gson.toJson(f);
        saveString("FRIENDJSON"+cur ,t);
        cur += 1;
        saveInt("FRIENDJSONSIZE", cur);
    }

    public void saveMarks(String marks){
        saveString("MARKSJSON", marks);
    }

    public void saveviewShowCase(Boolean shown){preferences.edit().putBoolean("ViewShowCase",shown).commit();}

    public void saveviewShowCaseFrnd(Boolean shown){preferences.edit().putBoolean("ViewShowCaseFrnd",shown).commit();}

    public void saveTimeTable(String tt){
        saveString("TIMETABLEJSON", tt);
    }

    public void saveRegNo(String regno){ saveString("REGNO", regno.trim());}

    public void saveDob(int[] dob){for(int i = 0; i < 3; i++) saveInt("DOB"+i, dob[i]);}

    public void saveCampus(Boolean isVellore){preferences.edit().putBoolean("isVellore",isVellore).commit();}

    public void setFbLogin(Boolean isFbLogin){preferences.edit().putBoolean("isFbLogin", isFbLogin).commit();}

    public void saveToken(String json){saveString("PINJSON", json);}

    public void saveFriends(ArrayList<Friend> friends){
        Gson gson = new Gson();
        String t;
        for(int i = 0 ; i < friends.size(); i++){
            t = gson.toJson(friends.get(i));
            saveString("FRIENDJSON" + i , t);
        }
        saveInt("FRIENDJSONSIZE", friends.size());
    }

    public String getRegNo(){
        return preferences.getString("REGNO", "").toUpperCase();
    }

    public boolean getviewShowCase(){return  preferences.getBoolean("ViewShowCase",false);}

    public boolean getviewShowCaseFrnd(){return  preferences.getBoolean("ViewShowCaseFrnd",false);}

    public int[] getDOB(){int[] dob = new int[3]; for(int i = 0; i < 3; i++)dob[i] = preferences.getInt("DOB"+i, 0); return dob;}

    public String getDOBString(){int[] dob = getDOB(); return check_dob(dob[0]) + check_dob(dob[1] + 1) + Integer.toString(dob[2]);}

    public boolean isVellore(){return preferences.getBoolean("isVellore", true);}

    public boolean isFacebookLogin(){return preferences.getBoolean("isFbLogin", false);}

    public String getTimeTable(){return  preferences.getString("TIMETABLEJSON", "");}

    public String getMarks(){return  preferences.getString("MARKSJSON","");}

    public int getDefUi(){return Integer.parseInt(preferences.getString("defUi","0"));}

    public ArrayList<Friend> getFreinds(){
        ArrayList<Friend> t = new ArrayList<Friend>();
        int size = preferences.getInt("FRIENDJSONSIZE", 0);

        Gson gson = new Gson();

        for(int i = 0; i < size; i++){
            t.add(gson.fromJson(preferences.getString("FRIENDJSON"+i,""), Friend.class));
            t.get(i).img_profile = null;
        }

        return t;
    }

    public String getTokenExpiryTimeString(){
        try {
            JSONObject obj = new JSONObject(preferences.getString("PINJSON",""));
            String dt = obj.getString("expiry");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Date expiry_date =  df.parse(dt);

            return "Expires " + DateUtils.getRelativeTimeSpanString(expiry_date.getTime(), new Date().getTime() , 0).toString();
        }catch (Exception ignore){}
        return "";
    }

    public String getToken(){
        try {
            JSONObject obj = new JSONObject(preferences.getString("PINJSON",""));
            String dt = obj.getString("expiry");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Date expiry_date =  df.parse(dt);

            if(expiry_date.after(new Date())){
                return obj.getString("token");
            }
        }catch (Exception ignore){}
        return "expired";
    }

    private String check_dob(int num){
        String t = Integer.toString(num);
        if(t.length() == 1)
            t = "0"+t;
        return t;
    }

    public ArrayList<Subject> getAllSubjects(){

        sqDatabase sq = new sqDatabase(context);
        return sq.getAllSubjects();
    }
    public Subject getSubject(String clsnbr){
        sqDatabase db = new sqDatabase(context);
        Subject s = db.getSubject(clsnbr);
        s.marksJSON = getMarks();
        return s;
    }

    public ArrayList<PushMessage> getAllPushMessage(){
        ArrayList<PushMessage> temp = new ArrayList<PushMessage>();
        try{

            Gson gson = new Gson();
            int size = preferences.getInt("PUSHMESSAGEJSONSIZE",0);

            for(int i = 0; i < size; i++){
                PushMessage p = gson.fromJson("PUSHMESSAGEJSON"+i,PushMessage.class);
                temp.add(p);
            }

        }catch (Exception e){e.printStackTrace();}

        return temp;
    }

    public void deletePushMessage(PushMessage msg){
        ArrayList<PushMessage> temp = getAllPushMessage();
        for(int i = 0 ; i < temp.size(); i++){
            if(msg.title.equals(temp.get(i).title) && msg.message.equals(temp.get(i).message))
            {
                temp.remove(i);
                break;
            }
        }
        savePushMessages(temp);

    }

    public void deleteFriend(Friend f){
        try {
            ArrayList<Friend> t = getFreinds();

            for(int i = 0; i < t.size(); i++){
                if(f.regno.equals(t.get(i).regno)){
                    t.remove(i);
                    break;
                }
            }
            saveFriends(t);

        }catch (Exception e){e.printStackTrace();}
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
