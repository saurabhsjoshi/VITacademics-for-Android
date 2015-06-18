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

public class Grade {

    @Expose
    @SerializedName("course_code")
    private String courseCode;

    @Expose
    @SerializedName("course_title")
    private String courseTitle;

    @Expose
    @SerializedName("course_type")
    private String courseType;

    @Expose
    @SerializedName("credits")
    private int credits;

    @Expose
    @SerializedName("grade")
    private char grade;

    @Expose
    @SerializedName("exam_held")
    private String examHeld;

    @Expose
    @SerializedName("result_date")
    private String resultDate;

    @Expose
    @SerializedName("option")
    private String option;

    public Grade() {
    }

    public Grade(String courseCode, String courseTitle, String courseType, int credits, char grade, String examHeld, String resultDate, String option) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseType = courseType;
        this.credits = credits;
        this.grade = grade;
        this.examHeld = examHeld;
        this.resultDate = resultDate;
        this.option = option;
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

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    public String getExamHeld() {
        return examHeld;
    }

    public void setExamHeld(String examHeld) {
        this.examHeld = examHeld;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
