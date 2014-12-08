package com.karthikb351.vitinfo2.api.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreeram on 8/12/14.
 */
public class Pbl_marks {

    @SerializedName("details")
    @Expose
    private List<Pbl_details> marks = new ArrayList<Pbl_details>();

    @Expose
    private String supported;

    @Expose
    private String type;

    public String getSupported() {
        return supported;
    }

    public void setSupported(String supported) {
        this.supported = supported;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Pbl_details> getMarks() {
        return marks;
    }

    public void setMarks(List<Pbl_details> marks) {
        this.marks = marks;
    }
}
