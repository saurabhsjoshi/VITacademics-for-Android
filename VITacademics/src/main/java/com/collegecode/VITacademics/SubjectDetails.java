package com.collegecode.VITacademics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.collegecode.adapters.SubjecDetailsPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by saurabh on 4/30/14.
 */
public class SubjectDetails extends FragmentActivity {
    private String clsnbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectdetails);

        Intent intent = getIntent();
        clsnbr = intent.getStringExtra("clsnbr");
        ViewPager pager = (ViewPager) findViewById(R.id.timetable_pager);
        pager.setAdapter(new SubjecDetailsPagerAdapter(this, clsnbr, getSupportFragmentManager()));
        TitlePageIndicator titlePageIndicator = (TitlePageIndicator) findViewById(R.id.timetable_pager_indicator);
        titlePageIndicator.setViewPager(pager);
        titlePageIndicator.setTextColor(getResources().getColor(R.color.Gray));
    }
}
