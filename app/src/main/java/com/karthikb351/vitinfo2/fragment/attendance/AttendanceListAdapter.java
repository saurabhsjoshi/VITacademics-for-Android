/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.attendance;

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

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.AttendanceViewHolder> {

    int layoutId;
    Context context;
    Course course;
    Attendance attendance;
    AttendanceDetail attendanceDetail[];

    public AttendanceListAdapter(Context context, Course course) {
        this.context = context;
        this.course = course;
        attendance = course.getAttendance();
        attendanceDetail = attendance.getDetails();
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        holder.date.setText(attendanceDetail[position - 1].getDate());
        holder.statusDetails.setText(attendanceDetail[position - 1].getStatus());
        holder.classUnits.setText(attendanceDetail[position - 1].getClassUnits());
        holder.reason.setText(attendanceDetail[position - 1].getReason());
        holder.courseName.setText(course.getCourseTitle());
        holder.courseCode.setText(course.getCourseCode());
        holder.courseRoom.setText(course.getVenue());
        holder.courseSlot.setText(course.getSlot());
        holder.attended.setText(attendance.getAttendedClasses());
        holder.totalClasses.setText(attendance.getTotalClasses());
        holder.attendancePercent.setText(attendance.getAttendancePercentage());
        holder.registeredOn.setText(attendance.getRegistrationDate());
        if (attendance.getAttendancePercentage() <= 75) {
            holder.status.setTextColor(R.color.error_color);
            holder.status.setText("DEBARRED");
            holder.progressBarAttendance.setBackgroundColor(R.color.error_color);
        } else {
            holder.status.setTextColor(R.color.colorPrimaryDark);
            holder.status.setText("SAFE");
            holder.progressBarAttendance.setBackgroundColor(R.color.colorPrimaryDark);
        }
        holder.progressBarAttendance.setProgress(attendance.getAttendancePercentage());
    }

    @Override
    public int getItemCount() {
        return (attendanceDetail.length + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            layoutId = R.layout.card_attendance;
        else
            layoutId = R.layout.attendance_details;
        return layoutId;
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {

        public TextView date, statusDetails, classUnits, reason, courseName, courseCode, courseRoom, courseSlot, status,
                attended, totalClasses, attendancePercent, registeredOn;
        public ProgressBar progressBarAttendance;

        public AttendanceViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.tv_date);
            statusDetails = (TextView) view.findViewById(R.id.tv_status_details);
            classUnits = (TextView) view.findViewById(R.id.tv_class_units);
            reason = (TextView) view.findViewById(R.id.tv_attendance_reason);
            courseName = (TextView) view.findViewById(R.id.course_name);
            courseCode = (TextView) view.findViewById(R.id.course_code);
            courseRoom = (TextView) view.findViewById(R.id.tv_course_room);
            courseSlot = (TextView) view.findViewById(R.id.course_slot);
            status = (TextView) view.findViewById(R.id.tv_attendance_status);
            attended = (TextView) view.findViewById(R.id.tv_attended);
            totalClasses = (TextView) view.findViewById(R.id.tv_total_classes);
            attendancePercent = (TextView) view.findViewById(R.id.tv_attendance_percent);
            registeredOn = (TextView) view.findViewById(R.id.tv_registered_on);
            progressBarAttendance = (ProgressBar) view.findViewById(R.id.progress_bar_marks);
        }
    }

}
