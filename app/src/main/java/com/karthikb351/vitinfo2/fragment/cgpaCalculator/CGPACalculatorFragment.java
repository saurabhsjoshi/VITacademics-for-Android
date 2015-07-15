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

package com.karthikb351.vitinfo2.fragment.cgpaCalculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;

import java.util.List;

import de.greenrobot.event.EventBus;

public class CGPACalculatorFragment extends Fragment implements View.OnClickListener {

    private List<Grade> grades;
    private List<Course> courses;
    private int creditsRegistered;
    private int creditsEarned;
    private float cgpa;
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private View view;
    private CGPACalculatorListAdapter listAdapter;

    public CGPACalculatorFragment() {
        //empty default constructor
    }

    public static CGPACalculatorFragment newInstance() {
        return new CGPACalculatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cgpa_calculator, container, false);
        initialize();
        return view;
    }

    public void initialize() {
        this.grades = ((MainApplication)getActivity().getApplication()).getDataHolderInstance().getGrades();
        this.courses = ((MainApplication)getActivity().getApplication()).getDataHolderInstance().getCourses();
        this.creditsRegistered = ((MainApplication)getActivity().getApplication()).getDataHolderInstance().getCreditsRegistered();
        this.creditsEarned = ((MainApplication)getActivity().getApplication()).getDataHolderInstance().getCreditsEarned();
        this.cgpa = ((MainApplication)getActivity().getApplication()).getDataHolderInstance().getCgpa();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.listAdapter = new CGPACalculatorListAdapter(getActivity(), courses, grades, creditsRegistered, creditsEarned, cgpa);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_cgpa_calculator);
        this.imageButton = (ImageButton) view.findViewById(R.id.iv_calculate);
        this.imageButton.setOnClickListener(this);
        this.recyclerView.setAdapter(listAdapter);
        this.recyclerView.setLayoutManager(layoutManager);
        String Title = getActivity().getResources().getString(R.string.fragment_cgpa_calculator_title);
        getActivity().setTitle(Title);
    }

    @Override
    public void onClick(View v) {

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
}
