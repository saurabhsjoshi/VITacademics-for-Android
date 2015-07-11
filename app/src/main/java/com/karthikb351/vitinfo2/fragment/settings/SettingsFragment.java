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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.LoginActivity;
import com.karthikb351.vitinfo2.api.ResetTask;
import com.karthikb351.vitinfo2.fragment.AboutFragment;
import com.karthikb351.vitinfo2.fragment.LicensesFragment;
import com.karthikb351.vitinfo2.fragment.contributors.ContributorsFragment;


public class SettingsFragment extends ListFragment {


    //ListView listView;
    private String settingsTopics[];
    private String settingsMessages[];
    private SettingsAdapter adapter ;

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
        String Title = getActivity().getResources().getString(R.string.settings_title);
        getActivity().setTitle(Title);
        settingsTopics = getResources().getStringArray(R.array.settings_topic);
        settingsMessages = getResources().getStringArray(R.array.settings_message);
        adapter = new SettingsAdapter(getActivity(),R.layout.list_item_settings,settingsTopics,settingsMessages);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        switch (position) {
            case 0:
                // Reset App
                new ResetTask(getActivity()).execute();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case 1:
                // Show licenses of libraries
                LicensesFragment.displayLicensesFragment(getFragmentManager());
                break;
            case 2:
                // Contributor List
                ContributorsFragment contributorsFragment = new ContributorsFragment();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.flContent, contributorsFragment, null)
                        .addToBackStack(null).commit();
                break;
            case 3:
                // About Page
                AboutFragment aboutFragment = new AboutFragment();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.flContent, aboutFragment, null)
                        .addToBackStack(null).commit();
                break;
        }

    }

}
