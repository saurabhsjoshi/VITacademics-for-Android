/*
 * VITacademics
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
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

import android.util.Pair;

import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SortedArrayList extends ArrayList<Pair<Course, Integer>> {

    public void insert(Pair<Course, Integer> data) {
        try {
            Date date = new Date();
            Timing thisTime = data.first.getTimings().get(data.second);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.JSON_ISO8601_DATETIME_FORMAT, Locale.US);
            Date startDate = simpleDateFormat.parse(thisTime.getStartTime());
            Date endDate = simpleDateFormat.parse(thisTime.getEndTime());
            if (date.after(endDate))
                return;
            int i = 0;
            for (; i < size(); i++) {
                Date time = simpleDateFormat.parse(get(i).first.getTimings().get(get(i).second).getStartTime());
                if (!(time.before(startDate)))
                    break;
            }
            add(i, data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
