package com.karthikb351.vitinfo2.fragment.attendance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;

/**
 * Created by gaurav on 5/7/15.
 */
public class AttendanceFragment extends Fragment{

    Course course;
    RecyclerView recyclerView;
    AttendanceListAdapter listAdapter;

    public AttendanceFragment(){

    }

    public static AttendanceFragment newInstance(Course course){
        AttendanceFragment attendanceFragment= new AttendanceFragment();
        attendanceFragment.course=course;
        return attendanceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.attendance,container,false);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        listAdapter=new AttendanceListAdapter(getActivity(),course);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view_attendance);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
