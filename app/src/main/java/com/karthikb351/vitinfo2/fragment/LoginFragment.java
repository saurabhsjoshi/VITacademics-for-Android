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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.activity.MainActivity;
import com.karthikb351.vitinfo2.api.NetworkController;
import com.karthikb351.vitinfo2.api.RequestConfig;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.ResultListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoginFragment extends Fragment {

    private final int PROGRESS_START = 0;
    private final int PROGRESS_END = 100;
    private final int PROGRESS_INCREMENT = 20;
    private EditText editTextRegisterNumber, editTextDateOfBirth, editTextMobileNumber;
    private Button buttonLogin;
    private RadioGroup radioGroupCampus;
    private RadioButton radioVelloreCampus, radioChennaiCampus;
    private Calendar calendar;
    private View rootView;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private ProgressBar progressBar;
    private TextView loadingMessage;
    private int progress;
    private String campus;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
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
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar_login);
        loadingMessage = (TextView) rootView.findViewById(R.id.loading_message);

        View.OnClickListener loginViewOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_login:
                        String registerNumber = editTextRegisterNumber.getText().toString();
                        String dateOfBirth = editTextDateOfBirth.getText().toString();
                        String mobileNumber = editTextMobileNumber.getText().toString();
                        loadingMessage.setText(getString(R.string.message_login_loading));
                        loginToServer(campus, registerNumber, dateOfBirth, mobileNumber);
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

    private void showDatePicker(View view) {
        new DatePickerDialog(getActivity(), onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void loginToServer(String campus, String registerNumber, String dateOfBirth, String mobileNumber) {

        NetworkController networkController = NetworkController.getInstance(getActivity(), campus, registerNumber, dateOfBirth, mobileNumber);

        final ResultListener resultListener = new ResultListener() {
            @Override
            public void onSuccess() {
                progress = PROGRESS_END;
                progressBar.setProgress(progress);
                startActivity(new Intent(getActivity(), MainActivity.class));
            }

            @Override
            public void onFailure(Status status) {
                try {
                    Toast.makeText(getActivity(), status.getMessage(), Toast.LENGTH_SHORT).show();
                    loadingMessage.setText("");
                    progress = PROGRESS_START;
                    progressBar.setProgress(progress);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onProgress() {
                try {
                    LoginFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress = progress + PROGRESS_INCREMENT;
                            progressBar.incrementProgressBy(PROGRESS_INCREMENT);
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        };

        RequestConfig requestConfig = new RequestConfig(new ResultListener() {
            @Override
            public void onSuccess() {
                try {
                    ((MainApplication) getActivity().getApplication()).getDataHolderInstance().refreshData(getActivity(), resultListener);
                } catch (NullPointerException ignore){
                }
            }

            @Override
            public void onFailure(Status status) {
                resultListener.onFailure(status);
            }

            @Override
            public void onProgress() {
                try {
                    LoginFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress = progress + PROGRESS_INCREMENT;
                            progressBar.incrementProgressBy(PROGRESS_INCREMENT);
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
        requestConfig.addRequest(RequestConfig.REQUEST_SYSTEM);
        requestConfig.addRequest(RequestConfig.REQUEST_REFRESH);
        requestConfig.addRequest(RequestConfig.REQUEST_GRADES);
        requestConfig.addRequest(RequestConfig.REQUEST_TOKEN);

        networkController.dispatch(requestConfig);
    }
}
