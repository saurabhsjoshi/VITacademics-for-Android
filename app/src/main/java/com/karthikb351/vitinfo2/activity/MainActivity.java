/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
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

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.NetworkController;
import com.karthikb351.vitinfo2.api.RequestConfig;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;
import com.karthikb351.vitinfo2.fragment.AboutFragment;
import com.karthikb351.vitinfo2.fragment.UnavailableFragment;
import com.karthikb351.vitinfo2.fragment.courses.CoursesFragment;
import com.karthikb351.vitinfo2.fragment.grades.GradesFragment;
import com.karthikb351.vitinfo2.fragment.messages.MessagesFragment;
import com.karthikb351.vitinfo2.fragment.settings.SettingsFragment;
import com.karthikb351.vitinfo2.fragment.timetable.TimetableFragment;
import com.karthikb351.vitinfo2.fragment.today.TodayFragment;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.utility.ResultListener;

import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private List<Course> courses;
    private List<String> navigationTabs;
    private LinearLayout mainContent;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
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

    public void initialize() {
        navigationTabs = Arrays.asList(getResources().getStringArray(R.array.navigation_tab));
        courses = ((MainApplication)getApplication()).getDataHolderInstance().getCourses();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainContent = (LinearLayout) findViewById(R.id.llMainContent);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                    this,  drawerLayout, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close
            );
            drawerLayout.setDrawerListener(mDrawerToggle);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            mDrawerToggle.syncState();
        }
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String navString = (String) menuItem.getTitle();
                menuItem.setChecked(true);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment frag = null;
                int pos = 0;
                // settings can be passed in the new instance function
                //TODO: inefficient for already created instances. Fix.
                switch (navString) {
                    // TODO Use String resources to compare. This should work even if app is in a different language
                    // TODO Check https://developer.android.com/training/implementing-navigation/nav-drawer.html
                    case "Today":
                        frag = TodayFragment.newInstance();
                        pos = 0;
                        break;
                    case "Courses":
                        frag = CoursesFragment.newInstance();
                        pos = 1;
                        break;
                    case "Timetable":
                        frag = TimetableFragment.newInstance();
                        pos = 2;
                        break;
                    case "Grades":
                        frag = GradesFragment.newInstance();
                        pos = 3;
                        break;
                    case "Settings":
                        frag = SettingsFragment.newInstance();
                        pos = 4;
                        break;
                    case "Messages":
                        frag = MessagesFragment.newInstance();
                        pos = 5;
                        break;
                    case "About":
                        frag = AboutFragment.newInstance();
                        pos = 6;
                        break;
                }
                ft.replace(R.id.flContent, frag, navigationTabs.get(pos)).addToBackStack(null).commit();

                drawerLayout.closeDrawers();
                return true;
            }
        });
        //lv = (ListView) findViewById(R.id.lvDrawer);
        if (courses == null || courses.isEmpty()) {
            getSupportFragmentManager().beginTransaction().add(R.id.flContent, new UnavailableFragment(), "Unavailable").commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.flContent, new TodayFragment(), "Today Fragment").commit();
        }
    }

    public void pullToRefresh() {

        NetworkController networkController = NetworkController.getInstance(MainActivity.this);
        // TODO Progress Ring Start
        final ResultListener resultListener = new ResultListener() {
            @Override
            public void onSuccess() {
                initialize();
                // TODO Progress Ring Stop
                EventBus.getDefault().post(new RefreshFragmentEvent());
            }

            @Override
            public void onFailure(Status status) {
                // TODO Progress Ring Stop
                Toast.makeText(MainActivity.this, status.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        RequestConfig requestConfig = new RequestConfig(new ResultListener() {
            @Override
            public void onSuccess() {
                ((MainApplication)getApplication()).getDataHolderInstance().refreshData(MainActivity.this, resultListener);
            }

            @Override
            public void onFailure(Status status) {
                resultListener.onFailure(status);
            }
        });
        requestConfig.addRequest(RequestConfig.REQUEST_SYSTEM);
        requestConfig.addRequest(RequestConfig.REQUEST_REFRESH);
        requestConfig.addRequest(RequestConfig.REQUEST_GRADES);
        requestConfig.addRequest(RequestConfig.REQUEST_TOKEN);

        networkController.dispatch(requestConfig);
    }

    public LinearLayout getMainContent() {
        return mainContent;
    }
}
