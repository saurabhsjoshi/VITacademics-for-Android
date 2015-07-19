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

package com.karthikb351.vitinfo2.api;

public class StatusCodes {

    public static final int SUCCESS = 0;
    public static final int TIMED_OUT = 11;
    public static final int INVALID = 12;
    public static final int CAPTCHA_PARSING = 13;
    public static final int TOKEN_EXPIRED = 14;
    public static final int NO_DATA = 15;
    public static final int DATA_PARSING = 16;
    public static final int TODO = 50;
    public static final int DEPRECATED = 60;
    public static final int VIT_DOWN = 89;
    public static final int MONGO_DOWN = 97;
    public static final int MAINTENANCE_DOWN = 98;
    public static final int UNKNOWN = 99;
}
