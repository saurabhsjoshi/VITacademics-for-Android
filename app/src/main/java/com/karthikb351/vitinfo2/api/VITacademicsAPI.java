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

    private Database database;

    private APIService service;
    private static VITacademicsAPI api;

    private VITacademicsAPI(Context context) {

        this.database = new Database(context);
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(APIService.class);
        api = this;
    }

    public static VITacademicsAPI getAPI(Context context) {
        if (api == null) {
            api = new VITacademicsAPI(context);
        }
        return api;
    }

    public APIService getAPIService() {
        return service;
    }

    public void system() {
        service.system(new Callback<SystemResponse>() {
            @Override
            public void success(SystemResponse systemResponse, Response response) {
                // TODO Handle success
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle failure
            }
        });
    }

    public void refresh(String campus, String regno, String dob, String mobile) {
        service.refresh(campus, regno, dob, mobile, new Callback<RefreshResponse>() {
            @Override
            public void success(RefreshResponse refreshResponse, Response response) {
                // TODO Handle success
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle failure
            }
        });
    }

    public void login(String campus, String regno, String dob, String mobile) {
        service.login(campus, regno, dob, mobile, new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                // TODO Handle success
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle error
            }
        });

    }

    public void token(String campus, String regno, String dob, String mobile) {
        service.token(campus, regno, dob, mobile, new Callback<TokenResponse>() {
            @Override
            public void success(TokenResponse tokenResponse, Response response) {
                // TODO Handle success
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle failure
            }
        });
    }

    public void grades(String campus, String regno, String dob, String mobile) {
        service.grades(campus, regno, dob, mobile, new Callback<GradesResponse>() {
            @Override
            public void success(GradesResponse gradesResponse, Response response) {
                // TODO Handle success
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle failure
            }
        });
    }

    public void share(String campus, String token, String receiver) {
        service.share(campus, token, receiver, new Callback<Friend>() {
            @Override
            public void success(Friend friend, Response response) {
                // TODO Handle success
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle failure
            }
        });
    }

    public void share(String campus, String regno, String dob, String mobile, String receiver) {
        service.share(campus, regno, dob, mobile, receiver, new Callback<Friend>() {
            @Override
            public void success(Friend friend, Response response) {
                // TODO Handle success
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle failure
            }
        });
    }

    private int parseStatus(Status status) {

        int result;
        switch (status.getCode()) {
            case StatusCodes.SUCCESS:
                result = 0;
                break;
            case StatusCodes.TIMED_OUT:
            case StatusCodes.TOKEN_EXPIRED:
                result = 1;
                break;
            case StatusCodes.INVALID:
            case StatusCodes.DATA_PARSING:
                result = 2;
                break;
            case StatusCodes.NO_DATA:
                result = 3;
                break;
            default:
                result = 4;
                break;
        }
        return result;
    }
}
