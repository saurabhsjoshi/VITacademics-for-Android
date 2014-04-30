package com.collegecode.objects.NowListFiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.Subject;
import com.collegecode.objects.TimeTableFiles.TTSlot;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by saurabh on 4/24/14.
 */
public class NowListItem implements NowItem{
    public TTSlot ttSlot;
    Context context;

    public NowListItem(Context context, TTSlot subject){
        this.context = context;
        this.ttSlot = subject;
    }

    @Override
    public int getViewType() {
        return NowType.NOW_CLASS.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if(convertView == null)
            view = inflater.inflate(R.layout.now_list_item_now, null);
        else
            view = convertView;

        TextView lbl_subject = (TextView) view.findViewById(R.id.lbl_now_subject);
        TextView lbl_slot = (TextView) view.findViewById(R.id.lbl_now_slot);
        TextView lbl_venue = (TextView) view.findViewById(R.id.lbl_now_venue);
        TextView lbl_per = (TextView) view.findViewById(R.id.lbl_now_att_now);
        TextView lbl_timing = (TextView) view.findViewById(R.id.lbl_now_timing);

        DataHandler dat = new DataHandler(context);
        Subject subject = dat.getSubject(ttSlot.clsnbr);

        lbl_subject.setText(subject.title);
        lbl_slot.setText(ttSlot.slt);
        lbl_venue.setText(ttSlot.venue);


        getGo(subject.attended, subject.conducted, (TextView) view.findViewById(R.id.lbl_now_att_go));
        getMiss(subject.attended, subject.conducted, (TextView) view.findViewById(R.id.lbl_now_att_miss));

        lbl_per.setText(subject.percentage + "%");
        lbl_per.setTextColor(DataHandler.getPerColor(subject.percentage));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.getDefault());
        lbl_timing.setText(timeFormat.format(ttSlot.frm_time.getTime()) + " - " + timeFormat.format(ttSlot.to_time.getTime()));
        return view;
    }

    private void getGo(int att, int cond, TextView lbl_go){
        att += 1;
        cond += 1;
        int per = (int) DataHandler.getPer(att,cond);

        if (DataHandler.getPer(att, cond) > per)
            per += 1;

        lbl_go.setText("Go: " + Integer.toString(per) + "%");
        lbl_go.setTextColor(DataHandler.getPerColor(per));
    }

    private void getMiss(int att, int cond, TextView lbl_miss){
        cond += 1;
        int per = (int) DataHandler.getPer(att,cond);

        if (DataHandler.getPer(att,cond) > per)
            per += 1;
        lbl_miss.setText("Miss: " + Integer.toString(per) + "%");
        lbl_miss.setTextColor(DataHandler.getPerColor(per));
    }
}
