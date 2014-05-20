package com.karthikb351.vitinfo2.PrefsUI;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.DataHandler;

;

/**
 * Created by saurabh on 4/29/14.
 */
public class PrefsDob extends DialogPreference {

    private DatePicker datePicker;
    private DataHandler dat;

    public PrefsDob(Context context, AttributeSet attrs){
        super(context, attrs);
        setDialogLayoutResource(R.layout.prefdialog_datepicker);
        dat = new DataHandler(context);
        setPositiveButtonText("Ok");
        setNegativeButtonText("Cancel");
    }

    @Override
    public void onBindDialogView(View view){
        super.onBindDialogView(view);

        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        int[] dob =  dat.getDOB();
        if(dob[0]!= 0 && dob[1] != 0 && dob[2] != 0){
            datePicker.updateDate(dob[2], dob[1], dob[0]);
        }
        else{
            datePicker.updateDate(1990,0,1);
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if(positiveResult)
            dat.saveDob(new int[]{datePicker.getDayOfMonth(),datePicker.getMonth(),datePicker.getYear()});
    }

}
