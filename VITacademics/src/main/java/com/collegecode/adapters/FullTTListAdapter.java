package com.collegecode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.Subject;
import com.collegecode.objects.TimeTableFiles.TTSlot;

import java.util.ArrayList;

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
        Subject sub = dat.getSubject(t.clsnbr);

        ((TextView) convertView.findViewById(R.id.lbl_now_slot)).setText(t.slt);
        ((TextView) convertView.findViewById(R.id.lbl_now_subject)).setText(sub.title);
        return convertView;
    }
}
