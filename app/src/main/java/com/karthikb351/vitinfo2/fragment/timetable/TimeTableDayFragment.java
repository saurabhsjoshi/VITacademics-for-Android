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
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.MainActivity;
import com.karthikb351.vitinfo2.adapter.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.fragment.today.TodayListAdapter;
import com.karthikb351.vitinfo2.utility.DataHolder;
import com.karthikb351.vitinfo2.utility.SortedArrayList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TimeTableDayFragment extends Fragment {

    String [] daysOfWeek = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday"};
    TimeTableListAdapter adapter ;
    RecyclerView recyclerview;
    ProgressBar load ;
    int dayOfWeek;

    public static TimeTableDayFragment newInstance(int dayOfWeek) {
        TimeTableDayFragment fragment = new TimeTableDayFragment();
        fragment.dayOfWeek = dayOfWeek+1;
        return fragment;
    }

    public TimeTableDayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.timetable_day_fragment, container, false);
        recyclerview = (RecyclerView)view.findViewById(R.id.recycler_view_timetable);
        load = (ProgressBar)view.findViewById(R.id.timeTableProgressBar);
        new LoadData().execute();
        return view;
    }

    void onListItemClicked(Course course)
    {
        //TODO: implement redirection on itemclick
    }

    class LoadData extends AsyncTask<Void,Void,ArrayList<Course>>
    {
        @Override
        protected void onPreExecute()
        {
            load.setVisibility(View.VISIBLE);
        }
        @Override
        protected ArrayList<Course> doInBackground(Void... params)
        {
            ArrayList<Course> finalArray = new ArrayList<>();
            List<Course> courses =  DataHolder.getCourses();
            for(Course c : courses)
            {
                for(int i = 0 ; i < c.getTimings().length ; i++)
                {
                    if(c.getTimings()[i].getDay() == dayOfWeek)
                        finalArray.add(c);
                }
            }
            return finalArray;
        }
        @Override
        protected void onPostExecute(ArrayList<Course> res)
        {
            load.setVisibility(View.GONE);
            adapter = new TimeTableListAdapter(getActivity(),res);
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
