package com.karthikb351.vitinfo2.api;

import com.karthikb351.vitinfo2.VITacademics;
import com.karthikb351.vitinfo2.api.models.LoginResponse;
import com.karthikb351.vitinfo2.api.models.RefreshResponse;
import com.karthikb351.vitinfo2.api.models.ShareTokenResponse;
import com.karthikb351.vitinfo2.bus.APIErrorEvent;
import com.karthikb351.vitinfo2.bus.events.GetShareTokenCompleteEvent;
import com.karthikb351.vitinfo2.bus.events.GetShareTokenEvent;
import com.karthikb351.vitinfo2.bus.events.LoginCompleteEvent;
import com.karthikb351.vitinfo2.bus.events.LoginEvent;
import com.karthikb351.vitinfo2.bus.events.RefreshDataCompleteEvent;
import com.karthikb351.vitinfo2.bus.events.RefreshDataEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by karthikbalakrishnan on 05/02/15.
 */
public class VITacademicsAPI {


    Bus mBus;
    private VITacademicsService service;

    @Inject
    public VITacademicsAPI(RestAdapter restAdapter, Bus mBus) {

        VITacademics.component().inject(this);
        service = restAdapter.create(VITacademicsService.class);
        this.mBus = mBus;
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
                mBus.post(new APIErrorEvent(error.getMessage()));
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
                mBus.post(new APIErrorEvent(error.getMessage()));
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
                mBus.post(new APIErrorEvent(error.getMessage()));
            }
        });
    }
}
