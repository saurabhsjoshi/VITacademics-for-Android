package com.collegecode.VITacademics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.collegecode.fragments.WelcomeScreens.Screen1;

/**
 * Created by saurabh on 4/26/14.
 */
public class NewUser extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newuser);
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

            default:
                frag = new Screen1();
                break;
        }
        transaction.replace(R.id.content_area, frag).commitAllowingStateLoss();
    }
}
