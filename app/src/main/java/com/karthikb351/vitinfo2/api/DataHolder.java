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

package com.karthikb351.vitinfo2.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Contributor;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.GradeCount;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.contract.WithdrawnCourse;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.ResultListener;

import java.util.List;

import co.uk.rushorm.core.RushSearch;

public class DataHolder {

    private boolean initialized;

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
    private float cgpa;
    private int creditsEarned;
    private int creditsRegistered;
    private String gradesRefreshed;
    private List<Friend> friends;
    private String token;
    private String tokenIssued;

    public DataHolder() {
        initialized = false;
    }

    public void refreshData(final Context context, final ResultListener resultListener) {

        new AsyncTask<Boolean, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Boolean... params) {
                try {
                    messages = new RushSearch().find(Message.class);
                    contributors = new RushSearch().find(Contributor.class);
                    courses = new RushSearch().find(Course.class);
                    withdrawnCourses = new RushSearch().find(WithdrawnCourse.class);
                    grades = new RushSearch().find(Grade.class);
                    gradeCounts = new RushSearch().find(GradeCount.class);
                    semesterWiseGrades = new RushSearch().find(SemesterWiseGrade.class);
                    friends = new RushSearch().find(Friend.class);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);

                DataHolder.this.initialized = result;

                if (result) {

                    SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);

                    campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
                    registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
                    dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
                    mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);
                    latestVersion = sharedPreferences.getString(Constants.KEY_ANDROID_LATEST_VERSION, null);
                    earliestSupportedVersion = sharedPreferences.getString(Constants.KEY_ANDROID_SUPPORTED_VERSION, null);
                    semester = sharedPreferences.getString(Constants.KEY_SEMESTER, null);
                    coursesRefreshed = sharedPreferences.getString(Constants.KEY_COURSES_REFRESHED, null);
                    gradesRefreshed = sharedPreferences.getString(Constants.KEY_GRADES_REFRESHED, null);
                    token = sharedPreferences.getString(Constants.KEY_SHARE_TOKEN, null);
                    tokenIssued = sharedPreferences.getString(Constants.KEY_SHARE_TOKEN_ISSUED, null);
                    cgpa = sharedPreferences.getFloat(Constants.KEY_GRADES_CGPA, 0);
                    creditsEarned = sharedPreferences.getInt(Constants.KEY_GRADES_CREDITS_EARNED, 0);
                    creditsRegistered = sharedPreferences.getInt(Constants.KEY_GRADES_CREDITS_REGISTERED, 0);
                    resultListener.onSuccess();
                } else {
                    resultListener.onFailure(new com.karthikb351.vitinfo2.model.Status(StatusCodes.UNKNOWN, context.getResources().getString(R.string.api_unknown_error)));
                }
            }
        }.execute(true);

    }

    public void refreshData(final Context context) {

        new AsyncTask<Boolean, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Boolean... params) {
                try {
                    messages = new RushSearch().find(Message.class);
                    contributors = new RushSearch().find(Contributor.class);
                    courses = new RushSearch().find(Course.class);
                    withdrawnCourses = new RushSearch().find(WithdrawnCourse.class);
                    grades = new RushSearch().find(Grade.class);
                    gradeCounts = new RushSearch().find(GradeCount.class);
                    semesterWiseGrades = new RushSearch().find(SemesterWiseGrade.class);
                    friends = new RushSearch().find(Friend.class);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);

                DataHolder.this.initialized = result;

                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);

                campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
                registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
                dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
                mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);
                latestVersion = sharedPreferences.getString(Constants.KEY_ANDROID_LATEST_VERSION, null);
                earliestSupportedVersion = sharedPreferences.getString(Constants.KEY_ANDROID_SUPPORTED_VERSION, null);
                semester = sharedPreferences.getString(Constants.KEY_SEMESTER, null);
                coursesRefreshed = sharedPreferences.getString(Constants.KEY_COURSES_REFRESHED, null);
                gradesRefreshed = sharedPreferences.getString(Constants.KEY_GRADES_REFRESHED, null);
                token = sharedPreferences.getString(Constants.KEY_SHARE_TOKEN, null);
                tokenIssued = sharedPreferences.getString(Constants.KEY_SHARE_TOKEN_ISSUED, null);
                cgpa = sharedPreferences.getFloat(Constants.KEY_GRADES_CGPA, 0);
                creditsEarned = sharedPreferences.getInt(Constants.KEY_GRADES_CREDITS_EARNED, 0);
                creditsRegistered = sharedPreferences.getInt(Constants.KEY_GRADES_CREDITS_REGISTERED, 0);
            }
        }.execute(true);

    }

    public boolean isInitialized() {
        return initialized;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCampus() {
        return campus;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public String getEarliestSupportedVersion() {
        return earliestSupportedVersion;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Contributor> getContributors() {
        return contributors;
    }

    public String getSemester() {
        return semester;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<WithdrawnCourse> getWithdrawnCourses() {
        return withdrawnCourses;
    }

    public String getCoursesRefreshed() {
        return coursesRefreshed;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public List<GradeCount> getGradeCounts() {
        return gradeCounts;
    }

    public List<SemesterWiseGrade> getSemesterWiseGrades() {
        return semesterWiseGrades;
    }

    public float getCgpa() {
        return cgpa;
    }

    public int getCreditsEarned() {
        return creditsEarned;
    }

    public int getCreditsRegistered() {
        return creditsRegistered;
    }

    public String getGradesRefreshed() {
        return gradesRefreshed;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public String getToken() {
        return token;
    }

    public String getTokenIssued() {
        return tokenIssued;
    }
}
