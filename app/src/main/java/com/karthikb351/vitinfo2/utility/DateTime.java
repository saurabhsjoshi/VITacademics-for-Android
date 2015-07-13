/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTime {

    public static String parseISO8601DateTime(String jsonString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(Constants.JSON_ISO8601_DATETIME_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE_UTC));
        Date dateTime = dateFormat.parse(jsonString);
        return SimpleDateFormat.getDateTimeInstance().format(dateTime);
    }

    public static String parseISO8601Date(String jsonString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(Constants.JSON_ISO8601_DATE_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE_UTC));
        Date date = dateFormat.parse(jsonString);
        return SimpleDateFormat.getDateInstance().format(date);
    }

    public static String parseISO8601Time(String jsonString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(Constants.JSON_ISO8601_TIME_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE_UTC));
        Date time = dateFormat.parse(jsonString);
        return SimpleDateFormat.getTimeInstance().format(time);
    }

    public static int compareTimes(String lhsTimeString, String rhsTimeString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(Constants.JSON_ISO8601_TIME_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE_UTC));
        Date lhsTime = dateFormat.parse(lhsTimeString);
        Date rhsTime = dateFormat.parse(rhsTimeString);

        if (lhsTime.after(rhsTime)) {
            return 1;
        }

        if (lhsTime.before(rhsTime)) {
            return -1;
        }
        return 0;
    }
}
