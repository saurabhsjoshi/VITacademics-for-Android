package com.karthikb351.vitinfo2.fragment.messages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.contract.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaurav on 5/7/15.
 */
public class MessagesFragment extends Fragment {

    List<Message> messageList;
    ArrayList<Message> messages;
    RecyclerView recyclerView;
    MessageListAdapter listAdapter;
    View rootView;
    public MessagesFragment(){

    }

    public static MessagesFragment newInstance(){

        return new MessagesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
