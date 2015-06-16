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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.karthikb351.vitinfo2.api.contract.course.Timing;

public class FriendCourse {

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
    private String ltpc;

    @Expose
    @SerializedName("course_mode")
    private String courseMode;

    @Expose
    @SerializedName("course_option")
    private String courseOption;

    @Expose
    @SerializedName("slot")
    private String slot;

    @Expose
    @SerializedName("venue")
    private String venue;

    @Expose
    @SerializedName("faculty")
    private String faculty;

    @Expose
    @SerializedName("project_title")
    private String projectTitle;

    @Expose
    @SerializedName("course_type")
    private int courseType;

    @Expose
    @SerializedName("timings")
    private Timing[] timings;

    public FriendCourse() {
    }

    public FriendCourse(int classNumber, String courseCode, String courseTitle, String subjectType, String ltpc, String courseMode, String courseOption, String slot, String venue, String faculty, String projectTitle, int courseType, Timing[] timings) {
        this.classNumber = classNumber;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.subjectType = subjectType;
        this.ltpc = ltpc;
        this.courseMode = courseMode;
        this.courseOption = courseOption;
        this.slot = slot;
        this.venue = venue;
        this.faculty = faculty;
        this.projectTitle = projectTitle;
        this.courseType = courseType;
        this.timings = timings;
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

    public String getLtpc() {
        return ltpc;
    }

    public void setLtpc(String ltpc) {
        this.ltpc = ltpc;
    }

    public String getCourseMode() {
        return courseMode;
    }

    public void setCourseMode(String courseMode) {
        this.courseMode = courseMode;
    }

    public String getCourseOption() {
        return courseOption;
    }

    public void setCourseOption(String courseOption) {
        this.courseOption = courseOption;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public Timing[] getTimings() {
        return timings;
    }

    public void setTimings(Timing[] timings) {
        this.timings = timings;
    }
}
