/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.model.CourseModel;

import java.util.ArrayList;

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
    public void onBindViewHolder(CourseViewHolder holder, int position) {

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
