package com.collegecode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.collegecode.objects.NowListFiles.NowItem;
import com.collegecode.objects.NowListFiles.NowType;

import java.util.ArrayList;

/**
 * Created by saurabh on 4/24/14.
 */
public class NowFragmentListAdapter extends ArrayAdapter<NowItem> {
    private LayoutInflater mInflater;

    public NowFragmentListAdapter(Context context, ArrayList<NowItem> items){
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return NowType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position).getView(mInflater, convertView);
    }
}
