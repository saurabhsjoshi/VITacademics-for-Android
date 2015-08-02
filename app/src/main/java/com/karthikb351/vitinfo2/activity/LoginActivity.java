/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Saurabh Joshi <saurabhjoshi94@outlook.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Karthik Balakrishnan <karthikb351@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 * Copyright (C) 2015  Hemant Jain <hemanham@gmail.com>
 * Copyright (C) 2015  Darshan Mehta <darshanmehta17@gmail.com>
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

package com.karthikb351.vitinfo2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avast.android.dialogs.fragment.DatePickerDialogFragment;
import com.avast.android.dialogs.iface.IDateDialogListener;
import com.karthikb351.vitinfo2.MainApplication;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.NetworkController;
import com.karthikb351.vitinfo2.api.RequestConfig;
import com.karthikb351.vitinfo2.api.ResetTask;
import com.karthikb351.vitinfo2.model.Status;
import com.karthikb351.vitinfo2.utility.Constants;
import com.karthikb351.vitinfo2.utility.NetworkUtils;
import com.karthikb351.vitinfo2.utility.ResultListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, IDateDialogListener, RadioGroup.OnCheckedChangeListener {

    private Toolbar toolbar;

    private Button loginButton;
    private Button exitButton;

    private EditText etRegNo;
    private EditText etDOB;
    private EditText etPhone;

    private TextInputLayout etRegNoHolder;
    private TextInputLayout etDOBHolder;
    private TextInputLayout etPhoneHolder;

    private ProgressBar progressBar;

    private RelativeLayout loginLayout;

    private LinearLayout loginDetailP1;
    private LinearLayout loginDetailP2;

    private String campus;
    private String registrationNumber;
    private String dateOfBirth;
    private String mobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    /**
     *  Checks if the user has already logged in or not and accordingly calls animation on the toolbar.
     *  @see #collapseView(View)
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();

        //  Used to set the inner left padding of toolbar to zero to let the image view be placed at center exactly.
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.requestLayout();

        final boolean isLoggedIn = loginCheck();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    ((MainApplication) getApplication()).getDataHolderInstance().refreshData(LoginActivity.this, new ResultListener() {
                        @Override
                        public void onSuccess() {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                        @Override
                        public void onFailure(Status status) {
                            Toast.makeText(LoginActivity.this, status.getMessage(), Toast.LENGTH_SHORT).show();
                            new ResetTask(LoginActivity.this).execute();
                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                        }
                    });
                } else {
                    collapseView(toolbar);
                }
            }
        }, 1500);

    }

    /**
     * Animates the view by scaling it from MATCH_PARENT to WRAP_CONTENT.
     * @param view - View to be collapsed
     */
    private void collapseView(final View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        final int initialHeight = rect.bottom - rect.top;

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int targetHeight = view.getMeasuredHeight();

        Animation animation = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    view.getLayoutParams().height = targetHeight;
                }else{
                    view.getLayoutParams().height = targetHeight +(int) ((initialHeight - targetHeight) * (1 - interpolatedTime));
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration((int) ((initialHeight - targetHeight) / view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);
    }

    /**
     *  Initializes all the variables and objects of this class.
     */
    private void initialize() {

        toolbar = (Toolbar) findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);

        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        exitButton = (Button)findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);

        etRegNo = (EditText) findViewById(R.id.etRegNo);
        etDOB = (EditText) findViewById(R.id.etDOB);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etDOB.setOnFocusChangeListener(this);
        etDOB.setOnClickListener(this);

        etRegNoHolder = (TextInputLayout) findViewById(R.id.etRegNoHolder);
        etDOBHolder = (TextInputLayout) findViewById(R.id.etDOBHolder);
        etPhoneHolder = (TextInputLayout) findViewById(R.id.etPhoneHolder);

        loginLayout = (RelativeLayout) findViewById(R.id.rlLoginDetails);

        progressBar = (ProgressBar)findViewById(R.id.progressBarLogin);

        loginDetailP1 = (LinearLayout) findViewById(R.id.llLoginDetailsP1);
        loginDetailP2 = (LinearLayout) findViewById(R.id.llLoginDetailsP2);

        RadioGroup radioGroupCampus = (RadioGroup) findViewById(R.id.select_campus);
        radioGroupCampus.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.loginButton:
                if (loginDetailP1.getVisibility() == View.VISIBLE){
                    switchToFormTwo();
                }else{
                    validateForm();
                }
                break;

            case R.id.exitButton:
                if(loginDetailP2.getVisibility() == View.VISIBLE){
                    switchToFormOne();
                } else{
                    finish();
                }
                break;

            case R.id.etDOB:
                launchDatePicker();
                break;
        }
    }

    /**
     *  Hides the login form and makes campus choice radio buttons visible
     */
    private void switchToFormOne() {
        loginDetailP1.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        loginDetailP2.setAnimation(animation1);
        loginDetailP2.animate();
        loginDetailP1.setAnimation(animation);
        loginDetailP1.animate();
        loginButton.setText(getResources().getString(R.string.button_next));
        loginDetailP2.setVisibility(View.GONE);
    }

    /**
     *  Hides the campus choice radio buttons and makes the login form visible
     */
    private void switchToFormTwo() {
        loginDetailP2.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        loginDetailP2.setAnimation(animation1);
        loginDetailP2.animate();
        loginDetailP1.setAnimation(animation);
        loginDetailP1.animate();
        loginButton.setText(getResources().getString(R.string.button_login));
        loginDetailP1.setVisibility(View.GONE);
    }

    /**
     *  Validates the sign in form and shows appropriate error for incomplete and invalid input
     */
    private void validateForm() {
        registrationNumber = etRegNo.getText().toString().trim().toUpperCase();
        dateOfBirth = etDOB.getText().toString().trim();
        mobileNumber = etPhone.getText().toString().trim();

        etRegNoHolder.setError(null);
        etDOBHolder.setError(null);
        etPhoneHolder.setError(null);

        if(registrationNumber.isEmpty()){
            etRegNoHolder.setError("Enter a valid registration number");
            setFocusTo(etRegNo, etDOB, etPhone);
        }else if(!isValidRegNo(registrationNumber)){
            etRegNoHolder.setError("Enter a valid registration number");
            setFocusTo(etRegNo, etDOB, etPhone);
        }else if(dateOfBirth.isEmpty()){
            etDOBHolder.setError("Choose a valid date");
            setFocusTo(etDOB, etRegNo, etPhone);
        }else if((mobileNumber.isEmpty() || mobileNumber.length() != 10) && campus.contentEquals(Constants.CAMPUS_VELLORE)){
            etPhoneHolder.setError("Enter a valid phone number");
            setFocusTo(etPhone, etDOB, etRegNo);
        }else{
            if(NetworkUtils.isNetworkConnected(this)){
                showForm(false);
                loginToServer();
            } else{
                launchNetworkError();
            }
        }

    }

    /**
     *  Authenticates credentials entered in the form with the server and fetches data
     */
    private void loginToServer() {

        NetworkController networkController = NetworkController.getInstance(this, campus, registrationNumber, dateOfBirth, mobileNumber);

        final ResultListener resultListener = new ResultListener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Status status) {
                try {
                    Snackbar.make(findViewById(R.id.rlLoginLayout), status.getMessage(), Snackbar.LENGTH_LONG).show();
                    showForm(true);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgress() {
            }
        };

        RequestConfig requestConfig = new RequestConfig(new ResultListener() {
            @Override
            public void onSuccess() {
                try {
                    ((MainApplication) LoginActivity.this.getApplication()).getDataHolderInstance().refreshData(LoginActivity.this, resultListener);
                } catch (NullPointerException ignore){
                }
            }

            @Override
            public void onFailure(Status status) {

                resultListener.onFailure(status);
            }

            @Override
            public void onProgress() {
            }
        });
        requestConfig.addRequest(RequestConfig.REQUEST_SYSTEM);
        requestConfig.addRequest(RequestConfig.REQUEST_REFRESH);
        requestConfig.addRequest(RequestConfig.REQUEST_GRADES);
        requestConfig.addRequest(RequestConfig.REQUEST_TOKEN);

        networkController.dispatch(requestConfig);
    }

    /**
     *  Checks if the entered registration number is valid
     * @param registrationNumber - The number to be validated
     * @return true if valid, false otherwise
     */
    private boolean isValidRegNo(String registrationNumber) {
        if(registrationNumber.length() > 5){
            String patternAlnum = "^[A-Z0-9]*$";
            String patternNum = "^[0-9]*$";
            if(registrationNumber.matches(patternAlnum) && registrationNumber.substring(0,2).matches(patternNum)){
                Calendar calendar = Calendar.getInstance();
                String year = calendar.get(Calendar.YEAR)+"";
                year = year.substring(2);
                int iyear = Integer.parseInt(year);
                String yearReg = registrationNumber.substring(0, 2);
                int iyearReg = Integer.parseInt(yearReg);
                if(iyearReg <= iyear ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *  Toggles the visibility of the login form
     * @param isVisible true if form is to be made visible
     */
    private void showForm(boolean isVisible){
        loginButton.setEnabled(isVisible);
        exitButton.setEnabled(isVisible);
        if(isVisible){
            loginLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else{
            loginLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     *  Launches a SnackBar which notifies the user that there is no active internet connection
     */
    private void launchNetworkError() {
        Snackbar.make(findViewById(R.id.rlLoginLayout), "No Internet Connection", Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validateForm();
                    }
                }).show();
    }

    /**
     * Switches the focus of the edit text
     * @param toFocusOn view to be brought into focus
     * @param toRemoveFocusFrom1 view to be cleared from user's focus
     * @param toRemoveFocusFrom2 view to be cleared from user's focus
     */
    private void setFocusTo(EditText toFocusOn, EditText toRemoveFocusFrom1, EditText toRemoveFocusFrom2) {
        toRemoveFocusFrom1.clearFocus();
        toRemoveFocusFrom2.clearFocus();
        toFocusOn.requestFocus();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            launchDatePicker();
        }
    }

    /**
     * Launches the Date Picker
     */
    private void launchDatePicker() {
        DatePickerDialogFragment
                .createBuilder(this, getSupportFragmentManager())
                .setDate(new Date())
                .setPositiveButtonText(android.R.string.ok)
                .setNegativeButtonText(android.R.string.cancel)
                .setRequestCode(24)
                .show();
    }

    @Override
    public void onPositiveButtonClicked(int i, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.API_DATEOFBIRTH_FORMAT, Locale.US);
        etDOB.setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onNegativeButtonClicked(int i, Date date) {

    }

    /**
     * Checks the shared preferences if the user has already logged in
     * @return true is logged in already, false otherwise
     */
    private boolean loginCheck() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.FILENAME_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        String campus = sharedPreferences.getString(Constants.KEY_CAMPUS, null);
        String registerNumber = sharedPreferences.getString(Constants.KEY_REGISTERNUMBER, null);
        String dateOfBirth = sharedPreferences.getString(Constants.KEY_DATEOFBIRTH, null);
        String mobileNumber = sharedPreferences.getString(Constants.KEY_MOBILE, null);

        return (Constants.CAMPUS_VELLORE.equals(campus) && registerNumber != null && dateOfBirth != null && mobileNumber != null) || (Constants.CAMPUS_CHENNAI.equals(campus) && registerNumber != null && dateOfBirth != null);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        loginButton.setEnabled(true);

        switch (checkedId){
            case R.id.select_vellore:
                campus = Constants.CAMPUS_VELLORE;
                etPhoneHolder.setVisibility(View.VISIBLE);
                break;
            case R.id.select_chennai:
                campus = Constants.CAMPUS_CHENNAI;
                etPhoneHolder.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(loginDetailP2.getVisibility() == View.VISIBLE){
                switchToFormOne();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
