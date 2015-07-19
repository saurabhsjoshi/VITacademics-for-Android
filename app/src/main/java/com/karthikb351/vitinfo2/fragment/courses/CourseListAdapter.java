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

package com.karthikb351.vitinfo2.fragment.courses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        android.support.v7.widget.CardView rootcard = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(R.layout.card_courses_course, parent, false);
        return new CourseViewHolder(rootcard);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int position) {

        courseViewHolder.classNumber.setText(Integer.toString(courses.get(position).getClassNumber()));
        courseViewHolder.courseCode.setText(courses.get(position).getCourseCode());
        courseViewHolder.courseName.setText(courses.get(position).getCourseTitle());
        courseViewHolder.faculty.setText(courses.get(position).getFaculty());
        courseViewHolder.subjectType.setText(courses.get(position).getSubjectType());
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

        public CourseViewHolder(View view) {
            super(view);

            classNumber = (TextView) view.findViewById(R.id.class_number);
            courseName = (TextView) view.findViewById(R.id.course_name);
            courseCode = (TextView) view.findViewById(R.id.course_code);
            faculty = (TextView) view.findViewById(R.id.faculty);
            subjectType = (TextView) view.findViewById(R.id.subject_type);

            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Course course = courses.get(getAdapterPosition());
            onClickListener.onItemClick(course);
        }
    }
}
