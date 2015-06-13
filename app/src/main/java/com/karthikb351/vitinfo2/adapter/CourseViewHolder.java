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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;

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
