/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
 * Copyright (C) 2015  Gaurav Agerwala <gauravagerwala@gmail.com>
 * Copyright (C) 2015  Pulkit Juneja <pulkit.16296@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.karthikb351.vitinfo2.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.karthikb351.vitinfo2.Constants;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.api.event.MessageEvent;
import com.karthikb351.vitinfo2.api.event.SuccessEvent;
import com.karthikb351.vitinfo2.api.utilities.Network;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.greenrobot.event.EventBus;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private int refreshStatus;

    private Network network;

    private EditText editTextRegisterNumber, editTextDateOfBirth, editTextMobileNumber;
    private Button buttonLogin;
    private RadioGroup radioGroupCampus;
    private RadioButton radioVelloreCampus, radioChennaiCampus;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeLayouts();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:
                refreshStatus = 0;
                // TODO get values and fill in below code
                // new Network(LoginActivity.this, ).refreshAll();
                break;
            case R.id.etDob:
                showDatePicker(v);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeLayouts() {
        editTextRegisterNumber = (EditText) findViewById(R.id.etRegNo);
        editTextDateOfBirth = (EditText) findViewById(R.id.etDob);
        editTextMobileNumber = (EditText) findViewById(R.id.etPhone);
        radioGroupCampus = (RadioGroup) findViewById(R.id.rgCampus);
        radioVelloreCampus = (RadioButton) findViewById(R.id.rbVellore);
        radioChennaiCampus = (RadioButton) findViewById(R.id.rbChennai);
        buttonLogin = (Button) findViewById(R.id.bLogin);
        buttonLogin.setOnClickListener(this);
        editTextDateOfBirth.setOnClickListener(this);

        // To disable EditText editTextMobileNumber for Chennai Campus
        radioGroupCampus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rbChennai:
                        editTextMobileNumber.setFocusable(false);
                        break;

                }
            }
        });

        calendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                editTextDateOfBirth.setText(sdf.format(calendar.getTime()));
            }
        };
    }

    public void showDatePicker(View view) {
        new DatePickerDialog(this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onEventMainThread(SuccessEvent successEvent) {
        refreshStatus = refreshStatus + successEvent.type;
        if (refreshStatus == Constants.EVENT_CODE_REFRESH_ALL) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    public void onEventMainThread(MessageEvent messageEvent) {
        Toast.makeText(LoginActivity.this, messageEvent.message, Toast.LENGTH_SHORT).show();
    }
}
