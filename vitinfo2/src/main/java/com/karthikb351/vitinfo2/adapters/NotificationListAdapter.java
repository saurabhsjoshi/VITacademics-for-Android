package com.karthikb351.vitinfo2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.PushMessage;
import com.karthikb351.vitinfo2.objects.RecyclerViewOnClickListener;

import java.util.ArrayList;

;

/**
 * Created by saurabh on 5/19/14.
 */
public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    ArrayList<PushMessage> msgs;
    RecyclerViewOnClickListener listener;

    public NotificationListAdapter(ArrayList<PushMessage> msgs, RecyclerViewOnClickListener listener){
        this.msgs = msgs;
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view.getTag(), false);
    }

    @Override
    public boolean onLongClick(View view) {
        listener.onClick(view.getTag(), true);
        return true;
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        PushMessage m = msgs.get(position);
        holder.title.setText(m.title);
        holder.message.setText(m.message);
        holder.itemView.setTag(m);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);
        v.setOnLongClickListener(this);
        v.setOnLongClickListener(this);
        return new ViewHolder(v);
    }

    @Override public int getItemCount() {
        return msgs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.lbl_title);
            title = (TextView) itemView.findViewById(R.id.lbl_message);
        }
    }

}
