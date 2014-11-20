package com.karthikb351.vitinfo2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by saurabh on 5/25/14.
 */
public class TeamScreen extends ActionBarActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Tracker t = ((Application) getApplication()).getTracker(Application.TrackerName.GLOBAL_TRACKER);
        t.setScreenName("TeamScreen");
        t.send(new HitBuilders.AppViewBuilder().build());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_team_screen);

        setStuff((TextView) findViewById(R.id.lbl_sau));
        setStuff((TextView) findViewById(R.id.lbl_sid));
        setStuff((TextView) findViewById(R.id.lbl_kis));
        setStuff((TextView) findViewById(R.id.lbl_vib));
        setStuff((TextView) findViewById(R.id.lbl_kar));

        setStuff((TextView) findViewById(R.id.lbl_sau_sub));
        setStuff((TextView) findViewById(R.id.lbl_sid_sub));
        setStuff((TextView) findViewById(R.id.lbl_kis_sub));
        setStuff((TextView) findViewById(R.id.lbl_vib_sub));
        setStuff((TextView) findViewById(R.id.lbl_kar_sub));

    }

    private void setStuff(TextView textView){
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);

        switch (view.getId()){
            case R.id.lbl_sau:
                i.setData(Uri.parse("https://github.com/saurabhsjoshi"));
                break;

            case R.id.lbl_sid:
                i.setData(Uri.parse("https://github.com/biocross"));
                break;

            case R.id.lbl_kis:
                i.setData(Uri.parse("https://github.com/kishore-narendran"));
                break;

            case R.id.lbl_vib:
                i.setData(Uri.parse("https://github.com/vibhorvarshneya"));
                break;

            case R.id.lbl_kar:
                i.setData(Uri.parse("https://github.com/karthikb351"));
                break;

            default:
                return;
        }

        startActivity(i);
    }
}
