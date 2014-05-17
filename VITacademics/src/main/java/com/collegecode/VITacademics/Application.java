package com.collegecode.VITacademics;

import com.parse.Parse;
import com.parse.PushService;

/**
 * Created by saurabh on 5/16/14.
 */
public class Application extends android.app.Application {
    public Application() {
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
