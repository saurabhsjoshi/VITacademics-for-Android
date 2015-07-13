/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
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

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.DateTime;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {

    private Context context;
    private List<Message> messages;

    public MessageListAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.card_message, parent, false);
        return new MessageViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.messageId.setText(Integer.toString(messages.get(position).getMessageId()));
        holder.message.setText(messages.get(position).getMessage());

        try {
            holder.timestamp.setText(DateTime.parseISO8601DateTime(messages.get(position).getTimestamp()));

        } catch (ParseException ex) {
            holder.timestamp.setText(messages.get(position).getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageId, message, timestamp;

        public MessageViewHolder(View view) {
            super(view);

            messageId = (TextView) view.findViewById(R.id.message_id);
            message = (TextView) view.findViewById(R.id.message);
            timestamp = (TextView) view.findViewById(R.id.message_timestamp);
        }
    }
}
