package com.collegecode.objects.TimeTableFiles;

import java.util.Calendar;

/**
 * Created by saurabh on 4/25/14.
 */
public class TTSlot{
    public Calendar frm_time;
    public Calendar to_time;
    public String slt, clsnbr;

    public boolean isLab = false;

    public TTSlot(String slt , String clsnbr) {
        this.slt = slt;
        this.clsnbr = clsnbr;
    }

    public void setTime(int index){
        frm_time = Calendar.getInstance();
        to_time = Calendar.getInstance();

        //frm_time.set(Calendar.HOUR_OF_DAY, 8);

    }
}
