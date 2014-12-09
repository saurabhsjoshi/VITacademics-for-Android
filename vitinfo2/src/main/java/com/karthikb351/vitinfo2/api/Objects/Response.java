package com.karthikb351.vitinfo2.api.Objects;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @Expose
    private Status status;
    @Expose
    private Boolean cached;
    @SerializedName("reg_no")
    @Expose
    private String regNo;
    @Expose
    private Share share;
    @Expose
    private List<Course> courses = new ArrayList<Course>();
    @Expose
    private String refreshed;
    @Expose
    private Timetable timetable;
    @Expose
    private Boolean withdrawn;

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The cached
     */
    public Boolean getCached() {
        return cached;
    }

    /**
     * 
     * @param cached
     *     The cached
     */
    public void setCached(Boolean cached) {
        this.cached = cached;
    }

    /**
     * 
     * @return
     *     The regNo
     */
    public String getRegNo() {
        return regNo;
    }

    /**
     * 
     * @param regNo
     *     The reg_no
     */
    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    /**
     * 
     * @return
     *     The share
     */
    public Share getShare() {
        return share;
    }

    /**
     * 
     * @param share
     *     The share
     */
    public void setShare(Share share) {
        this.share = share;
    }

    /**
     * 
     * @return
     *     The courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * 
     * @param courses
     *     The courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     * 
     * @return
     *     The refreshed
     */
    public String getRefreshed() {
        return refreshed;
    }

    /**
     * 
     * @param refreshed
     *     The refreshed
     */
    public void setRefreshed(String refreshed) {
        this.refreshed = refreshed;
    }

    /**
     * 
     * @return
     *     The timetable
     */
    public Timetable getTimetable() {
        return timetable;
    }

    /**
     * 
     * @param timetable
     *     The timetable
     */
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * 
     * @return
     *     The withdrawn
     */
    public Boolean getWithdrawn() {
        return withdrawn;
    }

    /**
     * 
     * @param withdrawn
     *     The withdrawn
     */
    public void setWithdrawn(Boolean withdrawn) {
        this.withdrawn = withdrawn;
    }

    public Course getCourse(String clsnbr){
        for(int i = 0; i < courses.size(); i++){
            if(courses.get(i).getClassNumber().equals(clsnbr))
                return courses.get(i);
        }
        return null;
    }

}
