package com.karthikb351.vitinfo2.fragment.grades;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Grade;
import java.util.List;

/**
 * Created by pulkit on 12/08/2015.
 */

public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.GradeViewHolder> {

    private Context context;
    private List<Grade> grades;

    public GradeListAdapter(Context context , List<Grade> grades)
    {
        this.context = context ;
        this.grades = grades ;
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.card_acad_history,parent,false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position)
    {
        Log.d("rec","here");
        Grade current = grades.get(position);
        holder.courseName.setText(current.getCourseTitle());
        holder.courseCode.setText(current.getCourseCode());
        holder.credit.setText(Integer.toString(current.getCredits()) + "credits");
        holder.grade.setText(current.getGrade());
    }

    @Override
    public int getItemCount() {
       return grades.size();
    }

    class GradeViewHolder extends RecyclerView.ViewHolder
    {
        TextView grade ,
                 courseCode ,
                 credit ,
                 courseName;

        public GradeViewHolder(View view)
        {
          super(view);
          grade = (TextView)view.findViewById(R.id.tv_grade);
          courseCode = (TextView)view.findViewById(R.id.tv_course_code);
          courseName = (TextView)view.findViewById(R.id.tv_course_name);
          credit = (TextView)view.findViewById(R.id.tv_course_credit);
        }
    }
}
