package com.karthikb351.vitinfo2.api.Objects;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh on 2015-01-17.
 */
public class Data {

    public boolean isFb = false;

    public Bitmap img_profile;

    public String fbId;

    public String title;

    @SerializedName("reg_no")
    @Expose
    private String regNo;

    @Expose
    private List<Course> courses = new ArrayList<>();

    @Expose
    private Timetable timetable;

    public String getRegNo(){
        return regNo;
    }

    public List<Course> getCourses(){
        return courses;
    }

    public Timetable getTimetable(){
        return timetable;
    }

    public void setRegNo(String regNo){
        this.regNo = regNo;
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

    public void setTimetable(Timetable timetable){
        this.timetable = timetable;
    }

    public Course getCourse(String clsnbr){
        for(int i = 0; i < courses.size(); i++)
            if(courses.get(i).getClassNumber().equals(clsnbr))
                return courses.get(i);
        return null;
    }

}
