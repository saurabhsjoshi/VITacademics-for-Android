package com.karthikb351.vitinfo2.objects.NowListFiles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;

;

/**
 * Created by saurabh on 4/24/14.
 */
public class NowListHeader implements NowItem {
    String Header;

    public NowListHeader(String Header) {
        this.Header = Header;

    }

    @Override
    public int getViewType() {
        return NowType.LIST_HEADER.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView, ViewGroup parent) {
        View view;

        if(convertView == null)
            view = inflater.inflate(R.layout.now_list_item_header, parent, false);
        else
            view = convertView;

        ((TextView) view.findViewById(R.id.lbl_now_header_title)).setText(Header);

        return view;
    }
}
