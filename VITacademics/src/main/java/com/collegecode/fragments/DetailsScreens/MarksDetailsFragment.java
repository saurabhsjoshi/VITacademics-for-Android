package com.collegecode.fragments.DetailsScreens;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.Mark;
import com.collegecode.objects.Subject;
import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;

import java.util.ArrayList;

/**
 * Created by saurabh on 4/30/14.
 */
public class MarksDetailsFragment extends Fragment {
    Subject subject;
    public float total = 0;

    public MarksDetailsFragment(Subject subject){
        this.subject = subject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        if(subject.marks_valid){
            v = inflater.inflate(R.layout.fragment_marks,container, false);
            total = 0;
            Mark m = subject.mark;

            //FIRST BAR GRAPH

            ArrayList<Bar> points = new ArrayList<Bar>();

            Bar d = new Bar();
            d.setColor(Color.parseColor("#99CC00"));
            d.setName("Quiz 1");
            d.setValue(getMark(m.quiz[0].marks));

            Bar d2 = new Bar();
            d2.setColor(Color.parseColor("#FFBB33"));
            d2.setName("Quiz 2");
            d2.setValue(getMark(m.quiz[1].marks));

            Bar d3 = new Bar();
            d3.setColor(Color.parseColor("#FFBB33"));
            d3.setName("Quiz 3");
            d3.setValue(getMark(m.quiz[2].marks));

            Bar d4 = new Bar();
            d4.setColor(Color.parseColor("#FFBB33"));
            d4.setName("Assignment");
            d4.setValue(getMark(m.asgn.marks));

            points.add(d);
            points.add(d2);
            points.add(d3);
            points.add(d4);

            BarGraph g = (BarGraph)v.findViewById(R.id.graph_quiz);
            g.setBars(points);

            //SECOND BAR GRAPH
            g = (BarGraph)v.findViewById(R.id.graph_cat);

            ArrayList<Bar> points2 = new ArrayList<Bar>();

            Bar d5 = new Bar();
            d5.setColor(Color.parseColor("#99CC00"));
            d5.setName("CAT I");
            d5.setValue(getCMark(m.cat[0].marks));

            Bar d6 = new Bar();
            d6.setColor(Color.parseColor("#FFBB33"));
            d6.setName("CAT II");
            d6.setValue(getCMark(m.cat[1].marks));

            Bar d7 = new Bar();
            d7.setColor(Color.parseColor("#FF0000"));
            d7.setName("Total");
            d7.setValue(total);

            points2.add(d5);
            points2.add(d6);
            points2.add(d7);
            g.setBars(points2);

            ((TextView) v.findViewById(R.id.lbl_mrk_details)).setText("");

        }
        else
            v = inflater.inflate(R.layout.fragment_nomarks,container, false);
        return v;
    }

    private float getMark(String num){
       try{
           float f = Float.parseFloat(num);
           total += f;
           return f;

       }catch (Exception ignore){return  0;}
    }

    private float getCMark(String num){
        try{
            float f = (Float.parseFloat(num) * 100) / 100;

            total += Float.parseFloat(String.format("%.2f",(f/50 * 15)));
            return f;

        }catch (Exception ignore){return  0;}
    }
}
