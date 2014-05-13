package com.collegecode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.Friend;

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

        ((TextView) view.findViewById(R.id.lbl_title)).setText(f.title);

        return view;
    }
}
