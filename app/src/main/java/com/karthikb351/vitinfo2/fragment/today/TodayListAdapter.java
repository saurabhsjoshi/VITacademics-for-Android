/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.today;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.DateTime;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TodayListAdapter extends RecyclerView.Adapter<TodayListAdapter.TodayViewHolder> {

    private Context context;
    private List<Course> courses;
    private int dayOfWeek;
    private RecyclerViewOnClickListener<Course> OnclickListener;

    public TodayListAdapter(Context context, int dayOfWeek, List<Course> courses) {
        this.context = context;
        this.dayOfWeek = dayOfWeek;
        this.courses = courses;
    }

    @Override
    public TodayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView rootCard = (CardView) LayoutInflater.from(context).inflate(R.layout.card_today, parent, false);
        return new TodayViewHolder(rootCard);
    }

    @Override
    public void onBindViewHolder(TodayViewHolder todayViewHolder, int position) {

        int AttendanceP = courses.get(position).getAttendance().getAttendancePercentage();
        todayViewHolder.courseCode.setText(courses.get(position).getCourseCode());
        todayViewHolder.courseName.setText(courses.get(position).getCourseTitle());
        todayViewHolder.Venue.setText(courses.get(position).getVenue());
        todayViewHolder.Slot.setText(courses.get(position).getSlot());
        todayViewHolder.Attendance.setText(Integer.toString(AttendanceP));
        todayViewHolder.pbAttendance.setProgress(AttendanceP);

        long diff = 0;
        boolean ended = false;
        for (Course course : courses) {
            for (Timing timing : course.getTimings()) {
                if (timing.getDay() == dayOfWeek) {
                    diff = getTimeDifference(timing);
                    ended = checkIfSlotEnded(timing);
                    break;
                }
            }
        }

        todayViewHolder.TimeLeft.setText(getTimeDifferenceString(diff, ended));
    }

    public void setOnclickListener(RecyclerViewOnClickListener<Course> listener) {
        OnclickListener = listener;
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    private String getTimeDifferenceString(long diff, boolean ended) {
        int hours;
        int minutes;
        if (diff < 0 && ended) {
            return context.getString(R.string.today_course_timing_done);
        } else if (diff > Constants.MILLISECONDS_IN_HOUR) {
            hours = (int) TimeUnit.MILLISECONDS.toHours(diff);
            minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(hours));
            if (hours == 0) {
                return Integer.toString(minutes) + context.getString(R.string.today_course_timing_minutes) + context.getString(R.string.today_course_timing_later);
            } else if (minutes == 0) {
                return Integer.toString(hours) + context.getString(R.string.today_course_timing_hours) + context.getString(R.string.today_course_timing_later);
            } else {
                return Integer.toString(hours) + context.getString(R.string.today_course_timing_hours) + context.getString(R.string.today_course_timing_and) + Integer.toString(minutes) + context.getString(R.string.today_course_timing_minutes) +  context.getString(R.string.today_course_timing_later);
            }
        } else {
            return context.getString(R.string.today_course_timing_right_now);
        }
    }

    private long getTimeDifference(Timing timing) {
        Date now = new Date();
        try {
            Date courseStartTime = DateTime.getISO8601TimeObject(timing.getStartTime());
            return courseStartTime.getTime() - now.getTime();
        } catch (ParseException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private boolean checkIfSlotEnded(Timing timing) {
        Date now = new Date();
        try {
            Date courseEndTime = DateTime.getISO8601TimeObject(timing.getEndTime());
            return courseEndTime.before(now);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView courseName, courseCode, Attendance, Slot, Venue, TimeLeft;
        public ProgressBar pbAttendance;

        public TodayViewHolder(View view) {
            super(view);
            courseName = (TextView) view.findViewById(R.id.tv_course_name);
            courseCode = (TextView) view.findViewById(R.id.tv_course_code);
            Attendance = (TextView) view.findViewById(R.id.tv_attendance);
            Slot = (TextView) view.findViewById(R.id.tv_slot);
            Venue = (TextView) view.findViewById(R.id.tv_venue);
            TimeLeft = (TextView) view.findViewById(R.id.tvTimeLeft);
            pbAttendance = (ProgressBar) view.findViewById(R.id.process_bar_attendance);
            pbAttendance.setMax(100);
        }

        public void onClick(View view) {
            Course course = courses.get(getAdapterPosition());
            OnclickListener.onItemClick(course);
        }
    }
}
