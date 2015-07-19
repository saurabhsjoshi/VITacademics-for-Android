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

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;

public class DetailsPagerAdapter extends FragmentStatePagerAdapter {

    private final int TAB_COUNT_DETAILS;
    private String detailsTitles[];
    private Fragment fragment;
    private Course course;

    public DetailsPagerAdapter(FragmentManager fragmentManager, Context context, Course course) {
        super(fragmentManager);
        this.detailsTitles = context.getResources().getStringArray(R.array.course_details_tab);
        this.TAB_COUNT_DETAILS = context.getResources().getStringArray(R.array.course_details_tab).length;
        this.course = course;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                fragment = OverviewFragment.newInstance(course);
                break;
            case 1:
                fragment = AttendanceFragment.newInstance(course);
                break;
            case 2:
                fragment = MarksFragment.newInstance(course);
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT_DETAILS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return detailsTitles[position];
    }
}
