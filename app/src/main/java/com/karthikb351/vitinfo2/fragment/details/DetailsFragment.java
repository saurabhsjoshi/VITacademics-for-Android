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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.utility.Constants;

import java.util.List;

public class DetailsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailsAdapter adapter;
    private View view;
    private Course course;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(Constants.INTENT_EXTRA_CLASS_NUMBER)) {
            new LoadCourseTask().execute(intent.getIntExtra(Constants.INTENT_EXTRA_CLASS_NUMBER, -1));
            view = inflater.inflate(R.layout.fragment_details, container, false);
        }
        else {
            view = inflater.inflate(R.layout.app_message_not_available, container, false);
            TextView errorMessage = (TextView) view.findViewById(R.id.message);
            errorMessage.setText(getActivity().getString(R.string.not_available));
        }
        return view;
    }

    private class LoadCourseTask extends AsyncTask<Integer, Void, Course> {
        @Override
        protected void onPreExecute() {
            // TODO Progress Start
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Course course) {
            super.onPostExecute(course);

            DetailsFragment.this.course = course;

            tabLayout = (TabLayout) view.findViewById(R.id.tabs_details);
            viewPager = (ViewPager) view.findViewById(R.id.view_pager_details);

            adapter = new DetailsAdapter(getActivity().getSupportFragmentManager(), getActivity(), course);
            viewPager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewPager);
            // TODO Progress Stop
        }

        @Override
        protected Course doInBackground(Integer... params) {

            Course foundCourse = null;

            int classNumber = -1;
            if (params.length > 0) {
                classNumber = params[0];
            }
            if (classNumber == -1) {
                return null;
            }

            List<Course> courseList = ((MainApplication)getActivity().getApplication()).getDataHolderInstance().getCourses();
            for (Course courseItem : courseList) {
                if (courseItem.getClassNumber() == classNumber) {
                    foundCourse = courseItem;
                }
            }
            return foundCourse;
        }
    }
}
