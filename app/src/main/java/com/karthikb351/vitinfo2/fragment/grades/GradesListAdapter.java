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
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;

import java.util.List;

public class GradesListAdapter extends RecyclerView.Adapter<GradesListAdapter.GradesViewHolder> {

    private List<Grade> grades;
    private Context context;
    private RecyclerViewOnClickListener<Grade> onClickListener;
    private int layoutId;

    GradesListAdapter(Context context, List<Grade> grades) {
        this.context = context;
        this.grades = grades;
    }

    @Override
    public GradesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new GradesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            layoutId = R.layout.card_grade_count;
        else
            layoutId = R.layout.card_grade;
        return layoutId;
    }

    @Override
    public void onBindViewHolder(GradesViewHolder holder, int position) {
        holder.courseCode.setText(grades.get(position).getCourseCode());
        holder.courseName.setText(grades.get(position).getCourseTitle());
        holder.credits.setText(grades.get(position).getCredits());
        holder.courseType.setText(grades.get(position).getCourseType());
        holder.grade.setText(grades.get(position).getGrade());
    }

    @Override
    public int getItemCount() {
        if (grades == null || grades.isEmpty())
            return 1;
        else
            return (grades.size() + 1);
    }

    public class GradesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView courseCode,
                courseName,
                courseType,
                credits,
                grade;

        public GradesViewHolder(View itemView) {
            super(itemView);
            courseCode = (TextView) itemView.findViewById(R.id.course_code);
            courseName = (TextView) itemView.findViewById(R.id.course_name);
            courseType = (TextView) itemView.findViewById(R.id.course_type);
            credits = (TextView) itemView.findViewById(R.id.course_credits);
            grade = (TextView) itemView.findViewById(R.id.grade);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {

        }
    }

}
