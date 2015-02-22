package com.karthikb351.vitinfo2.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
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
    }

    public VITacademicsService getApiService()
    {
        return service;
    }
}
