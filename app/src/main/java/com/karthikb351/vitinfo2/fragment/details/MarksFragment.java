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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.utility.Constants;

public class MarksFragment extends Fragment {

    private Course course;
    private RecyclerView recyclerView;
    private MarksListAdapter listAdapter;

    public MarksFragment() {
    }

    public static MarksFragment newInstance(Course course) {
        MarksFragment fragment = new MarksFragment();
        fragment.course = course;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (course.getCourseType() == Constants.COURSE_TYPE_PBC || course.getCourseType() == Constants.COURSE_TYPE_PBC_NO_PROJECT) {
            View view = inflater.inflate(R.layout.app_message_not_available, container, false);
            TextView message = (TextView) view.findViewById(R.id.message);
            message.setText(getActivity().getResources().getQuantityString(R.plurals.message_not_applicable, Constants.PLURAL_VALUE, getString(R.string.tab_course_marks)));
            return view;
        } else {
            View view = inflater.inflate(R.layout.fragment_details_marks, container, false);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            listAdapter = new MarksListAdapter(getActivity(), course);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_assessments);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(listAdapter);
            return view;
        }
    }
}
