package com.karthikb351.vitinfo2.PrefsUI;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.objects.DataHandler;

;

/**
 * Created by saurabh on 4/30/14.
 */
public class PrefsCampus extends DialogPreference {
    RadioButton radVel, radChen;
    DataHandler dat;

    public PrefsCampus(Context context, AttributeSet attrs){
        super(context, attrs);
        setDialogLayoutResource(R.layout.prefdialog_campus);
        dat = new DataHandler(context);
    }

    @Override
    public void onBindDialogView(View view){
        super.onBindDialogView(view);

        radVel = (RadioButton) view.findViewById(R.id.radioVel);
        radChen = (RadioButton) view.findViewById(R.id.radioChen);

        if(dat.isVellore())
            radVel.setChecked(true);
        else
            radChen.setChecked(true);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if(positiveResult){
            if(radVel.isChecked())
                dat.saveCampus(true);
            else
                dat.saveCampus(false);
        }
    }

}
