/*
 * VITacademics
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karthikb351.vitinfo2.api.response.GradesResponse;
import com.karthikb351.vitinfo2.api.response.LoginResponse;
import com.karthikb351.vitinfo2.api.response.RefreshResponse;
import com.karthikb351.vitinfo2.api.response.ShareResponse;
import com.karthikb351.vitinfo2.api.response.SystemResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class VITacademicsAPI {

    private static final String BASE_URL = "https://vitacademics-staging.herokuapp.com";

    private APIService service;
    private static VITacademicsAPI api;

    public VITacademicsAPI() {
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(APIService.class);
        api=this;
    }

    public static VITacademicsAPI getAPI() {
        if(api==null)
            api = new VITacademicsAPI();
        return api;
    }

    public APIService getAPIService()
    {
        return service;
    }

    void system() {
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

    void refresh(String campus, String regno, String dob, String mobile) {
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

    void login(String campus, String regno, String dob, String mobile) {
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

    void share(String campus, String regno, String dob, String mobile) {
        service.share(campus, regno, dob, mobile, new Callback<ShareResponse>() {
            @Override
            public void success(ShareResponse shareTokenResponse, Response response) {
                // TODO Handle success
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO Handle failure
            }
        });
    }

    void grades(String campus, String regno, String dob, String mobile) {
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
}
