/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 * Copyright (C) 2015  Darshan Mehta <darshanmehta17@gmail.com>
 *
 * This file is part of VITacademics.
 *
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

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.response.GradesResponse;
import com.karthikb351.vitinfo2.response.LoginResponse;
import com.karthikb351.vitinfo2.response.RefreshResponse;
import com.karthikb351.vitinfo2.response.SystemResponse;
import com.karthikb351.vitinfo2.response.TokenResponse;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.ResultListener;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class VITacademicsAPI {

    private Context context;
    private APIService service;
    private DatabaseController databaseController;

    public VITacademicsAPI(Context context) {

        this.context = context;
        this.databaseController = DatabaseController.getInstance(context);

        Retrofit restAdapter = new Retrofit.Builder()
//                .setLogLevel(RestAdapter.LogLevel.FULL)
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = restAdapter.create(APIService.class);
    }

    public APIService getAPIService() {
        return this.service;
    }

    public void system(final ResultListener resultListener) {
        Call<SystemResponse> call = service.system();
        call.enqueue(new Callback<SystemResponse>() {
            @Override
            public void onResponse(Response<SystemResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    SystemResponse systemResponse = response.body();
                    switch (systemResponse.getStatus().getCode()) {
                        case StatusCodes.SUCCESS:
                            databaseController.saveSystem(systemResponse, new ResultListener() {
                                @Override
                                public void onSuccess() {
                                    resultListener.onSuccess();
                                }

                                @Override
                                public void onFailure(Status status) {
                                    resultListener.onFailure(status);
                                }
                            });
                            break;
                        default:
                            resultListener.onFailure(systemResponse.getStatus());
                    }
                } else {
                    int statusCode = response.code();
                    resultListener.onFailure(new Status(StatusCodes.UNKNOWN, context.getResources().getString(R.string.api_unknown_error)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO: Add no internet error
            }
        });
    }

    public void refresh(final String campus, final String regno, final String dob, final String mobile, final ResultListener resultListener) {
        Call<RefreshResponse> call = service.refresh(campus,regno,dob,mobile);

        call.enqueue(new Callback<RefreshResponse>() {
            @Override
            public void onResponse(Response<RefreshResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    RefreshResponse refreshResponse = response.body();
                    switch (refreshResponse.getStatus().getCode()) {
                        case StatusCodes.SUCCESS:
                            databaseController.saveCourses(refreshResponse, new ResultListener() {
                                @Override
                                public void onSuccess() {
                                    resultListener.onSuccess();
                                }

                                @Override
                                public void onFailure(Status status) {
                                    resultListener.onFailure(status);
                                }
                            });
                            break;
                        case StatusCodes.TIMED_OUT:
                            resultListener.onFailure(refreshResponse.getStatus());
                            break;
                        default:
                            resultListener.onFailure(refreshResponse.getStatus());
                            break;
                    }
                }
                else {
                int statusCode = response.code();
                    resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO: Add no internet error
            }
        });

    }

    public void login(final String campus, final String regno, final String dob, final String mobile, final ResultListener resultListener) {
        Call<LoginResponse> call = service.login(campus, regno, dob, mobile);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    LoginResponse loginResponse = response.body();
                    switch (loginResponse.getStatus().getCode()) {
                        case StatusCodes.SUCCESS:
                            databaseController.saveLogin(loginResponse, new ResultListener() {
                                @Override
                                public void onSuccess() {
                                    resultListener.onSuccess();
                                }

                                @Override
                                public void onFailure(Status status) {
                                    resultListener.onFailure(status);
                                }
                            });
                            break;
                        default:
                            resultListener.onFailure(loginResponse.getStatus());
                            break;
                    }
                }
                    else{
                        resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
                    }
                }

            @Override
            public void onFailure(Throwable t) {
                //TODO: Add no internet error
            }
        });
    }

    public void token(final String campus, final String regno, final String dob, final String mobile, final ResultListener resultListener) {
        Call<TokenResponse> call = service.token(campus, regno, dob, mobile);

        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Response<TokenResponse> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    TokenResponse tokenResponse = response.body();
                    switch (tokenResponse.getStatus().getCode()) {
                        case StatusCodes.SUCCESS:
                            databaseController.saveToken(tokenResponse, new ResultListener() {
                                @Override
                                public void onSuccess() {
                                    resultListener.onSuccess();
                                }

                                @Override
                                public void onFailure(Status status) {
                                    resultListener.onFailure(status);
                                }
                            });
                            break;
                        default:
                            resultListener.onFailure(tokenResponse.getStatus());
                            break;
                    }
                }
                else{
                    resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO : Internet Error
            }
        });
    }

    public void grades(final String campus, final String regno, final String dob, final String mobile, final ResultListener resultListener) {

        final Call<GradesResponse> call = service.grades(campus, regno, dob, mobile);

        call.enqueue(new Callback<GradesResponse>() {
            @Override
            public void onResponse(Response<GradesResponse> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    GradesResponse gradesResponse = response.body();
                    switch (gradesResponse.getStatus().getCode()) {
                        case StatusCodes.SUCCESS:
                            databaseController.saveGrades(gradesResponse, new ResultListener() {
                                @Override
                                public void onSuccess() {
                                    resultListener.onSuccess();
                                }

                                @Override
                                public void onFailure(Status status) {
                                    resultListener.onFailure(status);
                                }
                            });
                            break;
                        case StatusCodes.TIMED_OUT:
                            resultListener.onFailure(gradesResponse.getStatus());
                            break;
                        default:
                            resultListener.onFailure(gradesResponse.getStatus());
                            break;
                    }
                }
                else {
                    resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO : Internet Error
            }
        });
    }

    public void share(final String campus, final String token, final String receiver, final ResultListener resultListener) {
        final Call<Friend> call = service.share(campus,token,receiver);

        call.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(Response<Friend> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Friend friend = response.body();
                    switch (friend.getStatus().getCode()) {
                        case StatusCodes.SUCCESS:
                            databaseController.saveFriend(friend, new ResultListener() {
                                @Override
                                public void onSuccess() {
                                    resultListener.onSuccess();
                                }

                                @Override
                                public void onFailure(Status status) {
                                    resultListener.onFailure(status);
                                }
                            });
                            break;
                        default:
                            resultListener.onFailure(friend.getStatus());
                            break;
                    }
                }
                else {
                    resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void share(final String campus, final String regno, final String dob, final String mobile, final String receiver, final ResultListener resultListener) {
        Call<Friend> call = service.share(campus, regno, dob, mobile, receiver);


        call.enqueue(new Callback<Friend>() {
            @Override
            public void onResponse(Response<Friend> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Friend friend = response.body();
                    switch (friend.getStatus().getCode()) {
                        case StatusCodes.SUCCESS:
                            databaseController.saveFriend(friend, new ResultListener() {
                                @Override
                                public void onSuccess() {
                                    resultListener.onSuccess();
                                }

                                @Override
                                public void onFailure(Status status) {
                                    resultListener.onFailure(status);
                                }
                            });
                            break;
                        default:
                            resultListener.onFailure(friend.getStatus());
                            break;
                    }
                }

                else{
                    resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //TODO
            }
        });
    }
}
