package com.karthikb351.vitinfo2.api.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabh on 2015-01-17.
 */
public class Data {

    @SerializedName("reg_no")
    @Expose
    private String regNo;

    @Expose
    private List<Course> courses = new ArrayList<Course>();

    @Expose
    private Timetable timetable;
}
