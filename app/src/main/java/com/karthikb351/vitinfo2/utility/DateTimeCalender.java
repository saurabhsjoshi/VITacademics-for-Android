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

package com.karthikb351.vitinfo2.utility;

import com.karthikb351.vitinfo2.contract.Timing;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeCalender {

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
        return SimpleDateFormat.getDateInstance(DateFormat.FULL).format(date);
    }

    public static String parseISO8601Time(String jsonString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(Constants.JSON_ISO8601_TIME_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE_UTC));
        Date time = dateFormat.parse(jsonString);
        return SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(time);
    }

    public static Date getTodayTimeObject(String jsonString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(Constants.JSON_ISO8601_TIME_FORMAT, Locale.US);
        DateTime today = new DateTime().withTimeAtStartOfDay();
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TIMEZONE_UTC));
        Date parsedTime = dateFormat.parse(jsonString);
        return today.plus(parsedTime.getTime()).toDate();
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

    public static int getDayOfWeek() {
        return (new DateTime().getDayOfWeek() - 1);
    }

    public static int getNextDay(int day) {
        if (day == 6) {
            return 0;
        }
        return (day + 1);
    }

    public static boolean isTomorrow(int day) {
        return (DateTimeCalender.getNextDay(getDayOfWeek()) == day);
    }

    public static long getTimeDifference(Timing timing) throws ParseException {
        Date now = new Date();
        Date courseStartTime = DateTimeCalender.getTodayTimeObject(timing.getStartTime());
        return courseStartTime.getTime() - now.getTime();
    }

    public static boolean checkIfSlotEnded(Timing timing) throws ParseException {
        Date now = new Date();
        Date courseEndTime = DateTimeCalender.getTodayTimeObject(timing.getEndTime());
        return courseEndTime.before(now);
    }
}
