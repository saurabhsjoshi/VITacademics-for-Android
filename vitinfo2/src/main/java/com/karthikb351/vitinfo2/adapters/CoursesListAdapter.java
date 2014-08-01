package com.karthikb351.vitinfo2.adapters;

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

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.Subject;

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
        dat = DataHandler.getInstance(context);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    private static class ViewHolder{
        TextView title, slot, type, atten, status,date;
        ProgressBar prg;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.courses_list_item,parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.slot = (TextView) convertView.findViewById(R.id.slot);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.atten = (TextView) convertView.findViewById(R.id.atten);
            holder.status = (TextView) convertView.findViewById(R.id.atten_listitem_status);
            holder.date = (TextView) convertView.findViewById(R.id.atten_listitem_date);
            holder.prg = (ProgressBar) convertView.findViewById(R.id.progress);
            convertView.setTag(holder);
        }

        else
            holder = (ViewHolder)convertView.getTag();

        Subject sub = getItem(position);

        holder.title.setText(sub.title);
        holder.slot.setText(sub.slot);
        holder.type.setText(sub.type);
        holder.atten.setText(sub.attended+"/"+sub.conducted+"\n"+sub.percentage+"%");

        if(sub.att_valid){
            holder.date.setText("As of: "+sub.atten_last_date);
            holder.status.setText(sub.atten_last_status);
            if(sub.atten_last_status.equalsIgnoreCase("absent"))
                holder.status.setTextColor(Color.parseColor("#FF0000"));
            else
                holder.status.setTextColor(Color.parseColor("#000000"));
        }
        else {
            holder.status.setText("");
            holder.date.setText("");
        }

        float x[]={5,5,5,5,5,5,5,5};
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(x, null,null));
        pgDrawable.getPaint().setColor(DataHandler.getPerColor(sub.percentage));
        ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        holder.prg.setProgressDrawable(progress);

        holder.prg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.progress_bar_green));

        holder.prg.setMax(sub.conducted);
        holder.prg.setProgress(sub.attended);
        holder.prg.invalidate();
        return convertView;
    }
}
