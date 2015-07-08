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

package com.karthikb351.vitinfo2.fragment.messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.utility.DataHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MessagesFragment extends Fragment {

    List<Message> messageList;
    ArrayList<Message> messages;
    RecyclerView recyclerView;
    MessageListAdapter listAdapter;
    View rootView;

    public MessagesFragment() {
        //required empty public constructor
    }

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    public void initialize() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        messageList = DataHolder.getMessages();
        messages = new ArrayList<>(messageList);
        listAdapter = new MessageListAdapter(getActivity(), messages);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_messages);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.card_message, container, false);
        initialize();
        return rootView;
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
