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

package com.karthikb351.vitinfo2.fragment.grades;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;

import java.util.List;

public class GradesPagerAdapter extends FragmentPagerAdapter {

    private final int GRADES_COUNT;
    private FragmentManager fragmentManager;
    private List<SemesterWiseGrade> semesterWiseGrades;
    private Context context;

    public GradesPagerAdapter(Context context, FragmentManager fragmentManager, List<SemesterWiseGrade> swg) {
        super(fragmentManager);
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.semesterWiseGrades = swg;
        GRADES_COUNT = semesterWiseGrades.size();
    }

    @Override
    public Fragment getItem(int position) {
        return GradeSemesterCardFragment.newInstance(semesterWiseGrades.get(position), position);
    }

    @Override
    public int getCount() {
        return GRADES_COUNT;
    }
}
