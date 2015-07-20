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

package com.karthikb351.vitinfo2.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.fragment.details.DetailsPagerAdapter;
import com.karthikb351.vitinfo2.utility.Constants;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailsPagerAdapter adapter;
    private Course course;
    private ProgressBar loadProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent intent = getIntent();
        if (intent.hasExtra(Constants.INTENT_EXTRA_CLASS_NUMBER)) {
            setContentView(R.layout.activity_details);
            initToolbar();
            loadProgress = (ProgressBar) findViewById(R.id.progress_bar_details);
            new LoadCourseTask().execute(intent.getIntExtra(Constants.INTENT_EXTRA_CLASS_NUMBER, -1));

        } else {
            setContentView(R.layout.app_message_not_available);
            TextView errorMessage = (TextView) findViewById(R.id.message);
            errorMessage.setText(getString(R.string.not_available));
        }
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.activity_details_title));

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LoadCourseTask extends AsyncTask<Integer, Void, Course> {
        @Override
        protected void onPreExecute() {
            loadProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Course course) {
            loadProgress.setVisibility(View.GONE);

            if (course == null)
                return;
            DetailsActivity.this.course = course;

            tabLayout = (TabLayout) findViewById(R.id.tabs_details);
            viewPager = (ViewPager) findViewById(R.id.view_pager_details);

            adapter = new DetailsPagerAdapter(getSupportFragmentManager(), DetailsActivity.this, DetailsActivity.this.course);
            viewPager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewPager);
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

            List<Course> courseList = ((MainApplication) getApplication()).getDataHolderInstanceInitialized().getCourses();
            for (Course courseItem : courseList) {
                if (courseItem.getClassNumber() == classNumber) {
                    foundCourse = courseItem;
                }
            }
            return foundCourse;
        }
    }


}
