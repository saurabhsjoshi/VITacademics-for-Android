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

package com.karthikb351.vitinfo2.fragment.friends;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.model.FriendModel;

import java.util.ArrayList;

public class    FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsViewHolder> {

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

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        public TextView FriendName, FriendRegNo;
        public ImageView FriendImage;
        public FriendsViewHolder(View itemView) {
            super(itemView);
            FriendRegNo=(TextView)itemView.findViewById(R.id.tvFriendRegistrationNumber);
            FriendName=(TextView)itemView.findViewById(R.id.tvFriendName);
            FriendImage=(ImageView)itemView.findViewById(R.id.FriendPhoto);
        }
    }
}
