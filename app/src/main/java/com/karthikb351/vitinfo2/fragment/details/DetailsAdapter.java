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
package com.karthikb351.vitinfo2.fragment.details;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.fragment.OverviewFragment;
import com.karthikb351.vitinfo2.fragment.assesment.AssesmentFragment;
import com.karthikb351.vitinfo2.fragment.attendance.AttendanceFragment;

public class DetailsAdapter extends FragmentStatePagerAdapter {

    Fragment fragment;
    Context context;
    Course course;
    int TAB_COUNT_DETAILS = 3;

    public DetailsAdapter(FragmentManager fragmentManager, Context context, Course course) {
        super(fragmentManager);
        this.context = context;
        this.course = course;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            fragment = OverviewFragment.newInstance(course);
        else if (position == 1)
            fragment = AttendanceFragment.newInstance(course);
        else if (position == 2)
            fragment = AssesmentFragment.newInstance(course);
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT_DETAILS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String detailsTitles[] = {"Overview", "Attendance", "Marks"};
        return detailsTitles[position];
    }
}
