package com.karthikb351.vitinfo2.api.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sreeram on 8/12/14.
 */
public class Lbc_marks {

    @SerializedName("lab_cam")
    @Expose
    private String labcam;

    @SerializedName("lab_cam_status")
    @Expose
    private  String labcam_status;

    @Expose
    private String supported;

    @Expose
    private String type;

    public String getLabcam() {
        return labcam;
    }

    public void setLabcam(String labcam) {
        this.labcam = labcam;
    }

    public String getLabcam_status() {
        return labcam_status;
    }

    public void setLabcam_status(String labcam_status) {
        this.labcam_status = labcam_status;
    }

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
}
