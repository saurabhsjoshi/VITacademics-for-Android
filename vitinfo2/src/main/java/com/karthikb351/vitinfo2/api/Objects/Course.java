package com.karthikb351.vitinfo2.api.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Course {

    @Expose
    private String slot;
    @Expose
    private Attendance attendance;
    @SerializedName("course_title")
    @Expose
    private String courseTitle;
    @Expose
    private String faculty;
    @Expose
    private String ltpc;
    @SerializedName("course_mode")
    @Expose
    private String courseMode;
    @SerializedName("course_type")
    @Expose
    private String courseType;
    @SerializedName("course_option")
    @Expose
    private String courseOption;
    @Expose
    private String venue;

    @SerializedName("cbl_marks")
    @Expose
    private Cbl_marks cblMarks;

    @SerializedName("pbl_marks")
    @Expose
    private List<Pbl_details> pblMarks = new ArrayList<Pbl_details>();

    @SerializedName("lbc_marks")
    @Expose
    private Lbc_marks lbcMarks;

    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("class_number")
    @Expose
    private String classNumber;

    @SerializedName("bill_date")
    @Expose

    private String billDate;


    @SerializedName("registration_status")

    @Expose
    private String registrationStatus;

    @SerializedName("project_title")
    @Expose
    private String ProjectTitle;


    /**
     * 
     * @return
     *     The slot
     */
    public String getSlot() {
        return slot;
    }

    /**
     * 
     * @param slot
     *     The slot
     */
    public void setSlot(String slot) {
        this.slot = slot;
    }

    /**
     * 
     * @return
     *     The attendance
     */
    public Attendance getAttendance() {
        return attendance;
    }

    /**
     * 
     * @param attendance
     *     The attendance
     */
    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    /**
     * 
     * @return
     *     The courseTitle
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * 
     * @param courseTitle
     *     The course_title
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * 
     * @return
     *     The faculty
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * 
     * @param faculty
     *     The faculty
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * 
     * @return
     *     The ltpc
     */
    public String getLtpc() {
        return ltpc;
    }

    /**
     * 
     * @param ltpc
     *     The ltpc
     */
    public void setLtpc(String ltpc) {
        this.ltpc = ltpc;
    }

    /**
     * 
     * @return
     *     The courseMode
     */
    public String getCourseMode() {
        return courseMode;
    }

    /**
     * 
     * @param courseMode
     *     The course_mode
     */
    public void setCourseMode(String courseMode) {
        this.courseMode = courseMode;
    }

    /**
     * 
     * @return
     *     The courseType
     */
    public String getCourseType() {
        return courseType;
    }

    /**
     * 
     * @param courseType
     *     The course_type
     */
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    /**
     * 
     * @return
     *     The courseOption
     */
    public String getCourseOption() {
        return courseOption;
    }

    /**
     * 
     * @param courseOption
     *     The course_option
     */
    public void setCourseOption(String courseOption) {
        this.courseOption = courseOption;
    }

    /**
     * 
     * @return
     *     The venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     * 
     * @param venue
     *     The venue
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Cbl_marks getCblMarks() {
        return cblMarks;
    }

    public void setCblMarks(Cbl_marks cblMarks) {
        this.cblMarks = cblMarks;
    }

    public List<Pbl_details> getPblMarks() {
        return pblMarks;
    }

    public void setPblMarks(List<Pbl_details> pblMarks) {
        this.pblMarks = pblMarks;
    }

    public Lbc_marks getLbcMarks() {
        return lbcMarks;
    }

    public void setLbcMarks(Lbc_marks lbcMarks) {
        this.lbcMarks = lbcMarks;
    }

    /**
     * 
     * @return
     *     The courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * 
     * @param courseCode
     *     The course_code
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * 
     * @return
     *     The classNumber
     */
    public String getClassNumber() {
        return classNumber;
    }

    /**
     * 
     * @param classNumber
     *     The class_number
     */
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /**
     * 
     * @return
     *     The registrationStatus
     */
    public String getRegistrationStatus() {
        return registrationStatus;
    }

    /**
     * 
     * @param registrationStatus
     *     The registration_status
     */
    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public String getProjectTitle() {
        return ProjectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        ProjectTitle = projectTitle;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
