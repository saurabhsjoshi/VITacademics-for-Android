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

package com.karthikb351.vitinfo2.api.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.api.contract.Contributor;
import com.karthikb351.vitinfo2.api.contract.Course;
import com.karthikb351.vitinfo2.api.contract.Friend;
import com.karthikb351.vitinfo2.api.contract.Grade;
import com.karthikb351.vitinfo2.api.contract.GradeCount;
import com.karthikb351.vitinfo2.api.contract.Message;
import com.karthikb351.vitinfo2.api.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.api.contract.WithdrawnCourse;
import com.karthikb351.vitinfo2.api.event.FriendEvent;
import com.karthikb351.vitinfo2.api.event.SuccessEvent;
import com.karthikb351.vitinfo2.api.response.GradesResponse;
import com.karthikb351.vitinfo2.api.response.LoginResponse;
import com.karthikb351.vitinfo2.api.response.RefreshResponse;
import com.karthikb351.vitinfo2.api.response.SystemResponse;
import com.karthikb351.vitinfo2.api.response.TokenResponse;
import com.orm.SugarTransactionHelper;

import java.util.List;

import de.greenrobot.event.EventBus;

public class Database {

    private SharedPreferences sharedPreferences;

    public Database(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveSystem(final SystemResponse systemResponse, SuccessEvent successEvent) {

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

        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_ANDROID_SUPPORTED_VERSION, systemResponse.getAndroid().getEarliestSupportedVersion());
        editor.putString(Constants.KEY_ANDROID_LATEST_VERSION, systemResponse.getAndroid().getLatestVersion());
        editor.apply();

        successEvent.setSystemDone(true);
        EventBus.getDefault().post(successEvent);
    }

    public void saveLogin(final LoginResponse loginResponse, SuccessEvent successEvent) {

        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_CAMPUS, loginResponse.getCampus());
        editor.putString(Constants.KEY_REGISTERNUMBER, loginResponse.getRegisterNumber());
        editor.putString(Constants.KEY_DATEOFBIRTH, loginResponse.getDateOfBirth());
        editor.putString(Constants.KEY_MOBILE, loginResponse.getMobileNumber());
        editor.apply();

        successEvent.setLoginRequired(false);
        EventBus.getDefault().post(successEvent);
    }

    public void saveCourses(final RefreshResponse refreshResponse, SuccessEvent successEvent) {

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

        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_SEMESTER, refreshResponse.getSemester());
        editor.putString(Constants.KEY_COURSES_REFRESHED, refreshResponse.getRefreshed());
        editor.apply();

        successEvent.setRefreshDone(true);
        EventBus.getDefault().post(successEvent);
    }

    public void saveGrades(final GradesResponse gradesResponse, SuccessEvent successEvent) {

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

        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_GRADES_REFRESHED, gradesResponse.getRefreshed());
        editor.apply();

        successEvent.setGradesDone(true);
        EventBus.getDefault().post(successEvent);
    }

    public void saveToken(final TokenResponse tokenResponse, SuccessEvent successEvent) {

        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_SHARE_TOKEN, tokenResponse.getTokenShare().getToken());
        editor.putString(Constants.KEY_SHARE_TOKEN_ISSUED, tokenResponse.getTokenShare().getIssued());
        editor.apply();

        successEvent.setTokenDone(true);
        EventBus.getDefault().post(successEvent);
    }

    public void saveFriend(final Friend friend) {

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

        EventBus.getDefault().post(new FriendEvent(friend.getCampus(), friend.getRegisterNumber()));
    }
}
