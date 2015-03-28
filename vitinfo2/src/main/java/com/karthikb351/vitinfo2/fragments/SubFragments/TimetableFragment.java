package com.karthikb351.vitinfo2.fragments.SubFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapters.TimetableAdapter;

/**
 * Created by aashrai on 28/3/15.
 */
public class TimetableFragment extends Fragment {

    ViewPager timetablePager;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_timetable, container, false);
        timetablePager = (ViewPager) v;
        timetablePager.setOffscreenPageLimit(Constants.days.length);

        timetablePager.setAdapter(new TimetableAdapter());

        return v;
    }
}
