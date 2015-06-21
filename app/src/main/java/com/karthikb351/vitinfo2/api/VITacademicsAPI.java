/*
 * VITacademics
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karthikb351.vitinfo2.api.contract.Friend;
import com.karthikb351.vitinfo2.api.models.Status;
import com.karthikb351.vitinfo2.api.response.GradesResponse;
import com.karthikb351.vitinfo2.api.response.LoginResponse;
import com.karthikb351.vitinfo2.api.response.RefreshResponse;
import com.karthikb351.vitinfo2.api.response.SystemResponse;
import com.karthikb351.vitinfo2.api.response.TokenResponse;
import com.karthikb351.vitinfo2.api.utilities.Database;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class VITacademicsAPI {

    private static final String BASE_URL = "https://vitacademics-staging.herokuapp.com";

    private Context context;

    private Database database;

    private APIService service;

    private static final int CODE_SUCCESS = 0;
    private static final int CODE_EXPIRED_TRY_AGAIN = 1;
    private static final int CODE_INVALID = 2;
    private static final int CODE_UNAVAILABLE = 3;
    private static final int CODE_ERROR = 4;

    public VITacademicsAPI(Context context) {

        this.context = context;
        this.database = new Database(context);

        Gson gson = new GsonBuilder().create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        this.service = restAdapter.create(APIService.class);
    }

    public APIService getAPIService() {
        return this.service;
    }

    public void system() {
        service.system(new Callback<SystemResponse>() {
            @Override
            public void success(SystemResponse systemResponse, Response response) {
                switch (parseStatus(systemResponse.getStatus())) {
                    case CODE_SUCCESS:
                        database.saveSystem(systemResponse);
                        break;
                    default:
                        // TODO Handle System Failure
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle System Failure
            }
        });
    }

    public void refresh(final String campus, final String regno, final String dob, final String mobile) {
        service.refresh(campus, regno, dob, mobile, new Callback<RefreshResponse>() {
            @Override
            public void success(RefreshResponse refreshResponse, Response response) {
                switch (parseStatus(refreshResponse.getStatus())) {
                    case CODE_SUCCESS:
                        database.saveCourses(refreshResponse);
                        break;
                    case CODE_EXPIRED_TRY_AGAIN:
                        login(campus, regno, dob, mobile);
                        refresh(campus, regno, dob, mobile);
                        break;
                    case CODE_INVALID:
                        // TODO Handle Invalid Credentials/Data Parsing
                        break;
                    default:
                        // TODO Handle Course Refresh Failure
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle Course Refresh Failure
            }
        });
    }

    public void login(String campus, String regno, String dob, String mobile) {
        service.login(campus, regno, dob, mobile, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                switch (parseStatus(loginResponse.getStatus())) {
                    case CODE_SUCCESS:
                        database.saveLogin(loginResponse);
                        break;
                    case CODE_INVALID:
                        // TODO Handle Invalid Credentials
                        break;
                    default:
                        // TODO Handle Course Refresh Failure
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle error
            }
        });

    }

    public void token(final String campus, final String regno, final String dob, final String mobile) {
        service.token(campus, regno, dob, mobile, new Callback<TokenResponse>() {
            @Override
            public void success(TokenResponse tokenResponse, Response response) {
                switch (parseStatus(tokenResponse.getStatus())) {
                    case CODE_SUCCESS:
                        database.saveToken(tokenResponse);
                        break;
                    case CODE_INVALID:
                        // TODO Handle Invalid Credentials
                        break;
                    default:
                        // TODO Handle Token Refresh Failure
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle token failure
            }
        });
    }

    public void grades(final String campus, final String regno, final String dob, final String mobile) {
        service.grades(campus, regno, dob, mobile, new Callback<GradesResponse>() {
            @Override
            public void success(GradesResponse gradesResponse, Response response) {
                switch (parseStatus(gradesResponse.getStatus())) {
                    case CODE_SUCCESS:
                        database.saveGrades(gradesResponse);
                        break;
                    case CODE_EXPIRED_TRY_AGAIN:
                        login(campus, regno, dob, mobile);
                        grades(campus, regno, dob, mobile);
                        break;
                    case CODE_INVALID:
                        // TODO Handle Invalid Credentials/Data Parsing
                        break;
                    default:
                        // TODO Handle Grades Refresh Failure
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle Grades Refresh Failure
            }
        });
    }

    public void share(final String campus, final String token, final String receiver) {
        service.share(campus, token, receiver, new Callback<Friend>() {
            @Override
            public void success(Friend friend, Response response) {
                switch (parseStatus(friend.getStatus())) {
                    case CODE_SUCCESS:
                        database.saveFriend(friend);
                        break;
                    case CODE_EXPIRED_TRY_AGAIN:
                        // TODO Handle Expired Token
                        break;
                    case CODE_INVALID:
                        // TODO Handle Invalid Credentials
                        break;
                    case CODE_UNAVAILABLE:
                        // TODO Handle No User Data Available
                        break;
                    default:
                        // TODO Handle Friend Refresh Failure
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle Friend Refresh Failure
            }
        });
    }

    public void share(final String campus, final String regno, final String dob, final String mobile, final String receiver) {
        service.share(campus, regno, dob, mobile, receiver, new Callback<Friend>() {
            @Override
            public void success(Friend friend, Response response) {
                switch (parseStatus(friend.getStatus())) {
                    case CODE_SUCCESS:
                        database.saveFriend(friend);
                        break;
                    case CODE_EXPIRED_TRY_AGAIN:
                        // TODO Handle Expired Token
                        break;
                    case CODE_INVALID:
                        // TODO Handle Invalid Credentials
                        break;
                    case CODE_UNAVAILABLE:
                        // TODO Handle No User Data Available
                        break;
                    default:
                        // TODO Handle Friend Refresh Failure
                        break;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle Friend Refresh Failure
            }
        });
    }

    private int parseStatus(Status status) {

        int result;
        switch (status.getCode()) {
            case StatusCodes.SUCCESS:
                result = CODE_SUCCESS;
                break;
            case StatusCodes.TIMED_OUT:
            case StatusCodes.TOKEN_EXPIRED:
                result = CODE_EXPIRED_TRY_AGAIN;
                break;
            case StatusCodes.INVALID:
            case StatusCodes.DATA_PARSING:
                result = CODE_INVALID;
                break;
            case StatusCodes.NO_DATA:
                result = CODE_UNAVAILABLE;
                break;
            default:
                result = CODE_ERROR;
                break;
        }
        return result;
    }
}
