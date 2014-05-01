package com.collegecode.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.collegecode.VITacademics.R;
import com.collegecode.VITacademics.VITxAPI;
import com.collegecode.adapters.CoursesListAdapter;
import com.collegecode.objects.CaptchaDialogListener;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.OnTaskComplete;
import com.collegecode.objects.Subject;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by saurabh on 4/24/14.
 */
public class CoursesFragment extends Fragment {
    private ListView listView;
    private PullToRefreshLayout mPullToRefreshLayout;
    private VITxAPI api;

    private OnTaskComplete l1 = new OnTaskComplete() {
        @Override
        public void onTaskCompleted(Exception e, Object result) {
            mPullToRefreshLayout.setRefreshComplete();
            if(e!=null && e.getMessage().equals("needref"))
                new CaptchaDialog(getActivity(), l2).show();
            else if(e == null){
                new Load_Data().execute();
                Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            mPullToRefreshLayout.setRefreshComplete();
            if(e != null && e.getMessage().equals("cape"))
                Toast.makeText(getActivity(), "Incorrect Captcha. Please try again!", Toast.LENGTH_SHORT).show();
            else if (e == null){
                new Load_Data().execute();
                Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_courses,container, false);
        listView = (ListView) v.findViewById(R.id.list);

        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);

        OnRefreshListener listener = new OnRefreshListener() {
            @Override
            public void onRefreshStarted(View view) {
                api = new VITxAPI(getActivity(), l1);
                api.loadAttendanceWithRegistrationNumber();
            }
        };

        ActionBarPullToRefresh.from(getActivity())
                // Mark All Children as pullable
                .allChildrenArePullable()
                        // Set a OnRefreshListener
                .listener(listener)
                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);

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
            subs = new DataHandler(getActivity()).getAllSubjects();
            return null;
        }

        protected void onPostExecute(Void voids){
            listView.setAdapter(new CoursesListAdapter(getActivity(), subs));
            mPullToRefreshLayout.setRefreshComplete();
        }

    }

}
