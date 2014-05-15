package com.collegecode.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.Friend;
import com.collegecode.objects.TimeTableFiles.TimeTable;

import java.util.ArrayList;

/**
 * Created by saurabh on 5/12/14.
 */
public class FreindsListAdapter extends ArrayAdapter<Friend> {

    private LayoutInflater inflater;
    private DataHandler dat;
    private Context context;

    public FreindsListAdapter(Context context,  ArrayList<Friend> friends){
        super(context,0,friends);

        dat = new DataHandler(context);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView ( int position, View view, ViewGroup parent ) {
        Friend f = getItem(position);
        view = inflater.inflate(R.layout.friends_list_item,parent, false);

        if(f.isFb && f.img_profile!=null)
            ((ImageView) view.findViewById(R.id.img_profile)).setImageBitmap(f.img_profile);

        ((TextView) view.findViewById(R.id.lbl_title)).setText(f.title);
        TimeTable t = new TimeTable(context);

        TextView txt_status = (TextView) view.findViewById(R.id.lbl_status);

        if(t.getFriendStatus(f.timetable)){
            txt_status.setText("In class");
            txt_status.setTextColor(Color.parseColor("#ffa500"));
            ((TextView) view.findViewById(R.id.lbl_endsin)).setText("Class ends " + t.FreindEndsIn);
        }

        else {
            txt_status.setText("Idle");
            txt_status.setTextColor(Color.parseColor("#008000"));
            ((TextView) view.findViewById(R.id.lbl_endsin)).setText("");
        }

        ((TextView) view.findViewById(R.id.lbl_venue)).setText(t.FriendVenue);

        return view;
    }
}
