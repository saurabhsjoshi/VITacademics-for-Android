package com.karthikb351.vitinfo2.fragments.DetailsScreens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapters.DayByDayListAdapter;
import com.karthikb351.vitinfo2.objects.Subject;

;

/**
 * Created by saurabh on 4/30/14.
 */
public class DayByDayFragment extends Fragment {
    public Subject subject;
    public Context context;

    public DayByDayFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_by_day,container, false);
        ListView lv=(ListView)v.findViewById(R.id.atten_details_popup_listview);
        lv.setEnabled(true);
        lv.setAdapter(new DayByDayListAdapter(context, R.layout.day_by_day_list_item, subject.attendance));
        return v;
    }
}