package com.karthikb351.vitinfo2.fragments;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.karthikb351.vitinfo2.VITxAPI;
import com.karthikb351.vitinfo2.adapters.CoursesListAdapter;
import com.karthikb351.vitinfo2.objects.CaptchaDialogListener;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.OnTaskComplete;
import com.karthikb351.vitinfo2.objects.Subject;

import java.util.ArrayList;

/**
 * Created by saurabh on 4/24/14.
 */
public class CoursesFragment extends Fragment {
    private ListView listView;
    private SwipeRefreshLayout mPullToRefreshLayout;
    private VITxAPI api;

    private OnTaskComplete l1 = new OnTaskComplete() {
        @Override
        public void onTaskCompleted(Exception e, Object result) {
            try {
                mPullToRefreshLayout.setRefreshing(false);
                if (e != null && e.getMessage().equals("needref"))
                    new CaptchaDialog(getActivity(), l2).show();
                else if (e == null) {
                    new Load_Data().execute();
                    Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e1){e1.printStackTrace();}
        }
    };

    private CaptchaDialogListener l2 = new CaptchaDialogListener() {
        @Override
        public void onTaskCompleted(String Captcha, Boolean Response) {
            if(Response)
            {
                api.changeListner(l3);
                api.Captcha = Captcha;
                mPullToRefreshLayout.setRefreshing(true);
                api.submitCaptcha();
            }
        }
    };

    private OnTaskComplete l3 = new OnTaskComplete() {
        @Override
        public void onTaskCompleted(Exception e, Object result) {
            try {
                mPullToRefreshLayout.setRefreshing(false);
                if (e != null && e.getMessage().equals("cape"))
                    Toast.makeText(getActivity(), "Incorrect Captcha. Please try again!", Toast.LENGTH_SHORT).show();
                else if (e == null) {
                    api.changeListner(l1);
                    api.loadAttendanceWithRegistrationNumber();
                } else
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }catch (Exception e1){e1.printStackTrace();}}
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_courses,container, false);
        listView = (ListView) v.findViewById(R.id.list);

        Tracker t = ((Application) getActivity().getApplication()).getTracker(Application.TrackerName.GLOBAL_TRACKER);
        t.setScreenName("Courses Fragment");
        t.send(new HitBuilders.AppViewBuilder().build());

        mPullToRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.ptr_layout);

        mPullToRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.HoloBright),
                getResources().getColor(R.color.HoloOrange),
                getResources().getColor(R.color.HoloGreen),
                getResources().getColor(R.color.HoloRed));

        mPullToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                api = new VITxAPI(getActivity(), l1);
                api.loadAttendanceWithRegistrationNumber();
            }
        });

        new Load_Data().execute();

        return v;
    }

    private class Load_Data extends AsyncTask<Void,Void,Void>{
        private ArrayList<Subject> subs;

        protected void onPreExecute(){
            subs = new ArrayList<Subject>();
            mPullToRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
            subs = new DataHandler(getActivity()).getAllSubjects();
            }catch (Exception e){e.printStackTrace();}
            return null;
        }

        protected void onPostExecute(Void voids){
            try{
                listView.setAdapter(new CoursesListAdapter(getActivity(), subs));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            Subject temp = (Subject) listView.getItemAtPosition(i);
                            Intent intent = new Intent(getActivity(), SubjectDetails.class);
                            intent.putExtra("clsnbr", temp.classnbr);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
            }catch (Exception e){e.printStackTrace();}
            mPullToRefreshLayout.setRefreshing(false);
        }

    }

}