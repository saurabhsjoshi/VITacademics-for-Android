package com.collegecode.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.collegecode.VITacademics.Home;
import com.collegecode.VITacademics.R;
import com.collegecode.VITacademics.VITxAPI;
import com.collegecode.adapters.FreindsListAdapter;
import com.collegecode.objects.BarCodeScanner.IntentIntegrator;
import com.collegecode.objects.BarCodeScanner.ZXingLibConfig;
import com.collegecode.objects.DataHandler;
import com.collegecode.objects.Friend;
import com.collegecode.objects.OnTaskComplete;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;

/**
 * Created by saurabh on 5/11/14.
 */
public class FriendsFragment extends Fragment{
    private DataHandler dat;
    private ZXingLibConfig zxingLibConfig;
    private ListView listView;
    private PullToRefreshLayout mPullToRefreshLayout;
    private TextView lbl_empty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends,container, false);

        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);
        listView = (ListView) v.findViewById(R.id.list);
        lbl_empty = (TextView) v.findViewById(R.id.lbl_empty);

        ActionBarPullToRefresh.from(getActivity()).setup(mPullToRefreshLayout);

        setHasOptionsMenu(true);
        dat = new DataHandler(getActivity());
        zxingLibConfig = new ZXingLibConfig();
        zxingLibConfig.useFrontLight = true;
        new Load_Data().execute();
        new Refresh_Data().execute();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_fragment_friends, menu);

        if(dat.isFacebookLogin())
            menu.removeItem(R.id.menu_fb_login);
    }

    private ProgressDialog diag;

    private void showShareAlert(){
        diag = new ProgressDialog(getActivity());
        diag.setMessage("Loading");
        diag.setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Share TimeTable")
                .setItems(R.array.freinds_share, new DialogInterface.OnClickListener() {

                    public void onClick(final DialogInterface dialog, final int which) {
                        diag.show();
                        VITxAPI api = new VITxAPI(getActivity(), new OnTaskComplete() {
                            @Override
                            public void onTaskCompleted(Exception e, Object result) {
                                if(e != null)
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                else{
                                    Home h = (Home) getActivity();
                                    h.token = (String) result;

                                    //QR CODE
                                    if(which == 0)
                                        h.selectItem_Async(6);
                                    //NFC
                                    else
                                        ((Home) getActivity()).enableNdefExchangeMode();
                                }
                                diag.dismiss();
                            }});
                        api.getToken();
                    }});
        builder.show();
    }

    public String TOKEN = "";
    ProgressDialog pdiag;

    public void addFriendToList(){
        pdiag = new ProgressDialog(getActivity());
        pdiag.setMessage("Adding Friend");
        pdiag.setTitle("Please wait");
        pdiag.setCancelable(false);
        pdiag.show();
        VITxAPI api = new VITxAPI(getActivity(), new OnTaskComplete() {
            @Override
            public void onTaskCompleted(Exception e, Object result) {
                if(e == null){
                    Toast.makeText(getActivity(), "Friend Added!", Toast.LENGTH_SHORT).show();
                    ((Home) getActivity()).selectItem_Async(3);
                }
                else
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                pdiag.dismiss();
            }
        });
        api.Token = TOKEN;

        api.submitToken();
    }



    private void showAddAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Friend")
                .setItems(R.array.freinds_add, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int which) {
                        //Scan Barcode
                        if(which == 1)
                            IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
                        //Enter PIN
                        else if(which == 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Enter PIN");
                            final EditText input = new EditText(getActivity());
                            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                            builder.setView(input);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TOKEN = input.getText().toString();
                                    addFriendToList();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }

                    }
                });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_fb_login:
                ((Home) getActivity()).selectItem_Async(5);
                return true;
            case R.id.menu_add_person:
                showAddAlert();
                return true;
            case R.id.menu_share_tt:
                showShareAlert();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class Load_Data extends AsyncTask<Void,Void,Void> {
        private ArrayList<Friend> friends;

        protected void onPreExecute(){
            friends = new ArrayList<Friend>();
            mPullToRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            friends = new DataHandler(getActivity()).getFreinds();
            for(int i = 0; i < friends.size(); i++)
            {
                if(friends.get(i).isFb){
                    File file = new File(getActivity().getCacheDir().getPath() + "/" + friends.get(i).fbId + ".jpg");
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        friends.get(i).img_profile = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                    }catch (Exception e){e.printStackTrace();}
                }
            }
            return null;
        }

        protected void onPostExecute(Void voids){
            listView.setAdapter(new FreindsListAdapter(getActivity(), friends));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });
            mPullToRefreshLayout.setRefreshComplete();
            if(friends.size() == 0)
                lbl_empty.setVisibility(View.VISIBLE);
            else
                lbl_empty.setVisibility(View.GONE);
        }



    }

    private class Refresh_Data extends AsyncTask<Void,Void,Void>{

        private ArrayList<Friend> friends;

        protected void onPreExecute(){
            friends = new ArrayList<Friend>();
        }

        private void downloadProfileImage(final ArrayList<Friend> friends){
            for(int i = 0; i < friends.size(); i++){
                String fbId;
                fbId = friends.get(i).fbId;
                final int j = i;
                if(friends.get(i).isFb){
                    Ion.with(getActivity())
                            .load("http://graph.facebook.com/" + fbId + "/picture?type=large")
                            .write(new File(getActivity().getCacheDir().getPath() + "/" + fbId + ".jpg"))
                            .setCallback(new FutureCallback<File>() {
                                @Override
                                public void onCompleted(Exception e, File file) {
                                    if (e == null) {
                                        System.out.println("GOT PROFILE PICTURE!: " + file.getPath() );
                                        BitmapFactory.Options options = new BitmapFactory.Options();
                                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                                        try {
                                            friends.get(j).img_profile = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                                        } catch (FileNotFoundException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            });
                }
            }
        }
        private boolean needSaving = false;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                friends = new DataHandler(getActivity()).getFreinds();
                for(int i = 0; i < friends.size(); i++){
                    if(!friends.get(i).isFb){
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        ParseUser u = (query.whereEqualTo("username","11BEC0262")).getFirst();
                        if(u.get("isSignedIn").equals("true")){
                            friends.get(i).isFb = true;
                            friends.get(i).fbId = u.get("facebookID").toString();
                            needSaving = true;
                        }
                    }
                }
                if(needSaving)
                    dat.saveFriends(friends);
            }catch (Exception e){e.printStackTrace();}
            downloadProfileImage(friends);
            return null;
        }

        protected void onPostExecute(Void voids){
            if(needSaving){
                Toast.makeText(getActivity(), "Friends list was updated.", Toast.LENGTH_SHORT).show();
                ((Home) getActivity()).selectItem_Async(3);
            }
            if(friends.size() == 0)
                lbl_empty.setVisibility(View.VISIBLE);
            else
                lbl_empty.setVisibility(View.GONE);
        }
    }

}
