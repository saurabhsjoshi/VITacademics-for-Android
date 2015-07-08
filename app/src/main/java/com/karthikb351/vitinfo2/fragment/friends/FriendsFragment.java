/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 *
 * This file is part of VITacademics.
 * VITacademics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VITacademics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VITacademics.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.fragment.friends;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class FriendsFragment extends Fragment {

    ArrayList<Friend> friends;
    RecyclerView friendsRecyclerView;
    FriendsListAdapter adapter;
    View rootView;

    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance() {
        FriendsFragment fragment = new FriendsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        initialize();
        return rootView;
    }

    void initialize() {
        friends = new ArrayList<Friend>();
        RecyclerView.LayoutManager friendsLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new FriendsListAdapter(getActivity(), friends);
        friendsRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_friends);
        friendsRecyclerView.setLayoutManager(friendsLayoutManager);
        friendsRecyclerView.setAdapter(adapter);
        adapter.setOnclickListener(new RecyclerViewOnClickListener<Friend>() {
            @Override
            public void onItemClick(Friend data) {
                onListItemClick(data);
            }
        });
    }

    void onListItemClick(Friend friend) {
        // add on item click functionality
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
        initialize();
    }
}
