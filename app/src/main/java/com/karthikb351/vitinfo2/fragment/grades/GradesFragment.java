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

package com.karthikb351.vitinfo2.fragment.grades;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.GradeCount;
import com.karthikb351.vitinfo2.utility.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class GradesFragment extends Fragment {

    List<Course> coursesList;
    List<Grade> gradesList;
    List<GradeCount> gradeCountList;
    ArrayList<GradeCount> gradeCounts;
    ArrayList<Course> courses;
    ArrayList<Grade> grades;

    public GradesFragment(){

    }

    public static GradesFragment newInstance(){

     return new GradesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initialize(){
        coursesList= DataHolder.getCourses();
        gradesList=DataHolder.getGrades();
        gradeCountList=DataHolder.getGradeCounts();
        courses=new ArrayList<>(coursesList);
        grades=new ArrayList<>(gradesList);
        gradeCounts=new ArrayList<>(gradeCountList);

    }
}
