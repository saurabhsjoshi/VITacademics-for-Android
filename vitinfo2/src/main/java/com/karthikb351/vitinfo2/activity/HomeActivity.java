package com.karthikb351.vitinfo2.activity;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.squareup.otto.Bus;

import org.parceler.javaxinject.Inject;

/**
 * Created by aashrai on 21/3/15.
 */
public class HomeActivity extends FragmentActivity {

    @Inject
    SharedPreferences prefs;
    @Inject
    SharedPreferences.Editor prefEditor;
    @Inject
    Bus mBus;

}
