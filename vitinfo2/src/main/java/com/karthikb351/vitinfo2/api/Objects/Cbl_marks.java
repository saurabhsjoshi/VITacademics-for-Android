package com.karthikb351.vitinfo2.api.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sreeram on 8/12/14.
 */
public class Cbl_marks {

    @SerializedName("cat1")
    @Expose
    private String cat1;

    @SerializedName("cat1_status")
    @Expose
    private String cat1_status;

    @SerializedName("cat2")
    @Expose
    private String cat2;

    @SerializedName("cat2_status")
    @Expose
    private String cat2_status;

    @SerializedName("quiz1")
    @Expose
    private String quiz1;

    @SerializedName("quiz1_status")
    @Expose
    private String quiz1_status;

    @SerializedName("quiz2")
    @Expose
    private String quiz2;

    @SerializedName("quiz2_status")
    @Expose
    private String quiz2_status;

    @SerializedName("quiz3_status")
    @Expose
    private String quiz3_status;

    @SerializedName("assignment")
    @Expose
    private String assignment;

    @SerializedName("assignment_status")
    @Expose
    private String assignment_status;

    public String getCat1() {
        return cat1;
    }

    public void setCat1(String cat1) {
        this.cat1 = cat1;
    }

    public String getCat1_status() {
        return cat1_status;
    }

    public void setCat1_status(String cat1_status) {
        this.cat1_status = cat1_status;
    }

    public String getCat2() {
        return cat2;
    }

    public void setCat2(String cat2) {
        this.cat2 = cat2;
    }

    public String getCat2_status() {
        return cat2_status;
    }

    public void setCat2_status(String cat2_status) {
        this.cat2_status = cat2_status;
    }

    public String getQuiz1() {
        return quiz1;
    }

    public void setQuiz1(String quiz1) {
        this.quiz1 = quiz1;
    }

    public String getQuiz1_status() {
        return quiz1_status;
    }

    public void setQuiz1_status(String quiz1_status) {
        this.quiz1_status = quiz1_status;
    }

    public String getQuiz2() {
        return quiz2;
    }

    public void setQuiz2(String quiz2) {
        this.quiz2 = quiz2;
    }

    public String getQuiz2_status() {
        return quiz2_status;
    }

    public void setQuiz2_status(String quiz2_status) {
        this.quiz2_status = quiz2_status;
    }

    public String getQuiz3_status() {
        return quiz3_status;
    }

    public void setQuiz3_status(String quiz3_status) {
        this.quiz3_status = quiz3_status;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getAssignment_status() {
        return assignment_status;
    }

    public void setAssignment_status(String assignment_status) {
        this.assignment_status = assignment_status;
    }

    public Boolean getSupported() {
        return supported;
    }

    public void setSupported(Boolean supported) {
        this.supported = supported;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Expose
    private Boolean supported;

    @Expose
    private String type;
}
