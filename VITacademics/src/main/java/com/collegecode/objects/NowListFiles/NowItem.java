package com.collegecode.objects.NowListFiles;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by saurabh on 4/24/14.
 */
public interface NowItem {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
