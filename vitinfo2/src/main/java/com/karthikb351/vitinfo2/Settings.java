package com.karthikb351.vitinfo2;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.OnParseFinished;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TTSlot;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TimeTable;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

;

/**
 * Created by saurabh on 4/29/14.
 */
@SuppressWarnings("ALL")
public class Settings extends PreferenceActivity {
    Context context;
    SharedPreferences sp;

    public void cancelAllAlarms(){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent updateServiceIntent = new Intent(context, AlarmReceiver.class);

        // Cancel alarms
        try {
            Gson gson = new Gson();
            ArrayList<Integer> ids = gson.fromJson(sp.getString("NOTIFICATION_IDS", ""), new TypeToken<List<Integer>>(){}.getType());
            for(int i = 0; i < ids.size(); i++){
                PendingIntent pendingUpdateIntent = PendingIntent.getBroadcast(context, ids.get(i), updateServiceIntent, 0);
                alarmManager.cancel(pendingUpdateIntent);
            }

        } catch (Exception e) {e.printStackTrace();}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
                if(key.equals("defTimer")){
                    int choice = Integer.parseInt(sharedPreferences.getString("defTimer","0"));
                    int toSub = 0;
                    switch (choice){
                        case 0:
                            cancelAllAlarms();
                            break;
                        case 1:
                            toSub = -10;break;
                        case 2:
                            toSub = -20;break;
                        case 3:
                            toSub = -30;break;
                    }

                    if(choice != 0){
                        cancelAllAlarms();
                        ArrayList<Integer> ids = new ArrayList<Integer>();

                        for(int i = Calendar.MONDAY ; i <= Calendar.FRIDAY; i++){
                            ArrayList<TTSlot> slots = new ArrayList<TTSlot>();
                            slots = new TimeTable(getApplicationContext()).getTT(i);

                            for(int j = 0; j < slots.size(); j++){
                                Gson gson = new Gson();
                                PendingIntent mAlarmSender;
                                Intent intent = new Intent(context, AlarmReceiver.class);
                                intent.putExtra("SLOT", gson.toJson(slots.get(j)));
                                final int _id = (int) System.currentTimeMillis();
                                ids.add(_id);

                                mAlarmSender = PendingIntent.getBroadcast(context, _id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                slots.get(j).frm_time.add(Calendar.MINUTE, toSub);
                                slots.get(j).frm_time.set(Calendar.SECOND, 0);
                                slots.get(j).frm_time.add(Calendar.WEEK_OF_YEAR, 1);
                                slots.get(j).frm_time.set(Calendar.DAY_OF_WEEK, i);
                                AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

                                am.setRepeating(AlarmManager.RTC_WAKEUP, slots.get(j).frm_time.getTimeInMillis(), am.INTERVAL_DAY * 7, mAlarmSender);
                            }
                        }
                        Toast.makeText(context, "Notifications will be displayed from next week!", Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();
                        sharedPreferences.edit().putString("NOTIFICATION_IDS", gson.toJson(ids)).commit();
                    }
                }
            }
        });

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
                                DataHandler.getInstance(context).setNewUser(true);
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
                                        DataHandler.getInstance(context).setFbLogin(false);
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