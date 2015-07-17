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

package com.karthikb351.vitinfo2.fragment.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;


public class OverviewFragment extends Fragment {

    Course course;
    TextView courseCode, courseName, slot, faculty, mode, ltpc, type, venue, classNumber;

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance(Course course) {
        OverviewFragment overviewFragment = new OverviewFragment();
        overviewFragment.course = course;
        return new OverviewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_overview, container, false);
        courseCode=(TextView)view.findViewById(R.id.course_code);
        courseName=(TextView)view.findViewById(R.id.course_name);
        slot=(TextView)view.findViewById(R.id.course_slot);
        faculty=(TextView)view.findViewById(R.id.faculty);
        mode=(TextView)view.findViewById(R.id.course_mode);
        ltpc=(TextView)view.findViewById(R.id.course_credits);
        type=(TextView)view.findViewById(R.id.course_type);
        venue=(TextView)view.findViewById(R.id.course_venue);
        classNumber=(TextView)view.findViewById(R.id.class_number);

        courseCode.setText(course.getCourseCode());
        courseName.setText(course.getCourseTitle());
        slot.setText(course.getSlot());
        faculty.setText(course.getFaculty());
        mode.setText(course.getCourseMode());
        ltpc.setText(course.getLtpc());
        type.setText(course.getCourseType());
        venue.setText(course.getVenue());
        classNumber.setText(course.getClassNumber());

        return view;
    }
}
