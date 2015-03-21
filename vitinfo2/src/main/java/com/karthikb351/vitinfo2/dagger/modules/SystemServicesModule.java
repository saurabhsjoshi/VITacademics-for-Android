package com.karthikb351.vitinfo2.dagger.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.karthikb351.vitinfo2.activity.MainActivity;
import com.karthikb351.vitinfo2.bus.BusProvider;
import com.squareup.otto.Bus;

import org.parceler.transfuse.annotations.Provides;

import javax.inject.Singleton;

import dagger.Module;

@Module
public class SystemServicesModule {

    private final Application application;

    public SystemServicesModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideContext() {
        return application;
    }


    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);

    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideSharedPreferencesEditor(SharedPreferences preferences) {
        return preferences.edit();
    }

    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager() {
        return (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    Bus provideOttoBus() {
        return BusProvider.getInstance();
    }
}