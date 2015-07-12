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

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.ResultListener;

public class NetworkController {

    private static NetworkController networkController;

    private String campus;
    private String registerNumber;
    private String dateOfBirth;
    private String mobileNumber;

    private Context context;
    private VITacademicsAPI viTacademicsAPI;

    private NetworkController(Context context, String campus, String registerNumber, String dateOfBirth, String mobileNumber) {
        this.campus = campus;
        this.registerNumber = registerNumber;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;

        this.context = context;
        this.viTacademicsAPI = new VITacademicsAPI(context);
        networkController = this;
    }

    private NetworkController(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        this.campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
        this.registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
        this.dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
        this.mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);

        this.context = context;
        this.viTacademicsAPI = new VITacademicsAPI(context);
        networkController = this;
    }

    public static NetworkController getInstance(Context context) {
        if (networkController != null) {
            networkController.context = context;
            networkController.viTacademicsAPI = new VITacademicsAPI(context);

            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
            networkController.campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
            networkController.registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
            networkController.dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
            networkController.mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);

            return networkController;
        }
        return new NetworkController(context);
    }

    public static NetworkController getInstance(Context context, String campus, String registerNumber, String dateOfBirth, String mobileNumber) {
        if (networkController != null) {
            networkController.context = context;
            networkController.viTacademicsAPI = new VITacademicsAPI(context);

            networkController.campus = campus;
            networkController.registerNumber = registerNumber;
            networkController.dateOfBirth = dateOfBirth;
            networkController.mobileNumber = mobileNumber;

            return networkController;
        }
        return new NetworkController(context, campus, registerNumber, dateOfBirth, mobileNumber);
    }

    public void dispatch(final RequestConfig requestConfig) {
        final ResultListener resultListener = new ResultListener() {
            @Override
            public void onSuccess() {
                requestConfig.removeRequest(requestConfig.getFirstRequest());
                if (requestConfig.getRequestsLeft() > 0) {
                    performRequest(requestConfig.getFirstRequest(), this);
                }
                else {
                    requestConfig.getResultListener().onSuccess();
                }
            }

            @Override
            public void onFailure(Status status) {
                if (status.getCode() == StatusCodes.TIMED_OUT) {
                    requestConfig.addRequest(RequestConfig.REQUEST_LOGIN);
                    performRequest(requestConfig.getFirstRequest(), this);
                }
                else {
                    requestConfig.getResultListener().onFailure(status);
                }
            }
        };

        if (requestConfig.getRequestsLeft() > 0) {
            performRequest(requestConfig.getFirstRequest(), resultListener);
        }
        else {
            requestConfig.getResultListener().onSuccess();
        }
    }

    private void performRequest(int request, ResultListener resultListener) {
        switch (request) {
            case RequestConfig.REQUEST_SYSTEM:
                viTacademicsAPI.system(resultListener);
                break;
            case RequestConfig.REQUEST_LOGIN:
                viTacademicsAPI.login(campus, registerNumber, dateOfBirth, mobileNumber, resultListener);
                break;
            case RequestConfig.REQUEST_REFRESH:
                viTacademicsAPI.refresh(campus, registerNumber, dateOfBirth, mobileNumber, resultListener);
                break;
            case RequestConfig.REQUEST_GRADES:
                viTacademicsAPI.grades(campus, registerNumber, dateOfBirth, mobileNumber, resultListener);
                break;
            case RequestConfig.REQUEST_TOKEN:
                viTacademicsAPI.token(campus, registerNumber, dateOfBirth, mobileNumber, resultListener);
                break;
            default:
                resultListener.onFailure(new Status(StatusCodes.UNKNOWN, context.getResources().getString(R.string.api_unknown_error)));
        }
    }
}
