package com.karthikb351.vitinfo2.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.karthikb351.vitinfo2.fragments.FullTTListFragment;

/**
 * Created by saurabh on 4/28/14.
 */
public class FullTTPagerAdapter extends FragmentPagerAdapter {
    String days[];
    Context context;

    public FullTTPagerAdapter(Context context, FragmentManager mgr){
        super(mgr);
        this.context = context;
        days=new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
    }

    @Override
    public Fragment getItem(int position) {
        return new FullTTListFragment(position, context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public String getPageTitle(int position) {
        return(days[position]);
    }
}
