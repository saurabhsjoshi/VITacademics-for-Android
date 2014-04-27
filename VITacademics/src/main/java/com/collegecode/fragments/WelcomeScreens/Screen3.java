package com.collegecode.fragments.WelcomeScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegecode.VITacademics.R;

/**
 * Created by saurabh on 4/27/14.
 */
public class Screen3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newuser_3,container, false);

        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Finalizing");

        return view;
    }

}
