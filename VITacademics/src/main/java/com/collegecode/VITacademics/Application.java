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
        Parse.initialize(this, "pslFDPvG2NmCKEW3v20X9QtgOabxtAvsetd3Keq6", "o3g05te1eRhgbc7pUC6bbzFtVypUuHLEauM3x4vY");

        // Specify an Activity to handle all pushes by default.
        PushService.setDefaultPushCallback(this, Home.class);
    }

}
