package com.karthikb351.vitinfo2.utility;

import android.util.Pair;

import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Timing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pulkit on 03/07/2015.
 */
public class SortedArrayList extends ArrayList<Pair<Course,Integer>> {

    public void insert(Pair<Course,Integer> data) {
        try {
            Date date = new Date();
            Timing thistime = data.first.getTimings()[data.second];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy KK:mm:ss a Z");
            Date startDate = simpleDateFormat.parse(thistime.getStartTime());
            Date endDate = simpleDateFormat.parse(thistime.getEndTime());
            if (date.after(endDate))
                return ;
                int i = 0;
            for (; i < size(); i++) {
                Date time = simpleDateFormat.parse(get(i).first.getTimings()[get(i).second].getStartTime());
                if(time.before(startDate))
                    continue;
                else
                    break;
            }
            add(i, data);
        }
        catch(ParseException e){
            e.printStackTrace();
        }
    }
}
