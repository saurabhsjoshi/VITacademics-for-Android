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
import android.content.SharedPreferences.Editor;
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
import com.karthikb351.vitinfo2.response.GradesResponse;
import com.karthikb351.vitinfo2.response.LoginResponse;
import com.karthikb351.vitinfo2.response.RefreshResponse;
import com.karthikb351.vitinfo2.response.SystemResponse;
import com.karthikb351.vitinfo2.response.TokenResponse;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.ResultListener;

import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.RushSearch;

public class DatabaseController {

    public static DatabaseController databaseController;
    private Context context;
    private SharedPreferences sharedPreferences;

    private DatabaseController(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);

        databaseController = this;
    }

    public static DatabaseController getInstance(Context context) {
        if (databaseController != null) {
            databaseController.context = context;
            return databaseController;
        }
        return new DatabaseController(context);
    }

    public void saveSystem(final SystemResponse systemResponse, final ResultListener resultListener) {

        new AsyncTask<Boolean, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Boolean... params) {
                try {
                    RushCore.getInstance().deleteAll(Message.class);
                    RushCore.getInstance().deleteAll(Contributor.class);

                    for (Message message : systemResponse.getMessages()) {
                        message.save();
                    }
                    for (Contributor contributor : systemResponse.getContributors()) {
                        contributor.save();
                    }
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
                    Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.KEY_ANDROID_SUPPORTED_VERSION, systemResponse.getAndroid().getEarliestSupportedVersion());
                    editor.putString(Constants.KEY_ANDROID_LATEST_VERSION, systemResponse.getAndroid().getLatestVersion());
                    editor.apply();
                    resultListener.onSuccess();
                } else {
                    resultListener.onFailure(new com.karthikb351.vitinfo2.model.Status(StatusCodes.UNKNOWN, context.getResources().getString(R.string.api_unknown_error)));
                }
            }
        }.execute(true);
    }

    public void saveLogin(final LoginResponse loginResponse, ResultListener resultListener) {

        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_CAMPUS, loginResponse.getCampus());
        editor.putString(Constants.KEY_REGISTERNUMBER, loginResponse.getRegisterNumber());
        editor.putString(Constants.KEY_DATEOFBIRTH, loginResponse.getDateOfBirth());
        editor.putString(Constants.KEY_MOBILE, loginResponse.getMobileNumber());
        editor.apply();

        resultListener.onSuccess();
    }

    public void saveCourses(final RefreshResponse refreshResponse, final ResultListener resultListener) {

        new AsyncTask<Boolean, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Boolean... params) {
                try {
                    RushCore.getInstance().deleteAll(Course.class);
                    RushCore.getInstance().deleteAll(WithdrawnCourse.class);

                    for (Course course : refreshResponse.getCourses()) {
                        course.save();
                    }
                    for (WithdrawnCourse withdrawnCourse : refreshResponse.getWithdrawnCourses()) {
                        withdrawnCourse.save();
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.KEY_SEMESTER, refreshResponse.getSemester());
                    editor.putString(Constants.KEY_COURSES_REFRESHED, refreshResponse.getRefreshed());
                    editor.apply();
                    resultListener.onSuccess();
                } else {
                    resultListener.onFailure(new com.karthikb351.vitinfo2.model.Status(StatusCodes.UNKNOWN, context.getResources().getString(R.string.api_unknown_error)));
                }
            }
        }.execute(true);

    }

    public void saveGrades(final GradesResponse gradesResponse, final ResultListener resultListener) {

        new AsyncTask<Boolean, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Boolean... params) {
                try {
                    RushCore.getInstance().deleteAll(Grade.class);
                    RushCore.getInstance().deleteAll(SemesterWiseGrade.class);
                    RushCore.getInstance().deleteAll(GradeCount.class);

                    for (Grade grade : gradesResponse.getGrades()) {
                        grade.save();
                    }
                    for (SemesterWiseGrade semesterWiseGrade : gradesResponse.getSemesterWiseGrades()) {
                        semesterWiseGrade.save();
                    }
                    for (GradeCount gradeCount : gradesResponse.getGradeCount()) {
                        gradeCount.save();
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.KEY_GRADES_REFRESHED, gradesResponse.getRefreshed());
                    editor.putFloat(Constants.KEY_GRADES_CGPA, gradesResponse.getCgpa());
                    editor.putInt(Constants.KEY_GRADES_CREDITS_EARNED, gradesResponse.getCreditsEarned());
                    editor.putInt(Constants.KEY_GRADES_CREDITS_REGISTERED, gradesResponse.getCreditsRegistered());
                    editor.apply();
                    resultListener.onSuccess();
                } else {
                    resultListener.onFailure(new com.karthikb351.vitinfo2.model.Status(StatusCodes.UNKNOWN, context.getResources().getString(R.string.api_unknown_error)));
                }
            }
        }.execute(false);

    }

    public void saveToken(final TokenResponse tokenResponse, ResultListener resultListener) {

        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_SHARE_TOKEN, tokenResponse.getTokenShare().getToken());
        editor.putString(Constants.KEY_SHARE_TOKEN_ISSUED, tokenResponse.getTokenShare().getIssued());
        editor.apply();

        resultListener.onSuccess();
    }

    public void saveFriend(final Friend friend, final ResultListener resultListener) {

        new AsyncTask<Boolean, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Boolean... params) {
                try {
                    Friend oldFriend = new RushSearch().whereEqual(Constants.SQL_FIELD_CAMPUS, friend.getCampus()).whereEqual(Constants.SQL_FIELD_REGISTER_NUMBER, friend.getRegisterNumber()).findSingle(Friend.class);
                    if (oldFriend != null) {
                        oldFriend.delete();
                    }
                    friend.save();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    resultListener.onSuccess();
                } else {
                    resultListener.onFailure(new com.karthikb351.vitinfo2.model.Status(StatusCodes.UNKNOWN, context.getResources().getString(R.string.api_unknown_error)));
                }
            }
        }.execute(false);
    }
}
