/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
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

package com.karthikb351.vitinfo2.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Contributor;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.GradeCount;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.contract.WithdrawnCourse;
import com.karthikb351.vitinfo2.event.RefreshActivityEvent;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.fragment.SettingsFragment;
import com.karthikb351.vitinfo2.fragment.courses.CoursesFragment;
import com.karthikb351.vitinfo2.fragment.friends.FriendsFragment;
import com.karthikb351.vitinfo2.fragment.timetable.TimeTableFragment;
import com.karthikb351.vitinfo2.fragment.today.MainFragment;
import com.karthikb351.vitinfo2.utility.DataHolder;
import com.karthikb351.vitinfo2.utility.Network;
import com.karthikb351.vitinfo2.utility.ResultListener;
import com.orm.SugarTransactionHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private int flag = 0;
    private int refreshStatus;
    private Network network;
    private String topics[];
    private DrawerLayout drawerLayout;
    private ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHolder.refreshData(MainActivity.this, new ResultListener() {
            @Override
            public void onSuccess() {
                initializeLayouts();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void initializeLayouts() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        //  actionBar.setTitle("VitAcademics");
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_hamburger);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String navString = (String) menuItem.getTitle();
                menuItem.setChecked(true);
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                android.support.v4.app.Fragment frag = null;
                int pos = 0;
                // settings can be passed in the new instance function
                //TODO: inefficient for already created instances. Fix.
                switch (navString) {

                    case "Today":
                        frag = MainFragment.newInstance();
                        pos = 0;
                        break;
                    case "Courses":
                        frag = CoursesFragment.newInstance();
                        pos = 1;
                        break;
                    case "Time Table":
                        frag = TimeTableFragment.newInstance();
                        pos = 2;
                        break;
                    case "Friends":
                        frag = FriendsFragment.newInstance();
                        pos = 3;
                        break;
                    case "Settings":
                        frag = SettingsFragment.newInstance();
                        pos = 4;
                        break;
                }
                ft.replace(R.id.flContent, frag, topics[pos]).addToBackStack(null).commit();

                drawerLayout.closeDrawers();
                return true;
            }
        });
        //lv = (ListView) findViewById(R.id.lvDrawer);
        topics = getResources().getStringArray(R.array.topic);
        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(topics));
        getSupportFragmentManager().beginTransaction().add(R.id.flContent, new MainFragment(), "mainFragment").commit();
    }

    public void onEventMainThread(RefreshActivityEvent refreshActivityEvent) {
        DataHolder.refreshData(MainActivity.this, new ResultListener() {
            @Override
            public void onSuccess() {
                initializeLayouts();
                EventBus.getDefault().post(new RefreshFragmentEvent());
            }

            @Override
            public void onFailure() {

            }
        });
    }


}
