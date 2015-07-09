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

public class AttendanceDetail extends RushObject {

    @Expose
    @SerializedName("sl")
    private int serial;

    @Expose
    @SerializedName("date")
    private String date;

    @Expose
    @SerializedName("slot")
    private String slot;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("class_units")
    private int classUnits;

    @Expose
    @SerializedName("reason")
    private String reason;

    public AttendanceDetail() {
    }

    public AttendanceDetail(int serial, String date, String slot, String status, int classUnits, String reason) {
        this.serial = serial;
        this.date = date;
        this.slot = slot;
        this.status = status;
        this.classUnits = classUnits;
        this.reason = reason;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClassUnits() {
        return classUnits;
    }

    public void setClassUnits(int classUnits) {
        this.classUnits = classUnits;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
