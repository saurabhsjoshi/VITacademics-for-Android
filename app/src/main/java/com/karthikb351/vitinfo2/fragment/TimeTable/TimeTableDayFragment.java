package com.karthikb351.vitinfo2.fragment.TimeTable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
