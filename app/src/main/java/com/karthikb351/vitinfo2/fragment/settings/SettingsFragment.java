package com.karthikb351.vitinfo2.fragment.settings;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gaurav on 20/6/15.
 */
public class SettingsFragment extends ListFragment {

    public SettingsFragment(){
        //required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static SettingsFragment newInstance(){

        return new SettingsFragment();
    }

}
