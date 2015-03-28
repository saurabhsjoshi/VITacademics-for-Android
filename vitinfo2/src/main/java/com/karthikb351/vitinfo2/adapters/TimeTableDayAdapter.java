package com.karthikb351.vitinfo2.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.StyleSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.models.core.Course;

import java.util.List;

import static android.text.Layout.Alignment.ALIGN_NORMAL;
import static android.text.Layout.Alignment.ALIGN_OPPOSITE;

/**
 * Created by aashrai on 28/3/15.
 */
public class TimeTableDayAdapter extends RecyclerView.Adapter<TimeTableDayAdapter.ViewHolder> {


    private List<Course> courseList;

    public TimeTableDayAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView cardView = new CardView(parent.getContext());
        cardView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView tvCourse = new TextView(parent.getContext());
        tvCourse.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        cardView.addView(tvCourse, 0);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvCourse.setText(setTextSpan(courseList.get(position)));
    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    private SpannableString setTextSpan(Course course) {

        int start = 0;
        int end = course.getCourseSlotLength() + 1;
        SpannableString spannable = new SpannableString(course.getCourse());
        spannable.setSpan(new StyleSpan(R.style.TimetableCardTop), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AlignmentSpan.Standard(ALIGN_NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = course.getCourseRoomLength();
        spannable.setSpan(new StyleSpan(R.style.TimetableCardTop), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AlignmentSpan.Standard(ALIGN_OPPOSITE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = course.getCourseNameLength() + 3;
        spannable.setSpan(new StyleSpan(R.style.TimeTableText), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AlignmentSpan.Standard(ALIGN_NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = course.getCourseAttendanceLength();
        spannable.setSpan(new StyleSpan(R.style.TimeTableAttendance), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AlignmentSpan.Standard(ALIGN_NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = course.getCourseTimeLength();
        spannable.setSpan(new StyleSpan(R.style.TimeTableCardBottom), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AlignmentSpan.Standard(ALIGN_OPPOSITE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCourse;

        public ViewHolder(CardView cardView) {
            super(cardView);
            tvCourse = (TextView) cardView.getChildAt(0);
        }
    }


}
