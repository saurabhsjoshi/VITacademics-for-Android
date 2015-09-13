/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 * Copyright (C) 2015  Darshan Mehta <darshanmehta17@gmail.com>
 *
 * This file is part of VITacademics.
 *
 * VITacademics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VITacademics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VITacademics.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.customwidget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.utility.DateTimeCalender;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.joda.time.ReadablePeriod;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class ScheduleView extends RelativeLayout {

    private TimeLineView timeLineView;

    private CustomTextView tvTime;
    private CustomTextView tvTimeAMPM;
    private CustomTextView tvCourseName;
    private CustomTextView tvVenue;

    private NumberProgressBar progressAttendance;


    private void initialize(){
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.schedule_widget, this, true);

        timeLineView = (TimeLineView) findViewById(R.id.timeline);

        tvTime = (CustomTextView) findViewById(R.id.tvTime);
        tvTimeAMPM = (CustomTextView) findViewById(R.id.tvTimeAMPM);
        tvCourseName = (CustomTextView) findViewById(R.id.tvCourseName);
        tvVenue= (CustomTextView) findViewById(R.id.tvVenue);

        progressAttendance = (NumberProgressBar) findViewById(R.id.progressAttendance);
        progressAttendance.setProgressTextColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
        progressAttendance.setReachedBarColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
        progressAttendance.setMax(100);
//        progressBar.setProgressTextSize(12*3);
        progressAttendance.setProgress(0);
    }

    public int getAttendance(){
        return progressAttendance.getProgress();
    }

    public void setAttendance(int attendance){
        ObjectAnimator animation = ObjectAnimator.ofInt(progressAttendance, "progress", attendance);
        animation.setDuration(1500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        if(attendance >= 75){
            progressAttendance.setReachedBarColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
            progressAttendance.setProgressTextColor(ContextCompat.getColor(getContext(), R.color.text_secondary));
        }else{
            progressAttendance.setReachedBarColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
            progressAttendance.setProgressTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
        }
    }

    public void setCourseName(String courseName){
        tvCourseName.setText(courseName);
    }

    public String getCourseName(){
        return tvCourseName.getText().toString();
    }

    public void setTime(String time){
        String timeHeader = time.substring(0, time.length() - 3);
        String AMPM = time.substring(time.length() - 2);

        if(timeHeader.length() == 4){
            timeHeader = "0" + timeHeader;
        }
        compareTimeAndUpdate(time);
        tvTime.setText(timeHeader);
        tvTimeAMPM.setText(AMPM.toUpperCase());
    }

    private void compareTimeAndUpdate(String time) {

        // Formatting strings to get the nearest hours
        String AMPM = time.substring(time.length() - 2);
        String timeHeader = time.substring(0, time.length() - 3);

        if(timeHeader.length() == 4){
            timeHeader = "0" + timeHeader;
        }

        String hour = timeHeader.substring(0,2);

        int hr = Integer.parseInt(hour);

        if(AMPM.compareToIgnoreCase("PM") == 0 && hr != 12){
            hr += 12;
        }

        // TODO fix timezone

        String floorHour = hr + ":00";
        String ceilHour = hr + ":45";

        // Comparing time
        LocalTime nowTime = LocalTime.now(DateTimeZone.UTC);

        // To correct the timezone difference
        Log.d("SCHEDULE_VIEW", "Now Time 1: " + nowTime);
        nowTime.plusHours(5);
        Log.d("SCHEDULE_VIEW", "Now Time 2: " + nowTime);
        nowTime.plusMinutes(30);

        LocalTime floorTime = new LocalTime(floorHour);
        LocalTime ceilTime = new LocalTime(ceilHour);

        boolean lowerCheck = nowTime.isAfter(floorTime) || nowTime.isEqual(floorTime);
        boolean upperCheck = nowTime.isBefore(ceilTime);
        boolean upperOverCheck = nowTime.isAfter(ceilTime) || nowTime.isEqual(ceilTime);

        if(lowerCheck && upperCheck){
            setState(TimeLineView.STATE_CURRENT);
            Log.d("SCHEDULE_VIEW", "Now Time: " + nowTime);
            Log.d("SCHEDULE_VIEW", "Floor Time: " + floorTime);
            Log.d("SCHEDULE_VIEW", "Ceil Time: " + ceilTime);
        }else if(lowerCheck && upperOverCheck){
            setState(TimeLineView.STATE_FINISHED);
        }else {
            setState(TimeLineView.STATE_SCHEDULED);
        }
    }

    public void setVenue(String venue){
        tvVenue.setText(venue);
    }

    public String getVenue(){
        return tvVenue.getText().toString();
    }

    public void setValues(String courseName, String venue, String time, int attendance){
        tvCourseName.setText(courseName);
        tvVenue.setText(venue);
        setTime(time);
        setAttendance(attendance);
    }

    public void setState(int state){
        timeLineView.setState(state);
    }

    public void setType(int type){
        timeLineView.setType(type);
    }

    public ScheduleView(Context context) {
        super(context);
        initialize();
    }

    public ScheduleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

}
