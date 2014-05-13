package com.collegecode.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends,container, false);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);
        listView = (ListView) v.findViewById(R.id.list);

        ActionBarPullToRefresh.from(getActivity()).setup(mPullToRefreshLayout);

        setHasOptionsMenu(true);
        dat = new DataHandler(getActivity());
        zxingLibConfig = new ZXingLibConfig();
        zxingLibConfig.useFrontLight = true;
        new Load_Data().execute();

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
                                        Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
                                }
                                diag.dismiss();
                            }});
                        api.getToken();
                    }});

        builder.show();
    }

    private void showAddAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Friend")
                .setItems(R.array.freinds_add, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int which) {
                        if(which == 1){
                            IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
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
            Friend f = new Friend();
            f.title = "Saurabh Joshi";
            friends = new DataHandler(getActivity()).getFreinds();
            friends.add(f);
            friends.add(f);
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
        }

    }


}
