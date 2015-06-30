package com.karthikb351.vitinfo2.fragment.grades;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gaurav on 28/6/15.
 */
public class GradesFragment extends Fragment {

    public GradesFragment(){

    }

    public static GradesFragment newInstance(){

     return new GradesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
