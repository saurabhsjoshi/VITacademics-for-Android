package com.collegecode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegecode.VITacademics.R;
import com.collegecode.adapters.FullTTPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import java.lang.reflect.Field;

/**
 * Created by saurabh on 4/28/14.
 */
public class FullTimeTableFragment extends Fragment {

    private static final Field sChildFragmentManagerField;
    static {
        Field f = null;
        try {
            f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.e("Home.TimetableFragment.init", "Error getting mChildFragmentManager field", e);
        }
        sChildFragmentManagerField = f;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                Log.e("Home.TimetableFragment.onDetach", "Error setting mChildFragmentManager field", e);
            }
        }
    }

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
