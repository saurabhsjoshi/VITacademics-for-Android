package com.karthikb351.vitinfo2.contract;

import co.uk.rushorm.core.RushObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushTableAnnotation;

@RushTableAnnotation
public class SpotlightMessage extends RushObject{
    @Expose
    @SerializedName("text")
    private String spotlightText;


    public int getSpotlightType() {
        return spotlightType;
    }

    public void setSpotlightType(int spotlightType) {
        this.spotlightType = spotlightType;
    }

    @Expose
    private int spotlightType;

    public String getSpotlightURL() {
        return spotlightURL;
    }

    public void setSpotlightURL(String spotlightURL) {
        this.spotlightURL = spotlightURL;
    }

    public String getSpotlightText() {
        return spotlightText;
    }

    public void setSpotlightText(String spotlightText) {
        this.spotlightText = spotlightText;
    }

    public SpotlightMessage(){

    }

    public SpotlightMessage(String spotlightText, String spotlightURL, int spotlightType){
        this.spotlightText = spotlightText;
        this.spotlightURL = spotlightURL;
        this.spotlightType = spotlightType;
    }
    @Expose
    @SerializedName("url")
    private String spotlightURL;

}
