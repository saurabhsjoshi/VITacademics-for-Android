package com.karthikb351.vitinfo2.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.Friend;
import com.karthikb351.vitinfo2.objects.RecyclerViewOnClickListener;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TimeTable;

import java.util.ArrayList;

/**
 * Created by saurabh on 7/20/14.
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener{

    private ArrayList<Friend> friends;
    private int itemLayout;
    private Context context;
    private RecyclerViewOnClickListener listener;

    public FriendsAdapter(Context context, ArrayList<Friend> friends, RecyclerViewOnClickListener l) {
        this.context = context;
        this.friends = friends;
        this.listener = l;
        this.itemLayout = R.layout.friends_list_item;
    }

    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        v.setOnClickListener(FriendsAdapter.this);
        v.setOnLongClickListener(FriendsAdapter.this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friend f = friends.get(position);
        if(f.isFb && f.img_profile!=null)
            holder.image.setImageBitmap(f.img_profile);
        else
            holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_person));

        holder.name.setText(f.title);
        TimeTable t = new TimeTable(context);

        if(t.getFriendStatus(f.timetable)){
            holder.status.setText("In class");
            holder.status.setTextColor(Color.parseColor("#ffa500"));
            holder.venue.setText("Class ends " + t.FreindEndsIn);
        }

        else {
            holder.status.setText("Idle");
            holder.status.setTextColor(Color.parseColor("#008000"));
            holder.endsin.setText("");
        }

        holder.venue.setText(t.FriendVenue);
        holder.itemView.setTag(f);

    }

    @Override public int getItemCount() {
        return friends.size();
    }

    @Override
    public void onClick(View view) {
        Friend f = (Friend) view.getTag();
        for(int i = 0; i < getItemCount(); i++){
            if(f.regno.equals(friends.get(i).regno)){
                listener.onClick(friends.get(i), false);
                return;
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        Friend f = (Friend) view.getTag();
        for(int i = 0; i < getItemCount(); i++){
            if(f.regno.equals(friends.get(i).regno)){
                listener.onClick(friends.get(i), true);
                return true;
            }
        }
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView status;
        public TextView endsin;
        public TextView venue;

        public ViewHolder(View itemView) {
            super(itemView);
            image =  (ImageView) itemView.findViewById(R.id.img_profile);
            name = (TextView) itemView.findViewById(R.id.lbl_title);
            status = (TextView) itemView.findViewById(R.id.lbl_status);
            endsin = (TextView) itemView.findViewById(R.id.lbl_endsin);
            venue = (TextView) itemView.findViewById(R.id.lbl_venue);
        }
    }
}
