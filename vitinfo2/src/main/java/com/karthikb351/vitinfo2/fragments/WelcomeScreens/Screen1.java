package com.karthikb351.vitinfo2.fragments.WelcomeScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.karthikb351.vitinfo2.NewUser;
import com.karthikb351.vitinfo2.R;

;

/**
 * Created by saurabh on 4/26/14.
 */
public class Screen1 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newuser_1,container, false);

        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Welcome");

        Button btn_next = (Button) view.findViewById(R.id.btn_next1);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewUser nw = (NewUser) getActivity();
                nw.changeScreen(1);
            }
        });
        return view;
    }

    //Prevents crash due to bug in Android
    @Override
    public void onSaveInstanceState( Bundle outState ) {}
}
