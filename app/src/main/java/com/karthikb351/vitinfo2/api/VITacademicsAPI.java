package com.karthikb351.vitinfo2.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karthikb351.vitinfo2.api.response.LoginResponse;
import com.karthikb351.vitinfo2.api.response.RefreshResponse;
import com.karthikb351.vitinfo2.api.response.ShareResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by karthikbalakrishnan on 16/06/15.
 */
public class VITacademicsAPI {

    private static final String BASE_URL = "https://vitacademics-dev.herokuapp.com";
    private APIService service;

    public VITacademicsAPI() {
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(APIService.class);
    }

    public APIService getApiService()
    {
        return service;
    }

    void refresh() {
        service.refresh("vellore", "11BCE0354", "28011993", new Callback<RefreshResponse>() {
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

    void login() {
        service.login("vellore", "11BCE0354", "28011993", new Callback<LoginResponse>() {
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

    void getShareToken() {
        service.getShareToken("vellore", "11BCE0354", "28011993", new Callback<ShareResponse>() {
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
}
