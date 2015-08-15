package com.karthikb351.vitinfo2.fragment.grades;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import java.util.List;

/**
 * Created by pulkit on 13/08/2015.
 */
public class GradesPagerAdapter extends FragmentPagerAdapter {

    private final int GRADES_COUNT;
    private Context context;
    FragmentManager fm ;
    List<SemesterWiseGrade> semesterWiseGrades ;

    public GradesPagerAdapter(Context context,FragmentManager fm,List<SemesterWiseGrade> swg)
    {
        super(fm);
        this.context = context;
        this.fm=fm;
        this.semesterWiseGrades=swg;
        GRADES_COUNT = semesterWiseGrades.size();
    }

    @Override
    public Fragment getItem(int position) {
        return GradeSemesterCardFragment.newInstance(semesterWiseGrades.get(position),position);
    }

    @Override
    public int getCount() {
        return GRADES_COUNT;
    }
}
