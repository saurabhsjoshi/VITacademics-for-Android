package com.collegecode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.collegecode.VITacademics.Home;
import com.collegecode.VITacademics.R;
import com.collegecode.objects.BarCodeScanner.IntentIntegrator;
import com.collegecode.objects.BarCodeScanner.ZXingLibConfig;
import com.collegecode.objects.DataHandler;

/**
 * Created by saurabh on 5/11/14.
 */
public class FriendsFragment extends Fragment{
    private DataHandler dat;
    private ZXingLibConfig zxingLibConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends,container, false);
        setHasOptionsMenu(true);
        dat = new DataHandler(getActivity());
        zxingLibConfig = new ZXingLibConfig();
        zxingLibConfig.useFrontLight = true;
        return v;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_fragment_friends, menu);

        if(dat.isFacebookLogin())
            menu.removeItem(R.id.menu_fb_login);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_fb_login:
                ((Home) getActivity()).selectItem_Async(5);
                return true;
            case R.id.menu_add_person:
                IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
