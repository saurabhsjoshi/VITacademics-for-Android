package com.karthikb351.vitinfo2.utility;

import android.util.Pair;

import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;

import java.util.ArrayList;

/**
 * Created by pulkit on 03/07/2015.
 */
public class SortedArrayList extends ArrayList<Pair<Course,Integer>> {

    public void insert(Pair<Course,Integer> data)
    {
        Timing thistime = data.first.getTimings()[data.second];
        int i = 0 ;
        for (; i < size() ; i++)
        {
            Timing time = get(i).first.getTimings()[get(i).second];
            //compare timings and advance i based on that
        }
        add(i,data);
    }
}
