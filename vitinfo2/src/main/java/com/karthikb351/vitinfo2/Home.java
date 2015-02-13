package com.karthikb351.vitinfo2;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.karthikb351.vitinfo2.adapters.DrawerListAdapter;
import com.karthikb351.vitinfo2.api.Objects.VITxApi;
import com.karthikb351.vitinfo2.fragments.CoursesFragment;
import com.karthikb351.vitinfo2.fragments.FriendsFragment;
import com.karthikb351.vitinfo2.fragments.FriendsFragments.FaceBookLogin;
import com.karthikb351.vitinfo2.fragments.FriendsFragments.NFCAddFragment;
import com.karthikb351.vitinfo2.fragments.FriendsFragments.NFCFragment;
import com.karthikb351.vitinfo2.fragments.FriendsFragments.QRCodeFragment;
import com.karthikb351.vitinfo2.fragments.FullTimeTableFragment;
import com.karthikb351.vitinfo2.fragments.MapFragment;
import com.karthikb351.vitinfo2.fragments.NotificationFragment;
import com.karthikb351.vitinfo2.fragments.NowFragment;
import com.karthikb351.vitinfo2.fragments.SettingsFragment;
import com.karthikb351.vitinfo2.objects.BarCodeScanner.IntentIntegrator;
import com.karthikb351.vitinfo2.objects.BarCodeScanner.IntentResult;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.parse.ParseFacebookUtils;

import org.json.JSONObject;

/**
 * Created by saurabh on 4/22/14.
 */

