/*
 * VITacademics
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.today;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapter.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;

import java.util.ArrayList;
import java.util.Calendar;

public class TodayListAdapter extends RecyclerView.Adapter<TodayListAdapter.TodayViewHolder> {

    Context context;
    ArrayList<Pair<Course,Integer>> courses;
    RecyclerViewOnClickListener<Course> OnclickListener ;

    public TodayListAdapter(Context context, ArrayList<Pair<Course,Integer>> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public TodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView rootCard = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(R.layout.card_today, parent, false);
        return new TodayViewHolder(rootCard);
    }

    @Override
    public void onBindViewHolder(TodayViewHolder holder, int position) {

        int AttendanceP = courses.get(position).first.getAttendance().getAttendancePercentage();
        TodayViewHolder todayViewHolder = (TodayViewHolder) holder;
        todayViewHolder.courseCode.setText(courses.get(position).first.getCourseCode());
        todayViewHolder.courseName.setText(courses.get(position).first.getCourseTitle());
        todayViewHolder.Venue.setText(courses.get(position).first.getVenue());
        todayViewHolder.Slot.setText(courses.get(position).first.getSlot());
        todayViewHolder.Attendance.setText(Integer.toString(AttendanceP));
        todayViewHolder.pbAttendance.setProgress(AttendanceP);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Timing time = courses.get(position).first.getTimings()[courses.get(position).second];
        //calculate time gap from now to next instance of class
    }

    public void setOnclickListener(RecyclerViewOnClickListener<Course> listener){
        OnclickListener = listener ;
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public class TodayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView courseName, courseCode, Attendance, Slot ,Venue;
        public ProgressBar pbAttendance;

        public TodayViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.tv_course_name);
            courseCode = (TextView) view.findViewById(R.id.tv_course_code);
            Attendance = (TextView)view.findViewById(R.id.tv_attendance);
            Slot = (TextView)view.findViewById(R.id.tv_slot);
            Venue = (TextView)view.findViewById(R.id.tv_venue);
            pbAttendance = (ProgressBar)view.findViewById(R.id.process_bar_attendance);
            pbAttendance.setMax(100);
        }

        public  void onClick(View view)
        {
            Course course = courses.get(getAdapterPosition()).first;
            OnclickListener.onItemClick(course);
        }
    }
}