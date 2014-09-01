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

    private class ViewHolder{
        TextView date, status;
    }

    @Override
    public View getView ( int position, View convertview, ViewGroup parent ) {
        ViewHolder holder;
        //if(convertview == null){
            convertview = inflater.inflate(R.layout.day_by_day_list_item, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertview.findViewById(R.id.atten_detail_date);
            holder.status = (TextView) convertview.findViewById(R.id.atten_detail_status);
            convertview.setTag(holder);
        //}
        //else
           // holder = (ViewHolder) convertview.getTag();

        holder.date.setText(att[position].getDate());
        holder.status.setText(att[position].getStatus());
        if(att[position].getStatus().equals("Absent"))
            holder.status.setTextColor(Color.parseColor("#FF0000"));

        return convertview;
    }
}