public class Home extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    //Initialize drawer tabs
    private String[] titles = { "Today", "Courses", "Timetable","Friends", "Notifications", "Campus Map", "Settings"};
    private int[] imgs = new int[]{ R.drawable.ic_action_about, R.drawable.ic_action_sort_by_size, R.drawable.ic_action_event, R.drawable.ic_action_group, R.drawable.ic_action_unread,R.drawable.ic_action_place, R.drawable.ic_action_settings};

    //Drawer ListView
    public DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DrawerListAdapter mDrawerListAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    //Check if this activity is alive still
    private boolean isActive = true;

    //Titles
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    public boolean hasNFC = false;

    private NfcAdapter mNfcAdapter;
    PendingIntent mNfcPendingIntent;
    IntentFilter[] mNdefExchangeFilters;
    private boolean mResumed = false;
    private boolean mWriteMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataHandler dat = DataHandler.getInstance(this);
        new ParseAPI(this).parseInit();

        //Check if newUser
        if(dat.isNewUser()) {
            isActive = false;
            startActivity(new Intent(this, NewUser.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }

        if(!dat.isHeroku()){
            Toast.makeText(getApplicationContext(), "We have switched to new server. Please login again!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, NewUser.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK));
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }

        if(BuildConfig.DEBUG) {
            GoogleAnalytics googleAnalytics = GoogleAnalytics.getInstance(getApplicationContext());
            googleAnalytics.setAppOptOut(true);
        }



        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, getToolbar(), R.string.drawer_open, R.string.drawer_close
        );

        //Enable actionbar support for the drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerListAdapter = new DrawerListAdapter(this,titles,imgs);
        // Set the adapter for the list view
        mDrawerList.setAdapter(mDrawerListAdapter);

        //Set listner for actionbar drawer click
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        selectItem(dat.getDefUi());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            if(mNfcAdapter!=null){
                hasNFC = true;
                mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, ((Object) this).getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
                // Intent filters for exchanging over p2p.
                IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
                try {
                    ndefDetected.addDataType("text/plain");
                } catch (IntentFilter.MalformedMimeTypeException e) { }
                mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
            }
        }

        VITxApi.getInstance(this).saveServerStatus(new VITxApi.onTaskCompleted() {
            @Override
            public void onCompleted(Object result, Exception e) {
                boolean res = (Boolean) result;
                if(res && isActive)
                    Toast.makeText(getApplicationContext(), "New Notificiation!", Toast.LENGTH_SHORT).show();
            }
        });

        if(getIntent().getExtras() != null){
            try {
                JSONObject data = new JSONObject(getIntent().getExtras().getString("com.parse.Data"));
                Log.i("PUSH SERVICE", data.toString());
                selectItem(9);
            }catch (Exception ignore){}
        }

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            // Highlight the selected item, update the title, and close the drawer
            mDrawerList.setItemChecked(position, true);

            if(position != 6){
                setTitle(titles[position]);
                mDrawerListAdapter.setSelectedItem(position);
            }
            mDrawerLayout.closeDrawer(mDrawerList);
            //Use Handler to avoid lag in the transaction
            if(position == 4)
                selectItem(9);
            else if(position == 6)
                selectItem(4);
            else if(position == 5)
                selectItem(10);
            else
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //For QR and NFC
    public String token = "";

    /** Swaps fragments in the main content view */
    public void selectItem_Async(int position) {

        Fragment fragment;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch(position){
            case 0:
                fragment = new NowFragment();
                break;
            case 1:
                fragment = new CoursesFragment();
                break;
            case 2:
                fragment = new FullTimeTableFragment();
                break;
            case 3:
                fragment = new FriendsFragment();
                break;
            case 4:
                startActivity(new Intent(this, Settings.class));
                fragment = new SettingsFragment();
                break;
            case 5:
                fragment = new FaceBookLogin();
                break;
            case 6:
                fragment = new QRCodeFragment();
                break;
            case 7:
                fragment = new NFCFragment();
                break;
            case 8:
                fragment = new NFCAddFragment();
                break;
            case 9:
                fragment = new NotificationFragment();
                break;
            case 10:
                fragment = new MapFragment();
                break;
            default:
                fragment = new NowFragment();
        }
        if(position > 4 && position != 9){
            ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        }
        if(position != 4){
            ft.replace(R.id.content_frame, fragment);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        //mDrawerToggle.syncState();
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

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                        resultCode, data);
                if (scanResult.getContents() != null) {
                    final String result = scanResult.getContents();
                    FriendsFragment f = (FriendsFragment)getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    f.TOKEN = result;
                    f.addFriendToList();
                    System.out.println(result);
                }
                break;
            default:
                ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
        }

    }

    NdefMessage[] getNdefMessages(Intent intent) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            // Parse the intent
            NdefMessage[] msgs = null;
            String action = intent.getAction();
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                if (rawMsgs != null) {
                    msgs = new NdefMessage[rawMsgs.length];
                    for (int i = 0; i < rawMsgs.length; i++) {
                        msgs[i] = (NdefMessage) rawMsgs[i];
                    }
                } else {
                    // Unknown tag type
                    byte[] empty = new byte[] {};
                    NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                    NdefMessage msg = new NdefMessage(new NdefRecord[] {
                            record
                    });
                    msgs = new NdefMessage[] {
                            msg
                    };
                }
            } else {
                Log.d("ERROR", "Unknown intent.");
                finish();
            }
            return msgs;
        }
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // NDEF exchange mode
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            try{
                if (!mWriteMode && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
                    NdefMessage[] msgs = getNdefMessages(intent);
                    final String result = new String(msgs[0].getRecords()[0].getPayload());
                    NFCAddFragment f = (NFCAddFragment)getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    f.TOKEN = result;
                    f.addFriendToList();
                }

            }catch (Exception ignore){}
        }
    }

    private NdefMessage getNoteAsNdef() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            byte[] textBytes = token.getBytes();
            NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(),new byte[] {}, textBytes);
            //selectItem(3);
            return new NdefMessage(new NdefRecord[] {textRecord});
        }
        return null;
    }

    public void disableNdefExchangeMode() {
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
                //mNfcAdapter.disableForegroundNdefPush(this);
                //mNfcAdapter.disableForegroundDispatch(this);
            }
        }catch (Exception ignore){}

    }

    public void enableNdefExchangeMode() {
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
                mNfcAdapter.enableForegroundNdefPush(Home.this, getNoteAsNdef());
                mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
            }
        }catch (Exception ignore){}
    }

    @Override
    protected void onPause() {
        super.onPause();
        mResumed = false;
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
                mNfcAdapter.disableForegroundNdefPush(this);
            }
        }catch (Exception ignore){}
    }

    @Override
    protected void onResume() {
        super.onResume();
        mResumed = true;
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
                if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
                    NdefMessage[] messages = getNdefMessages(getIntent());
                    byte[] payload = messages[0].getRecords()[0].getPayload();
                    Log.i("com.karthikb351.VITacademics", new String(payload));
                    setIntent(new Intent()); // Consume this intent.
                }
                enableNdefExchangeMode();
            }

        }catch (Exception ignore){}
    }
}
