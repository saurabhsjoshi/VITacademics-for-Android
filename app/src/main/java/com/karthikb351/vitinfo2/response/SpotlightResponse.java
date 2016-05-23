package com.karthikb351.vitinfo2.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.karthikb351.vitinfo2.contract.Spotlight;
import com.karthikb351.vitinfo2.contract.SpotlightMessage;
import com.karthikb351.vitinfo2.model.Status;

public class SpotlightResponse {

    @Expose
    @SerializedName("campus")
    private String campus;

    @Expose
    @SerializedName("status")
    private Status status;

    @Expose
    @SerializedName("spotlight")
    private Spotlight spotlight;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Spotlight getSpotlight() {
        return spotlight;
    }

    public void setSpotlight(Spotlight spotlight) {
        this.spotlight = spotlight;
    }

    SpotlightResponse(){

    }

    public SpotlightResponse(String campus, Status status, Spotlight spotlight) {
        this.campus = campus;
        this.status = status;
        this.spotlight = spotlight;
    }
}
