package com.karthikb351.vitinfo2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.Objects.Detail;

import java.util.List;

;

/**
 * Created by saurabh on 4/30/14.
 */
public class DayByDayListAdapter extends ArrayAdapter<Detail> {
    private LayoutInflater inflater;

    public DayByDayListAdapter ( Context ctx,int resid,List<Detail> att) {
        super(ctx,resid, att);
        inflater = LayoutInflater.from(ctx);
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

        holder.date.setText(getItem((super.getCount()-1) - position).getDate());
        holder.status.setText(getItem((super.getCount()-1) -position).getStatus());
        if(getItem((super.getCount()-1) -position).getStatus().equals("Absent"))
            holder.status.setTextColor(Color.parseColor("#FF0000"));

        return convertview;
    }
}
