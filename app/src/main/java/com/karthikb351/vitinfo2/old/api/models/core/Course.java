package com.karthikb351.vitinfo2.old.api.models.core;

import org.parceler.Parcel;

/**
 * Created by karthikbalakrishnan on 23/02/15.
 */
@Parcel
public class Course {

    private String courseName;

    private String courseSlot;

    private String courseRoom;

    private String courseAttendance;

    private String courseTime;


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


    public String getCourse() {
        return "\n" + courseSlot + courseRoom + "\n" + courseName + "\n\n" + courseAttendance + courseTime + "\n";
    }

    public int getCourseNameLength() {
        return courseName.length();
    }

    public int getCourseSlotLength() {
        return courseSlot.length();
    }

    public int getCourseRoomLength() {
        return courseRoom.length();
    }

    public int getCourseAttendanceLength() {
        return courseAttendance.length();
    }

    public int getCourseTimeLength() {
        return courseTime.length();
    }

}
