package com.collegecode.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.collegecode.VITacademics.R;
import com.collegecode.adapters.NowFragmentListAdapter;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.NowListFiles.NowItem;
import com.collegecode.objects.NowListFiles.NowListHeader;
import com.collegecode.objects.NowListFiles.NowListItem;
import com.collegecode.objects.NowListFiles.NowListNoClass;
import com.collegecode.objects.Subject;

import java.util.ArrayList;

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

        ArrayList<NowItem> subs = new ArrayList<NowItem>();

        Subject temp = new Subject();
        temp.title = "Test Subject Name";
        temp.slot = "T1";

        subs.add(new NowListHeader("RIGHT NOW"));
        subs.add(new NowListNoClass());
        //subs.add(new NowListItem(temp));

        subs.add(new NowListHeader("TODAY"));
        subs.add(new NowListItem(temp));
        subs.add(new NowListItem(temp));
        subs.add(new NowListItem(temp));

        ListView mainList = (ListView) v.findViewById(R.id.list_now);
        mainList.setAdapter(new NowFragmentListAdapter(getActivity(), subs));
        return v;
    }
}
