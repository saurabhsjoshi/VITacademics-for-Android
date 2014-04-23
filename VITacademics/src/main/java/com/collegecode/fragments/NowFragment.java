package com.collegecode.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.DataHandler;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
/**
 * Created by saurabh on 4/22/14.
 */
public class NowFragment extends Fragment {
    private PullToRefreshLayout mPullToRefreshLayout;
    DataHandler dat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now,container, false);
        dat = new DataHandler(getActivity());

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

        return v;
    }
}
