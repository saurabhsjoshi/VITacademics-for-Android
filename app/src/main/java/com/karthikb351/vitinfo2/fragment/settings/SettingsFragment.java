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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.LoginActivity;
import com.karthikb351.vitinfo2.api.ResetTask;
import com.karthikb351.vitinfo2.fragment.LicensesFragment;
import com.karthikb351.vitinfo2.fragment.contributors.ContributorsFragment;
import com.karthikb351.vitinfo2.utility.Constants;


public class SettingsFragment extends ListFragment {

    private String settingsTopics[];
    private String settingsMessages[];
    private SettingsAdapter adapter;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String Title = getActivity().getResources().getString(R.string.fragment_settings_title);
        getActivity().setTitle(Title);
        settingsTopics = getResources().getStringArray(R.array.settings_topic);
        settingsMessages = getResources().getStringArray(R.array.settings_message);
        adapter = new SettingsAdapter(getActivity(), R.layout.app_settings_list_item, settingsTopics, settingsMessages);
        setListAdapter(adapter);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        switch (position) {
            case 0:
                // Reset App
                new ResetTask(getActivity()).execute();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
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
                // Rate on Google Play
                final String appPackageName = getActivity().getPackageName();
                Intent googlePlay = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GOOGLE_PLAY_DETAILS_URL + appPackageName));
                startActivity(googlePlay);
                break;
            case 4:
                // Google+ Community
                Intent googlePlus = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GOOGLE_PLUS_COMMUNITY_URL));
                startActivity(googlePlus);
                break;
            case 5:
                // Facebook Page
                Intent facebook = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FACEBOOK_PAGE_URL));
                startActivity(facebook);
                break;
            case 6:
                // Share app
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType(Constants.SHARE_TYPE);

                share.putExtra(Intent.EXTRA_SUBJECT, getActivity().getString(R.string.android_share_message_subject));
                share.putExtra(Intent.EXTRA_TEXT, getActivity().getString(R.string.android_share_message_start, Constants.API_BASE_URL));

                startActivity(Intent.createChooser(share, getActivity().getString(R.string.android_share_select)));
                break;
        }
    }
}
