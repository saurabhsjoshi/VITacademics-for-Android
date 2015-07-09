/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.timetable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.DataHolder;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class TimeTableDayFragment extends Fragment {

    String[] daysOfWeek = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}; // TODO, put under arrays.xml
    TimeTableListAdapter adapter;
    RecyclerView recyclerview;
    ProgressBar load;
    int dayOfWeek;
    View rootView;

    public TimeTableDayFragment() {
    }

    public static TimeTableDayFragment newInstance(int dayOfWeek) {
        TimeTableDayFragment fragment = new TimeTableDayFragment();
        fragment.dayOfWeek = dayOfWeek + 1;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.timetable_day_fragment, container, false);
        initialize();
        return rootView;
    }

    void initialize() {
        recyclerview = (RecyclerView) rootView.findViewById(R.id.recycler_view_timetable);
        load = (ProgressBar) rootView.findViewById(R.id.timeTableProgressBar);
        new LoadData().execute();
    }


    void onListItemClicked(Course course) {
        //TODO: implement redirection on itemclick
    }

    void onListItemClick(Friend friend) {
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

    class LoadData extends AsyncTask<Void, Void, ArrayList<Course>> {
        @Override
        protected void onPreExecute() {
            load.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Course> doInBackground(Void... params) {
            ArrayList<Course> finalArray = new ArrayList<>();
            List<Course> courses = DataHolder.getCourses();
            for (Course c : courses) {
                for (int i = 0; i < c.getTimings().length; i++) {
                    if (c.getTimings()[i].getDay() == dayOfWeek)
                        finalArray.add(c);
                }
            }
            return finalArray;
        }

        @Override
        protected void onPostExecute(ArrayList<Course> res) {
            load.setVisibility(View.GONE);
            adapter = new TimeTableListAdapter(getActivity(), res);
            adapter.setOnclickListener(new RecyclerViewOnClickListener<Course>() {
                @Override
                public void onItemClick(Course data) {
                    onListItemClicked(data);
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(layoutManager);
            recyclerview.setAdapter(adapter);
        }

    }
}
