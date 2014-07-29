package com.karthikb351.vitinfo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.Subject;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TTSlot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

;

/**
 * Created by saurabh on 4/28/14.
 */
public class FullTTListAdapter extends ArrayAdapter<TTSlot> {
    private LayoutInflater mInflater;
    private DataHandler dat;


    public FullTTListAdapter(Context context, ArrayList<TTSlot> items){
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
        dat = DataHandler.getInstance(context);
    }
    private class ViewHolder{
        TextView slot, subject, venue, timing, att;
    }
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.fulltt_list_item, parent, false);
            holder = new ViewHolder();
            holder.slot = ((TextView) convertView.findViewById(R.id.lbl_now_slot));
            holder.subject = ((TextView) convertView.findViewById(R.id.lbl_now_subject));
            holder.venue = ((TextView) convertView.findViewById(R.id.lbl_now_venue));
            holder.timing = ((TextView) convertView.findViewById(R.id.lbl_now_timing));
            holder.att = ((TextView) convertView.findViewById(R.id.lbl_now_att_now));
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        TTSlot t = getItem(pos);
        Subject subject = dat.getSubject(t.clsnbr);

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.getDefault());

        holder.slot.setText(t.slt);
        holder.subject.setText(subject.title);
        holder.venue.setText(t.venue);

        holder.att.setText("Attendance: "+ subject.percentage + "%");
        holder.att.setTextColor(DataHandler.getPerColor(subject.percentage));
        holder.timing.setText(timeFormat.format(t.frm_time.getTime()) + " - " + timeFormat.format(t.to_time.getTime()));
        return convertView;
    }


}
