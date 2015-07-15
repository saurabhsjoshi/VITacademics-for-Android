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

package com.karthikb351.vitinfo2.fragment.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;

public class DetailsFragment extends Fragment {

    //final ActionBar actionBar = getActivity().getActionBar();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailsAdapter adapter;
    private Course course;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(Course course) {
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.course = course;
        return detailsFragment ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // TODO, rethink this. Must not change Minimum API level
       /// actionBar.setDisplayHomeAsUpEnabled(false);
        // actionBar.setDisplayShowTitleEnabled(false);
 //       getActivity().setTitle(course.getCourseCode());
        tabLayout = (TabLayout) view.findViewById(R.id.tabs_details);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager_details);

        adapter = new DetailsAdapter(getActivity().getSupportFragmentManager(), getActivity(), course);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
