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
import com.orm.SugarRecord;

public class Assessment extends SugarRecord<Assessment> {

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("max_marks")
    private double maxMarks;

    @Expose
    @SerializedName("weightage")
    private double weightage;

    @Expose
    @SerializedName("conducted_on")
    private String conductedOn;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("scored_marks")
    private double scoredMarks;

    @Expose
    @SerializedName("scored_percentage")
    private double scoredPercentage;

    public Assessment() {
    }

    public Assessment(String title, double maxMarks, double weightage, String conductedOn, String status, double scoredMarks, double scoredPercentage) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
        this.conductedOn = conductedOn;
        this.status = status;
        this.scoredMarks = scoredMarks;
        this.scoredPercentage = scoredPercentage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public double getWeightage() {
        return weightage;
    }

    public void setWeightage(double weightage) {
        this.weightage = weightage;
    }

    public String getConductedOn() {
        return conductedOn;
    }

    public void setConductedOn(String conductedOn) {
        this.conductedOn = conductedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
