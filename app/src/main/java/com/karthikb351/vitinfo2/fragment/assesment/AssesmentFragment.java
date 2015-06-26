package com.karthikb351.vitinfo2.fragment.assesment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;

/**
 * Created by gaurav on 27/6/15.
 */
public class AssesmentFragment extends Fragment{

    public AssesmentFragment(){

    }

    public static AssesmentFragment newInstance(){
        return new AssesmentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assesment,container,false);
        return view;
    }
}
