/*
 * VITacademics
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
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

package com.karthikb351.vitinfo2.fragment.CGPAcalculator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.utility.DataHolder;

import java.util.ArrayList;

public class CGPAcalculatorListAdapter extends RecyclerView.Adapter<CGPAcalculatorListAdapter.CGPAcalculatorViewHolder>{

    ArrayList<Course> courses;
    ArrayList<Grade> grades;
    Context context;
    int regCreds;
    int earnedCreds;
    float cgpa;
    int layoutId;
    float totalCGP;
    float newCGPA;
    int newCredits;

    public CGPAcalculatorListAdapter(Context context,ArrayList<Course> courses,ArrayList<Grade> grades){
        this.context=context;
        this.grades=grades;
        this.courses=courses;
        this.regCreds= DataHolder.getCreditsRegistered();
        this.earnedCreds=DataHolder.getCreditsEarned();
        this.cgpa=DataHolder.getCgpa();
        totalCGP=cgpa*regCreds;
    }
    @Override
    public CGPAcalculatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(layoutId,parent,false);
        return new CGPAcalculatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CGPAcalculatorViewHolder holder, int position) {
        holder.newCGPA.setText(Float.toString(totalCGP/(regCreds+newCredits)));
    }

    @Override
    public int getItemCount() {
        return courses.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==courses.size())
            ;
            else;
        return layoutId;
        }

    public class CGPAcalculatorViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener{


        public TextView courseCode,courseName,courseCredits,newCGPA;
        public Spinner spinner;
        public CGPAcalculatorViewHolder(View view){
            super(view);
            courseCode=(TextView)view.findViewById(R.id.tv_course_code);
            courseName=(TextView)view.findViewById(R.id.tv_course_name);
            courseCredits=(TextView)view.findViewById(R.id.tv_course_credit);
            spinner=(Spinner)view.findViewById(R.id.spinner_course_grade);
            spinner.setOnItemClickListener(this);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           totalCGP=totalCGP+position;
           String ltpc=courses.get(position).getLtpc();
           int credits=(int)ltpc.charAt(3);
           newCredits+=credits;
        }

    }
}

