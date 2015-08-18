package com.karthikb351.vitinfo2.fragment.grades;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.contract.Grade;
import com.karthikb351.vitinfo2.contract.SemesterWiseGrade;
import com.karthikb351.vitinfo2.event.RefreshFragmentEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class GradeSemesterCardFragment extends Fragment {

    public SemesterWiseGrade semester ;
    View rootView ;
    int semPosition ;
    TextView semeterName ;
    LayoutInflater inflater;
    int [] gradeColors ;
    GradeListAdapter listAdapter;
  //  RecyclerView gradeRecyclerView;
    TextView gpa ;
    LinearLayout semCardContainer;
    List<Grade> gradeList ;

    public static GradeSemesterCardFragment newInstance(SemesterWiseGrade semester,int position) {
        GradeSemesterCardFragment fragment = new GradeSemesterCardFragment();
        fragment.semester = semester;
        fragment.semPosition = position;
        return fragment;
    }

    public GradeSemesterCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment_grade_semester_card, container, false);
        initialize();
       return rootView ;
    }

    void initialize()
    {
        gradeColors = getResources().getIntArray(R.array.grade_colors);
        semeterName = (TextView)rootView.findViewById(R.id.text_view_semester_name);
        gpa = (TextView)rootView.findViewById(R.id.text_view_semester_gpa);
        semeterName.setText("Semester "+Integer.toString(semPosition+1));
        semCardContainer = (LinearLayout)rootView.findViewById(R.id.linear_layout_grade_container);
        //gradeRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view_semester_grades);
        inflater = LayoutInflater.from(getActivity());
        ArrayList<Grade> gradestoadd = new ArrayList<>();
        gpa.setText(Double.toString(semester.getGpa()));
        gradeList = ((MainApplication) getActivity().getApplication()).getDataHolderInstanceInitialized().getGrades();
        for( Grade g : gradeList)
        {
            if(g.getExamHeld().equals(semester.getExamHeld()))
            {
              //  gradestoadd.add(g);
                addGrade(g);
            }
        }
      //  Log.d("grade length",Integer.toString(gradestoadd.size()));
     //   gradeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
     //   listAdapter = new GradeListAdapter(getActivity(),gradestoadd);
       // gradeRecyclerView.setAdapter(listAdapter);
     //   listAdapter.notifyDataSetChanged();
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

    void addGrade(Grade g)
    {
        View view = inflater.inflate(R.layout.card_acad_history,semCardContainer,false);
        TextView grade = ((TextView)view.findViewById(R.id.tv_grade));
        grade.setText(g.getGrade());
        int pos = (int)g.getGrade().charAt(0)-65;
        if(pos>=gradeColors.length)
        {
            if( g.getGrade().charAt(0)=='S')
                pos = gradeColors.length - 2;
            else
                pos = gradeColors.length - 1;
        }
        ((GradientDrawable)grade.getBackground()).setColor(gradeColors[pos]);
        ((TextView)view.findViewById(R.id.tv_course_code)).setText(g.getCourseCode());
        ((TextView)view.findViewById(R.id.tv_course_name)).setText(g.getCourseTitle());
        ((TextView)view.findViewById(R.id.tv_course_credit)).setText(Integer.toString(g.getCredits()) + " credits");
        semCardContainer.addView(view);
    }
}
