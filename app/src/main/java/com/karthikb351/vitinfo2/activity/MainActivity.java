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
import com.karthikb351.vitinfo2.fragment.courses.CoursesFragment;
import com.karthikb351.vitinfo2.fragment.friends.FriendsFragment;
import com.karthikb351.vitinfo2.fragment.settings.SettingsFragment;
import com.karthikb351.vitinfo2.fragment.timetable.TimeTableFragment;
import com.karthikb351.vitinfo2.fragment.today.MainFragment;
import com.karthikb351.vitinfo2.utility.Network;
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

    // User data
    private String registerNumber;
    private String dateOfBirth;
    private String mobileNumber;
    private String campus;
    private String latestVersion;
    private String earliestSupportedVersion;
    private List<Message> messages;
    private List<Contributor> contributors;
    private String semester;
    private List<Course> courses;
    private List<WithdrawnCourse> withdrawnCourses;
    private String coursesRefreshed;
    private List<Grade> grades;
    private List<GradeCount> gradeCounts;
    private List<SemesterWiseGrade> semesterWiseGrades;
    private String gradesRefreshed;
    private List<Friend> friends;
    private String token;
    private String tokenIssued;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeData();
        initializeLayouts();
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

    public void initializeData() {
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);

        MainActivity.this.campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
        MainActivity.this.registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
        MainActivity.this.dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
        MainActivity.this.mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);
        MainActivity.this.latestVersion = sharedPreferences.getString(Constants.KEY_ANDROID_LATEST_VERSION, null);
        MainActivity.this.earliestSupportedVersion = sharedPreferences.getString(Constants.KEY_ANDROID_SUPPORTED_VERSION, null);
        MainActivity.this.semester = sharedPreferences.getString(Constants.KEY_SEMESTER, null);
        MainActivity.this.coursesRefreshed = sharedPreferences.getString(Constants.KEY_COURSES_REFRESHED, null);
        MainActivity.this.gradesRefreshed = sharedPreferences.getString(Constants.KEY_GRADES_REFRESHED, null);
        MainActivity.this.token = sharedPreferences.getString(Constants.KEY_SHARE_TOKEN, null);
        MainActivity.this.tokenIssued = sharedPreferences.getString(Constants.KEY_SHARE_TOKEN_ISSUED, null);

        SugarTransactionHelper.doInTansaction(new SugarTransactionHelper.Callback() {
            @Override
            public void manipulateInTransaction() {
                MainActivity.this.messages = Message.listAll(Message.class);
                MainActivity.this.contributors = Contributor.listAll(Contributor.class);
                MainActivity.this.courses = Course.listAll(Course.class);
                MainActivity.this.withdrawnCourses = WithdrawnCourse.listAll(WithdrawnCourse.class);
                MainActivity.this.grades = Grade.listAll(Grade.class);
                MainActivity.this.gradeCounts = GradeCount.listAll(GradeCount.class);
                MainActivity.this.semesterWiseGrades = SemesterWiseGrade.listAll(SemesterWiseGrade.class);
                MainActivity.this.friends = Friend.listAll(Friend.class);
            }
        });
    }

    public void onEventMainThread(RefreshActivityEvent refreshActivityEvent) {
        initializeData();
        initializeLayouts();
        EventBus.getDefault().post(new RefreshFragmentEvent());
    }

    public String getRegisterNumber() {
        return this.registerNumber;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public String getCampus() {
        return this.campus;
    }

    public String getLatestVersion() {
        return this.latestVersion;
    }

    public String getEarliestSupportedVersion() {
        return this.earliestSupportedVersion;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public String getSemester() {
        return this.semester;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public List<WithdrawnCourse> getWithdrawnCourses() {
        return this.withdrawnCourses;
    }

    public String getCoursesRefreshed() {
        return this.coursesRefreshed;
    }

    public List<Grade> getGrades() {
        return this.grades;
    }

    public List<GradeCount> getGradeCounts() {
        return this.gradeCounts;
    }

    public List<SemesterWiseGrade> getSemesterWiseGrades() {
        return this.semesterWiseGrades;
    }

    public String getGradesRefreshed() {
        return this.gradesRefreshed;
    }

    public List<Friend> getFriends() {
        return this.friends;
    }

    public String getToken() {
        return this.token;
    }

    public String getTokenIssued() {
        return this.tokenIssued;
    }

    public List<Contributor> getContributors() {
        return this.contributors;
    }
}
