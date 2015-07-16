/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 *
 * This file is part of VITacademics.
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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public AttendanceListAdapter(Context context, Course course) {
        this.context = context;
        this.course = course;
        if (course.getAttendance().isSupported()) {
            this.attendance = course.getAttendance();
            this.attendanceDetails = attendance.getDetails();
            Collections.reverse(this.attendanceDetails);
        } else {
            this.attendanceDetails = new ArrayList<>();
            this.attendance = new Attendance(context.getString(R.string.registration_date_unavailable), 0, 0, 0, attendanceDetails, true);
        }
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        if (position == 0) {
            holder.courseName.setText(course.getCourseTitle());
            holder.courseCode.setText(course.getCourseCode());
            holder.courseRoom.setText(course.getVenue());
            holder.courseSlot.setText(course.getSlot());
            holder.attendanceClasses.setText(context.getString(R.string.label_attendance_classes, attendance.getAttendedClasses(), attendance.getTotalClasses()));
            holder.attendancePercent.setText(Integer.toString(attendance.getAttendancePercentage()));
            try {
                holder.registeredOn.setText(context.getString(R.string.course_registered_on, DateTimeCalender.parseISO8601DateTime(attendance.getRegistrationDate())));
            } catch (ParseException ex) {
                ex.printStackTrace();
                holder.registeredOn.setText(context.getString(R.string.course_registered_on, attendance.getRegistrationDate()));
            }
            if (attendance.getAttendancePercentage() < 75) {
                holder.attendanceStatus.setTextColor(context.getResources().getColor(R.color.error_color));
                holder.attendanceStatus.setText(context.getString(R.string.label_attendance_debarred));
                holder.progressBarAttendance.setBackgroundColor(context.getResources().getColor(R.color.error_color));
            } else {
                holder.attendanceStatus.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                holder.attendanceStatus.setText(context.getString(R.string.label_attendance_safe));
                holder.progressBarAttendance.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            holder.progressBarAttendance.setProgress(attendance.getAttendancePercentage());
        } else {
            try {
                holder.date.setText(DateTimeCalender.parseISO8601Date(attendanceDetails.get(position - 1).getDate()));
            } catch (ParseException ex) {
                ex.printStackTrace();
                holder.date.setText(attendanceDetails.get(position - 1).getDate());
            }
            holder.detailStatus.setText(attendanceDetails.get(position - 1).getStatus());
            holder.classUnits.setText(context.getString(R.string.attendance_class_units_earned, attendanceDetails.get(position - 1).getClassUnits()));
            holder.reason.setText(attendanceDetails.get(position - 1).getReason());
        }
    }

    @Override
    public int getItemCount() {
        return (attendanceDetails.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            layoutId = R.layout.card_attendance_summary;
        }
        else {
            layoutId = R.layout.card_attendance_detail;
        }
        return layoutId;
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {

        public TextView date, detailStatus, classUnits, reason;
        public TextView courseName, courseCode, courseRoom, courseSlot, attendanceStatus, attendanceClasses, attendancePercent, registeredOn;
        public ProgressBar progressBarAttendance;

        public AttendanceViewHolder(View view) {
            super(view);

            date = (TextView) view.findViewById(R.id.date);
            detailStatus = (TextView) view.findViewById(R.id.detail_status);
            classUnits = (TextView) view.findViewById(R.id.class_units);
            reason = (TextView) view.findViewById(R.id.reason);

            courseName = (TextView) view.findViewById(R.id.course_name);
            courseCode = (TextView) view.findViewById(R.id.course_code);
            courseRoom = (TextView) view.findViewById(R.id.course_venue);
            courseSlot = (TextView) view.findViewById(R.id.course_slot);
            attendanceStatus = (TextView) view.findViewById(R.id.attendance_status);
            attendanceClasses = (TextView) view.findViewById(R.id.attendance_classes);
            attendancePercent = (TextView) view.findViewById(R.id.attendance_percent);
            registeredOn = (TextView) view.findViewById(R.id.registered_date);
            progressBarAttendance = (ProgressBar) view.findViewById(R.id.progress_bar_attendance);
        }
    }
}
