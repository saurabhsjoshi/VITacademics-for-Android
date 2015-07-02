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

package com.karthikb351.vitinfo2.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class Attendance extends SugarRecord<Attendance> {

    @Expose
    @SerializedName("registration_date")
    private String registrationDate;

    @Expose
    @SerializedName("attended_classes")
    private int attendedClasses;

    @Expose
    @SerializedName("total_classes")
    private int totalClasses;

    @Expose
    @SerializedName("attendance_percentage")
    private int attendancePercentage;

    @Expose
    @SerializedName("details")
    private AttendanceDetail[] details;

    @Expose
    @SerializedName("supported")
    private boolean supported;

    public Attendance() {
    }

    public Attendance(String registrationDate, int attendedClasses, int totalClasses, int attendancePercentage, AttendanceDetail[] details, boolean supported) {
        this.registrationDate = registrationDate;
        this.attendedClasses = attendedClasses;
        this.totalClasses = totalClasses;
        this.attendancePercentage = attendancePercentage;
        this.details = details;
        this.supported = supported;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public void setAttendedClasses(int attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(int attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public AttendanceDetail[] getDetails() {
        return details;
    }

    public void setDetails(AttendanceDetail[] details) {
        this.details = details;
    }

    public boolean isSupported() {
        return supported;
    }

    public void setSupported(boolean supported) {
        this.supported = supported;
    }
}
