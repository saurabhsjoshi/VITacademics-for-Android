package com.karthikb351.vitinfo2.fragment.TimeTable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapter.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.api.contract.Course;

import java.util.ArrayList;

/**
 * Created by pulkit on 23/06/2015.
 */
public class TimeTableListAdapter extends RecyclerView.Adapter<TimeTableListAdapter.TimeTableViewHolder> {


    RecyclerViewOnClickListener<Course> onClickListener;
    ArrayList<Course> coursesForTheDay ;
    Context context ;

    TimeTableListAdapter(Context context , ArrayList<Course> objects)
    {
        this.context=context;
        this.coursesForTheDay = objects ;
    }

    @Override
    public TimeTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView rootcard = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(R.layout.course_card, parent, false);
        return new TimeTableViewHolder(rootcard);
    }

    @Override
    public void onBindViewHolder(TimeTableViewHolder holder, int position) {

        int AttendanceP = coursesForTheDay.get(position).getAttendance().getAttendancePercentage();
        TimeTableViewHolder cvHolder = (TimeTableViewHolder) holder;
        cvHolder.courseCode.setText(coursesForTheDay.get(position).getCourseCode());
        cvHolder.courseName.setText(coursesForTheDay.get(position).getCourseTitle());
        cvHolder.Venue.setText(coursesForTheDay.get(position).getVenue());
        cvHolder.Slot.setText(coursesForTheDay.get(position).getSlot());
        cvHolder.Attendance.setText(Integer.toString(AttendanceP));
        cvHolder.pbAttendance.setProgress(AttendanceP);
    }

    public void setOnclickListener(RecyclerViewOnClickListener<Course> listener)
    {
        onClickListener = listener ;
    }

    @Override
    public int getItemCount() {
        return coursesForTheDay.size();
    }

    public class TimeTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //data to be changed based on format of timetable card

        public TextView courseName, courseCode , Attendance , Slot ,Venue;
        public ProgressBar pbAttendance;
        public TimeTableViewHolder(View v) {
            super(v);
            courseName = (TextView) v.findViewById(R.id.tvCourseName);
            courseCode = (TextView) v.findViewById(R.id.tvCourseCode);
            Attendance = (TextView)v.findViewById(R.id.tvAttendance);
            Slot = (TextView)v.findViewById(R.id.tvSlot);
            Venue = (TextView)v.findViewById(R.id.tvVenue);
            pbAttendance = (ProgressBar)v.findViewById(R.id.pbAttendance);
            pbAttendance.setMax(100);
            v.setOnClickListener(this);
        }
        public  void onClick(View v)
        {
            Course course = coursesForTheDay.get(getAdapterPosition());
            onClickListener.onItemClick(course);
        }
    }
}
