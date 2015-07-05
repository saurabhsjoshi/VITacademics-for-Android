/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.fragment.contributors;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.MainActivity;
import com.karthikb351.vitinfo2.contract.Contributor;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.utility.DataHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class ContributorsFragment extends Fragment {

    List<Contributor> contributorsList;
    ArrayList<Contributor> contributors;
    RecyclerView recyclerView;
    ContributorListAdapter contributorsListAdapter;
    MainActivity mainActivity;
    RecyclerView.LayoutManager contributorsLayoutManager;
    View view;

    public ContributorsFragment(){
        //empty constructor
    }

    public ContributorsFragment newInstance(){
        return new ContributorsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.contributors,container,false);
        getDataAndInitailse();
        return view;
    }
    public void getDataAndInitailse(){
        contributorsLayoutManager=new LinearLayoutManager(getActivity());
        contributorsList= DataHolder.getContributors();
        contributors=new ArrayList<>(contributorsList);
        contributorsListAdapter=new ContributorListAdapter(getActivity(),contributors);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_contributors);
        recyclerView.setLayoutManager(contributorsLayoutManager);
        recyclerView.setAdapter(contributorsListAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    // This method will be called when a RefreshFragmentEvent is posted
    public void onEvent(RefreshFragmentEvent event) {
        // get data and reinitialize layouts
    }
}
