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

package com.karthikb351.vitinfo2.fragment.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.DateTimeCalender;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends Fragment {

    private Course course;
    private TextView courseName, classNumber, courseCode, faculty, courseMode, courseType, ltpjc, courseOption;
    private TextView registeredOn, slot, venue;
    private TextView projectTitle;
    private ListView timingList;

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance(Course course) {
        OverviewFragment overviewFragment = new OverviewFragment();
        overviewFragment.course = course;
        return overviewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        if (course.getCourseType() == Constants.COURSE_TYPE_PBC || course.getCourseType() == Constants.COURSE_TYPE_PBC_NO_PROJECT) {
            view = inflater.inflate(R.layout.fragment_details_overview_project, container, false);

            if (course.getCourseType() == Constants.COURSE_TYPE_PBC) {
                projectTitle = (TextView) view.findViewById(R.id.project_title);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_details_overview_class, container, false);

            registeredOn = (TextView) view.findViewById(R.id.registered_on);
            venue = (TextView) view.findViewById(R.id.venue);
            slot = (TextView) view.findViewById(R.id.slot);
            timingList = (ListView) view.findViewById(R.id.timing_list);
        }

        courseName = (TextView) view.findViewById(R.id.course_name);
        classNumber = (TextView) view.findViewById(R.id.class_number);
        courseCode = (TextView) view.findViewById(R.id.course_code);
        faculty = (TextView) view.findViewById(R.id.faculty);
        courseType = (TextView) view.findViewById(R.id.course_type);
        courseMode = (TextView) view.findViewById(R.id.course_mode);
        ltpjc = (TextView) view.findViewById(R.id.ltpjc);
        courseOption = (TextView) view.findViewById(R.id.course_option);

        if (course.getCourseType() == Constants.COURSE_TYPE_PBC) {
            projectTitle.setText(getString(R.string.label_project_title, course.getProjectTitle()));
            projectTitle.setVisibility(View.VISIBLE);
        } else if (course.getCourseType() == Constants.COURSE_TYPE_CBL || course.getCourseType() == Constants.COURSE_TYPE_LBC || course.getCourseType() == Constants.COURSE_TYPE_PBL || course.getCourseType() == Constants.COURSE_TYPE_RBL) {
            slot.setText(getString(R.string.label_slot, course.getSlot()));
            venue.setText(getString(R.string.label_venue, course.getVenue()));

            if (course.getAttendance().isSupported()) {
                String registeredDate;
                try {
                    registeredDate = DateTimeCalender.parseISO8601DateTime(course.getAttendance().getRegistrationDate());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    registeredDate = course.getAttendance().getRegistrationDate();
                }
                registeredOn.setText(getString(R.string.label_registered_on, registeredDate));
                registeredOn.setVisibility(View.VISIBLE);
            }

            List<String> timings = new ArrayList<>();
            for (Timing timing : course.getTimings()) {
                String day = getDayOfWeek(timing.getDay());
                String startTime;
                String endTime;
                try {
                    startTime = DateTimeCalender.parseISO8601Time(timing.getStartTime());
                    endTime = DateTimeCalender.parseISO8601Time(timing.getEndTime());
                } catch (ParseException ex) {
                    startTime = timing.getStartTime();
                    endTime = timing.getEndTime();
                }
                timings.add(getActivity().getString(R.string.label_slot_timing, day, startTime, endTime));
            }
            ArrayAdapter timingsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, timings);
            timingList.setAdapter(timingsAdapter);
        }

        courseName.setText(course.getCourseTitle());
        classNumber.setText(getString(R.string.label_class_number, course.getClassNumber()));
        courseCode.setText(getString(R.string.label_course_code, course.getCourseCode()));
        faculty.setText(getString(R.string.label_faculty, course.getFaculty()));
        ltpjc.setText(getString(R.string.label_ltpjc, course.getLtpjc()));
        courseType.setText(getString(R.string.label_course_type, course.getSubjectType()));
        courseMode.setText(getString(R.string.label_course_mode, course.getCourseMode()));
        courseOption.setText(getString(R.string.label_course_option, course.getCourseOption()));

        return view;
    }

    private String getDayOfWeek(int day) {
        String daysOfWeek[] = getActivity().getResources().getStringArray(R.array.days_of_week);
        return daysOfWeek[day];
    }
}
