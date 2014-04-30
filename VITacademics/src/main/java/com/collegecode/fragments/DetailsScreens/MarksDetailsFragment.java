package com.collegecode.fragments.DetailsScreens;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegecode.VITacademics.R;

/**
 * Created by saurabh on 4/30/14.
 */
public class MarksDetailsFragment extends Fragment {

    public MarksDetailsFragment(Context context){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_attendance_details,container, false);
        return v;
    }
}
