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

import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.utility.DataHolder;

import java.util.ArrayList;

public class CGPAcalculatorListAdapter extends RecyclerView.Adapter<CGPAcalculatorListAdapter.CGPAcalculatorViewHolder> implements View.OnClickListener{

    ArrayList<Course> courses;
    ArrayList<Grade> grades;
    Context context;
    int regCreds;
    int earnedCreds;
    float cgpa;
    int layoutId;

    public CGPAcalculatorListAdapter(Context context,ArrayList<Course> courses,ArrayList<Grade> grades){
        this.context=context;
        this.grades=grades;
        this.courses=courses;
        this.regCreds= DataHolder.getCreditsRegistered();
        this.earnedCreds=DataHolder.getCreditsEarned();
        this.cgpa=DataHolder.getCgpa();
    }
    @Override
    public CGPAcalculatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(layoutId,parent,false);
        return new CGPAcalculatorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CGPAcalculatorViewHolder holder, int position) {
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

    @Override
    public void onClick(View v) {

    }

    public class CGPAcalculatorViewHolder extends RecyclerView.ViewHolder{

        public CGPAcalculatorViewHolder(View view){
            super(view);
        }

    }
}

