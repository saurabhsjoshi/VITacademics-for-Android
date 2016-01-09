/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 * Copyright (C) 2015  Darshan Mehta <darshanmehta17@gmail.com>
 *
 * This file is part of VITacademics.
 *
 * VITacademics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VITacademics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VITacademics.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.fragment.grades;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;

public class GradesFragment extends Fragment {

    private View rootView;
    private LineChart chart;
    private ViewPager pager;
    float Cgpa;
    private GradesPagerAdapter pagerAdapter;
    private List<SemesterWiseGrade> semesterWiseGrades;
    private RecyclerView gradesRecyclerView;

    public GradesFragment() {
    }

    public static GradesFragment newInstance() {
        GradesFragment fragment = new GradesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        initialize();
        return rootView;
    }

    void initialize() {
        semesterWiseGrades = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getSemesterWiseGrades();
        Collections.sort(semesterWiseGrades, new SemCompare());
        Log.w("Tester:", semesterWiseGrades.get(0).getExamHeld());
        chart = (LineChart) rootView.findViewById(R.id.grades_chart);
        Cgpa = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getCgpa();
        initializeChart();
        pager = (ViewPager) rootView.findViewById(R.id.view_pager_grades);
        pagerAdapter = new GradesPagerAdapter(getActivity(), getActivity().getSupportFragmentManager(), semesterWiseGrades);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new semCardChangeListener());
        String Title = getActivity().getResources().getString(R.string.fragment_grades_title);
        getActivity().setTitle(Title);
    }

    void initializeChart() {
        Resources r = getResources();
        int CgpaColor;
        if (Cgpa > 8.0f)
            CgpaColor = r.getColor(R.color.highAttend);
        else if (Cgpa > 6.0f && Cgpa < 8.0f)
            CgpaColor = r.getColor(R.color.midAttend);
        else
            CgpaColor = r.getColor(R.color.lowAttend);
        ArrayList<Entry> data = new ArrayList<>();
        ArrayList<Entry> CGPAentryList = new ArrayList<>();
        float maxGpa = 0.0f, minGpa = 10.0f;
        ArrayList<String> xVals = new ArrayList<>();
        for (int i = 0; i < semesterWiseGrades.size(); i++) {
            float gpa = (float) semesterWiseGrades.get(i).getGpa();
            xVals.add(getString(R.string.label_semester_no, i + 1));
            data.add(new Entry(gpa, i));
            if (gpa > maxGpa)
                maxGpa = gpa;
            if (gpa < minGpa)
                minGpa = gpa;
        }
        CGPAentryList.add(new Entry(Cgpa, 0));
        CGPAentryList.add(new Entry(Cgpa, data.size() - 1));
        LineDataSet dset = new LineDataSet(data, getString(R.string.label_grade_gpa));
        LineDataSet CGPAdset = new LineDataSet(CGPAentryList, "CGPA " + String.valueOf(Cgpa));
        CGPAdset.setDrawCircleHole(false);
        CGPAdset.setHighlightEnabled(false);
        CGPAdset.setDrawFilled(true);
        CGPAdset.setFillAlpha(80);
        CGPAdset.enableDashedLine(1.0f, 0.5f, 0.5f);
        CGPAdset.setDrawCircles(false);
        CGPAdset.setDrawValues(false);
        dset.setLineWidth(2.0f);
        CGPAdset.setLineWidth(1.0f);
        dset.setValueTextSize(10.0f);
        CGPAdset.setValueTextSize(10.0f);
        dset.setColor(r.getColor(R.color.colorPrimary));
        CGPAdset.setColor(CgpaColor);
        CGPAdset.setFillColor(CgpaColor);
        dset.setHighLightColor(r.getColor(R.color.colorPrimary));
        dset.setCircleColor(r.getColor(R.color.colorAccent));
        dset.setDrawCircleHole(false);
        LineData chartData = new LineData(xVals, Arrays.asList(dset, CGPAdset));
        YAxis leftaxis = chart.getAxisLeft();
        leftaxis.setStartAtZero(false);
        leftaxis.setAxisMinValue(minGpa - 0.1f);
        leftaxis.setAxisMaxValue(maxGpa + 0.1f);
        chart.setDescription("");
        YAxis rightAxis = chart.getAxisRight();
        leftaxis.setEnabled(false);
        rightAxis.setEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.setData(chartData);
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.highlightValue(0, 0);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                pager.setCurrentItem(e.getXIndex());
            }

            @Override
            public void onNothingSelected() {
                pager.setCurrentItem(0);
            }
        });
    }
    class SemCompare implements Comparator<SemesterWiseGrade> {
        @Override
        public int compare(SemesterWiseGrade s1, SemesterWiseGrade s2) {
            // write comparison logic here like below , it's just a sample
            String heldDate1[] = s1.getExamHeld().split("-"); //{month, year}
            String heldDate2[] = s2.getExamHeld().split("-");
            Log.w("Test Start",heldDate1[1]+"-"+heldDate1[0]);
            if (heldDate1[0].compareTo(heldDate2[0]) == 0)
                return heldDate1[1].compareTo(heldDate2[1]);
            else
                return heldDate1[0].compareTo(heldDate2[0]);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    // This method will be called when a RefreshFragmentEvent is posted
    public void onEvent(RefreshFragmentEvent event) {
        initialize();
    }

    class semCardChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            chart.highlightValue(position, 0);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}


