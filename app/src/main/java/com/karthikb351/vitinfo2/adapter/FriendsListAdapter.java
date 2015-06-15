package com.karthikb351.vitinfo2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
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
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.freinds_card, parent, false);

        return new FriendsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
        holder.FriendName.setText(friends.get(position).FriendName);
        holder.FriendRegNo.setText(friends.get(position).FriendRegNo);
        holder.FriendImage.setImageResource(friends.get(position).FriendPhotoId);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
