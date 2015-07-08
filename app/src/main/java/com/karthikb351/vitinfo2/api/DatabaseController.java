/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
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

package com.karthikb351.vitinfo2.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

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
import com.karthikb351.vitinfo2.response.GradesResponse;
import com.karthikb351.vitinfo2.response.LoginResponse;
import com.karthikb351.vitinfo2.response.RefreshResponse;
import com.karthikb351.vitinfo2.response.SystemResponse;
import com.karthikb351.vitinfo2.response.TokenResponse;
import com.karthikb351.vitinfo2.utility.ResultListener;
import com.orm.SugarTransactionHelper;

import java.util.List;

public class DatabaseController {

    public static DatabaseController databaseController;
    private Context context;
    private SharedPreferences sharedPreferences;

    private DatabaseController(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
    }

    public static DatabaseController getDatabaseSingleton(Context context) {
        if (databaseController != null) {
            databaseController = new DatabaseController(context);
        }
        return databaseController;
    }

    public void saveSystem(final SystemResponse systemResponse, final ResultListener resultListener) {

        new AsyncTask<Boolean, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Boolean... params) {
                try {
                    SugarTransactionHelper.doInTansaction(new SugarTransactionHelper.Callback() {
                        @Override
                        public void manipulateInTransaction() {
                            Message.deleteAll(Message.class);
                            Contributor.deleteAll(Contributor.class);

                            for (Message message : systemResponse.getMessages()) {
                                message.save();
                            }
                            for (Contributor contributor : systemResponse.getContributors()) {
                                contributor.save();
                            }
                        }
                    });
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
                    SugarTransactionHelper.doInTansaction(new SugarTransactionHelper.Callback() {
                        @Override
                        public void manipulateInTransaction() {
                            Course.deleteAll(Course.class);
                            WithdrawnCourse.deleteAll(WithdrawnCourse.class);

                            for (Course course : refreshResponse.getCourses()) {
                                course.save();
                            }
                            for (WithdrawnCourse withdrawnCourse : refreshResponse.getWithdrawnCourses()) {
                                withdrawnCourse.save();
                            }
                        }
                    });
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
                    SugarTransactionHelper.doInTansaction(new SugarTransactionHelper.Callback() {
                        @Override
                        public void manipulateInTransaction() {
                            Grade.deleteAll(Grade.class);
                            SemesterWiseGrade.deleteAll(SemesterWiseGrade.class);

                            for (Grade grade : gradesResponse.getGrades()) {
                                grade.save();
                            }
                            for (SemesterWiseGrade semesterWiseGrade : gradesResponse.getSemesterWiseGrades()) {
                                semesterWiseGrade.save();
                            }
                            for (GradeCount gradeCount : gradesResponse.getGradeCount()) {
                                gradeCount.save();
                            }
                        }
                    });
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
                    SugarTransactionHelper.doInTansaction(new SugarTransactionHelper.Callback() {
                        @Override
                        public void manipulateInTransaction() {
                            List<Friend> friends = Friend.find(Friend.class, "campus = ? and reg_no = ?", friend.getCampus(), friend.getRegisterNumber());
                            if (friends.size() != 0) {
                                friend.setId(friends.get(0).getId());
                            }
                            friend.save();
                        }
                    });
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
