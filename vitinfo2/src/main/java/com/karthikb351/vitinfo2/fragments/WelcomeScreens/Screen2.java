package com.karthikb351.vitinfo2.fragments.WelcomeScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.karthikb351.vitinfo2.NewUser;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.VITxAPI;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.OnTaskComplete;

;

/**
 * Created by saurabh on 4/27/14.
 */
public class Screen2 extends Fragment {
    DataHandler dat;
    VITxAPI api;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newuser_2,container, false);

        dat = new DataHandler(getActivity());

        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Credentials");

        final EditText reg_no = (EditText) view.findViewById(R.id.txt_settings_regno);
        final DatePicker dob = (DatePicker) view.findViewById(R.id.datePicker);
        final Button btn_next2 = (Button) view.findViewById(R.id.btn_next2);

        btn_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_next2.setEnabled(false);
                btn_next2.setText("Verifying");
                dat.saveRegNo(reg_no.getText().toString());
                dat.saveDob(new int[]{dob.getDayOfMonth(),dob.getMonth(),dob.getYear()});

                //Set Listener
                api = new VITxAPI(getActivity(), new OnTaskComplete() {

                    @Override
                    public void onTaskCompleted(Exception e, Object result) {
                        if(e !=null )
                        {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                            btn_next2.setEnabled(true);
                            btn_next2.setText("Sign In (Again)");
                        }
                        else {
                            NewUser nw = (NewUser) getActivity();
                            nw.changeScreen(2);
                        }
                    }
                });

                //Start the Paaartyyy
                api.CaptchaLessLoad();
            }
        });


        return view;
    }

    @Override
    public void onSaveInstanceState( Bundle outState ) {}
}
