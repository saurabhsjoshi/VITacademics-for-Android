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

package com.karthikb351.vitinfo2.fragment.timetable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.contract.Course;

import java.util.ArrayList;

public class TimeTableListAdapter extends RecyclerView.Adapter<TimeTableListAdapter.TimeTableViewHolder> {


    RecyclerViewOnClickListener<Course> onClickListener;
    ArrayList<Course> coursesForTheDay ;
    Context context ;

    TimeTableListAdapter(Context context , ArrayList<Course> objects)
    {
        this.context=context;
        this.coursesForTheDay = objects ;
    }

    @Override
    public TimeTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView rootcard = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(R.layout.card_course, parent, false);
        return new TimeTableViewHolder(rootcard);
    }

    @Override
    public void onBindViewHolder(TimeTableViewHolder cvHolder, int position) {

        int AttendanceP = coursesForTheDay.get(position).getAttendance().getAttendancePercentage();
        //TimeTableViewHolder cvHolder = (TimeTableViewHolder) holder;
        cvHolder.courseCode.setText(coursesForTheDay.get(position).getCourseCode());
        cvHolder.courseName.setText(coursesForTheDay.get(position).getCourseTitle());
        cvHolder.Venue.setText(coursesForTheDay.get(position).getVenue());
        cvHolder.Slot.setText(coursesForTheDay.get(position).getSlot());
        cvHolder.Attendance.setText(Integer.toString(AttendanceP));
        cvHolder.pbAttendance.setProgress(AttendanceP);
    }

    public void setOnclickListener(RecyclerViewOnClickListener<Course> listener)
    {
        onClickListener = listener ;
    }

    @Override
    public int getItemCount() {
        return coursesForTheDay.size();
    }

    public class TimeTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //data to be changed based on format of timetable card

        public TextView courseName, courseCode , Attendance , Slot ,Venue;
        public ProgressBar pbAttendance;
        public TimeTableViewHolder(View v) {
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
            Course course = coursesForTheDay.get(getAdapterPosition());
            onClickListener.onItemClick(course);
        }
    }
}
