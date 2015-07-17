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

package com.karthikb351.vitinfo2.fragment.details;

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

import java.util.ArrayList;
import java.util.List;

public class MarksListAdapter extends RecyclerView.Adapter<MarksListAdapter.AssesmentViewHolder> {

    private int layoutId;
    private Context context;
    private Marks marks;
    private Course course;
    private List<Assessment> assessments;

    public MarksListAdapter(Context context, Course course) {
        this.context = context;
        this.course = course;
        if (course.getMarks().isSupported()) {
            this.marks = course.getMarks();
            this.assessments = marks.getAssessments();
        } else {
            this.assessments = new ArrayList<>();
            this.marks = new Marks(50.0, 50.0, 0.0, 0.0, this.assessments, true);
        }
    }

    @Override
    public AssesmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView cardView;
        cardView = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(viewType, parent, false);
        return new AssesmentViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(AssesmentViewHolder holder, int position) {
        if (position == 0) {
            holder.internals.setText(context.getString(R.string.label_total_internals, Double.toString(marks.getScoredPercentage()), Double.toString(marks.getMaxPercentage())));
            holder.courseCode.setText(course.getCourseCode());
            holder.courseName.setText(course.getCourseTitle());
        } else {
            holder.assessmentTitle.setText(assessments.get(position - 1).getTitle());
            holder.assessmentMarks.setText(context.getString(R.string.label_assessment_marks, Double.toString(assessments.get(position - 1).getScoredMarks()), Double.toString(assessments.get(position - 1).getMaxMarks())));
            holder.assessmentWeightage.setText(context.getString(R.string.label_assessment_weightage, Double.toString(assessments.get(position - 1).getWeightage())));
            holder.assessmentContribution.setText(context.getString(R.string.label_assessment_contribution, Double.toString(assessments.get(position - 1).getScoredPercentage())));
            holder.marksProgressBar.setProgress((int)( assessments.get(position - 1).getScoredPercentage() / assessments.get(position - 1).getWeightage() * 100));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            layoutId = R.layout.card_marks_summary;
        } else {
            layoutId = R.layout.card_marks_assessment;
        }
        return layoutId;
    }

    @Override
    public int getItemCount() {
        if (assessments == null || assessments.isEmpty())
            return 1;
        else
            return (assessments.size() + 1);
    }

    public class AssesmentViewHolder extends RecyclerView.ViewHolder {

        public TextView assessmentTitle, assessmentMarks, assessmentWeightage, assessmentContribution;
        public TextView courseCode, courseName, internals;
        public ProgressBar marksProgressBar;

        public AssesmentViewHolder(View view) {
            super(view);

            assessmentTitle = (TextView) view.findViewById(R.id.assessment_title);
            assessmentMarks = (TextView) view.findViewById(R.id.assessment_marks);
            assessmentWeightage = (TextView) view.findViewById(R.id.assessment_weightage);
            assessmentContribution = (TextView) view.findViewById(R.id.assessment_contribution);
            marksProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar_marks);

            courseCode = (TextView) view.findViewById(R.id.course_code);
            courseName = (TextView) view.findViewById(R.id.course_name);
            internals = (TextView) view.findViewById(R.id.internal_marks);
        }
    }
}
