package com.collegecode.objects.NowListFiles;

import android.view.LayoutInflater;
import android.view.View;

import com.collegecode.VITacademics.R;

/**
 * Created by saurabh on 4/24/14.
 */
public class NowListNoClass implements NowItem {

    @Override
    public int getViewType() {
        return NowType.NO_CLASS.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        /* TODO: Make the textView exactly at center of image in the XML */
        view = inflater.inflate(R.layout.now_list_item_noclass,null);
        return view;
    }
}
