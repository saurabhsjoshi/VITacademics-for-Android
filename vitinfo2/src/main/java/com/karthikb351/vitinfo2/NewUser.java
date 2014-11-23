package com.karthikb351.vitinfo2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.karthikb351.vitinfo2.fragments.WelcomeScreens.Screen1;
import com.karthikb351.vitinfo2.fragments.WelcomeScreens.Screen2;
import com.karthikb351.vitinfo2.fragments.WelcomeScreens.Screen3;
import com.karthikb351.vitinfo2.objects.DataHandler;

;

/**
 * Created by saurabh on 4/26/14.
 */
public class NewUser extends BaseActivity {
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_newuser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataHandler.DELETE_ALL_DATA(this);
        changeScreen(0);
    }

    public void changeScreen(int num){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        Fragment frag;

        switch (num){
            case 0:
                frag = new Screen1();
                break;
            case 1:
                frag = new Screen2();
                break;
            case 2:
                frag = new Screen3();
                break;
            default:
                frag = new Screen1();
                break;
        }
        transaction.replace(R.id.content_area, frag).commitAllowingStateLoss();
    }

    //Radio Button in Settings Fragment Callback
    public void onRadioButtonClicked(View view) {
        DataHandler dat = DataHandler.getInstance(this);
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioChen:
                if (checked)
                    dat.saveCampus(false);
                break;
            case R.id.radioVel:
                if (checked)
                    dat.saveCampus(true);
                break;
        }
    }
}
