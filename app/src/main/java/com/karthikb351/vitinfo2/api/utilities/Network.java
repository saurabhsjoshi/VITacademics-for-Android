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

import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.api.VITacademicsAPI;
import com.karthikb351.vitinfo2.api.contract.Friend;

import java.util.List;

public class Network {

    private String campus;
    private String registerNumber;
    private String dateOfBirth;
    private String mobileNumber;

    private VITacademicsAPI viTacademicsAPI;

    public Network(Context context, String campus, String registerNumber, String dateOfBirth, String mobileNumber) {
        this.campus = campus;
        this.registerNumber = registerNumber;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;

        this.viTacademicsAPI = new VITacademicsAPI(context);
    }

    public Network(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        this.campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
        this.registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
        this.dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
        this.mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);

        this.viTacademicsAPI = new VITacademicsAPI(context);
    }

    public void getCourses(boolean callSystem) {
        if (callSystem) {
            viTacademicsAPI.system();
        }
        viTacademicsAPI.refresh(campus, registerNumber, dateOfBirth, mobileNumber);
    }

    public void getGrades(boolean callSystem) {
        if (callSystem) {
            viTacademicsAPI.system();
        }
        viTacademicsAPI.grades(campus, registerNumber, dateOfBirth, mobileNumber);
    }

    public void getToken(boolean callSystem) {
        if (callSystem) {
            viTacademicsAPI.system();
        }
        viTacademicsAPI.token(campus, registerNumber, dateOfBirth, mobileNumber);
    }

    public void getAllFriends(boolean callSystem) {
        if (callSystem) {
            viTacademicsAPI.system();
        }
        List<Friend> friends = Friend.listAll(Friend.class);
        for(Friend friend : friends) {
            getFriend(false, friend);
        }
    }

    public void getFriend(boolean callSystem, Friend friend) {
        if (callSystem) {
            viTacademicsAPI.system();
        }
        viTacademicsAPI.share(friend.getCampus(), friend.getRegisterNumber(), friend.getDateOfBirth(), friend.getMobileNumber(), this.registerNumber);
    }

    public void getFriend(boolean callSystem, String campus, String token) {
        if (callSystem) {
            viTacademicsAPI.system();
        }
        viTacademicsAPI.share(campus, token, this.registerNumber);
    }

    public void getFriend(boolean callSystem, String campus, String registerNumber, String dateOfBirth, String mobileNumber) {
        if (callSystem) {
            viTacademicsAPI.system();
        }
        viTacademicsAPI.share(campus, registerNumber, dateOfBirth, mobileNumber, this.registerNumber);
    }

    public void refreshAll() {
        viTacademicsAPI.system();
        getCourses(false);
        getGrades(false);
        getToken(false);
        getAllFriends(false);
    }
}
