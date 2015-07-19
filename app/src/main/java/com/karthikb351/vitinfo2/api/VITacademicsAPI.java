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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class VITacademicsAPI {

    private Context context;
    private APIService service;
    private DatabaseController databaseController;

    public VITacademicsAPI(Context context) {

        this.context = context;
        this.databaseController = DatabaseController.getInstance(context);

        Gson gson = new GsonBuilder().create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Constants.API_BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        this.service = restAdapter.create(APIService.class);
    }

    public APIService getAPIService() {
        return this.service;
    }

    public void system(final ResultListener resultListener) {
        service.system(new Callback<SystemResponse>() {
            @Override
            public void success(final SystemResponse systemResponse, Response response) {
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
            }

            @Override
            public void failure(RetrofitError error) {
                resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
            }
        });
    }

    public void refresh(final String campus, final String regno, final String dob, final String mobile, final ResultListener resultListener) {
        service.refresh(campus, regno, dob, mobile, new Callback<RefreshResponse>() {
            @Override
            public void success(final RefreshResponse refreshResponse, Response response) {
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

            @Override
            public void failure(RetrofitError error) {
                resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
            }
        });
    }

    public void login(final String campus, final String regno, final String dob, final String mobile, final ResultListener resultListener) {
        service.login(campus, regno, dob, mobile, new Callback<LoginResponse>() {
            @Override
            public void success(final LoginResponse loginResponse, Response response) {
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

            @Override
            public void failure(RetrofitError error) {
                resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
            }
        });

    }

    public void token(final String campus, final String regno, final String dob, final String mobile, final ResultListener resultListener) {
        service.token(campus, regno, dob, mobile, new Callback<TokenResponse>() {
            @Override
            public void success(final TokenResponse tokenResponse, Response response) {
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

            @Override
            public void failure(RetrofitError error) {
                resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
            }
        });
    }

    public void grades(final String campus, final String regno, final String dob, final String mobile, final ResultListener resultListener) {
        service.grades(campus, regno, dob, mobile, new Callback<GradesResponse>() {
            @Override
            public void success(GradesResponse gradesResponse, Response response) {
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

            @Override
            public void failure(RetrofitError error) {
                resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
            }
        });
    }

    public void share(final String campus, final String token, final String receiver, final ResultListener resultListener) {
        service.share(campus, token, receiver, new Callback<Friend>() {
            @Override
            public void success(Friend friend, Response response) {
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

            @Override
            public void failure(RetrofitError error) {
                resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
            }
        });
    }

    public void share(final String campus, final String regno, final String dob, final String mobile, final String receiver, final ResultListener resultListener) {
        service.share(campus, regno, dob, mobile, receiver, new Callback<Friend>() {
            @Override
            public void success(Friend friend, Response response) {
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

            @Override
            public void failure(RetrofitError error) {
                resultListener.onFailure(new Status(StatusCodes.MAINTENANCE_DOWN, context.getResources().getString(R.string.api_server_error)));
            }
        });
    }
}
