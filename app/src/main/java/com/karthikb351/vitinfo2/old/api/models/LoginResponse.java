package com.karthikb351.vitinfo2.old.api.models;

import com.google.gson.annotations.SerializedName;
import com.karthikb351.vitinfo2.old.api.models.core.Status;

import org.parceler.Parcel;

/**
 * Created by karthikbalakrishnan on 22/02/15.
 */
@Parcel
public class LoginResponse {

    @SerializedName("status")
    Status status;

    @SerializedName("reg_no")
    String regno;

    @SerializedName("dob")
    String dob;

    @SerializedName("campus")
    String campus;

    @SerializedName("mobile")
    String mobile;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
