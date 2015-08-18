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

package com.karthikb351.vitinfo2.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushList;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

@RushTableAnnotation
public class Marks extends RushObject {

    @Expose
    @SerializedName("max_marks")
    private double maxMarks;

    @Expose
    @SerializedName("max_percentage")
    private double maxPercentage;

    @Expose
    @SerializedName("scored_marks")
    private double scoredMarks;

    @Expose
    @SerializedName("scored_percentage")
    private double scoredPercentage;

    @Expose
    @SerializedName("assessments")
    @RushList(classType = Assessment.class)
    private List<Assessment> assessments;

    @Expose
    @SerializedName("supported")
    private boolean supported;

    public Marks() {
    }

    public Marks(double maxMarks, double maxPercentage, double scoredMarks, double scoredPercentage, List<Assessment> assessments, boolean supported) {
        this.maxMarks = maxMarks;
        this.maxPercentage = maxPercentage;
        this.scoredMarks = scoredMarks;
        this.scoredPercentage = scoredPercentage;
        this.assessments = assessments;
        this.supported = supported;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public double getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(double maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public double getScoredMarks() {
        return scoredMarks;
    }

    public void setScoredMarks(double scoredMarks) {
        this.scoredMarks = scoredMarks;
    }

    public double getScoredPercentage() {
        return scoredPercentage;
    }

    public void setScoredPercentage(double scoredPercentage) {
        this.scoredPercentage = scoredPercentage;
    }

    public List<Assessment> getAssessments() {
        if (assessments != null) {
            return assessments;
        }
        return new ArrayList<>();
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public boolean isSupported() {
        return supported;
    }

    public void setSupported(boolean supported) {
        this.supported = supported;
    }
}
