/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
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
import android.widget.Toast;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.ResetTask;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.ResultListener;

public class LaunchScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_launch_screen);

        boolean isLoggedIn = loginCheck();

        if (isLoggedIn) {
            ((MainApplication) getApplication()).getDataHolderInstance().refreshData(LaunchScreenActivity.this, new ResultListener() {
                @Override
                public void onSuccess() {
                    startActivity(new Intent(LaunchScreenActivity.this, MainActivity.class));
                }

                @Override
                public void onFailure(Status status) {
                    Toast.makeText(LaunchScreenActivity.this, status.getMessage(), Toast.LENGTH_SHORT).show();
                    new ResetTask(LaunchScreenActivity.this).execute();
                    startActivity(new Intent(LaunchScreenActivity.this, LoginActivity.class));
                }
            });
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LaunchScreenActivity.this, LoginActivity.class));
                }
            }, Constants.SPLASH_TIME_OUT);
        }
    }

    private boolean loginCheck() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        String campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
        String registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
        String dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
        String mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);

        return (Constants.CAMPUS_VELLORE.equals(campus) && registerNumber != null && dateOfBirth != null && mobileNumber != null) || (Constants.CAMPUS_CHENNAI.equals(campus) && registerNumber != null && dateOfBirth != null);
    }

    protected void onPause() {
        super.onPause();
        finish();
    }
}
