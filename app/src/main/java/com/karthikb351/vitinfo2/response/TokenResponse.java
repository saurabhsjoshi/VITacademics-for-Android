/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
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

package com.karthikb351.vitinfo2.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.model.TokenShare;

public class TokenResponse {

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
    @SerializedName("share")
    private TokenShare tokenShare;

    public TokenResponse() {
    }

    public TokenResponse(String registerNumber, String dateOfBirth, String mobileNumber, String campus, Status status, TokenShare tokenShare) {
        this.registerNumber = registerNumber;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.campus = campus;
        this.status = status;
        this.tokenShare = tokenShare;
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

    public TokenShare getTokenShare() {
        return tokenShare;
    }

    public void setTokenShare(TokenShare tokenShare) {
        this.tokenShare = tokenShare;
    }
}
