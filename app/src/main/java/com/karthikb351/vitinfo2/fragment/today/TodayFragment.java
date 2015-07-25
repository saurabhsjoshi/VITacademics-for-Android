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

package com.karthikb351.vitinfo2.fragment.today;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.DetailsActivity;
import com.karthikb351.vitinfo2.activity.MainActivity;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.DateTimeCalender;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.utility.ResultListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;

public class TodayFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView todayRecyclerView;
    private TodayListAdapter todayListAdapter;
    private ProgressBar loadProgress;
    private View rootView;
    private List<Course> courses;
    private int today, dayOfWeek;

    public TodayFragment() {

    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_today, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        loadProgress = (ProgressBar) rootView.findViewById(R.id.progress_bar_today);
        initialize();
        return rootView;
    }

    void onListItemClicked(Course course) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_CLASS_NUMBER, course.getClassNumber());
        startActivity(intent);
    }

    void initialize() {
        today = DateTimeCalender.getDayOfWeek();
        dayOfWeek = today;
        todayRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewToday);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        todayRecyclerView.setLayoutManager(layoutManager);
        getActivity().setTitle(getString(R.string.fragment_today_title));
        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorAccent));
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
        });
        initializeData();
    }

    private void initializeData() {
        courses = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getCourses();
        new LoadTodayTask().execute();
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
        if(courses == null || courses.size()==0) {
            courses = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getCourses();
        }
            initializeData();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private int getNextDay(int day) {
        if (day == 6) {
            return 0;
        }
        return (day + 1);
    }

    private String getDayOfWeek(int day) {
        String daysOfWeek[] = getActivity().getResources().getStringArray(R.array.days_of_week);
        return daysOfWeek[day];
    }

    private boolean isTomorrow(int day) {
        return (getNextDay(today) == day);
    }

    private class LoadTodayTask extends AsyncTask<Void, Void, List<Pair<Course, Timing>>> {
        @Override
        protected void onPreExecute() {
            loadProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Pair<Course, Timing>> doInBackground(Void... params) {
            List<Pair<Course, Timing>> finalArray = new ArrayList<>();
            for (Course course : courses) {
                Timing lastTiming = new Timing();
                if (course.getTimings() != null) {
                    for (Timing timing : course.getTimings()) {
                        if (timing.getDay() == dayOfWeek && !(timing.equals(lastTiming))) {
                            finalArray.add(new Pair<>(course, timing));
                            lastTiming = timing;
                        }
                    }
                }
            }

            Collections.sort(finalArray, new Comparator<Pair<Course, Timing>>() {
                @Override
                public int compare(Pair<Course, Timing> lhs, Pair<Course, Timing> rhs) {
                    String lhsStartTime = "";
                    String rhsStartTime = "";
                    for (Timing timing : lhs.first.getTimings()) {
                        if (timing.getDay() == dayOfWeek) {
                            lhsStartTime = timing.getStartTime();
                        }
                    }
                    for (Timing timing : rhs.first.getTimings()) {
                        if (timing.getDay() == dayOfWeek) {
                            rhsStartTime = timing.getStartTime();
                        }
                    }
                    try {
                        return DateTimeCalender.compareTimes(lhsStartTime, rhsStartTime);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                        return 0;
                    }
                }
            });

            return finalArray;
        }

        @Override
        protected void onPostExecute(List<Pair<Course, Timing>> finalCourses) {
            loadProgress.setVisibility(View.GONE);

            if (finalCourses.size() == 0) {
                if (getNextDay(dayOfWeek) == today) {
                    // TODO No classes message, change view
                    getActivity().setTitle(getString(R.string.fragment_today_title));
                } else {
                    dayOfWeek = getNextDay(dayOfWeek);
                    if (isTomorrow(dayOfWeek)) {
                        getActivity().setTitle(getString(R.string.fragment_today_title_tomorrow));
                    } else {
                        getActivity().setTitle(getString(R.string.fragment_today_title_on_day, getDayOfWeek(dayOfWeek)));
                    }
                    new LoadTodayTask().execute();
                }
            } else {
                todayListAdapter = new TodayListAdapter(getActivity(), dayOfWeek, finalCourses);
                todayRecyclerView.setAdapter(todayListAdapter);
                todayListAdapter.setOnclickListener(new RecyclerViewOnClickListener<Course>() {
                    @Override
                    public void onItemClick(Course data) {
                        onListItemClicked(data);
                    }
                });
            }
        }
    }
}
