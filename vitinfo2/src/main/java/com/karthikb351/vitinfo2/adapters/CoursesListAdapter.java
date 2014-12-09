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
import com.karthikb351.vitinfo2.api.Objects.Course;
import com.karthikb351.vitinfo2.objects.DataHandler;

import java.util.List;

/**
 * Created by saurabh on 4/28/14.
 */
public class CoursesListAdapter extends ArrayAdapter<Course> {
    private Context context;

    public CoursesListAdapter(Context context,  List<Course> subs){
        super(context,0 , subs);
        this.context = context;
    }

    private static class ViewHolder{
        TextView title, slot, type, atten, status,date;
        ProgressBar prg;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) {
        ViewHolder holder;

        //Recycling of progress bar colors not working :/
        //if(convertView == null)
        //{
            LayoutInflater inflater = LayoutInflater.from(context);
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
        //}


        //else
          //  holder = (ViewHolder)convertView.getTag();

        Course course = getItem(position);

        holder.title.setText(course.getCourseTitle());
        holder.slot.setText(course.getSlot());
        holder.type.setText(course.getCourseType());
        holder.atten.setText(course.getAttendance().getAttendedClasses()+"/"
                +course.getAttendance().getTotalClasses()+"\n"+course.getAttendance().getAttendancePercentage());

        int lastIndex = course.getAttendance().getDetails().size() - 1;
        holder.date.setText("As of: "+course.getAttendance().getDetails().get(lastIndex).getDate());
        holder.status.setText(course.getAttendance().getDetails().get(lastIndex).getStatus());
        if(course.getAttendance().getDetails().get(0).getStatus().equalsIgnoreCase("absent"))
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        else
            holder.status.setTextColor(Color.parseColor("#000000"));

        float x[]={5,5,5,5,5,5,5,5};
        ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(x, null,null));
        float t = DataHandler.getPer(
                Integer.parseInt(course.getAttendance().getAttendedClasses()),
                Integer.parseInt(course.getAttendance().getAttendedClasses())
        );
        pgDrawable.getPaint().setColor(DataHandler.getPerColor((int)t));
        ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);

        holder.prg.setProgressDrawable(progress);
        holder.prg.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.progress_bar_green));
        holder.prg.setMax(Integer.parseInt(course.getAttendance().getTotalClasses()));
        holder.prg.setProgress(Integer.parseInt(course.getAttendance().getAttendedClasses()));

        return convertView;
    }
}
