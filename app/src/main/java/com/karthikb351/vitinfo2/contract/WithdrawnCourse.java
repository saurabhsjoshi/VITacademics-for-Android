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

package com.karthikb351.vitinfo2.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

@RushTableAnnotation
public class WithdrawnCourse extends RushObject {

    @Expose
    @SerializedName("class_number")
    private int classNumber;

    @Expose
    @SerializedName("course_code")
    private String courseCode;

    @Expose
    @SerializedName("course_title")
    private String courseTitle;

    @Expose
    @SerializedName("subject_type")
    private String subjectType;

    @Expose
    @SerializedName("ltpc")
    private String LTPC;

    @Expose
    @SerializedName("course_mode")
    private String courseMode;

    public WithdrawnCourse() {
    }

    public WithdrawnCourse(int classNumber, String courseCode, String courseTitle, String subjectType, String LTPC, String courseMode) {
        this.classNumber = classNumber;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.subjectType = subjectType;
        this.LTPC = LTPC;
        this.courseMode = courseMode;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public String getLTPC() {
        return LTPC;
    }

    public void setLTPC(String LTPC) {
        this.LTPC = LTPC;
    }

    public String getCourseMode() {
        return courseMode;
    }

    public void setCourseMode(String courseMode) {
        this.courseMode = courseMode;
    }
}
