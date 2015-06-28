/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.karthikb351.vitinfo2.R;

import java.util.ArrayList;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    ArrayList<String> settingTopics;
    ListView listView;
    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.settings_list,container,false);
        listView=(ListView)view.findViewById(R.id.lvSettings);
        settingTopics.add("Licenses");
        settingTopics.add("Contributors");
        settingTopics.add("Log Out");
        settingTopics.add("About");
        listView.setAdapter(new SettingsListAdapter(getActivity(),R.layout.list_item_settings,settingTopics));
        listView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
