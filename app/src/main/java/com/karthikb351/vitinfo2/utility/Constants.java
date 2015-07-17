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

package com.karthikb351.vitinfo2.utility;

public class Constants {

    public static final String API_BASE_URL = "https://vitacademics-staging.herokuapp.com";

    public final static String FILENAME_SHAREDPREFERENCES = "vitacademics.prefs";

    public final static String KEY_COURSES_REFRESHED = "refreshed_courses";
    public final static String KEY_GRADES_REFRESHED = "refreshed_grades";

    public final static String KEY_CAMPUS = "campus";
    public final static String KEY_REGISTERNUMBER = "reg_no";
    public final static String KEY_DATEOFBIRTH = "dob";
    public final static String KEY_MOBILE = "mobile";

    public final static String CAMPUS_VELLORE = "vellore";
    public final static String CAMPUS_CHENNAI = "chennai";

    public final static String KEY_SEMESTER = "semester";

    public final static String KEY_ANDROID_LATEST_VERSION = "latest_version";
    public final static String KEY_ANDROID_SUPPORTED_VERSION = "supported_version";

    public final static String KEY_SHARE_TOKEN = "token";
    public final static String KEY_SHARE_TOKEN_ISSUED = "token_issued";

    public final static String KEY_GRADES_CGPA = "grades_cgpa";
    public final static String KEY_GRADES_CREDITS_EARNED = "grades_credits_earned";
    public final static String KEY_GRADES_CREDITS_REGISTERED = "grades_credits_registered";

    public final static int SPLASH_TIME_OUT = 2000;

    public final static String SQL_FIELD_CAMPUS = "campus";
    public final static String SQL_FIELD_REGISTER_NUMBER = "registerNumber";

    public final static String API_DATEOFBIRTH_FORMAT = "ddMMyyyy";
    public final static String JSON_ISO8601_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public final static String JSON_ISO8601_DATE_FORMAT = "yyyy-MM-dd";
    public final static String JSON_ISO8601_TIME_FORMAT = "HH:mm:ss'Z'";
    public final static String TIMEZONE_UTC = "UTC";

    public final static int MILLISECONDS_IN_MINUTE = 60000;

    public final static String SHARE_TYPE = "text/plain";

    public final static int COURSE_TYPE_CBL = 1;
    public final static int COURSE_TYPE_LBC = 2;
    public final static int COURSE_TYPE_PBL = 3;
    public final static int COURSE_TYPE_RBL = 4;
    public final static int COURSE_TYPE_PBC = 5;
    public final static int COURSE_TYPE_PBC_NO_PROJECT = 6;

    public final static int PLURAL_VALUE = 2;
    public final static int SINGULAR_VALUE = 1;

    public final static String INTENT_EXTRA_CLASS_NUMBER = "intent_extra_classnumber";
}
