package com.karthikb351.vitinfo2.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.karthikb351.vitinfo2.Application;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.SubjectDetails;
import com.karthikb351.vitinfo2.adapters.FullTTListAdapter;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TTSlot;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TimeTable;

import java.util.ArrayList;
import java.util.Calendar;

;

/**
 * Created by saurabh on 4/28/14.
 */
public class FullTTListFragment extends Fragment{

    public int day = 0;
    public Context context;

    public FullTTListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fullttlist,container, false);

        final ListView listView = (ListView) v.findViewById(R.id.list_one_tt);

        TimeTable timeTable = new TimeTable(context);

        ArrayList<TTSlot> ttSlots;

        Tracker t = ((Application) getActivity().getApplication()).getTracker(Application.TrackerName.GLOBAL_TRACKER);
        t.setScreenName("FullTT Fragment");
        t.send(new HitBuilders.AppViewBuilder().build());

        switch (day){
            case 0:
                ttSlots = timeTable.getTT(Calendar.MONDAY);
                break;
            case 1:
                ttSlots = timeTable.getTT(Calendar.TUESDAY);
                break;
            case 2:
                ttSlots = timeTable.getTT(Calendar.WEDNESDAY);
                break;
            case 3:
                ttSlots = timeTable.getTT(Calendar.THURSDAY);
                break;
            case 4:
                ttSlots = timeTable.getTT(Calendar.FRIDAY);
                break;
            default:
                ttSlots = timeTable.getTT(Calendar.MONDAY);
                break;
        }

        listView.setAdapter(new FullTTListAdapter(context, ttSlots));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TTSlot temp = (TTSlot) listView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), SubjectDetails.class);
                intent.putExtra("clsnbr", temp.clsnbr);
                startActivity(intent);
            }
        });
        return v;
    }
}
