package com.karthikb351.vitinfo2.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.Constants;

/**
 * Created by aashrai on 28/3/15.
 */
public class TimetableAdapter extends PagerAdapter {


    @Override
    public int getCount() {
        return Constants.days.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RecyclerView recyclerView = new RecyclerView(container.getContext());

        //TODO pass the courses for the day as a list
        TimeTableDayAdapter mAdapter = new TimeTableDayAdapter(null);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(container.getContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        container.addView(recyclerView, position);
        return recyclerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((RecyclerView) view);
    }


}
