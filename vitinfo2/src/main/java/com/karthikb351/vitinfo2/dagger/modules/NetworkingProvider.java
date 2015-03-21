package com.karthikb351.vitinfo2.dagger.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karthikb351.vitinfo2.api.VITacademicsAPI;
import com.squareup.otto.Bus;

import org.parceler.transfuse.annotations.Provides;

import javax.inject.Singleton;

import dagger.Module;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by aashrai on 21/3/15.
 */
@Module
public class NetworkingProvider {

    private static final String BASE_URL = "https://vitacademics-dev.herokuapp.com";


    //Maintaining a singleton instance of rest adapter costly to create everytime
    @Provides
    @Singleton
    public RestAdapter getRestAdapter(Gson gson) {
        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Provides
    @Singleton
    public Gson getGsonInstance() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    public VITacademicsAPI getVITacademicsAPI(RestAdapter adapter,Bus mBus){

        return new VITacademicsAPI(adapter,mBus);

    }

}
