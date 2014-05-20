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
        dat = new DataHandler(context);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        convertView = mInflater.inflate(R.layout.fulltt_list_item,null);

        TTSlot t = getItem(pos);
        Subject subject = dat.getSubject(t.clsnbr);

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.getDefault());

        ((TextView) convertView.findViewById(R.id.lbl_now_slot)).setText(t.slt);
        ((TextView) convertView.findViewById(R.id.lbl_now_subject)).setText(subject.title);
        ((TextView) convertView.findViewById(R.id.lbl_now_venue)).setText(t.venue);

        TextView lbl_timing = (TextView) convertView.findViewById(R.id.lbl_now_timing);
        TextView lbl_per = (TextView) convertView.findViewById(R.id.lbl_now_att_now);

        lbl_per.setText("Attendance: "+ subject.percentage + "%");
        lbl_per.setTextColor(DataHandler.getPerColor(subject.percentage));
        lbl_timing.setText(timeFormat.format(t.frm_time.getTime()) + " - " + timeFormat.format(t.to_time.getTime()));
        return convertView;
    }


}
