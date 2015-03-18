package com.karthikb351.vitinfo2.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karthikb351.vitinfo2.api.models.LoginResponse;
import com.karthikb351.vitinfo2.api.models.RefreshResponse;
import com.karthikb351.vitinfo2.api.models.ShareTokenResponse;
import com.karthikb351.vitinfo2.bus.BusProvider;
import com.karthikb351.vitinfo2.bus.events.GetShareTokenCompleteEvent;
import com.karthikb351.vitinfo2.bus.events.GetShareTokenEvent;
import com.karthikb351.vitinfo2.bus.events.LoginCompleteEvent;
import com.karthikb351.vitinfo2.bus.events.LoginEvent;
import com.karthikb351.vitinfo2.bus.events.RefreshDataEvent;
import com.karthikb351.vitinfo2.bus.events.RefreshDataCompleteEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by karthikbalakrishnan on 05/02/15.
 */
public class VITacademicsAPI {

    private static final String BASE_URL = "https://vitacademics-dev.herokuapp.com";
    private VITacademicsService service;
    private Bus mBus;

    public VITacademicsAPI() {
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(VITacademicsService.class);
        mBus=BusProvider.getInstance();
        mBus.register(this);
    }

    public VITacademicsService getApiService()
    {
        return service;
    }

    @Subscribe
    void refresh(RefreshDataEvent event) {
        service.refresh("vellore", "11BCE0354", "28011993", new Callback<RefreshResponse>() {
            @Override
            public void success(RefreshResponse refreshResponse, Response response) {
                mBus.post(new RefreshDataCompleteEvent());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Subscribe
    void login(LoginEvent event) {
        service.login("vellore", "11BCE0354", "28011993", new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                mBus.post(new LoginCompleteEvent());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    @Subscribe
    void getShareToken(GetShareTokenEvent event) {
        service.getShareToken("vellore", "11BCE0354", "28011993", new Callback<ShareTokenResponse>() {
            @Override
            public void success(ShareTokenResponse shareTokenResponse, Response response) {
                mBus.post(new GetShareTokenCompleteEvent());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
