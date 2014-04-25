package com.collegecode.objects.TimeTableFiles;

import android.content.Context;

import com.collegecode.objects.DataHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TimeTable{

    DataHandler dat;

    public TimeTable(Context context){
        dat = new DataHandler(context);

    }

    public ArrayList<TTSlot> getTT(int Day) throws Exception{

        ArrayList<TTSlot> today = new ArrayList<TTSlot>();
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, Day);

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            JSONObject root = new JSONObject(dat.getTimeTable());

            root = root.getJSONObject("timetable");

            JSONArray json_slts = root.getJSONArray(dayFormat.format(calendar.getTime()));

            for(int i = 0; i < json_slts.length(); i++){
                if(json_slts.getInt(i) != 0){

                }
            }


        }catch (Exception e){}


        return today;

    }
}