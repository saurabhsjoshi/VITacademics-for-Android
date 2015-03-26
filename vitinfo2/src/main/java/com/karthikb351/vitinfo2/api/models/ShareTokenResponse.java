package com.karthikb351.vitinfo2.api.models;

import com.google.gson.annotations.SerializedName;
import com.karthikb351.vitinfo2.api.models.core.ShareToken;
import com.karthikb351.vitinfo2.api.models.core.Status;

import org.parceler.Parcel;

/**
 * Created by karthikbalakrishnan on 22/02/15.
 */
@Parcel
public class ShareTokenResponse {

    @SerializedName("reg_no")
    String regno;

    @SerializedName("dob")
    String dob;

    @SerializedName("campus")
    String campus;

    @SerializedName("share")
    ShareToken share;

    @SerializedName("status")
    Status status;

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

    public ShareToken getShare() {
        return share;
    }

    public void setShare(ShareToken share) {
        this.share = share;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
