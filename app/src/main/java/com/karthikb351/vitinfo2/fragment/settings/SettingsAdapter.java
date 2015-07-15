/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;


public class SettingsAdapter extends ArrayAdapter<String> {

    private String[] topics;
    private String[] messages;
    private Context context;

    public SettingsAdapter(Context context, int Rid, String[] topics, String[] messages) {
        super(context, Rid, topics);
        this.topics = topics;
        this.context = context;
        this.messages = messages;
    }

    public class SettingsViewHolder {
        public View rootView;
        public TextView topic, message;

        SettingsViewHolder(View view) {
            topic = (TextView) view.findViewById(R.id.tv_settings_topic);
            message = (TextView) view.findViewById(R.id.tv_settings_message);
            rootView = view;
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        SettingsViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_settings, parent, false);
            holder = new SettingsViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (SettingsViewHolder) view.getTag();
        }
        holder.topic.setText(topics[position]);
        holder.message.setText(messages[position]);

        return view;
    }
}
