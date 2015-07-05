package com.karthikb351.vitinfo2.fragment.assesment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Assessment;
import com.karthikb351.vitinfo2.contract.Course;
import com.karthikb351.vitinfo2.contract.Marks;

/**
 * Created by gaurav on 5/7/15.
 */
public class AssesmentListAdapter extends RecyclerView.Adapter<AssesmentListAdapter.AssesmentViewHolder> {


    Context context;
    public int layoutId;
    Marks marks;
    Course course;
    Assessment[] assesments;


    public AssesmentListAdapter(Context context,Course course){
        this.context=context;
        this.course=course;
        marks=course.getMarks();
        assesments=marks.getAssessments();
    }
    @Override
    public AssesmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.support.v7.widget.CardView cardView;
        cardView=(android.support.v7.widget.CardView) LayoutInflater.from(context).inflate(viewType,parent,false);
        return new AssesmentViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(AssesmentViewHolder holder, int position) {
        //TODO: Set text for Marks card
        holder.assessmentType.setText(assesments[position-1].getTitle());
        holder.maxMarks.setText(Double.toString(assesments[position-1].getMaxMarks()));
        holder.scoredMarks.setText(Double.toString(assesments[position-1].getScoredMarks())+"/");
        holder.weightage.setText(Double.toString(assesments[position-1].getWeightage()));
        Double contribution=assesments[position-1].getScoredPercentage()*assesments[position-1].getWeightage();
        holder.contribution.setText(Double.toString(contribution));
        holder.marksProgressBar.setProgress((int)assesments[position-1].getScoredPercentage());
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0)
            layoutId=R.layout.card_marks;
        else
            layoutId= R.layout.card_assessment;
        return layoutId;
    }

    @Override
    public int getItemCount() {
        return (assesments.length+1);
    }

    public class AssesmentViewHolder extends RecyclerView.ViewHolder{

        public TextView assessmentType,maxMarks,scoredMarks,weightage,contribution;
        public ProgressBar marksProgressBar;
        public AssesmentViewHolder(View view){
            super(view);
            // TODO: Intialise marks card attributes
            assessmentType=(TextView)view.findViewById(R.id.tv_marks_type);
            maxMarks=(TextView)view.findViewById(R.id.tv_max_marks);
            scoredMarks=(TextView)view.findViewById(R.id.tv_scored_marks);
            weightage=(TextView)view.findViewById(R.id.tv_weightage);
            contribution=(TextView)view.findViewById(R.id.tv_contribution);
            marksProgressBar=(ProgressBar)view.findViewById(R.id.progress_bar_marks);

        }
    }
}
