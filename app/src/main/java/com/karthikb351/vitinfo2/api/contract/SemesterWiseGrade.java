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

public class SemesterWiseGrade {

    @Expose
    @SerializedName("exam_held")
    private String examHeld;

    @Expose
    @SerializedName("credits")
    private int credits;

    @Expose
    @SerializedName("gpa")
    private double gpa;

    public SemesterWiseGrade() {
    }

    public SemesterWiseGrade(String examHeld, int credits, double gpa) {
        this.examHeld = examHeld;
        this.credits = credits;
        this.gpa = gpa;
    }

    public String getExamHeld() {
        return examHeld;
    }

    public void setExamHeld(String examHeld) {
        this.examHeld = examHeld;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
