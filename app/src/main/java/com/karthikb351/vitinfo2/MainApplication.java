/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 * Copyright (C) 2015  Darshan Mehta <darshanmehta17@gmail.com>
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

package com.karthikb351.vitinfo2;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.karthikb351.vitinfo2.api.DataHolder;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.RushCore;
import io.fabric.sdk.android.Fabric;

public class MainApplication extends Application {

    private DataHolder dataHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        // Rush is initialized asynchronously to receive a callback after it initialized
        // set an InitializeListener on the config object
        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        RushCore.initialize(config);

        this.dataHolder = new DataHolder();
    }

    public DataHolder getDataHolderInstanceInitialized() {
        if (this.dataHolder != null) {
            if (this.dataHolder.isInitialized()) {
                return this.dataHolder;
            } else {
                this.dataHolder.refreshData(getApplicationContext());
                return this.dataHolder;
            }
        }
        this.dataHolder = new DataHolder();

        if (this.dataHolder.isInitialized()) {
            return this.dataHolder;
        } else {
            this.dataHolder.refreshData(getApplicationContext());
            return this.dataHolder;
        }
    }

    public DataHolder getDataHolderInstance() {
        if (this.dataHolder != null) {
            return this.dataHolder;
        }
        this.dataHolder = new DataHolder();
        return this.dataHolder;
    }
}
