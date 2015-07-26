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

public class Data {

    public static String toTitleCase(String text) {
        if (text != null) {
            return text.substring(0, 1).toUpperCase() + text.substring(1);
        }
        return "";
    }

    public static int getLabUnitsFromLtpjc(String ltpjc) {
        if (ltpjc != null) {
            return Character.getNumericValue(ltpjc.charAt(2));
        }
        return 2;
    }

    public static int getCreditsFromLtpjc(String ltpjc) {
        if (ltpjc != null) {
            return Integer.parseInt(ltpjc.substring(4));
        }
        return 0;
    }
}
