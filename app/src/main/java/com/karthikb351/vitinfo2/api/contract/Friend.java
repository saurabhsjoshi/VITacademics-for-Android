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
import com.karthikb351.vitinfo2.api.models.Status;
import com.orm.SugarRecord;

public class Friend extends SugarRecord<Friend> {

    @Expose
    @SerializedName("reg_no")
    private String registerNumber;

    @Expose
    @SerializedName("dob")
    private String dateOfBirth;

    @Expose
    @SerializedName("mobile")
    private String mobileNumber;

    @Expose
    @SerializedName("campus")
    private String campus;

    @Expose
    @SerializedName("status")
    private Status status;

    @Expose
    @SerializedName("semester")
    private String semester;

    @Expose
    @SerializedName("courses")
    private FriendCourse[] courses;

    @Expose
    @SerializedName("refreshed")
    private String refreshed;

    public Friend() {
    }

    public Friend(String registerNumber, String dateOfBirth, String mobileNumber, String campus, Status status, String semester, FriendCourse[] courses, String refreshed) {
        this.registerNumber = registerNumber;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.campus = campus;
        this.status = status;
        this.semester = semester;
        this.courses = courses;
        this.refreshed = refreshed;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public FriendCourse[] getCourses() {
        return courses;
    }

    public void setCourses(FriendCourse[] courses) {
        this.courses = courses;
    }

    public String getRefreshed() {
        return refreshed;
    }

    public void setRefreshed(String refreshed) {
        this.refreshed = refreshed;
    }
}
