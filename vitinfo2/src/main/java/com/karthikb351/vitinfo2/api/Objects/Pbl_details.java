package com.karthikb351.vitinfo2.api.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sreeram on 8/12/14.
 */
public class Pbl_details {

    @Expose
    private String weightage;

    @Expose
    private String status;

    @Expose
    private String title;

    @SerializedName("scored_%")
    @Expose
    private String scored;

    @SerializedName("max_marks")
    @Expose
    private String maxMarks;

    @SerializedName("scored_mark")
    @Expose
    private String scoredMark;

    @SerializedName("conducted_on")
    @Expose
    private String conductedOn;


    /**
     *
     * @return
     *     The weightage
     */
    public String getWeightage() {
        return weightage;
    }

    /**
     *
     * @param weightage
     *     The weightage
     */
    public void setWeightage(String weightage) {
        this.weightage = weightage;
    }

    /**
     *
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     *     The scored
     */
    public String getScored() {
        return scored;
    }

    /**
     *
     * @param scored
     *     The scored_%
     */
    public void setScored(String scored) {
        this.scored = scored;
    }

    /**
     *
     * @return
     *     The maxMarks
     */
    public String getMaxMarks() {
        return maxMarks;
    }

    /**
     *
     * @param maxMarks
     *     The max_marks
     */
    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    /**
     *
     * @return
     *     The scoredMark
     */
    public String getScoredMark() {
        return scoredMark;
    }

    /**
     *
     * @param scoredMark
     *     The scored_mark
     */
    public void setScoredMark(String scoredMark) {
        this.scoredMark = scoredMark;
    }

    /**
     *
     * @return
     *     The conductedOn
     */
    public String getConductedOn() {
        return conductedOn;
    }

    /**
     *
     * @param conductedOn
     *     The conducted_on
     */
    public void setConductedOn(String conductedOn) {
        this.conductedOn = conductedOn;
    }


}
