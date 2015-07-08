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

import com.karthikb351.vitinfo2.Constants;

import de.greenrobot.event.EventBus;

public class NetworkController {

    private static NetworkController networkController;

    private String campus;
    private String registerNumber;
    private String dateOfBirth;
    private String mobileNumber;

    private VITacademicsAPI viTacademicsAPI;

    private NetworkController(Context context, String campus, String registerNumber, String dateOfBirth, String mobileNumber) {
        this.campus = campus;
        this.registerNumber = registerNumber;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;

        this.viTacademicsAPI = new VITacademicsAPI(context);
        networkController = this;

        EventBus.getDefault().register(this);
    }

    private NetworkController(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        this.campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
        this.registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
        this.dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
        this.mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);

        this.viTacademicsAPI = new VITacademicsAPI(context);
        networkController = this;
    }

    public static NetworkController getNetworkControllerSingleton(Context context) {
        if (networkController != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
            networkController.viTacademicsAPI = new VITacademicsAPI(context);
            networkController.campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
            networkController.registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
            networkController.dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
            networkController.mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);
            return networkController;
        }
        return new NetworkController(context);
    }

    public static NetworkController getNetworkControllerSingleton(Context context, String campus, String registerNumber, String dateOfBirth, String mobileNumber) {
        if (networkController != null) {
            networkController.viTacademicsAPI = new VITacademicsAPI(context);

            networkController.campus = campus;
            networkController.registerNumber = registerNumber;
            networkController.dateOfBirth = dateOfBirth;
            networkController.mobileNumber = mobileNumber;
            return networkController;
        }
        return new NetworkController(context, campus, registerNumber, dateOfBirth, mobileNumber);
    }

    public void dispatch(RequestConfig requestConfig) {
        // TODO Dispatch API requests
    }
}
