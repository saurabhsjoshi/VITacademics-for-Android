package com.karthikb351.vitinfo2.model;

/**
 * Created by pulkit on 10/06/2015.
 */
public class CourseModel {
     public String courseName , courseCode ;
     public int photoResourceId ;

    CourseModel(String courseName , String courseCode , int photoResourceId)
    {
        this.courseCode = courseCode ;
        this.courseName = courseName ;
        this.photoResourceId = photoResourceId;
    }
}
