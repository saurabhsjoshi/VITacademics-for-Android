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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapter.NavigationDrawerAdapter;
import com.karthikb351.vitinfo2.api.contract.Course;
import com.karthikb351.vitinfo2.api.contract.Friend;
import com.karthikb351.vitinfo2.api.contract.Grade;
import com.karthikb351.vitinfo2.api.contract.GradeCount;
import com.karthikb351.vitinfo2.api.contract.Message;
import com.karthikb351.vitinfo2.api.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.api.contract.WithdrawnCourse;
import com.karthikb351.vitinfo2.fragment.MainFragment;
import com.karthikb351.vitinfo2.fragment.TimeTableFragment;
import com.karthikb351.vitinfo2.fragment.courses.CoursesFragment;
import com.karthikb351.vitinfo2.fragment.friends.FriendsFragment;
import com.karthikb351.vitinfo2.fragment.settings.SettingsFragment;
import com.karthikb351.vitinfo2.fragment.courses.CoursesFragment;
import com.karthikb351.vitinfo2.fragment.friends.FriendsFragment;
import com.orm.SugarTransactionHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import com.karthikb351.vitinfo2.model.DrawerItemClickListener;


public class MainActivity extends AppCompatActivity {

    int flag = 0;
    private String topics[];
    private DrawerLayout dl;
    private ListView lv;

    // User data
    private String registerNumber;
    private String dateOfBirth;
    private String mobileNumber;
    private String campus;
    private String latestVersion;
    private String earliestSupportedVersion;
    private List<Message> messages;
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
        loadAllData();
        initializeLayouts();
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

    void initializeLayouts() {
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        lv = (ListView) findViewById(R.id.lvDrawer);
        topics = getResources().getStringArray(R.array.topic);
        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(topics));
        lv.setAdapter(new NavigationDrawerAdapter(this, R.layout.drawer_menu_item, stringList));

        // When navigation drawer item is clicked
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                android.support.v4.app.Fragment frag = null;
                dl.closeDrawers();
                // settings can be passed in the new instance function
                //TODO: inefficient for already created instances. Fix.
                switch (position) {

                    case 0:
                        frag=MainFragment.newInstance();
                        break;
                    case 1:
                        frag = CoursesFragment.newInstance();
                        break;
                    case 2:
                        frag= TimeTableFragment.newInstance();
                        break;
                    case 3:
                        frag = FriendsFragment.newInstance();
                        break;
                    case 4:
                        frag = SettingsFragment.newInstance();
                        break;
                }
                ft.replace(R.id.flContent, frag, topics[position]).addToBackStack(null).commit();
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.flContent, new MainFragment(), "mainFragment").commit();
    }

    public void loadAllData() {

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
                MainActivity.this.courses = Course.listAll(Course.class);
                MainActivity.this.withdrawnCourses = WithdrawnCourse.listAll(WithdrawnCourse.class);
                MainActivity.this.grades = Grade.listAll(Grade.class);
                MainActivity.this.gradeCounts = GradeCount.listAll(GradeCount.class);
                MainActivity.this.semesterWiseGrades = SemesterWiseGrade.listAll(SemesterWiseGrade.class);
                MainActivity.this.friends = Friend.listAll(Friend.class);
            }
        });
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
}
