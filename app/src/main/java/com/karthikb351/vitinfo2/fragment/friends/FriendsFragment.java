/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
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
import android.widget.TextView;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.utility.RecyclerViewOnClickListener;

import java.util.List;

import de.greenrobot.event.EventBus;

public class FriendsFragment extends Fragment {

    private List<Friend> friends;
    private RecyclerView friendsRecyclerView;
    private FriendsListAdapter adapter;
    private View rootView;
    int layoutId;
    TextView errorMessage;

    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(friends==null || friends.isEmpty())
            layoutId=R.layout.not_available;
        else
            layoutId=R.layout.not_available;

        rootView = inflater.inflate(layoutId, container, false);
        initialize();
        return rootView;
    }

    void initialize() {
        friends = ((MainApplication)getActivity().getApplication()).getDataHolderInstance().getFriends();

        if(layoutId==R.layout.not_available) {
            errorMessage = (TextView) rootView.findViewById(R.id.tv_message);
            errorMessage.setText("This feature is not available at the moment");
        }
        else {
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

        String Title = getActivity().getResources().getString(R.string.fragment_friends_title);
        getActivity().setTitle(Title);
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
