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

package com.karthikb351.vitinfo2.fragment.courses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.utility.DataHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class CoursesFragment extends Fragment {

    RecyclerView recyclerView;
    CourseListAdapter courseListAdapter;
    ArrayList<Course> courses;
    List<Course> courseList;
    View rootView;

    public CoursesFragment() {
        // Required empty public constructor
    }

    public static CoursesFragment newInstance() {
        CoursesFragment fragment = new CoursesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.courses, container, false);
        initialize();
        return rootView;
    }

    void initialize() {
        RecyclerView.LayoutManager courseLayoutManager = new LinearLayoutManager(getActivity());
        courseList = DataHolder.getCourses();
        courses = new ArrayList<>(courseList);
        courseListAdapter = new CourseListAdapter(getActivity(), courses);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_courses);
        courseListAdapter.setOnclickListener(new RecyclerViewOnClickListener<Course>() {
            @Override
            public void onItemClick(Course data) {
                onListItemClick(data);
            }
        });
        recyclerView.setLayoutManager(courseLayoutManager);
        recyclerView.setAdapter(courseListAdapter);
    }

    void onListItemClick(Course course) {
        // add on item click functionality
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
