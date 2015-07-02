/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.today;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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
import com.karthikb351.vitinfo2.contract.Timing;
import com.karthikb351.vitinfo2.utility.SortedArrayList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainFragment extends Fragment {

    public static final String ARG = "number";

    RecyclerView todayRecyclerView;
    TodayListAdapter todayListAdapter;
    ProgressBar load ;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        load = (ProgressBar)rootView.findViewById(R.id.todayProgressBar);
        todayRecyclerView = (RecyclerView)rootView.findViewById(R.id.rvToday);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Today");
        // int i = getArguments().getInt(ARG);
        //int imageId = getResources().getIdentifier(topics.toLowerCase(Locale.getDefault()),
       //         "drawable", getActivity().getPackageName());
        // ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        String topics = getResources().getStringArray(R.array.topic)[0];
        getActivity().setTitle(topics);
        //return rootView;
        return rootView;
    }

     void onListItemClicked(Course course)
     {
         //TODO: implement redirection on itemclick
     }

    class loadToday extends AsyncTask<Void,Void,ArrayList<Pair<Course,Integer>>>
    {
        @Override
        protected void onPreExecute()
        {
            load.setVisibility(View.VISIBLE);
        }
        @Override
        protected ArrayList<Pair<Course,Integer>> doInBackground(Void... params)
        {
            int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1 ;
            SortedArrayList finalArray =  new SortedArrayList();
            List<Course> courses =  ((MainActivity) getActivity()).getCourses();
            for(Course c : courses)
            {
                for(int i = 0 ; i < c.getTimings().length ; i++)
                {
                  if(c.getTimings()[i].getDay() == dayOfWeek)
                  {
                      finalArray.insert(new Pair<Course, Integer>(c, i));
                  }
                }
            }
            return finalArray;
        }
        @Override
        protected void onPostExecute(ArrayList<Pair<Course,Integer>> res)
        {
           todayListAdapter  = new TodayListAdapter(getActivity(),res);
           todayListAdapter.setOnclickListener(new RecyclerViewOnClickListener<Course>() {
               @Override
               public void onItemClick(Course data) {
                   onListItemClicked(data);
               }
           });
           todayRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
           todayRecyclerView.setAdapter(todayListAdapter);
        }

    }
}
