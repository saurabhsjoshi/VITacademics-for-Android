/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 *
 * This file is part of VITacademics.
 * VITacademics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VITacademics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VITacademics.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.utility.Constants;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
                String campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
                String registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
                String dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
                String mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);

                if (Constants.CAMPUS_VELLORE.equals(campus)) {
                    if (registerNumber != null && dateOfBirth != null && mobileNumber != null) {
                        startActivity(new Intent(Splash.this,MainActivity.class));
                    } else {
                        startActivity(new Intent(Splash.this,LoginActivity.class));
                    }
                } else if (Constants.CAMPUS_CHENNAI.equals(campus)) {
                    if (registerNumber != null && dateOfBirth != null) {
                        startActivity(new Intent(Splash.this,MainActivity.class));
                    } else {
                        startActivity(new Intent(Splash.this,LoginActivity.class));
                    }
                } else {
                    startActivity(new Intent(Splash.this,LoginActivity.class));
                }
            }
        }, Constants.SPLASH_TIME_OUT);
    }

    protected void onPause() {
        super.onPause();
        finish();
    }
}
