package com.karthikb351.vitinfo2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.model.CourseModel;

import java.util.ArrayList;

/**
 * Created by pulkit on 10/06/2015.
 */
class CourseViewHolder extends RecyclerView.ViewHolder
{
    TextView courseName ,courseCode ;
    ImageView photo ;
    CourseViewHolder(View v)
    {
        super(v);
        courseName = (TextView)v.findViewById(R.id.tvCourseName);
        courseCode = (TextView)v.findViewById(R.id.tvCourseCode);
        photo = (ImageView)v.findViewById(R.id.photo);
    }
}

public class CourseListAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    Context context;
    ArrayList<CourseModel> courses;

    public CourseListAdapter(Context context, ArrayList<CourseModel> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView rootcard = (android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(R.layout.course_card, parent, false);
        return new CourseViewHolder(rootcard);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        CourseViewHolder cvHolder = (CourseViewHolder)holder ;
        cvHolder.courseCode.setText(courses.get(position).courseCode);
        cvHolder.courseName.setText(courses.get(position).courseName);
        cvHolder.photo.setImageResource(courses.get(position).photoResourceId);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

}
