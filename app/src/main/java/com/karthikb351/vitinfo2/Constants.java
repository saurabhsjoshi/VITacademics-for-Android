/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2;

public class Constants {

    public static final String API_BASE_URL = "https://vitacademics-rel.herokuapp.com";

    public final static String FILENAME_SHAREDPREFERENCES = "vitacademics.prefs";

    public final static String KEY_COURSES_REFRESHED = "refreshed_courses";
    public final static String KEY_GRADES_REFRESHED = "refreshed_grades";

    public final static String KEY_CAMPUS = "campus";
    public final static String KEY_REGISTERNUMBER = "reg_no";
    public final static String KEY_DATEOFBIRTH = "dob";
    public final static String KEY_MOBILE = "mobile";

    public final static String KEY_SEMESTER = "semester";

    public final static String KEY_ANDROID_LATEST_VERSION = "latest_version";
    public final static String KEY_ANDROID_SUPPORTED_VERSION = "supported_version";

    public final static String KEY_SHARE_TOKEN = "token";
    public final static String KEY_SHARE_TOKEN_ISSUED = "token_issued";

    public final static int EVENT_CODE_SYSTEM = 1;
    public final static int EVENT_CODE_REFRESH = 10;
    public final static int EVENT_CODE_GRADES = 100;
    public final static int EVENT_CODE_TOKEN = 1000;
    public final static int EVENT_CODE_SHARE = 1;
    public final static int EVENT_CODE_REFRESH_ALL = 1111;
    public final static int EVENT_PATH_LOGIN_REFRESH = 1;
    public final static int EVENT_PATH_LOGIN_GRADES = 2;
}
