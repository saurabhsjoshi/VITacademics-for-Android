/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
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

import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Contributor;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.GradeCount;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.contract.WithdrawnCourse;
import com.karthikb351.vitinfo2.utility.ResultListener;

import java.util.List;

import co.uk.rushorm.core.RushSearch;

public class DataHolder {

    private static String registerNumber;
    private static String dateOfBirth;
    private static String mobileNumber;
    private static String campus;
    private static String latestVersion;
    private static String earliestSupportedVersion;
    private static List<Message> messages;
    private static List<Contributor> contributors;
    private static String semester;
    private static List<Course> courses;
    private static List<WithdrawnCourse> withdrawnCourses;
    private static String coursesRefreshed;
    private static List<Grade> grades;
    private static List<GradeCount> gradeCounts;
    private static List<SemesterWiseGrade> semesterWiseGrades;
    private static float cgpa;
    private static int creditsEarned;
    private static int creditsRegistered;
    private static String gradesRefreshed;
    private static List<Friend> friends;
    private static String token;
    private static String tokenIssued;

    public static void refreshData(final Context context, final ResultListener resultListener) {

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

    public static String getRegisterNumber() {
        return DataHolder.registerNumber;
    }

    public static String getDateOfBirth() {
        return DataHolder.dateOfBirth;
    }

    public static String getMobileNumber() {
        return DataHolder.mobileNumber;
    }

    public static String getCampus() {
        return DataHolder.campus;
    }

    public static String getLatestVersion() {
        return DataHolder.latestVersion;
    }

    public static String getEarliestSupportedVersion() {
        return DataHolder.earliestSupportedVersion;
    }

    public static List<Message> getMessages() {
        return DataHolder.messages;
    }

    public static List<Contributor> getContributors() {
        return DataHolder.contributors;
    }

    public static String getSemester() {
        return DataHolder.semester;
    }

    public static List<Course> getCourses() {
        return DataHolder.courses;
    }

    public static List<WithdrawnCourse> getWithdrawnCourses() {
        return DataHolder.withdrawnCourses;
    }

    public static String getCoursesRefreshed() {
        return DataHolder.coursesRefreshed;
    }

    public static List<Grade> getGrades() {
        return DataHolder.grades;
    }

    public static List<GradeCount> getGradeCounts() {
        return DataHolder.gradeCounts;
    }

    public static List<SemesterWiseGrade> getSemesterWiseGrades() {
        return DataHolder.semesterWiseGrades;
    }

    public static float getCgpa() {
        return DataHolder.cgpa;
    }

    public static int getCreditsEarned() {
        return DataHolder.creditsEarned;
    }

    public static int getCreditsRegistered() {
        return DataHolder.creditsRegistered;
    }

    public static String getGradesRefreshed() {
        return DataHolder.gradesRefreshed;
    }

    public static List<Friend> getFriends() {
        return DataHolder.friends;
    }

    public static String getToken() {
        return DataHolder.token;
    }

    public static String getTokenIssued() {
        return DataHolder.tokenIssued;
    }
}
