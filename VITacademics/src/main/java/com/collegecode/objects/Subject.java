package com.collegecode.objects;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by saurabh on 4/24/14.
 */
public class Subject {

    public String title, code, type, slot, regdate, classnbr,atten_last_status,atten_last_date, detailsString;
    public int attended, conducted , percentage;
    public int attendance_length = 0;
    public boolean att_valid = false;
    public Attendance attendance[]=null;

    public void loadMarks(){
        /* TODO: Need to add marks handling */
    }

    public void putAttendanceDetails() {
        try {

            JSONArray j = new JSONArray(detailsString);
            int l = j.length();

            attendance = new Attendance[l/2];

            int c = l-1;

            for(int i=(l/2)-1; i>=0; i--)
            {
                String s,d;
                s = j.getString(c--);
                d = j.getString(c--);
                attendance[attendance_length] = new Attendance(getDay(d),s);
                attendance_length++;
                att_valid=true;
            }
        } catch (Exception e) {
            att_valid=false;
            e.printStackTrace();
        }
        if(att_valid)
        {
            atten_last_status=attendance[0].status;
            atten_last_date=attendance[0].date;
        }

    }

    String getDay(String date)
    {
        String dd=date.substring(0, date.indexOf('-')),mm=date.substring(date.indexOf('-')+1, date.lastIndexOf('-')),yy=date.substring(date.lastIndexOf('-')+1, date.length()-1);
        String pattern="dd-MMM-yyyy", newPattern="EEEE, dd-MMM", result="Error";
        SimpleDateFormat df= new SimpleDateFormat(pattern);
        try {
            Date d=df.parse(date);
            df= new SimpleDateFormat(newPattern);
            result=df.format(d);
        } catch (ParseException e) {
            result="error";
            e.printStackTrace();
        }
        return result;
    }
}
