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
 *
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
import com.karthikb351.vitinfo2.contract.Assessment;
import com.karthikb351.vitinfo2.contract.Attendance;
import com.karthikb351.vitinfo2.contract.AttendanceDetail;
import com.karthikb351.vitinfo2.contract.Contributor;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Friend;
import com.karthikb351.vitinfo2.contract.FriendCourse;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.GradeCount;
import com.karthikb351.vitinfo2.contract.Marks;
import com.karthikb351.vitinfo2.contract.Message;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.contract.Spotlight;
import com.karthikb351.vitinfo2.contract.SpotlightMessage;
import com.karthikb351.vitinfo2.contract.Timing;
import com.karthikb351.vitinfo2.contract.WithdrawnCourse;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {

    private DataHolder dataHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        //Init JodaTime to avoid exceptions
        JodaTimeAndroid.init(this);
        // Rush is initialized asynchronously to receive a callback after it initialized
        // set an InitializeListener on the config object

        List<Class<? extends Rush>> classes = new ArrayList<>();
        // Add classes
        classes.add(Assessment.class);
        classes.add(Attendance.class);
        classes.add(AttendanceDetail.class);
        classes.add(Contributor.class);
        classes.add(Course.class);
        classes.add(Friend.class);
        classes.add(FriendCourse.class);
        classes.add(Grade.class);
        classes.add(GradeCount.class);
        classes.add(Marks.class);
        classes.add(Message.class);
        classes.add(SemesterWiseGrade.class);
        classes.add(Timing.class);
        classes.add(WithdrawnCourse.class);
        classes.add(SpotlightMessage.class);
        classes.add(Spotlight.class);

        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        config.setClasses(classes);

        RushCore.initialize(config);

        this.dataHolder = new DataHolder();

        /**
         * Calligraphy configuration to set the default font to Roboto-Regular
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
    //                        .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
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
