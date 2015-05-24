package com.karthikb351.vitinfo2.old.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karthikb351.vitinfo2.old.api.models.LoginResponse;
import com.karthikb351.vitinfo2.old.api.models.RefreshResponse;
import com.karthikb351.vitinfo2.old.api.models.ShareTokenResponse;
import com.karthikb351.vitinfo2.old.api.typeadapters.LoginResponseSerializer;
import com.karthikb351.vitinfo2.old.api.typeadapters.ShareTokenResponseSerializer;
import com.karthikb351.vitinfo2.old.bus.APIErrorEvent;
import com.karthikb351.vitinfo2.old.bus.BusProvider;
import com.karthikb351.vitinfo2.old.bus.events.GetShareTokenCompleteEvent;
import com.karthikb351.vitinfo2.old.bus.events.GetShareTokenEvent;
import com.karthikb351.vitinfo2.old.bus.events.LoginCompleteEvent;
import com.karthikb351.vitinfo2.old.bus.events.LoginEvent;
import com.karthikb351.vitinfo2.old.bus.events.RefreshDataEvent;
import com.karthikb351.vitinfo2.old.bus.events.RefreshDataCompleteEvent;
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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LoginResponse.class, new LoginResponseSerializer())
                .registerTypeAdapter(ShareTokenResponse.class, new ShareTokenResponseSerializer())
                .create();

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
    public void refresh(RefreshDataEvent event) {
        service.refresh("vellore", "11BCE0354", "28011993", "9123456789", new Callback<RefreshResponse>() {
            @Override
            public void success(RefreshResponse refreshResponse, Response response) {
                mBus.post(new RefreshDataCompleteEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                mBus.post(new APIErrorEvent(error.getMessage()));
            }
        });
    }

    @Subscribe
    public void login(LoginEvent event) {
        service.login("vellore", "11BCE0354", "28011993", "9123456789", new Callback<LoginResponse>() {
            @Override
            public void success(LoginResponse loginResponse, Response response) {
                mBus.post(new LoginCompleteEvent(loginResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                mBus.post(new APIErrorEvent(error.getMessage()));
            }
        });

    }

    @Subscribe
    public void getShareToken(GetShareTokenEvent event) {
        service.getShareToken("vellore", "11BCE0354", "28011993", "9123456789", new Callback<ShareTokenResponse>() {
            @Override
            public void success(ShareTokenResponse shareTokenResponse, Response response) {
                mBus.post(new GetShareTokenCompleteEvent());
            }

            @Override
            public void failure(RetrofitError error) {
                mBus.post(new APIErrorEvent(error.getMessage()));
            }
        });
    }
}
