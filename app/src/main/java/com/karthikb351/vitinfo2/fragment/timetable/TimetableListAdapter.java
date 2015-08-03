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

package com.karthikb351.vitinfo2.fragment.timetable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;
import com.karthikb351.vitinfo2.utility.DateTimeCalender;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;

import java.text.ParseException;
import java.util.List;

public class TimetableListAdapter extends RecyclerView.Adapter<TimetableListAdapter.TimeTableViewHolder> {


    private RecyclerViewOnClickListener<Course> onClickListener;
    private List<Pair<Course, Timing>> courseTimingPairs;
    private Context context;

    TimetableListAdapter(Context context, List<Pair<Course, Timing>> courseTimingPairs) {
        this.context = context;
        this.courseTimingPairs = courseTimingPairs;
    }

    @Override
    public TimeTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView rootcard = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(R.layout.card_timetable_course, parent, false);
        return new TimeTableViewHolder(rootcard);
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public void onBindViewHolder(TimeTableViewHolder timeTableViewHolder, int position) {

        int attendancePercentage = 0;
        String startTime;
        String endTime;

        if (courseTimingPairs.get(position).first.getAttendance().isSupported()) {
            attendancePercentage = courseTimingPairs.get(position).first.getAttendance().getAttendancePercentage();
        }
        try {
            startTime = DateTimeCalender.parseISO8601Time(courseTimingPairs.get(position).second.getStartTime());
            endTime = DateTimeCalender.parseISO8601Time(courseTimingPairs.get(position).second.getEndTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
            startTime = courseTimingPairs.get(position).second.getStartTime();
            endTime = courseTimingPairs.get(position).second.getEndTime();
        }

        timeTableViewHolder.courseCode.setText(courseTimingPairs.get(position).first.getCourseCode());
        timeTableViewHolder.courseName.setText(courseTimingPairs.get(position).first.getCourseTitle());
        timeTableViewHolder.venue.setText(courseTimingPairs.get(position).first.getVenue());
        timeTableViewHolder.slot.setText(courseTimingPairs.get(position).first.getSlot());
        timeTableViewHolder.attendance.setText(Integer.toString(attendancePercentage));
        timeTableViewHolder.slotTiming.setText(context.getString(R.string.timetable_course_slot_timing, startTime, endTime));
        timeTableViewHolder.progressBarAttendance.setProgress(attendancePercentage);

        int sdk = android.os.Build.VERSION.SDK_INT;
        int bgColor = getAttendanceColor(attendancePercentage);

        timeTableViewHolder.progressBarAttendance.getProgressDrawable().setColorFilter(bgColor, PorterDuff.Mode.SRC_IN);
        GradientDrawable txt_bgShape;
        txt_bgShape = (GradientDrawable) timeTableViewHolder.attendance.getBackground();
        txt_bgShape.setColor(bgColor);

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            timeTableViewHolder.attendance.setBackgroundDrawable(txt_bgShape);
        } else {
            timeTableViewHolder.attendance.setBackground(txt_bgShape);
        }
    }

    public void setOnclickListener(RecyclerViewOnClickListener<Course> onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return courseTimingPairs.size();
    }

    private int getAttendanceColor(int attendance) {
        if (attendance >= 80) {
            return context.getResources().getColor(R.color.highAttend);
        } else if (attendance >= 75 && attendance < 80) {
            return context.getResources().getColor(R.color.midAttend);
        } else {
            return context.getResources().getColor(R.color.lowAttend);
        }
    }

    public class TimeTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView courseName, courseCode, attendance, slot, venue, slotTiming;
        public ProgressBar progressBarAttendance;

        public TimeTableViewHolder(View view) {
            super(view);

            courseName = (TextView) view.findViewById(R.id.course_name);
            courseCode = (TextView) view.findViewById(R.id.course_code);
            attendance = (TextView) view.findViewById(R.id.attendance);
            slot = (TextView) view.findViewById(R.id.slot);
            venue = (TextView) view.findViewById(R.id.venue);
            slotTiming = (TextView) view.findViewById(R.id.slot_timing);
            progressBarAttendance = (ProgressBar) view.findViewById(R.id.progress_bar_attendance);
            progressBarAttendance.setMax(100);

            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Course course = courseTimingPairs.get(getAdapterPosition()).first;
            onClickListener.onItemClick(course);
        }
    }
}
