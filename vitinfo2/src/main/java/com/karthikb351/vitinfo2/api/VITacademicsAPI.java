package com.karthikb351.vitinfo2.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karthikb351.vitinfo2.VITacademics;
import com.karthikb351.vitinfo2.api.models.RefreshResponse;
import com.karthikb351.vitinfo2.bus.BusProvider;
import com.karthikb351.vitinfo2.bus.events.OnRefreshDataEvent;
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

    public VITacademicsAPI() {
        Gson gson = new GsonBuilder().create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        service = restAdapter.create(VITacademicsService.class);
        BusProvider.getInstance().register(this);
    }

    public VITacademicsService getApiService()
    {
        return service;
    }

    @Subscribe
    void refreshAttendance(OnRefreshDataEvent event) {
        service.refresh("vellore", "11BCE0354", "28011993", new Callback<RefreshResponse>() {
            @Override
            public void success(RefreshResponse refreshResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
