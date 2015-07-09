/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
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
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.LoginActivity;
import com.karthikb351.vitinfo2.fragment.AboutFragment;
import com.karthikb351.vitinfo2.fragment.LicensesFragment;
import com.karthikb351.vitinfo2.fragment.contributors.ContributorsFragment;


public class SettingsFragment extends ListFragment {


    //ListView listView;
    String settingsTopics[];
    String settingsMessages[];

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Settings");
        settingsTopics = getResources().getStringArray(R.array.settingsTopic);
        settingsMessages = getResources().getStringArray(R.array.settingsMessage);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.list_item_settings,settingsTopics);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        switch (position) {
            case 0: //Log Out
                // TODO: Clear Database
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case 1: //Licenses
                getActivity().setTitle("Open Source Licenses");
                LicensesFragment.displayLicensesFragment(getFragmentManager());
                break;
            case 2: //Contributors
                ContributorsFragment contributorsFragment = new ContributorsFragment();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.flContent, contributorsFragment, null)
                        .addToBackStack(null).commit();
                break;
            case 3: //About
                AboutFragment aboutFragment = new AboutFragment();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.flContent, aboutFragment, null)
                        .addToBackStack(null).commit();
                break;
        }

    }

    public class SettingsAdapter extends ArrayAdapter<String> {

        Context context;

        public SettingsAdapter(Context context, int resource) {
            super(context, resource);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }
    }

}
