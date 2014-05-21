package com.karthikb351.vitinfo2;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.PushService;

import java.util.HashMap;

/**
 * Created by saurabh on 5/16/14.
 */
public class Application extends android.app.Application {
    public Application() {
    }

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.

    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.global_tracker);
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the Parse SDK.
        Parse.initialize(this, "vtpDFHGacMwZIpMtpDaFuu0ToBol9b9nQM9VD57N", "OCKn8dB6wqeGqvSdYbXHgYe9mDGFb2yukyDHT3Fs");

        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, Home.class);
    }

}
