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

package com.karthikb351.vitinfo2.api.contract;

import com.karthikb351.vitinfo2.api.contract.course.Attendance;
import com.karthikb351.vitinfo2.api.contract.course.Marks;
import com.karthikb351.vitinfo2.api.contract.course.Timing;

public class Course {

    private int classNumber;

    private String courseCode;

    private String courseTitle;

    private String subjectType;

    private String LTPC;

    private String courseMode;

    private String courseOption;

    private String slot;

    private String venue;

    private String faculty;

    private String registrationStatus;

    private String billDate;

    private int billNumber;

    private String projectTitle;

    private int courseType;

    private Attendance attendance;

    private Marks marks;

    private Timing[] timings;

}
