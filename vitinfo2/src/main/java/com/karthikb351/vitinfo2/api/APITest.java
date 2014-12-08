package com.karthikb351.vitinfo2.api;

import android.os.AsyncTask;
import android.util.Log;

import com.karthikb351.vitinfo2.api.Objects.Course;
import com.karthikb351.vitinfo2.api.Objects.Response;

import java.util.List;

/**
 * Created by sreeram on 9/12/14.
 */
public class APITest extends AsyncTask<Void, Void, String> {


    @Override
    protected String doInBackground(Void... voids) {
        Response response;
        String result;
        try {
            response = HomeCall.sendRequest("11bce0088", "05111993", "vellore", "/login/auto");
            response = HomeCall.sendRequest("11bce0088", "05111993", "vellore", "/data/first");
            List<Course> courses = response.getCourses();
            Course course = courses.get(0);
            Course course1 = courses.get(1);
            result = course.getFaculty() + " and " + course1.getFaculty();
        }
        catch(Exception e){
            e.printStackTrace();
            result = "Failed";
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("API Response", s);
    }
}
