package com.karthikb351.vitinfo2.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateUtils;
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
import com.karthikb351.vitinfo2.TeamScreen;
import com.karthikb351.vitinfo2.VITxAPI;
import com.karthikb351.vitinfo2.adapters.NowFragmentListAdapter;
import com.karthikb351.vitinfo2.objects.CaptchaDialogListener;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.NowListFiles.NowItem;
import com.karthikb351.vitinfo2.objects.NowListFiles.NowListHeader;
import com.karthikb351.vitinfo2.objects.NowListFiles.NowListItem;
import com.karthikb351.vitinfo2.objects.NowListFiles.NowListNoClass;
import com.karthikb351.vitinfo2.objects.OnTaskComplete;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TTSlot;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TimeTable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by saurabh on 4/22/14.
 */
public class NowFragment extends Fragment {
    private SwipeRefreshLayout mPullToRefreshLayout;
    DataHandler dat;
    Context cntx;
    ListView mainList;
    private VITxAPI api;

    private OnTaskComplete l1 = new OnTaskComplete() {
        @Override
        public void onTaskCompleted(Exception e, Object result) {
            try {
                mPullToRefreshLayout.setRefreshing(false);
                if (e != null && e.getMessage().equals("needref"))
                    new CaptchaDialog(getActivity(), l2).show();
                else if (e == null) {
                    new load_Data().execute();
                    Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e1){e1.printStackTrace();}}
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
        View v = inflater.inflate(R.layout.fragment_now,container, false);
        dat = DataHandler.getInstance(getActivity());
        cntx = getActivity();

        Tracker t = ((Application) getActivity().getApplication()).getTracker(Application.TrackerName.GLOBAL_TRACKER);
        t.setScreenName("Now Fragment");
        t.send(new HitBuilders.AppViewBuilder().build());

        mPullToRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.ptr_layout);
        mPullToRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.accent));
        mPullToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                api = new VITxAPI(getActivity(), l1);
                api.loadAttendanceWithRegistrationNumber();
            }
        });

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
                subs.clear();
                subs.add(new NowListHeader("IT'S A WEEKEND!"));
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
                            subs.add(new NowListItem(cntx, ttSlots.get(i + 1)));
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

            if(ttSlots.size() == 0){
                subs.clear();
                subs.add(new NowListHeader("DONE FOR THE DAY!"));
                if(temp.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                    subs.add(new NowListHeader("ON MONDAY"));
                else
                    subs.add(new NowListHeader("TOMORROW"));

                temp.add(Calendar.DAY_OF_WEEK, 1);
                ttSlots = tt.getTT(temp.get(Calendar.DAY_OF_WEEK));

            }
            else{
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
            }

            for(int i = 0; i < ttSlots.size(); i++){
                subs.add(new NowListItem(cntx, ttSlots.get(i)));
            }

            return null;
        }

        protected void onPostExecute(Void voids){
            try {
                mainList.setAdapter(new NowFragmentListAdapter(getActivity(), subs));
                mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            TTSlot temp = ((NowListItem) mainList.getItemAtPosition(i)).ttSlot;
                            Intent intent = new Intent(getActivity(), SubjectDetails.class);
                            intent.putExtra("clsnbr", temp.clsnbr);
                            startActivity(intent);
                        } catch (Exception ignored) {}
                    }
                });

            }catch (Exception e){e.printStackTrace();}
            mPullToRefreshLayout.setRefreshing(false);


            if(!dat.getviewShowCase()){
                if(getActivity() != null)
                    getActivity().startActivity(new Intent(getActivity(), TeamScreen.class));

                dat.saveviewShowCase(true);
            }
        }
    }
}
