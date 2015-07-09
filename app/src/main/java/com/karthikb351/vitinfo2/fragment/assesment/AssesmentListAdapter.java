/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.assesment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Assessment;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Marks;

import java.util.List;

public class AssesmentListAdapter extends RecyclerView.Adapter<AssesmentListAdapter.AssesmentViewHolder> {


    public int layoutId;
    Context context;
    Marks marks;
    Course course;
    List<Assessment> assesments;


    public AssesmentListAdapter(Context context, Course course) {
        this.context = context;
        this.course = course;
        marks = course.getMarks();
        assesments = marks.getAssessments();
    }

    @Override
    public AssesmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView cardView;
        cardView = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(viewType, parent, false);
        return new AssesmentViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(AssesmentViewHolder holder, int position) {
        holder.assessmentType.setText(assesments.get(position - 1).getTitle());
        holder.maxMarks.setText(Double.toString(assesments.get(position - 1).getMaxMarks()));
        holder.scoredMarks.setText(Double.toString(assesments.get(position - 1).getScoredMarks()) + "/");
        holder.weightage.setText(Double.toString(assesments.get(position - 1).getWeightage()));
        Double contribution = assesments.get(position - 1).getScoredPercentage() * assesments.get(position - 1).getWeightage();
        holder.contribution.setText(Double.toString(contribution));
        holder.marksProgressBar.setProgress((int) assesments.get(position - 1).getScoredPercentage());
        holder.totalScored.setText(Double.toString(marks.getScoredMarks()));
        holder.totalMax.setText(Double.toString(marks.getMaxMarks()));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            layoutId = R.layout.card_total_internals;
        else
            layoutId = R.layout.card_assessment;
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return (assesments.size() + 1);
    }

    public class AssesmentViewHolder extends RecyclerView.ViewHolder {

        public TextView assessmentType, maxMarks, scoredMarks, weightage, contribution, courseCode, courseName, totalScored, totalMax;
        public ProgressBar marksProgressBar;

        public AssesmentViewHolder(View view) {
            super(view);
            assessmentType = (TextView) view.findViewById(R.id.tv_marks_type);
            maxMarks = (TextView) view.findViewById(R.id.tv_max_marks);
            scoredMarks = (TextView) view.findViewById(R.id.tv_scored_marks);
            weightage = (TextView) view.findViewById(R.id.tv_weightage);
            contribution = (TextView) view.findViewById(R.id.tv_contribution);
            marksProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar_marks);
            courseCode = (TextView) view.findViewById(R.id.course_code);
            courseName = (TextView) view.findViewById(R.id.course_name);
            totalMax = (TextView) view.findViewById(R.id.tv_max_marks_total);
            totalScored = (TextView) view.findViewById(R.id.tv_scored_marks_total);

        }
    }
}
