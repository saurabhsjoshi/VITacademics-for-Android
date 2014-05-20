package com.karthikb351.vitinfo2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.PushMessage;

import java.util.ArrayList;

;

/**
 * Created by saurabh on 5/19/14.
 */
public class NotificationListAdapter extends ArrayAdapter<PushMessage> {
    private LayoutInflater inflater;

    public NotificationListAdapter(Context context, ArrayList<PushMessage> msgs){
        super(context, 0, msgs);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView ( int position, View view, ViewGroup parent ) {
        view = inflater.inflate(R.layout.notification_list_item,parent, false);
        PushMessage m = getItem(position);

        ((TextView) view.findViewById(R.id.lbl_title)).setText(m.title);
        ((TextView) view.findViewById(R.id.lbl_message)).setText(m.message);

        return view;
    }

}
