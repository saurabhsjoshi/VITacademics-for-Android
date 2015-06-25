package com.karthikb351.vitinfo2.fragment.TimeTable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by pulkit on 23/06/2015.
 */
public class TimeTablePagerAdapter extends FragmentStatePagerAdapter {

    Context context ;
    int NUM_VALUES = 5;
    private String tabTitles[] = new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday"};

    TimeTablePagerAdapter(FragmentManager fm , Context context)
    {
        super(fm);
        this.context = context;
    }
    @Override
    public int getCount() {
        return NUM_VALUES;
    }

    @Override
    public Fragment getItem(int position) {
        return TimeTableDayFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
