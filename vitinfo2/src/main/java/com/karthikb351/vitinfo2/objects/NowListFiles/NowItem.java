package com.karthikb351.vitinfo2.objects.NowListFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by saurabh on 4/24/14.
 */
public interface NowItem {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent);
}
