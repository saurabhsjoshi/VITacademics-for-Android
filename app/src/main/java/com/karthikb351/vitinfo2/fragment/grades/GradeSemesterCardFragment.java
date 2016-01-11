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

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class GradeSemesterCardFragment extends Fragment {

    private SemesterWiseGrade semester;
    private View rootView;
    private int semPosition;
    private TextView semeterName;
    private LayoutInflater inflater;
    private int[] gradeColors;
    private GradeListAdapter listAdapter;
    // private RecyclerView gradeRecyclerView;
    private TextView gpa;
    private LinearLayout semCardContainer;
    private List<Grade> gradeList;

    public GradeSemesterCardFragment() {
    }

    public static GradeSemesterCardFragment newInstance(SemesterWiseGrade semester, int position) {
        GradeSemesterCardFragment fragment = new GradeSemesterCardFragment();
        fragment.semester = semester;
        fragment.semPosition = position;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grade_semester_card, container, false);
        initialize();
        return rootView;
    }

    void initialize() {
        gradeColors = getResources().getIntArray(R.array.grade_colors);
        semeterName = (TextView) rootView.findViewById(R.id.text_view_semester_name);
        gpa = (TextView) rootView.findViewById(R.id.text_view_semester_gpa);
        semeterName.setText(getString(R.string.label_semester_no, semPosition + 1));
        semCardContainer = (LinearLayout) rootView.findViewById(R.id.linear_layout_grade_container);
        //gradeRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view_semester_grades);
        inflater = LayoutInflater.from(getActivity());
        ArrayList<Grade> gradestoadd = new ArrayList<>();
        if (semester !=null) {
            gpa.setText(Double.toString(semester.getGpa()));
            gradeList = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getGrades();
            for (Grade g : gradeList) {
                if (g.getExamHeld().equals(semester.getExamHeld())) {
                    //  gradestoadd.add(g);
                    addGrade(g);
                }
            }
        }
            //  Log.d("grade length",Integer.toString(gradestoadd.size()));
        //   gradeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //   listAdapter = new GradeListAdapter(getActivity(),gradestoadd);
        // gradeRecyclerView.setAdapter(listAdapter);
        //   listAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    // This method will be called when a RefreshFragmentEvent is posted
    public void onEvent(RefreshFragmentEvent event) {
        initialize();
    }

    void addGrade(Grade grade) {
        View view = inflater.inflate(R.layout.card_acad_history, semCardContainer, false);
        TextView textViewGrade = ((TextView) view.findViewById(R.id.tv_grade));
        textViewGrade.setText(grade.getGrade());
        int pos = (int) grade.getGrade().charAt(0) - 65;
        if (pos >= gradeColors.length) {
            if (grade.getGrade().charAt(0) == 'S')
                pos = gradeColors.length - 2;
            else
                pos = gradeColors.length - 1;
        }
        ((GradientDrawable) textViewGrade.getBackground()).setColor(gradeColors[pos]);
        ((TextView) view.findViewById(R.id.tv_course_code)).setText(grade.getCourseCode());
        ((TextView) view.findViewById(R.id.tv_course_name)).setText(grade.getCourseTitle());
        ((TextView) view.findViewById(R.id.tv_course_credit)).setText(getActivity().getResources().getQuantityString(R.plurals.course_credits, grade.getCredits(), grade.getCredits()));
        semCardContainer.addView(view);
    }
}
