package com.collegecode.VITacademics;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.Window;

import com.collegecode.adapters.SubjecDetailsPagerAdapter;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.Subject;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * Created by saurabh on 4/30/14.
 */
public class SubjectDetails extends ActionBarActivity {
    private String clsnbr;
    private ViewPager pager;
    private TitlePageIndicator titlePageIndicator;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_subjectdetails);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        Intent intent = getIntent();
        clsnbr = intent.getStringExtra("clsnbr");
        pager = (ViewPager) findViewById(R.id.timetable_pager);
        titlePageIndicator = (TitlePageIndicator) findViewById(R.id.timetable_pager_indicator);

        new Load_Data().execute();
    }

    private class Load_Data extends AsyncTask<Void,Void,Void> {
        private Subject sub;

        protected void onPreExecute(){
            sub = new Subject();
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sub = new DataHandler(context).getSubject(clsnbr);
            sub.putAttendanceDetails();
            sub.loadMarks();
            return null;
        }

        protected void onPostExecute(Void voids){
            pager.setAdapter(new SubjecDetailsPagerAdapter(context, sub, ((FragmentActivity)context).getSupportFragmentManager()));
            titlePageIndicator.setViewPager(pager);
            titlePageIndicator.setTextColor(getResources().getColor(R.color.Gray));
            setProgressBarIndeterminateVisibility(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
