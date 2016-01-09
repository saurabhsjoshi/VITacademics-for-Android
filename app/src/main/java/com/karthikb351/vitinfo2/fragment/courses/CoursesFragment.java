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

package com.karthikb351.vitinfo2.fragment.courses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.DetailsActivity;
import com.karthikb351.vitinfo2.activity.MainActivity;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.utility.ResultListener;

import java.util.List;

import de.greenrobot.event.EventBus;

public class CoursesFragment extends Fragment {

    TextView errorMessage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Course> courses;
    private RecyclerView recyclerView;
    private CourseListAdapter courseListAdapter;
    private View rootView;

    public CoursesFragment() {
    }

    public static CoursesFragment newInstance() {
        return new CoursesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        courses = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getCourses();
        if (courses == null || courses.isEmpty()) {
            rootView = inflater.inflate(R.layout.app_message_not_available, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_courses, container, false);
            swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        }
        initialize();
        return rootView;
    }

    void initialize() {
        initializeData();
        if (courses == null || courses.isEmpty()) {
            errorMessage = (TextView) rootView.findViewById(R.id.message);
            errorMessage.setText(getString(R.string.courses_none));
        } else {
            RecyclerView.LayoutManager courseLayoutManager = new LinearLayoutManager(getActivity());
            courseListAdapter = new CourseListAdapter(getActivity(), courses);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_courses);
            courseListAdapter.setOnClickListener(new RecyclerViewOnClickListener<Course>() {
                @Override
                public void onItemClick(Course data) {
                    onListItemClick(data);
                }
            });
            recyclerView.setLayoutManager(courseLayoutManager);
            recyclerView.setAdapter(courseListAdapter);

/*            swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorAccent));
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    ((MainActivity) getActivity()).pullToRefresh(new ResultListener() {
                        @Override
                        public void onSuccess() {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFailure(Status status) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            });*/
        }
        String Title = getActivity().getResources().getString(R.string.fragment_courses_title);
        getActivity().setTitle(Title);
    }

    private void initializeData() {
        try {
            courses = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getCourses();
            courseListAdapter.notifyDataSetChanged();
        } catch (Exception ignore) {
        }
    }

    void onListItemClick(Course course) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_CLASS_NUMBER, course.getClassNumber());
        startActivity(intent);
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
        initializeData();
    }
}
