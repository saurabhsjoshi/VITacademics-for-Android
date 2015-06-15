package com.karthikb351.vitinfo2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.ViewHolders.CourseViewHolder;
import com.karthikb351.vitinfo2.ViewHolders.FriendsViewHolder;
import com.karthikb351.vitinfo2.model.FriendModel;

import java.util.ArrayList;

/**
 * Created by gaurav on 15/6/15.
 */
public class FriendsListAdapter extends RecyclerView.Adapter<FriendsViewHolder> {

        ArrayList<FriendModel> friends;

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
        FriendsViewHolder cvHolder = (FriendsViewHolder) holder;
        cvHolder.FriendName.setText(friends.get(position).FriendName);
        cvHolder.FriendRegNo.setText(friends.get(position).FriendRegNo);
        cvHolder.FriendImage.setImageResource(friends.get(position).FriendPhotoId);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
