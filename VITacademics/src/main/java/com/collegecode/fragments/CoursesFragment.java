package com.collegecode.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.collegecode.VITacademics.R;
import com.collegecode.adapters.CoursesListAdapter;
import com.collegecode.objects.DataHandler;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_courses,container, false);
        listView = (ListView) v.findViewById(R.id.list);

        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);

        OnRefreshListener listener = new OnRefreshListener() {
            @Override
            public void onRefreshStarted(View view) {
                mPullToRefreshLayout.setRefreshComplete();
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
