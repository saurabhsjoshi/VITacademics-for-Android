package com.karthikb351.vitinfo2;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.karthikb351.vitinfo2.api.VITacademicsAPI;
import com.karthikb351.vitinfo2.bus.APIErrorEvent;
import com.karthikb351.vitinfo2.bus.BusProvider;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by karthikbalakrishnan on 05/02/15.
 */
public class VITacademics extends Application {

    private Bus mBus = BusProvider.getInstance();
    private VITacademicsAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        Timber.tag("VITacademicsApplication");
        Timber.d("Application Created");

        api = new VITacademicsAPI();

        mBus.register(this); //listen for "global" events
    }

    @Subscribe
    public void onAPIErrorEvent(APIErrorEvent event) {
        Timber.e("An error as occured: %s", event.getMessage());
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args) {
            Crashlytics.log(String.format(message, args));
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);

            Crashlytics.logException(t);
        }
    }
}
