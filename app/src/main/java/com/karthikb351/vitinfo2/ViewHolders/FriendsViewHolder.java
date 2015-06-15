package com.karthikb351.vitinfo2.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;

/**
 * Created by gaurav on 15/6/15.
 */
public class FriendsViewHolder extends RecyclerView.ViewHolder {

    public TextView FriendName, FriendRegNo;
    public ImageView FriendImage;
    public FriendsViewHolder(View itemView) {
        super(itemView);
        FriendRegNo=(TextView)itemView.findViewById(R.id.tvFriendRegistrationNumber);
        FriendName=(TextView)itemView.findViewById(R.id.tvFriendName);
        FriendImage=(ImageView)itemView.findViewById(R.id.FriendPhoto);
    }
}
