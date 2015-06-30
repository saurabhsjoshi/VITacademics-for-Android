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

package com.karthikb351.vitinfo2.fragment.timetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapter.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.api.contract.Course;

import java.util.ArrayList;


public class TimeTableDayFragment extends Fragment {

    ArrayList<Course> subjectsForTheDay ;
    String [] daysOfWeek = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday"};
    TimeTableListAdapter adapter ;
    RecyclerView recyclerview;
    int dayOfWeek;

    public static TimeTableDayFragment newInstance(int dayOfWeek) {
        TimeTableDayFragment fragment = new TimeTableDayFragment();
        fragment.dayOfWeek = dayOfWeek;
        return fragment;
    }

    public TimeTableDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.timetable_day_fragment, container, false);
        recyclerview = (RecyclerView)view.findViewById(R.id.rvTimeTable);
        TextView header = (TextView)view.findViewById(R.id.tvDayHeader);
        header.setText(daysOfWeek[dayOfWeek]);
        // get courses for day based on dayOfWeek;
        subjectsForTheDay = new ArrayList<Course>();
        adapter = new TimeTableListAdapter(getActivity(),subjectsForTheDay);
        adapter.setOnclickListener(new RecyclerViewOnClickListener<Course>() {
            @Override
            public void onItemClick(Course data) {
                //implement on click functionality
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
        return view;
    }

}
