package com.karthikb351.vitinfo2.fragment.today;
 /*
 * VITacademics
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapter.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.api.contract.Course;
import com.karthikb351.vitinfo2.fragment.today.TodayListAdapter;

import java.util.ArrayList;
/**
 * Created by Hemant on 22-06-2015.
 */
public class TodayFragment extends Fragment {

    RecyclerView recyclerView;
    TodayListAdapter todayListAdapter;
    ArrayList<Course> courses;

    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView.LayoutManager todayLayoutManager=new TodayLayoutManager();
        TextView textView = new TextView(getActivity());
        View view=inflater.inflate(R.layout.today,container,false);
        todayListAdapter=new TodayListAdapter(getActivity(),courses);
        recyclerView=(RecyclerView)view.findViewById(R.id.rvToday);
        todayListAdapter.setOnclickListener(new RecyclerViewOnClickListener<Course>() {
            @Override
            public void onItemClick(Course data) {
                // add on item click functionality
            }
        });
        recyclerView.setLayoutManager(todayLayoutManager);
        recyclerView.setAdapter(todayListAdapter);
        return view;
    }

    public class TodayLayoutManager extends RecyclerView.LayoutManager {

        public TodayLayoutManager(){

        }
        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return null;
        }
        @Override
        public void scrollToPosition(int position) {

        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
        }
    }

}
