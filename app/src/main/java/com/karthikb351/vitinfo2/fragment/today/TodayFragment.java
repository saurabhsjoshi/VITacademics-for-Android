/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.today;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.utility.SortedArrayList;

import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

public class TodayFragment extends Fragment {

    public static final String ARG = "number";

    RecyclerView todayRecyclerView;
    TodayListAdapter todayListAdapter;
    ProgressBar load;
    View rootView;
    List<Course> courses;
    int layoutId;

    public TodayFragment() {

    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.card_today, container, false);
        //load = (ProgressBar) rootView.findViewById(R.id.progress_bar_today);
        todayRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_today);
        initialize();
        return rootView;
    }

    void onListItemClicked(Course course) {
        //TODO: implement redirection on itemclick
    }

    void initialize() {
        getActivity().setTitle("Today");
        new loadToday().execute();
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

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    class loadToday extends AsyncTask<Void, Void, List<Pair<Course, Integer>>> {
        @Override
        protected void onPreExecute() {
            //load.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Pair<Course, Integer>> doInBackground(Void... params) {
            int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
            SortedArrayList finalArray = new SortedArrayList();
            courses = ((MainApplication)getActivity().getApplication()).getDataHolderInstance().getCourses();
            if (courses != null) {
                for (Course c : courses) {
                    for (int i = 0; i < c.getTimings().size(); i++) {
                        if (c.getTimings().get(i).getDay() == dayOfWeek)
                            finalArray.insert(new Pair<>(c, i));
                    }
                }
            }
            return finalArray;
        }

        @Override
        protected void onPostExecute(List<Pair<Course, Integer>> res) {
            //load.setVisibility(View.GONE);
            todayListAdapter = new TodayListAdapter(getActivity(), res);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            todayRecyclerView.setLayoutManager(layoutManager);
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
