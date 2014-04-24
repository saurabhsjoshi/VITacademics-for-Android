package com.collegecode.objects.NowListFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.Subject;

/**
 * Created by saurabh on 4/24/14.
 */
public class NowListItem implements NowItem{
    Subject subject;

    public NowListItem(Subject subject){
        this.subject = subject;
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

        lbl_subject.setText(subject.title);
        lbl_slot.setText(subject.slot);
        return view;
    }
}
