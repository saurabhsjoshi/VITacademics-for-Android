package com.collegecode.fragments.WelcomeScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegecode.VITacademics.R;
import com.collegecode.adapters.FullTTPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by saurabh on 4/28/14.
 */
public class FullTimeTableFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timetable,container, false);
        ViewPager pager = (ViewPager)v.findViewById(R.id.timetable_pager);
        pager.setAdapter(new FullTTPagerAdapter(getActivity().getSupportFragmentManager()));
        TitlePageIndicator titlePageIndicator = (TitlePageIndicator) v.findViewById(R.id.timetable_pager_indicator);
        titlePageIndicator.setViewPager(pager);
        titlePageIndicator.setTextColor(getResources().getColor(R.color.Gray));
        return v;
    }

}
