package com.karthikb351.vitinfo2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.karthikb351.vitinfo2.Constants;

/**
 * Created by aashrai on 28/3/15.
 */
public class TimetableAdapter extends FragmentPagerAdapter {

    public TimetableAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return Constants.days.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Constants.days[position];
    }


}
