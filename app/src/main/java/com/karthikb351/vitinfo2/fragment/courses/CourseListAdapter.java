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

package com.karthikb351.vitinfo2.fragment.courses;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courses;
    private RecyclerViewOnClickListener<Course> onClickListener;

    public CourseListAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootcard = LayoutInflater.from(context).inflate(R.layout.card_courses_course, parent, false);
        return new CourseViewHolder(rootcard);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int position) {

        //courseViewHolder.classNumber.setText(Integer.toString(courses.get(position).getClassNumber()));
        courseViewHolder.courseCode.setText(courses.get(position).getCourseCode());
        courseViewHolder.courseName.setText(courses.get(position).getCourseTitle());
        courseViewHolder.faculty.setText(courses.get(position).getFaculty());
        int attendance;
        if(courses.get(position) != null){
            attendance = courses.get(position).getAttendance().getAttendancePercentage();
            setAttendance(courseViewHolder,attendance);
        }
        courseViewHolder.numberProgressBar.setProgressTextColor(ContextCompat.getColor(this.context, R.color.text_secondary));
        courseViewHolder.numberProgressBar.setReachedBarColor(ContextCompat.getColor(this.context, R.color.text_secondary));
        courseViewHolder.numberProgressBar.setMax(100);
        courseViewHolder.numberProgressBar.setProgress(0);
        //courseViewHolder.subjectType.setText(courses.get(position).getSubjectType());
    }

    public void setAttendance(CourseViewHolder courseViewHolder, int attendance){
        ObjectAnimator animation = ObjectAnimator.ofInt(courseViewHolder.numberProgressBar, "progress", attendance);
        animation.setDuration(1500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        if(attendance >= 80){
            courseViewHolder.numberProgressBar.setReachedBarColor(ContextCompat.getColor(this.context, R.color.text_secondary));
            courseViewHolder.numberProgressBar.setProgressTextColor(ContextCompat.getColor(this.context, R.color.text_secondary));
        }
        else if(attendance < 75){
            courseViewHolder.numberProgressBar.setReachedBarColor(ContextCompat.getColor(this.context, android.R.color.holo_red_light));
            courseViewHolder.numberProgressBar.setProgressTextColor(ContextCompat.getColor(this.context, android.R.color.holo_red_light));
        }
        else{
            courseViewHolder.numberProgressBar.setReachedBarColor(ContextCompat.getColor(this.context, R.color.midAttend));
            courseViewHolder.numberProgressBar.setProgressTextColor(ContextCompat.getColor(this.context, R.color.midAttend));
        }
    }

    public void setOnClickListener(RecyclerViewOnClickListener<Course> onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView classNumber, courseName, courseCode, faculty, subjectType;
        public NumberProgressBar numberProgressBar;

        public CourseViewHolder(View view) {
            super(view);

            //classNumber = (TextView) view.findViewById(R.id.class_number);
            courseName = (TextView) view.findViewById(R.id.course_name);
            courseCode = (TextView) view.findViewById(R.id.course_code);
            faculty = (TextView) view.findViewById(R.id.faculty);
            numberProgressBar = (NumberProgressBar)view.findViewById(R.id.progressAttendanceCourse);
           // subjectType = (TextView) view.findViewById(R.id.subject_type);

            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Course course = courses.get(getAdapterPosition());
            onClickListener.onItemClick(course);
        }
    }
}
