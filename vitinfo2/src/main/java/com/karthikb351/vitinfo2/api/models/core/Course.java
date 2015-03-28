package com.karthikb351.vitinfo2.api.models.core;

import org.parceler.Parcel;

/**
 * Created by karthikbalakrishnan on 23/02/15.
 */
@Parcel
public class Course {

    public String courseName;

    public String courseSlot;

    public String courseRoom;

    public String courseAttendance;

    public String courseTime;


    public String getCourseName() {
        return courseName;
    }

    public String getCourseSlot() {
        return courseSlot;
    }

    public String getCourseRoom() {
        return courseRoom;
    }

    public String getCourseAttendance() {
        return courseAttendance;
    }

    public String getCourseTime() {
        return courseTime;
    }
}
