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

package com.karthikb351.vitinfo2.fragment.schedule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;
import com.karthikb351.vitinfo2.customwidget.ScheduleView;
import com.karthikb351.vitinfo2.customwidget.TimeLineView;
import com.karthikb351.vitinfo2.utility.DateTimeCalender;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ScheduleViewHolder> {

    private RecyclerViewOnClickListener<Course> onClickListener;
    private List<Pair<Course, Timing>> courseTimingPairs;
    private Context context;

    public ScheduleListAdapter(Context context, List<Pair<Course, Timing>> courseTimingPairs) {
        this.context = context;
        this.courseTimingPairs = courseTimingPairs;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleViewHolder(LayoutInflater.from(context).inflate(R.layout.schedule_day_widget, parent, false));
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(ScheduleViewHolder scheduleViewHolder, int position) {

        int attendancePercentage = 0;
        String startTime;
        String endTime;
        int day = 0;

        if (courseTimingPairs.get(position).first.getAttendance().isSupported()) {
            attendancePercentage = courseTimingPairs.get(position).first.getAttendance().getAttendancePercentage();
        }

        try {
            startTime = DateTimeCalender.parseISO8601Time(courseTimingPairs.get(position).second.getStartTime());
            endTime = DateTimeCalender.parseISO8601Time(courseTimingPairs.get(position).second.getEndTime());
            day = courseTimingPairs.get(position).second.getDay();
        } catch (ParseException ex) {
            ex.printStackTrace();
            startTime = courseTimingPairs.get(position).second.getStartTime();
            endTime = courseTimingPairs.get(position).second.getEndTime();
        }

        scheduleViewHolder.scheduleView.setValues(courseTimingPairs.get(position).first.getCourseTitle(),
                courseTimingPairs.get(position).first.getVenue(),
                startTime, attendancePercentage);

        if(position == 0){
            scheduleViewHolder.scheduleView.setType(TimeLineView.TYPE_INITIAL);
        }
        if(position == courseTimingPairs.size() - 1){
            scheduleViewHolder.scheduleView.setType(TimeLineView.TYPE_END);
        }

        updateTimeSign(scheduleViewHolder.scheduleView, day, startTime, endTime);

//        scheduleViewHolder.courseCode.setText(courseTimingPairs.get(position).first.getCourseCode());
//        scheduleViewHolder.courseName.setText();
//        scheduleViewHolder.venue.setText();
//        scheduleViewHolder.slot.setText(courseTimingPairs.get(position).first.getSlot());
//        scheduleViewHolder.attendance.setText();
//        scheduleViewHolder.slotTiming.setText(context.getString(R.string.timetable_course_slot_timing, startTime, endTime));
//        scheduleViewHolder.progressBarAttendance.setProgress(attendancePercentage);
//
//        int sdk = android.os.Build.VERSION.SDK_INT;
//        int bgColor = getAttendanceColor(attendancePercentage);
//
//        scheduleViewHolder.progressBarAttendance.getProgressDrawable().setColorFilter(bgColor, PorterDuff.Mode.SRC_IN);
//        GradientDrawable txt_bgShape;
//        txt_bgShape = (GradientDrawable) scheduleViewHolder.attendance.getBackground();
//        txt_bgShape.setColor(bgColor);
//
//        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            scheduleViewHolder.attendance.setBackgroundDrawable(txt_bgShape);
//        } else {
//            scheduleViewHolder.attendance.setBackground(txt_bgShape);
//        }
    }

    private void updateTimeSign(ScheduleView scheduleView, int day, String startTime, String endTime) {
        int calenderDay = getEquivalentDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));

        if(day == calenderDay){
            compareTimeAndUpdate(scheduleView, startTime, endTime);
        }else if(day < calenderDay){
            scheduleView.setState(TimeLineView.STATE_FINISHED);
        }

    }

    private void compareTimeAndUpdate(ScheduleView scheduleView, String startTime, String endTime) {

        // Formatting strings to get the nearest hours
        startTime = formatTime(startTime);
        endTime = formatTime(endTime);

        // Comparing time
        LocalTime nowTime = LocalTime.now(DateTimeZone.UTC);
        LocalTime floorTime = new LocalTime(startTime);
        LocalTime ceilTime = new LocalTime(endTime);

        // To correct the timezone difference
        nowTime = nowTime.plusHours(5);
        nowTime = nowTime.plusMinutes(30);

        boolean lowerCheck = nowTime.isAfter(floorTime) || nowTime.isEqual(floorTime);
        boolean upperCheck = nowTime.isBefore(ceilTime);
        boolean upperOverCheck = nowTime.isAfter(ceilTime) || nowTime.isEqual(ceilTime);

        if(lowerCheck && upperCheck){
            scheduleView.setState(TimeLineView.STATE_CURRENT);
        }else if(lowerCheck && upperOverCheck){
            scheduleView.setState(TimeLineView.STATE_FINISHED);
        }else {
            scheduleView.setState(TimeLineView.STATE_SCHEDULED);
        }
    }

    private String formatTime(String time) {
        String AMPM = time.substring(time.length() - 2);
        String timeHeader = time.substring(0, time.length() - 3);

        if(timeHeader.length() == 4){
            timeHeader = "0" + timeHeader;
        }

        String hour = timeHeader.substring(0, 2);
        String minutes = timeHeader.substring(3, 5);

        int hr = Integer.parseInt(hour);
        int mins = Integer.parseInt(minutes);

        if(AMPM.compareToIgnoreCase("PM") == 0 && hr != 12){
            hr += 12;
        }else if(AMPM.compareToIgnoreCase("AM") == 0 && hr == 12){
            hr = 0;
        }

        if(mins == 0){
            if(hr != 0){
                hr--;
            }
            mins = 50;
        }
        
        time = hr + ":" + mins;

        if(time.length() == 4){
            time = "0" + time;
        }

        return time;
    }

    private int getEquivalentDay(int day) {
        switch (day){
            case Calendar.SUNDAY:
                day = 6;
                break;
            case Calendar.MONDAY:
            case Calendar.TUESDAY:
            case Calendar.WEDNESDAY:
            case Calendar.THURSDAY:
            case Calendar.FRIDAY:
            case Calendar.SATURDAY:
                day -= 2;
                break;
            default: day = 0;
        }
        return day;
    }

    public void setOnclickListener(RecyclerViewOnClickListener<Course> onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return courseTimingPairs.size();
    }


    public class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ScheduleView scheduleView;

        public ScheduleViewHolder(View view) {
            super(view);

            scheduleView = (ScheduleView) view.findViewById(R.id.scheduleView);

            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Course course = courseTimingPairs.get(getAdapterPosition()).first;
            onClickListener.onItemClick(course);
        }
    }
}
