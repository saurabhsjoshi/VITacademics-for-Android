package com.collegecode.objects;

/**
 * Created by saurabh on 4/24/14.
 */
public class Attendance {
    String date,status;

    public Attendance(String d, String s) {
        super();
        date=d;
        status=s;
    }

    public String getDate() {return date;}

    public String getStatus() {return status;}
}
