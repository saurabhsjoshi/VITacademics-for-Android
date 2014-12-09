package com.karthikb351.vitinfo2.api.Objects;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    @Expose
    private List<Integer> wed = new ArrayList<Integer>();
    @Expose
    private List<Integer> thu = new ArrayList<Integer>();
    @Expose
    private List<Integer> tue = new ArrayList<Integer>();
    @Expose
    private List<Integer> mon = new ArrayList<Integer>();
    @Expose
    private List<Integer> fri = new ArrayList<Integer>();
    @Expose
    private List<Integer> sat = new ArrayList<Integer>();

    /**
     * 
     * @return
     *     The wed
     */
    public List<Integer> getWed() {
        return wed;
    }

    /**
     * 
     * @param wed
     *     The wed
     */
    public void setWed(List<Integer> wed) {
        this.wed = wed;
    }

    /**
     * 
     * @return
     *     The thu
     */
    public List<Integer> getThu() {
        return thu;
    }

    /**
     * 
     * @param thu
     *     The thu
     */
    public void setThu(List<Integer> thu) {
        this.thu = thu;
    }

    /**
     * 
     * @return
     *     The tue
     */
    public List<Integer> getTue() {
        return tue;
    }

    /**
     * 
     * @param tue
     *     The tue
     */
    public void setTue(List<Integer> tue) {
        this.tue = tue;
    }

    /**
     * 
     * @return
     *     The mon
     */
    public List<Integer> getMon() {
        return mon;
    }

    /**
     * 
     * @param mon
     *     The mon
     */
    public void setMon(List<Integer> mon) {
        this.mon = mon;
    }

    /**
     * 
     * @return
     *     The fri
     */
    public List<Integer> getFri() {
        return fri;
    }

    /**
     * 
     * @param fri
     *     The fri
     */
    public void setFri(List<Integer> fri) {
        this.fri = fri;
    }

    /**
     * 
     * @return
     *     The sat
     */
    public List<Integer> getSat() {
        return sat;
    }

    /**
     * 
     * @param sat
     *     The sat
     */
    public void setSat(List<Integer> sat) {
        this.sat = sat;
    }

    public List<Integer> getTimeTableforDay(String day){
        switch (day){
            case "Monday":
                return getMon();
            case "Tuesday":
                return getTue();
            case "Wednesday":
                return getWed();
            case "Thursday":
                return getThu();
            case "Friday":
                return getFri();
            default:
                return getMon();
        }
    }

}
