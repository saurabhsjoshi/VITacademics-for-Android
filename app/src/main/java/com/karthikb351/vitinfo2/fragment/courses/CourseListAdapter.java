/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.fragment.courses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapter.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.contract.Course;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    Context context;
    ArrayList<Course> courses;
    RecyclerViewOnClickListener<Course> OnclickListener ;

    public CourseListAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView rootcard = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(R.layout.card_course, parent, false);
        return new CourseViewHolder(rootcard);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int position) {

        int AttendanceP = courses.get(position).getAttendance().getAttendancePercentage();
        courseViewHolder.courseCode.setText(courses.get(position).getCourseCode());
        courseViewHolder.courseName.setText(courses.get(position).getCourseTitle());
        courseViewHolder.Venue.setText(courses.get(position).getVenue());
        courseViewHolder.Slot.setText(courses.get(position).getSlot());
        courseViewHolder.Attendance.setText(Integer.toString(AttendanceP));
        courseViewHolder.pbAttendance.setProgress(AttendanceP);
    }

    public void setOnclickListener(RecyclerViewOnClickListener<Course> listener)
    {
     OnclickListener = listener ;
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView courseName, courseCode , Attendance , Slot ,Venue;
        public ProgressBar pbAttendance;
        public CourseViewHolder(View v) {
            super(v);
            courseName = (TextView) v.findViewById(R.id.tv_course_name);
            courseCode = (TextView) v.findViewById(R.id.tv_course_code);
            Attendance = (TextView)v.findViewById(R.id.tv_attendance);
            Slot = (TextView)v.findViewById(R.id.tv_slot);
            Venue = (TextView)v.findViewById(R.id.tv_venue);
            pbAttendance = (ProgressBar)v.findViewById(R.id.process_bar_attendance);
            pbAttendance.setMax(100);
            v.setOnClickListener(this);
        }

        public  void onClick(View v)
        {
            Course course = courses.get(getAdapterPosition());
            OnclickListener.onItemClick(course);
        }
    }

}
