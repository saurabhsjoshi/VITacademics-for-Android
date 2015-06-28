package com.karthikb351.vitinfo2.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import com.karthikb351.vitinfo2.R;

/**
 * Created by gaurav on 28/6/15.
 */
public class Splash extends Activity {

    SoundPool sp;
    int sound=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        sp=new SoundPool(5, AudioManager.STREAM_MUSIC,00);
        //sound==sp.load(this,R.raw.something);
        Thread timer= new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent openDa = new Intent("com.karthikb351.vitinfo2.activity.LoginActivity");
                    startActivity(openDa);
                }
            }
        };
        timer.start();
    }

    protected void onPause(){
        super.onPause();
        sp.release();
        finish();
    }
}
