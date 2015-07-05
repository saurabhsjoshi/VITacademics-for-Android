package com.karthikb351.vitinfo2.fragment.attendance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Attendance;
import com.karthikb351.vitinfo2.contract.AttendanceDetail;
import com.karthikb351.vitinfo2.contract.Course;

/**
 * Created by gaurav on 5/7/15.
 */
public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.AttendanceViewHolder> {

    int layoutId;
    Context context;
    Course course;
    Attendance attendance;
    AttendanceDetail attendanceDetail[];

    public AttendanceListAdapter(Context context,Course course){
        this.context=context;
        this.course=course;
        attendance=course.getAttendance();
        attendanceDetail=attendance.getDetails();
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(viewType,parent,false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (attendanceDetail.length+1);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            layoutId= R.layout.card_attendance;
        else
            layoutId=R.layout.attendance_details;
        return layoutId;
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder{

        public AttendanceViewHolder(View view){
            super(view);
        }
    }

}
