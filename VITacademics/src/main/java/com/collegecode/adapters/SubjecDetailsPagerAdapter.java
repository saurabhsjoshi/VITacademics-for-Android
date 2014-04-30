package com.collegecode.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.collegecode.fragments.AttendanceDetailsFragment;
import com.collegecode.fragments.MarksDetailsFragment;

/**
 * Created by saurabh on 4/30/14.
 */
public class SubjecDetailsPagerAdapter extends FragmentPagerAdapter{

    String titles[];
    Context context;
    String clsnbr;
    public SubjecDetailsPagerAdapter(Context context, String clsnbr, FragmentManager mgr){
        super(mgr);
        titles = new String[]{"Attendance", "Marks", "Schedule"};
        this.context = context;
        this.clsnbr = clsnbr;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new AttendanceDetailsFragment(context);
        else
            return new MarksDetailsFragment(context);
    }

    @Override
    public String getPageTitle(int pos){return titles[pos];}

    @Override
    public int getCount() {
        return 3;
    }
}
