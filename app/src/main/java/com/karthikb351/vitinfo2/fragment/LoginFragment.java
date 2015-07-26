/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 *
 * This file is part of VITacademics.
 * VITacademics is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VITacademics is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VITacademics.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.utility.Constants;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoginFragment extends Fragment {

    private EditText editTextRegisterNumber, editTextDateOfBirth, editTextMobileNumber;
    private Button buttonLogin;
    private RadioGroup radioGroupCampus;
    private RadioButton radioVelloreCampus, radioChennaiCampus;
    private Calendar calendar;
    private View rootView;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TextView loadingMessage;
    private String campus;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_login,new LoginFragment(),"Login Fragment");
        initialize();
        return rootView;
    }

    private void initialize() {
        editTextRegisterNumber = (EditText) rootView.findViewById(R.id.input_reg_no);
        editTextDateOfBirth = (EditText) rootView.findViewById(R.id.input_dob);
        editTextMobileNumber = (EditText) rootView.findViewById(R.id.input_phone_no);
        radioGroupCampus = (RadioGroup) rootView.findViewById(R.id.select_campus);
        radioVelloreCampus = (RadioButton) rootView.findViewById(R.id.select_vellore);
        radioChennaiCampus = (RadioButton) rootView.findViewById(R.id.select_chennai);
        buttonLogin = (Button) rootView.findViewById(R.id.button_login);
        loadingMessage = (TextView) rootView.findViewById(R.id.loading_message);

        View.OnClickListener loginViewOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_login:
                        String registerNumber = editTextRegisterNumber.getText().toString();
                        String dateOfBirth = editTextDateOfBirth.getText().toString();
                        String mobileNumber = editTextMobileNumber.getText().toString();
                        LoggingInFragment loggingInFragment= LoggingInFragment.newInstance(campus, registerNumber, dateOfBirth, mobileNumber);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_login,loggingInFragment,"Logging In Fragment").
                                addToBackStack(null).commit();
                        break;
                    case R.id.input_dob:
                        showDatePicker(view);
                        break;
                }
            }
        };
        buttonLogin.setOnClickListener(loginViewOnClickListener);
        editTextDateOfBirth.setOnClickListener(loginViewOnClickListener);

        // To disable EditText editTextMobileNumber for Chennai Campus
        radioGroupCampus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.select_chennai:
                        campus = Constants.CAMPUS_CHENNAI;
                        editTextMobileNumber.setFocusable(false);
                        break;
                    case R.id.select_vellore:
                        campus = Constants.CAMPUS_VELLORE;
                        editTextMobileNumber.setFocusable(true);
                        break;

                }
            }
        });

        calendar = Calendar.getInstance();
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.API_DATEOFBIRTH_FORMAT, Locale.US);
                editTextDateOfBirth.setText(sdf.format(calendar.getTime()));
            }
        };
    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        thisActivity = activity;
    }*/

    private void showDatePicker(View view) {
        new DatePickerDialog(getActivity(), onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}
