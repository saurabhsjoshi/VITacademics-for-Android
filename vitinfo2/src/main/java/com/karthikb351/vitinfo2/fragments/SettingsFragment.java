package com.karthikb351.vitinfo2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.DataHandler;

;

/**
 * Created by saurabh on 4/22/14.
 */
public class SettingsFragment extends Fragment{
    DataHandler dat;
    DatePicker.OnDateChangedListener datechanged;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        dat = new DataHandler(getActivity());

        final EditText txt_regno = (EditText) view.findViewById(R.id.txt_settings_regno);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        txt_regno.setText(dat.getRegNo());
        txt_regno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void afterTextChanged(Editable editable) {
                dat.saveRegNo(txt_regno.getText().toString());
            }
        });

        datechanged = new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                int dob[] = new int[3];
                dob[0] = i3;
                dob[1] = i2;
                dob[2] = i;
                dat.saveDob(dob);
            }
        };

        int[] dob =  dat.getDOB();

        if(dob[0]!= 0 && dob[1] != 0 && dob[2] != 0){
            datePicker.init(dob[2],dob[1],dob[0], datechanged);
        }
        else{
            datePicker.init(1990,1,1, datechanged);
        }

        RadioButton r;
        if (dat.isVellore())
            r = (RadioButton) view.findViewById (R.id.radioVel);
        else
            r = (RadioButton) view.findViewById (R.id.radioChen);
        r.setChecked(true);

        return view;
    }
}
