package com.collegecode.VITacademics;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.collegecode.adapters.DrawerListAdapter;
import com.collegecode.fragments.NowFragment;
import com.collegecode.fragments.SettingsFragment;
import com.collegecode.fragments.WelcomeScreens.Screen1;
import com.collegecode.objects.DataHandler;

/**
 * Created by saurabh on 4/22/14.
 */

public class Home extends ActionBarActivity {

    //Initialize drawer tabs
    private String[] titles = { "Today", "Courses", "Timetable","Friends", "Settings"};
    private String[] subtitle = { "Realtime Overview", "Attendance|Marks|More", "Day|Week", "Coming Soon!", "Change credentials"};
    private int[] imgs = new int[]{ R.drawable.now, R.drawable.ic_action_sort_by_size, R.drawable.timetable, R.drawable.friends, R.drawable.settings};

    //Drawer ListView
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //Check if this activity is alive still
    private boolean isActive = true;

    //Titles
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataHandler dat = new DataHandler(this);

        //Check if newUser
        if(dat.isNewUser()) {
            isActive = false;
            startActivity(new Intent(this, NewUser.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }

        setContentView(R.layout.activity_home);

        mTitle = mDrawerTitle = getTitle();


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close)
        {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        //Enable actionbar support for the drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerListAdapter(this, titles, subtitle, imgs));
        //Set listner for actionbar drawer click
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        selectItem(0);



    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(titles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            //Use Handler to avoid lag in the transaction
            selectItem(position);
        }
    }

    private void selectItem(final int position){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isActive)
                    selectItem_Async(position);
            }
        }, 250);
    }



    //Radio Button in Settings Fragment Callback
    public void onRadioButtonClicked(View view) {
        DataHandler dat = new DataHandler(this);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /** Swaps fragments in the main content view */
    private void selectItem_Async(int position) {
        // Create a new fragment and specify the planet to show based on position
        //Bundle args = new Bundle();
        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
       // fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        Fragment fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(position){
            case 0:
                fragment = new NowFragment();
                break;
            case 1:
                fragment = new Screen1();
                break;
            case 4:
                fragment = new SettingsFragment();
                break;

            default:
                fragment = new NowFragment();
        }

        ft.replace(R.id.content_frame, fragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
