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

package com.karthikb351.vitinfo2.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.karthikb351.vitinfo2.BuildConfig;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.utility.Constants;

public class AboutFragment extends Fragment {

    Button feedbackButton;
    TextView appVersion, contribute;

    public AboutFragment() {
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        getActivity().setTitle(getString(R.string.fragment_about_title));

        feedbackButton = (Button) view.findViewById(R.id.button_feedback);
        appVersion = (TextView) view.findViewById(R.id.app_version);
        contribute = (TextView) view.findViewById(R.id.contribute);

        appVersion.setText(getString(R.string.app_version, BuildConfig.VERSION_NAME));

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedBack(view);
            }
        });
        contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contributeClick(view);
            }
        });

        return view;
    }

    void sendFeedBack(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType(Constants.INTENT_TYPE_PLAIN);
        intent.setData(Uri.parse(Constants.FEEDBACK_EMAIL_LINK));
        intent.putExtra(Intent.EXTRA_SUBJECT, Constants.FEEDBACK_EMAIL_SUBJECT);
        startActivity(intent);
    }

    void contributeClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_COLLEGECODE_URL));
        startActivity(browserIntent);
    }
}
