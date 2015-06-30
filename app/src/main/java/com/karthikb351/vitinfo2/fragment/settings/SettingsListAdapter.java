package com.karthikb351.vitinfo2.fragment.settings;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by gaurav on 21/6/15.
 */
public class SettingsListAdapter extends ArrayAdapter<String>{

    Context context;
    public ArrayList<String> topics;
    public SettingsListAdapter(Context context,int resource, ArrayList<String> topics){
        super(context,resource,topics);
        this.context=context;
        this.topics=topics;
    }
}
