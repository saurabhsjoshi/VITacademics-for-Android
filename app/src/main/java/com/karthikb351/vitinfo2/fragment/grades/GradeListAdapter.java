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

package com.karthikb351.vitinfo2.fragment.grades;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Grade;

import java.util.List;

public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.GradeViewHolder> {

    private Context context;
    private List<Grade> grades;

    public GradeListAdapter(Context context, List<Grade> grades) {
        this.context = context;
        this.grades = grades;
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_acad_history, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        Grade current = grades.get(position);
        holder.courseName.setText(current.getCourseTitle());
        holder.courseCode.setText(current.getCourseCode());
        holder.credit.setText(context.getResources().getQuantityString(R.plurals.course_credits, current.getCredits(), current.getCredits()));
        holder.grade.setText(current.getGrade());
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView grade,
                courseCode,
                credit,
                courseName;

        public GradeViewHolder(View view) {
            super(view);
            grade = (TextView) view.findViewById(R.id.tv_grade);
            courseCode = (TextView) view.findViewById(R.id.tv_course_code);
            courseName = (TextView) view.findViewById(R.id.tv_course_name);
            credit = (TextView) view.findViewById(R.id.tv_course_credit);
        }
    }
}
