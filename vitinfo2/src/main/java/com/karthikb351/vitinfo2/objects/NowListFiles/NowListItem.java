package com.karthikb351.vitinfo2.objects.NowListFiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.Objects.Course;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TTSlot;

import java.text.SimpleDateFormat;
import java.util.Locale;

;

/**
 * Created by saurabh on 4/24/14.
 */
public class NowListItem implements NowItem {
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
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null)
            view = inflater.inflate(R.layout.now_list_item_now, parent, false);
        else
            view = convertView;

        TextView lbl_subject = (TextView) view.findViewById(R.id.lbl_now_subject);
        TextView lbl_slot = (TextView) view.findViewById(R.id.lbl_now_slot);
        TextView lbl_venue = (TextView) view.findViewById(R.id.lbl_now_venue);
        TextView lbl_per = (TextView) view.findViewById(R.id.lbl_now_att_now);
        TextView lbl_timing = (TextView) view.findViewById(R.id.lbl_now_timing);

        DataHandler dat = DataHandler.getInstance(context);
        Course course = dat.getCourse(ttSlot.clsnbr);

        lbl_subject.setText(course.getCourseTitle());
        lbl_slot.setText(ttSlot.slt);
        lbl_venue.setText(ttSlot.venue);

        getGo(course.getAttendance().getAttendedClasses(), course.getAttendance().getTotalClasses(), (TextView) view.findViewById(R.id.lbl_now_att_go));
        getMiss(course.getAttendance().getAttendedClasses(), course.getAttendance().getTotalClasses(), (TextView) view.findViewById(R.id.lbl_now_att_miss));

        lbl_per.setText(course.getAttendance().getAttendancePercentage());

        float per = DataHandler.getPer(Integer.parseInt(course.getAttendance().getAttendedClasses()),
                Integer.parseInt(course.getAttendance().getTotalClasses()));

        lbl_per.setTextColor(DataHandler.getPerColor((int)per));

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.getDefault());
        lbl_timing.setText(timeFormat.format(ttSlot.frm_time.getTime()) + " - " + timeFormat.format(ttSlot.to_time.getTime()));
        return view;
    }

    private void getGo(String attS, String condS, TextView lbl_go){
        int att = Integer.parseInt(attS), cond = Integer.parseInt(condS);
        att += 1;
        cond += 1;
        int per = (int) DataHandler.getPer(att, cond);

        if (DataHandler.getPer(att, cond) > per)
            per += 1;

        lbl_go.setText("Go: " + Integer.toString(per) + "%");
        lbl_go.setTextColor(DataHandler.getPerColor(per));
    }

    private void getMiss(String attS, String condS, TextView lbl_miss){
        int att = Integer.parseInt(attS), cond = Integer.parseInt(condS);
        cond += 1;
        int per = (int) DataHandler.getPer(att, cond);

        if (DataHandler.getPer(att, cond) > per)
            per += 1;
        lbl_miss.setText("Miss: " + Integer.toString(per) + "%");
        lbl_miss.setTextColor(DataHandler.getPerColor(per));
    }
}
