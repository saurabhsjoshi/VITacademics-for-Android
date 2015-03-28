package com.karthikb351.vitinfo2.fragments.SubFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.support.v7.widget.RecyclerView.LayoutManager;
import static android.view.ViewGroup.LayoutParams;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by aashrai on 28/3/15.
 */
public class TimetableDayFragment extends Fragment {

    RecyclerView rvDay;
    LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rvDay = new RecyclerView(getActivity());
        rvDay.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        rvDay.setVerticalScrollBarEnabled(false);
        rvDay.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rvDay.setLayoutManager(layoutManager);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
