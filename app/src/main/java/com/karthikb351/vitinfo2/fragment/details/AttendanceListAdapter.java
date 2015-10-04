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

package com.karthikb351.vitinfo2.fragment.details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Attendance;
import com.karthikb351.vitinfo2.contract.AttendanceDetail;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.utility.DateTimeCalender;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.AttendanceViewHolder> {

    private int layoutId;
    private Context context;
    private Course course;
    private Attendance attendance;
    private List<AttendanceDetail> attendanceDetails;

    private int conducted, attended, classLength, miss = 0, attend = 0;


    public AttendanceListAdapter(Context context, Course course) {
        this.context = context;
        this.course = course;
        if (course.getAttendance().isSupported()) {
            this.attendance = course.getAttendance();
            this.attendanceDetails = attendance.getDetails();
            this.conducted = attendance.getTotalClasses();
            this.attended = attendance.getAttendedClasses();
            if (!(this.attendanceDetails == null || this.attendanceDetails.isEmpty())) {
                Collections.reverse(this.attendanceDetails);
            }
        } else {
            this.attendanceDetails = new ArrayList<>();
            this.attendance = new Attendance(context.getString(R.string.registration_date_unavailable), 0, 0, 0, attendanceDetails, true);
        }

        classLength = course.getClassLength();
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new AttendanceViewHolder(view);
    }


    @SuppressWarnings("deprecation")
    private void setProgressBarDrawable(ProgressBar progressBar) {
        if (getPercentage() >= 80) {
            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.ring_attendance_green));
        } else if (getPercentage() >= 75 && getPercentage() < 80) {
            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.ring_attendance_orange));
        } else {
            progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.ring_attendance_red));
        }
    }

    private void updateHolder(AttendanceViewHolder holder) {
        holder.attendanceClasses.setText(context.getString(R.string.label_attendance_classes,
                attended + attend, conducted + attend + miss));

        holder.txtAttend.setText(context.getString(R.string.label_class_go, attend));
        holder.txtMiss.setText(context.getString(R.string.label_class_miss, miss));
        holder.attendancePercent.setText(Integer.toString(getPercentage()));
        holder.progressBarAttendance.setProgress(getPercentage());
        setProgressBarDrawable(holder.progressBarAttendance);
    }

    private int getPercentage() {
        return ((int) Math.ceil((double) (attended + attend) / (conducted + attend + miss) * 100));
    }

    @Override
    public void onBindViewHolder(final AttendanceViewHolder holder, int position) {
        if (position == 0) {

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == holder.attendPlus) {
                        attend += classLength;
                        if (!holder.attendMinus.isClickable())
                            holder.attendMinus.setClickable(true);
                    } else if (view == holder.attendMinus) {
                        if (attend == 0)
                            holder.attendMinus.setClickable(false);
                        else
                            attend -= classLength;
                    } else if (view == holder.missPlus) {
                        miss += classLength;
                        if (!holder.missMinus.isClickable())
                            holder.missMinus.setClickable(true);
                    } else if (view == holder.missMinus) {
                        if (miss == 0)
                            holder.missMinus.setClickable(false);
                        else
                            miss -= classLength;
                    }
                    updateHolder(holder);
                }
            };

            updateHolder(holder);

            holder.courseName.setText(course.getCourseTitle());
            holder.courseCode.setText(course.getCourseCode());

            holder.attendPlus.setOnClickListener(onClickListener);
            holder.attendMinus.setOnClickListener(onClickListener);
            holder.missPlus.setOnClickListener(onClickListener);
            holder.missMinus.setOnClickListener(onClickListener);

        } else {
            try {
                holder.date.setText(DateTimeCalender.parseISO8601Date(attendanceDetails.get(position - 1).getDate()));
            } catch (ParseException ex) {
                ex.printStackTrace();
                holder.date.setText(attendanceDetails.get(position - 1).getDate());
            }
            holder.detailStatus.setText(attendanceDetails.get(position - 1).getStatus());
            if(holder.detailStatus.getText().equals("Present"))
                holder.detailStatus.setTextColor(ColorStateList.valueOf(Color.rgb(0,135,0)));
            else if(holder.detailStatus.getText().equals("On Duty"))
                holder.detailStatus.setTextColor(ColorStateList.valueOf(Color.rgb(40,40,230)));
            else
                holder.detailStatus.setTextColor(ColorStateList.valueOf(Color.rgb(230, 40, 40)));
            holder.classUnits.setText(context.getString(R.string.attendance_class_units_earned,
                    attendanceDetails.get(position - 1).getClassUnits()));

            holder.reason.setText(attendanceDetails.get(position - 1).getReason());
        }
    }

    @Override
    public int getItemCount() {
        if (attendanceDetails == null || attendanceDetails.isEmpty()) {
            return 1;
        }
        return (attendanceDetails.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            layoutId = R.layout.card_attendance_summary;
        } else {
            layoutId = R.layout.card_attendance_detail;
        }
        return layoutId;
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {

        public TextView date, detailStatus, classUnits, reason, txtMiss, txtAttend;
        public TextView courseName, courseCode, attendanceClasses, attendancePercent;
        public ProgressBar progressBarAttendance;
        public Button attendPlus, attendMinus, missPlus, missMinus;

        public AttendanceViewHolder(View view) {
            super(view);
            attendPlus = (Button) view.findViewById(R.id.button_attend_plus);
            attendMinus = (Button) view.findViewById(R.id.button_attend_minus);
            missPlus = (Button) view.findViewById(R.id.button_miss_plus);
            missMinus = (Button) view.findViewById(R.id.button_miss_minus);
            txtMiss = (TextView) view.findViewById(R.id.txt_miss);
            txtAttend = (TextView) view.findViewById(R.id.txt_attend);
            date = (TextView) view.findViewById(R.id.date);
            detailStatus = (TextView) view.findViewById(R.id.detail_status);
            classUnits = (TextView) view.findViewById(R.id.class_units);
            reason = (TextView) view.findViewById(R.id.reason);
            courseName = (TextView) view.findViewById(R.id.course_name);
            courseCode = (TextView) view.findViewById(R.id.course_code);
            attendanceClasses = (TextView) view.findViewById(R.id.attendance_classes);
            attendancePercent = (TextView) view.findViewById(R.id.attendance_percent);
            progressBarAttendance = (ProgressBar) view.findViewById(R.id.progress_bar_attendance);
        }
    }
}
