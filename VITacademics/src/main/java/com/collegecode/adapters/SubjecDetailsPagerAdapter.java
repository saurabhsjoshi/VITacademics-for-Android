package com.collegecode.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.collegecode.fragments.DetailsScreens.AttendanceDetailsFragment;
import com.collegecode.fragments.DetailsScreens.DayByDayFragment;
import com.collegecode.fragments.DetailsScreens.MarksDetailsFragment;
import com.collegecode.objects.Subject;

/**
 * Created by saurabh on 4/30/14.
 */
public class SubjecDetailsPagerAdapter extends FragmentPagerAdapter{

    String titles[];
    Context context;
    Subject subject;

    public SubjecDetailsPagerAdapter(Context context, Subject subject, FragmentManager mgr){
        super(mgr);
        titles = new String[]{"Attendance", "Marks", "Day By Day"};
        this.context = context;
        this.subject = subject;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new AttendanceDetailsFragment(context, subject);
        else if(position == 1)
            return new MarksDetailsFragment(context);
        else
            return new DayByDayFragment(context, subject);
    }

    @Override
    public String getPageTitle(int pos){return titles[pos];}

    @Override
    public int getCount() {
        return 3;
    }
}
