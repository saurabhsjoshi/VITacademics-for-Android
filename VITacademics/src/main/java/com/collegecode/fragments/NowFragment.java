package com.collegecode.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
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
import com.collegecode.objects.TimeTableFiles.TTSlot;
import com.collegecode.objects.TimeTableFiles.TimeTable;

import java.util.ArrayList;
import java.util.Calendar;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
/**
 * Created by saurabh on 4/22/14.
 */
public class NowFragment extends Fragment {
    private PullToRefreshLayout mPullToRefreshLayout;
    DataHandler dat;
    Context cntx;
    ListView mainList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_now,container, false);
        dat = new DataHandler(getActivity());
        cntx = getActivity();

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

        mainList = (ListView) v.findViewById(R.id.list_now);

        new load_Data().execute();
        return v;
    }

    private class load_Data extends AsyncTask<Void,Void,Void>{
        ArrayList<NowItem> subs;
        ArrayList<TTSlot> ttSlots;

        protected void onPreExecute(){
            subs = new ArrayList<NowItem>();
            mPullToRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            subs.add(new NowListHeader("RIGHT NOW"));
            TimeTable tt = new TimeTable(cntx);

            int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            boolean not_weekend = true;

            /* TODO: CLEANUP THIS CODE AND MAKE IT BETTER */
            Calendar temp = Calendar.getInstance();
            if(today == Calendar.SUNDAY || today == Calendar.SATURDAY) {
                subs.add(new NowListNoClass());
                subs.add(new NowListHeader("ON MONDAY"));
                today = Calendar.MONDAY;
                not_weekend = false;
                ttSlots = tt.getTT(today);
            }

            else
            {
                ttSlots = tt.getTT(today);
                boolean noClass = true;

                for(int i = 0; i < ttSlots.size(); i++){
                    if (temp.compareTo(ttSlots.get(i).frm_time) >= 0 && temp.compareTo(ttSlots.get(i).to_time) < 0){
                        noClass = false;
                        subs.add(new NowListItem(cntx, ttSlots.get(i)));
                        if(i != ttSlots.size()-1){
                            String text = DateUtils.getRelativeTimeSpanString( ttSlots.get(i+1).frm_time.getTimeInMillis(), temp.getTimeInMillis(), 0).toString();
                            subs.add(new NowListHeader("NEXT " + text.toUpperCase()));
                            subs.add(new NowListItem(cntx, ttSlots.get(i+1)));
                        }
                        break;
                    }
                }

                if(noClass){
                    subs.add(new NowListNoClass());
                    for(int i = 0; i < ttSlots.size(); i++){
                        if(temp.compareTo(ttSlots.get(i).frm_time) < 0){
                            String text = DateUtils.getRelativeTimeSpanString(ttSlots.get(i).frm_time.getTimeInMillis(), temp.getTimeInMillis(), 0).toString();
                            subs.add(new NowListHeader("NEXT " + text.toUpperCase()));
                            subs.add(new NowListItem(cntx, ttSlots.get(i)));
                            break;
                        }
                    }
                }
                subs.add(new NowListHeader("TODAY"));
            }

            if(temp.compareTo(ttSlots.get(ttSlots.size()-1).to_time) > 0 && not_weekend)
            {
                subs.clear();
                subs.add(new NowListHeader("DONE FOR THE DAY!"));
                if(temp.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                    subs.add(new NowListHeader("ON MONDAY"));
                else
                    subs.add(new NowListHeader("TOMORROW"));

                temp.add(Calendar.DAY_OF_WEEK, 1);
                ttSlots = tt.getTT(temp.get(Calendar.DAY_OF_WEEK));
            }

            for(int i = 0; i < ttSlots.size(); i++){
                subs.add(new NowListItem(cntx, ttSlots.get(i)));
            }

            return null;
        }

        protected void onPostExecute(Void voids){
            mainList.setAdapter(new NowFragmentListAdapter(getActivity(), subs));
            mPullToRefreshLayout.setRefreshComplete();
        }
    }
}
