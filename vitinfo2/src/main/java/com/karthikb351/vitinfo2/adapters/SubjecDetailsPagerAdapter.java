package com.karthikb351.vitinfo2.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.karthikb351.vitinfo2.api.Objects.Course;
import com.karthikb351.vitinfo2.fragments.DetailsScreens.AttendanceDetailsFragment;
import com.karthikb351.vitinfo2.fragments.DetailsScreens.DayByDayFragment;
import com.karthikb351.vitinfo2.fragments.DetailsScreens.MarksDetailsFragment;

/**
 * Created by saurabh on 4/30/14.
 */
public class SubjecDetailsPagerAdapter extends FragmentPagerAdapter{

    String titles[];
    Context context;
    Course course;

    public SubjecDetailsPagerAdapter(Context context, Course subject, FragmentManager mgr){
        super(mgr);
        titles = new String[]{"Attendance", "Marks", "Day By Day"};
        this.context = context;
        this.course = subject;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            AttendanceDetailsFragment f = new AttendanceDetailsFragment();
            f.context = context;
            f.course = course;
            return f;
        }
        else if(position == 1){
            MarksDetailsFragment f = new MarksDetailsFragment();
            f.course = course;
            return f;
        }

        else{
            DayByDayFragment f = new DayByDayFragment();
            f.context = context;
            f.course = course;
            return f;
        }
    }

    @Override
    public String getPageTitle(int pos){return titles[pos];}

    @Override
    public int getCount() {
        return 3;
    }
}
