package com.collegecode.VITacademics;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by saurabh on 4/29/14.
 */
@SuppressWarnings("ALL")
public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        addPreferencesFromResource(R.xml.activity_settings);

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
                Toast.makeText(getApplicationContext(), "Refresh app if settings are changed!",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
            Toast.makeText(getApplicationContext(), "Refresh app if settings are changed!",Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}