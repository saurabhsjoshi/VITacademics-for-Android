package com.karthikb351.vitinfo2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.karthikb351.vitinfo2.Application;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.SubjectDetails;
import com.karthikb351.vitinfo2.adapters.CoursesListAdapter;
import com.karthikb351.vitinfo2.api.Objects.Course;
import com.karthikb351.vitinfo2.api.Objects.VITxApi;
import com.karthikb351.vitinfo2.objects.DataHandler;

/**
 * Created by saurabh on 4/24/14.
 */
public class CoursesFragment extends Fragment {
    private ListView listView;
    private SwipeRefreshLayout mPullToRefreshLayout;
    private VITxApi api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_courses,container, false);
        listView = (ListView) v.findViewById(R.id.list);

        Tracker t = ((Application) getActivity().getApplication()).getTracker(Application.TrackerName.GLOBAL_TRACKER);
        t.setScreenName("Courses Fragment");
        t.send(new HitBuilders.AppViewBuilder().build());

        mPullToRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.ptr_layout);

        mPullToRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent));

        mPullToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                api = VITxApi.getInstance(getActivity());
                api.refreshData(new VITxApi.onTaskCompleted() {
                    @Override
                    public void onCompleted(Object result, Exception e) {
                        if(e!=null)
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        else{
                            loadData();
                            Toast.makeText(getActivity().getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                        }

                        mPullToRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        loadData();

        return v;
    }

    private void loadData(){
        try{
            listView.setAdapter(new CoursesListAdapter(getActivity(), DataHandler.getInstance(getActivity()).getAllCourses()));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        Course temp = (Course) listView.getItemAtPosition(i);
                        Intent intent = new Intent(getActivity(), SubjectDetails.class);
                        intent.putExtra("clsnbr", temp.getClassNumber());
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
        }catch (Exception e){e.printStackTrace();}
    }

}