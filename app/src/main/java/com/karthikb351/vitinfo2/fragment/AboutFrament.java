package com.karthikb351.vitinfo2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;


public class AboutFrament extends Fragment {

  public static AboutFrament newInstance(String param1, String param2) {
        AboutFrament fragment = new AboutFrament();
        return fragment;
    }

    public AboutFrament() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_frament, container, false);
    }


}
