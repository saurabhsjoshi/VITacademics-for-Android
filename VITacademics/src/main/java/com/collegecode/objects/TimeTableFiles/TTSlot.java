package com.collegecode.objects.TimeTableFiles;

import java.util.Calendar;

/**
 * Created by saurabh on 4/25/14.
 */
public class TTSlot{
    public Calendar frm_time;
    public Calendar to_time;
    public String slt, clsnbr;
    public String venue;
    public boolean isLab = false;

    public TTSlot(String slt , String clsnbr) {
        this.slt = slt;
        this.clsnbr = clsnbr;
    }

    public void setTime(int index){
        frm_time = Calendar.getInstance();
        to_time = Calendar.getInstance();

        if(slt.charAt(0) == 'l')
            isLab = true;

        switch (index){
            case 0:
                frm_time = set_hour_min(frm_time, 8, 0);
                to_time = set_hour_min(to_time, 8, 50);
                break;
            case 1:
                frm_time = set_hour_min(frm_time, 9, 0);
                to_time = set_hour_min(to_time, 9, 50);
                break;
            case 2:
                frm_time = set_hour_min(frm_time, 10, 0);
                to_time = set_hour_min(to_time, 10, 50);
                break;
            case 3:
                frm_time = set_hour_min(frm_time, 11, 0);
                to_time = set_hour_min(to_time, 11, 50);
                break;
            case 4:
                if(isLab)
                {
                    frm_time = set_hour_min(frm_time, 11, 50);
                    to_time = set_hour_min(to_time, 12, 40);
                }
                else
                {
                    frm_time = set_hour_min(frm_time, 12, 0);
                    to_time = set_hour_min(to_time, 12, 50);
                }
                break;
            case 5:
                frm_time = set_hour_min(frm_time, 12, 40);
                to_time = set_hour_min(to_time, 13, 30);
                break;
            case 6:
                frm_time = set_hour_min(frm_time, 14, 0);
                to_time = set_hour_min(to_time, 14, 50);
                break;
            case 7:
                frm_time = set_hour_min(frm_time, 15, 0);
                to_time = set_hour_min(to_time, 15, 50);
                break;
            case 8:
                frm_time = set_hour_min(frm_time, 16, 0);
                to_time = set_hour_min(to_time, 16, 50);
                break;
            case 9:
                frm_time = set_hour_min(frm_time, 17, 0);
                to_time = set_hour_min(to_time, 17, 50);
                break;
            case 10:
                if(isLab)
                {
                    frm_time = set_hour_min(frm_time, 17, 50);
                    to_time = set_hour_min(to_time, 18, 40);
                }
                else
                {
                    frm_time = set_hour_min(frm_time, 18, 0);
                    to_time = set_hour_min(to_time, 18, 50);
                }
                break;
            case 11:
                frm_time = set_hour_min(frm_time, 18, 40);
                to_time = set_hour_min(to_time, 19, 30);
                break;

        }
    }

    private Calendar set_hour_min(Calendar t, int hour, int min){
        t.set(Calendar.HOUR_OF_DAY, hour);
        t.set(Calendar.MINUTE, min);
        return t;
    }
}
