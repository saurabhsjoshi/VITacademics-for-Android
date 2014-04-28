package com.collegecode.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.Subject;

import java.util.ArrayList;

/**
 * Created by saurabh on 4/28/14.
 */
public class CoursesListAdapter extends ArrayAdapter<Subject> {

    private LayoutInflater inflater;
    private DataHandler dat;
    private Context context;

    public CoursesListAdapter(Context context,  ArrayList<Subject> subs){
        super(context,0 , subs);
        dat = new DataHandler(context);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView ( int position, View view, ViewGroup parent ) {

        view = inflater.inflate(R.layout.courses_list_item,parent, false);

        Subject sub = getItem(position);

        ((TextView) view.findViewById(R.id.title)).setText(sub.title);
        ((TextView) view.findViewById(R.id.slot)).setText(sub.slot);
        ((TextView) view.findViewById(R.id.type)).setText(sub.type);
        ((TextView) view.findViewById(R.id.atten)).setText(sub.attended+"/"+sub.conducted+"\n"+sub.percentage+"%");

        TextView status = (TextView) view.findViewById(R.id.atten_listitem_status);
        TextView date = (TextView) view.findViewById(R.id.atten_listitem_date);



        if(sub.att_valid){
            date.setText("As of: "+sub.atten_last_date);
            status.setText(sub.atten_last_status);
            if(sub.atten_last_status.equalsIgnoreCase("absent"))
                status.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            status.setText("");
            date.setText("");
        }

        ProgressBar pg= (ProgressBar) view.findViewById(R.id.progress);
        float x[]={5,5,5,5,5,5,5,5};
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(x, null,null));
        pgDrawable.getPaint().setColor(DataHandler.getPerColor(sub.percentage));
        ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        pg.setProgressDrawable(progress);

        pg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.progress_bar_green));

        pg.setMax(sub.conducted);
        pg.setProgress(sub.attended);
        pg.invalidate();
        return view;
    }


}
