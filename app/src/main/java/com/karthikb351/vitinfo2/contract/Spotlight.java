package com.karthikb351.vitinfo2.contract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.uk.rushorm.core.RushObject;
import co.uk.rushorm.core.annotations.RushList;

/**
 * Created by kushagra on 23/5/16.
 */
public class Spotlight extends RushObject{
    @Expose
    @SerializedName("academics")
    @RushList(classType = Spotlight.class)
    private List<SpotlightMessage> academicsSpotlightMessages;

    @Expose
    @SerializedName("coe")
    @RushList(classType = Spotlight.class)
    private List<SpotlightMessage> coeSpotlightMessages;

    @Expose
    @SerializedName("research")
    @RushList(classType = Spotlight.class)
    private List<SpotlightMessage> researchSpotlightMessages;

    public List<SpotlightMessage> getAcademicsSpotlightMessages() {
        return academicsSpotlightMessages;
    }

    public void setAcademicsSpotlightMessages(List<SpotlightMessage> academicsSpotlightMessages) {
        this.academicsSpotlightMessages = academicsSpotlightMessages;
    }

    public List<SpotlightMessage> getCoeSpotlightMessages() {
        return coeSpotlightMessages;
    }

    public void setCoeSpotlightMessages(List<SpotlightMessage> coeSpotlightMessages) {
        this.coeSpotlightMessages = coeSpotlightMessages;
    }

    public List<SpotlightMessage> getResearchSpotlightMessages() {
        return researchSpotlightMessages;
    }

    public void setResearchSpotlightMessages(List<SpotlightMessage> researchSpotlightMessages) {
        this.researchSpotlightMessages = researchSpotlightMessages;
    }

    Spotlight(){

    }


    public Spotlight(List<SpotlightMessage> academicsSpotlightMessages, List<SpotlightMessage> coeSpotlightMessages, List<SpotlightMessage> researchSpotlightMessages) {
        this.academicsSpotlightMessages = academicsSpotlightMessages;
        this.coeSpotlightMessages = coeSpotlightMessages;
        this.researchSpotlightMessages = researchSpotlightMessages;
    }
}
