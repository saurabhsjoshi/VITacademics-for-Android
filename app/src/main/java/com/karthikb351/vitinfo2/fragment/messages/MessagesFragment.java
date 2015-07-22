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

package com.karthikb351.vitinfo2.fragment.messages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MessagesFragment extends Fragment {

    private List<Message> messages;
    private RecyclerView recyclerView;
    private MessageListAdapter messageListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View rootView;

    public MessagesFragment() {
    }

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        initialize();
        return rootView;
    }

    public void initialize() {
        initializeData();
        layoutManager = new LinearLayoutManager(getActivity());
        messageListAdapter = new MessageListAdapter(getActivity(), messages);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_messages);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(messageListAdapter);
        String Title = getActivity().getResources().getString(R.string.fragment_messages_title);
        getActivity().setTitle(Title);
    }

    private void initializeData() {
        try {
            messages = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getMessages();
            Collections.reverse(messages);
            messageListAdapter.notifyDataSetChanged();
        } catch (Exception ignore) {
        }
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
