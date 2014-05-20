package com.karthikb351.vitinfo2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.Attendance;

;

/**
 * Created by saurabh on 4/30/14.
 */
public class DayByDayListAdapter extends ArrayAdapter<Attendance> {
    private LayoutInflater inflater;
    Attendance[] att;

    public DayByDayListAdapter ( Context ctx,int resid,Attendance[] att) {
        super(ctx,resid, att);
        inflater = LayoutInflater.from(ctx);
        this.att = att;
    }

    @Override
    public View getView ( int position, View view, ViewGroup parent ) {
        view = inflater.inflate(R.layout.day_by_day_list_item, null );

        ((TextView) view.findViewById(R.id.atten_detail_date)).setText(att[position].getDate());
        TextView status = (TextView) view.findViewById(R.id.atten_detail_status);
        status.setText(att[position].getStatus());
        if(att[position].getStatus().equals("Absent"))
            status.setTextColor(Color.parseColor("#FF0000"));
        return view;
    }
}
