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

package com.karthikb351.vitinfo2.fragment.grades;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.contract.Grade;

import java.util.ArrayList;

public class GradesListAdapter extends RecyclerView.Adapter<GradesListAdapter.GradesViewHolder> {

    ArrayList<Grade> gradeList;
    Context context;
    RecyclerViewOnClickListener<Grade> onClickListener;

    GradesListAdapter(Context context, ArrayList<Grade> grades) {
        this.context = context;
        gradeList = grades;
    }

    @Override
    public GradesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_grades, parent, false);
        return new GradesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradesViewHolder holder, int position) {
        holder.courseCode.setText(gradeList.get(position).getCourseCode());
        holder.courseName.setText(gradeList.get(position).getCourseTitle());
        holder.credits.setText(gradeList.get(position).getCredits());
        holder.courseType.setText(gradeList.get(position).getCourseType());
        holder.grade.setText(gradeList.get(position).getGrade());
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
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
            credits = (TextView) itemView.findViewById(R.id.tv_course_credit);
            grade = (TextView) itemView.findViewById(R.id.tv_grade);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {

        }
    }

}
