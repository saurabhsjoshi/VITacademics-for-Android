package com.karthikb351.vitinfo2.fragment.grades;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class GradesFragment_new extends Fragment {


    View rootView ;
    LineChart chart ;
    ViewPager pager ;
    GradesPagerAdapter pagerAdapter;
    List<SemesterWiseGrade> semesterWiseGrades ;
    RecyclerView gradesRecyclerView ;

    public  static GradesFragment_new newInstance() {
        GradesFragment_new fragment = new GradesFragment_new();
        return fragment;
    }

    public GradesFragment_new() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.fragment_grades_new, container, false);
        initialize();
        return rootView ;
    }

    void initialize()
    {
        semesterWiseGrades = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getSemesterWiseGrades();
        chart = (LineChart)rootView.findViewById(R.id.grades_chart);
        initializeChart();
        pager = (ViewPager)rootView.findViewById(R.id.view_pager_grades);
        pagerAdapter =new GradesPagerAdapter(getActivity(),getActivity().getSupportFragmentManager(),semesterWiseGrades);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new semCardChangeListener());
        String Title = getActivity().getResources().getString(R.string.fragment_grades_title);
        getActivity().setTitle(Title);
    }

    void initializeChart()
    {
        Resources r = getResources();
        ArrayList<Entry> data = new ArrayList<>();
        float maxGpa = 0.0f , minGpa = 10.0f;
        ArrayList<String> xVals = new ArrayList<>();
        for(int i = 0 ; i < semesterWiseGrades.size() ; i++)
        {
            float gpa = (float)semesterWiseGrades.get(i).getGpa();
            xVals.add("Semester "+Integer.toString(i+1));
            data.add(new Entry(gpa,i));
            if(gpa>maxGpa)
                maxGpa=gpa;
            if(gpa<minGpa)
                minGpa=gpa;
        }
        LineDataSet dset = new LineDataSet(data,"gpa");
        dset.setLineWidth(2.0f);
        dset.setValueTextSize(10.0f);
        dset.setColor(r.getColor(R.color.colorPrimary));
        dset.setHighLightColor(r.getColor(R.color.colorPrimary));
        dset.setCircleColor(r.getColor(R.color.colorAccent));
        dset.setDrawCircleHole(false);
        LineData chartData = new LineData(xVals,dset);
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
        chart.highlightValue(0,0);
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

    class semCardChangeListener implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            chart.highlightValue(position,0);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
