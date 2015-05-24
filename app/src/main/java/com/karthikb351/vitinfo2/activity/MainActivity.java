package com.karthikb351.vitinfo2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.bus.BusProvider;
import com.karthikb351.vitinfo2.bus.events.GetShareTokenEvent;
import com.karthikb351.vitinfo2.bus.events.LoginCompleteEvent;
import com.karthikb351.vitinfo2.bus.events.LoginEvent;
import com.karthikb351.vitinfo2.bus.events.RefreshDataEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by karthikbalakrishnan on 19/03/15.
 */
public class MainActivity extends Activity {

    Bus mBus = BusProvider.getInstance();

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = (Button)findViewById(R.id.login);
        Button refresh = (Button)findViewById(R.id.refresh);
        Button register = (Button)findViewById(R.id.shareToken);

        login.setOnClickListener(ocl);
        refresh.setOnClickListener(ocl);
        register.setOnClickListener(ocl);

    }

    View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           switch (v.getId()){

               case R.id.login:
                   mBus.post(new LoginEvent());
                   break;

               case R.id.refresh:
                   mBus.post(new RefreshDataEvent());

                   break;

               case R.id.shareToken:
                   mBus.post(new GetShareTokenEvent());
                   break;

           }
        }
    };

    @Subscribe
    public void onLoginCompleteEvent(LoginCompleteEvent event)
    {
        Toast.makeText(MainActivity.this, "Reg:"+event.getLoginResponse().getRegno()+" Status:"+event.getLoginResponse().getStatus().getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mBus.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBus.unregister(this);
    }

    /**
     * Stores the registration id, app versionCode, and expiration time in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration id
     */
    private void setRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        //Log.v(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration id for application on GCM service.
     * <p>
     * If result is empty, the registration has failed.
     *
     * @return registration id, or empty string if the registration is not
     *         complete.
     */
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.length() == 0) {
            //Log.v(TAG, "Registration not found.");
            return "";
        }
        // check if app was updated; if so, it must clear registration id to
        // avoid a race condition if GCM sends a message
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            //Log.v(TAG, "App version changed or registration expired.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }

}
