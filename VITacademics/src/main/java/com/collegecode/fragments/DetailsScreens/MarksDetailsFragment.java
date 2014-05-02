package com.collegecode.fragments.DetailsScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegecode.VITacademics.R;
import com.collegecode.objects.Mark;
import com.collegecode.objects.Subject;

/**
 * Created by saurabh on 4/30/14.
 */
public class MarksDetailsFragment extends Fragment {
    Subject subject;

    public MarksDetailsFragment( Subject subject){
        this.subject = subject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        if(subject.marks_valid){
            v = inflater.inflate(R.layout.fragment_marks,container, false);
            Mark m = subject.mark;
            //((TextView) v.findViewById(R.id.txt_cat1)).setText(m.cat[0].marks);
        }
        else
            v = inflater.inflate(R.layout.fragment_nomarks,container, false);
        return v;
    }
}
