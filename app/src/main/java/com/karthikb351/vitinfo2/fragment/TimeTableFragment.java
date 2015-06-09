package com.karthikb351.vitinfo2.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;

public class TimeTableFragment extends Fragment {

        public static TimeTableFragment newInstance() {
        TimeTableFragment fragment = new TimeTableFragment();
        return fragment;
    }

    public TimeTableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.timetable_fragment, container, false);
    }

}
