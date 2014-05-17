package com.collegecode.VITacademics;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.collegecode.objects.DataHandler;
import com.collegecode.objects.OnParseFinished;
import com.parse.ParseException;

/**
 * Created by saurabh on 4/29/14.
 */
@SuppressWarnings("ALL")
public class Settings extends PreferenceActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        addPreferencesFromResource(R.xml.activity_settings);

        ((Preference) findPreference("pref_reset")).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new DataHandler(context).setNewUser(true);
                                DataHandler.DELETE_ALL_DATA(context);
                                startActivity(new Intent(context, Home.class));
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to reset VITacademics?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return false;
            }
        });

        ((Preference) findPreference("pref_logout")).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ParseAPI api = new ParseAPI(context);
                                api.logoutUser(new OnParseFinished() {
                                    @Override
                                    public void onTaskCompleted(ParseException e) {
                                        if(e!=null)
                                            e.printStackTrace();
                                        new DataHandler(context).setFbLogin(false);
                                        Toast.makeText(context, "Facebook Logout Complete!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Toast.makeText(context, "You will be logged out shortly!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to logout of Facebook?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return false;
            }
        });

        try {
            final String versionName = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;

            ((Preference) findPreference("pref_build")).setSummary(versionName);
            ((Preference) findPreference("pref_feedback")).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "battlex94@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "VITacademics " + versionName + " feedback");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Feedback/Bug: ");
                    startActivity(Intent.createChooser(emailIntent, "Send Feedback"));
                    return false;
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "Refresh app if credentials are changed!",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
            Toast.makeText(getApplicationContext(), "Refresh app if credentials are changed!",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}