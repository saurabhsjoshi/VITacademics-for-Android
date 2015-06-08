/*
 * VITacademics
 * Copyright (C) 2015  Aneesh Neelam <neelam.aneesh@gmail.com>
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

import com.karthikb351.vitinfo2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText reg,dob,phone,otp;
    Button login;
    Calendar calendar = Calendar.getInstance();
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        reg=(EditText)findViewById(R.id.etRegNo);
        dob=(EditText)findViewById(R.id.etDob);
        phone=(EditText)findViewById(R.id.etPhone);
        otp=(EditText)findViewById(R.id.etOTP);
        login=(Button)findViewById(R.id.bLogin);
        login.setOnClickListener(this);
        dob.setOnClickListener(this);
    }

   DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calendar.set(i,i1,i2);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            dob.setText(sdf.format(calendar.getTime()));
        }
    };
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogin:
                /* TODO: @aneesh Create the thread to verify details from backend here */
                Intent i= new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showDatePicker(View view)
    {
        new DatePickerDialog(this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

}
