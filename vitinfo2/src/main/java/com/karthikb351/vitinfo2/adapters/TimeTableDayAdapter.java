package com.karthikb351.vitinfo2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.api.models.core.Course;

import java.util.List;

/**
 * Created by aashrai on 28/3/15.
 */
public class TimeTableDayAdapter extends RecyclerView.Adapter<TimeTableDayAdapter.ViewHolder> {


    private List<Course> courseList;

    public TimeTableDayAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView details;

        public ViewHolder(TextView v) {
            super(v);
            details = v;
        }
    }

}
