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

package com.karthikb351.vitinfo2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.Kartitk.licensefragment.model.LicenseID;
import com.Kartitk.licensefragment.support.v4.RecyclerViewLicenseFragment;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.LoginActivity;
import com.karthikb351.vitinfo2.fragment.contributors.ContributorsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SettingsFragment extends ListFragment {

    List<String> settingsList;
    //ListView listView;
    String settingsTopics[];


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
        settingsList = Arrays.asList(settingsTopics);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_settings, R.id.text_view_settings, settingsList);
        setListAdapter(arrayAdapter);
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
}
