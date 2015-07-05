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

import android.content.Context;
import android.provider.Telephony;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Message;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    Context context;
    ArrayList<Message> messages;

    public MessageListAdapter(Context context,ArrayList<Message> messages){
        this.context=context;
        this.messages=messages;
    }
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
           holder.message.setText(messages.get(position).getMessage());
           holder.timeStamp.setText(messages.get(position).getTimestamp());
           holder.messageId.setText(messages.get(position).getMessageId());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView messageId,message,timeStamp;
        public MessageViewHolder(View view){
            super(view);
            message=(TextView)view.findViewById(R.id.tv_message);
            timeStamp=(TextView)view.findViewById(R.id.tv_message_timestamp);
            messageId=(TextView)view.findViewById(R.id.tv_message_id);        }
    }
}
