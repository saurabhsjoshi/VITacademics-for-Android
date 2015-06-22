package com.karthikb351.vitinfo2.fragment.today;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;


public class TimeTableDayFragment extends Fragment {

    public static TimeTableDayFragment newInstance(String param1, String param2) {
        TimeTableDayFragment fragment = new TimeTableDayFragment();
        return fragment;
    }

    public TimeTableDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_table_day, container, false);
    }
}