package com.karthikb351.vitinfo2.fragment.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.fragment.assesment.AssesmentFragment;
import com.karthikb351.vitinfo2.fragment.attendance.AttendanceFragment;

/**
 * Created by gaurav on 5/7/15.
 */
public class DetailsFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    DetailsAdapter adapter;
    Course course;

    public DetailsFragment(){

    }

    public DetailsFragment newInstance(Course course){
        DetailsFragment detailsFragment=new DetailsFragment();
        detailsFragment.course=course;
        return new DetailsFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_details,container,false);
        tabLayout=(TabLayout)view.findViewById(R.id.tabs_details);
        viewPager=(ViewPager)view.findViewById(R.id.view_pager_details);
        adapter=new DetailsAdapter(getActivity().getSupportFragmentManager(),getActivity());
        viewPager.setAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public class DetailsAdapter extends FragmentStatePagerAdapter{

        Fragment fragment;
        Context context;
        int TAB_COUNT_DETAILS=2;

        public DetailsAdapter(FragmentManager fragmentManager,Context context){
            super(fragmentManager);
            this.context=context;
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0)
                fragment= AttendanceFragment.newInstance(course);
            else if(position==1)
                fragment= AssesmentFragment.newInstance(course);
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUNT_DETAILS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String detailsTitles[]={"Attendance","Marks"};
            return detailsTitles[position];
        }
    }

}