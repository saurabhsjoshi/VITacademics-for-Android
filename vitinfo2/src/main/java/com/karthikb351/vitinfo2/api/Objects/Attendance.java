
package com.karthikb351.vitinfo2.api.Objects;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Attendance {

    @SerializedName("total_classes")
    @Expose
    private String totalClasses;
    @SerializedName("registration_date")
    @Expose
    private String registrationDate;
    @Expose
    private Boolean supported;
    @SerializedName("attendance_percentage")
    @Expose
    private String attendancePercentage;
    @Expose
    private List<Detail> details = new ArrayList<Detail>();
    @SerializedName("attended_classes")
    @Expose
    private String attendedClasses;

    /**
     * 
     * @return
     *     The totalClasses
     */
    public String getTotalClasses() {
        return totalClasses;
    }

    /**
     * 
     * @param totalClasses
     *     The total_classes
     */
    public void setTotalClasses(String totalClasses) {
        this.totalClasses = totalClasses;
    }

    /**
     * 
     * @return
     *     The registrationDate
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * 
     * @param registrationDate
     *     The registration_date
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * 
     * @return
     *     The supported
     */
    public Boolean getSupported() {
        return supported;
    }

    /**
     * 
     * @param supported
     *     The supported
     */
    public void setSupported(Boolean supported) {
        this.supported = supported;
    }

    /**
     * 
     * @return
     *     The attendancePercentage
     */
    public String getAttendancePercentage() {
        return attendancePercentage;
    }

    /**
     * 
     * @param attendancePercentage
     *     The attendance_percentage
     */
    public void setAttendancePercentage(String attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    /**
     * 
     * @return
     *     The details
     */
    public List<Detail> getDetails() {
        return details;
    }

    /**
     * 
     * @param details
     *     The details
     */
    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    /**
     * 
     * @return
     *     The attendedClasses
     */
    public String getAttendedClasses() {
        return attendedClasses;
    }

    /**
     * 
     * @param attendedClasses
     *     The attended_classes
     */
    public void setAttendedClasses(String attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

}
