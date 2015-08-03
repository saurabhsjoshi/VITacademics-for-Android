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

package com.karthikb351.vitinfo2.fragment.cgpaCalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Grade;

import java.util.List;

public class CGPACalculatorListAdapter extends RecyclerView.Adapter<CGPACalculatorListAdapter.CGPAcalculatorViewHolder> {

    private List<Course> courses;
    private List<Grade> grades;
    private Context context;
    private int creditsRegistered;
    private int creditsEarned;
    private float cgpa;
    private int layoutId;
    private float totalgradeValue;
    private float newCGPA;
    private int newCredits;

    public CGPACalculatorListAdapter(Context context, List<Course> courses, List<Grade> grades, int creditsRegistered, int creditsEarned, float cgpa) {
        this.context = context;
        this.grades = grades;
        this.courses = courses;
        this.creditsRegistered = creditsRegistered;
        this.creditsEarned = creditsEarned;
        this.cgpa = cgpa;
        this.totalgradeValue = cgpa * creditsRegistered;
    }

    @Override
    public CGPAcalculatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new CGPAcalculatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CGPAcalculatorViewHolder holder, int position) {
        holder.tvNewCGPA.setText(Float.toString(newCGPA));
        holder.oldCGPA.setText(Float.toString(cgpa));
        holder.courseName.setText(courses.get(position).getCourseTitle());
        holder.courseCode.setText(courses.get(position).getCourseCode());
        holder.courseCredits.setText(Integer.toString(courses.get(position).getCredits()));
    }

    @Override
    public int getItemCount() {
        return (courses.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            layoutId = R.layout.card_cgpa_calculate;
        else
            layoutId = R.layout.card_cgpa_subjects;
        return layoutId;
    }

    public class CGPAcalculatorViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener {


        public TextView courseCode, courseName, courseCredits, tvNewCGPA, oldCGPA;
        public Spinner spinner;
        public ImageButton calculate;

        public CGPAcalculatorViewHolder(View view) {
            super(view);
            calculate = (ImageButton) view.findViewById(R.id.iv_calculate);
            tvNewCGPA = (TextView) view.findViewById(R.id.tv_cgpa_new);
            oldCGPA = (TextView) view.findViewById(R.id.tv_cgpa_old);
            courseCode = (TextView) view.findViewById(R.id.course_code);
            courseName = (TextView) view.findViewById(R.id.course_name);
            courseCredits = (TextView) view.findViewById(R.id.course_credits);
            spinner = (Spinner) view.findViewById(R.id.spinner_course_grade);
            //spinner.setOnItemClickListener(this);
            /*calculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newCGPA = totalgradeValue / newCredits;
                }
            });*/
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            totalgradeValue = totalgradeValue + position;
            int credits = Integer.parseInt(courses.get(position).getLtpjc().substring(4));
            newCredits += credits;
        }

    }
}
