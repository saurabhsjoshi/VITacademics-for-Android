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

package com.karthikb351.vitinfo2.fragment.grades;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.GradeCount;
import com.karthikb351.vitinfo2.api.DataHolder;

import java.util.ArrayList;

public class GradesFragment extends Fragment {

    ArrayList<GradeCount> gradeCounts;
    ArrayList<Grade> grades;
    View rootView;
    TextView CGPA;
    RecyclerView gradeListRecyclerview;
    TableLayout gradeCountTable;
    GradesListAdapter adapter;

    public GradesFragment() {

    }

    public static GradesFragment newInstance() {

        return new GradesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grade, container, false);
        initialize();
        return rootView;
    }

    public void initialize() {
        CGPA = (TextView) rootView.findViewById(R.id.text_view_CGPA);
        gradeCountTable = (TableLayout) rootView.findViewById(R.id.table_grade_count);
        gradeCounts = new ArrayList<>(DataHolder.getGradeCounts());
        grades = new ArrayList<>(DataHolder.getGrades());
        fillGradeCountData();
        gradeListRecyclerview = (RecyclerView) rootView.findViewById(R.id.recycler_view_grades);
        gradeListRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new GradesListAdapter(getActivity(), grades);
        gradeListRecyclerview.setAdapter(adapter);
    }

    void fillGradeCountData() {
        for (int i = 0; i < gradeCounts.size(); i++) {

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            row.addView(createTextView(gradeCounts.get(i).getGrade()));
            row.addView(createTextView(Integer.toString(gradeCounts.get(i).getCount())));
        }
    }

    TextView createTextView(String text) {
        TextView tv = new TextView(getActivity());
        tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(18);
        tv.setPadding(0, 5, 0, 5);
        tv.setText(text);
        return tv;
    }
}
