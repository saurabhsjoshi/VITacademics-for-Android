package com.collegecode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.collegecode.VITacademics.Home;
import com.collegecode.VITacademics.R;

/**
 * Created by saurabh on 5/15/14.
 */
public class NFCAddFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nfc,container, false);
        setHasOptionsMenu(true);
        WebView view = (WebView) v.findViewById(R.id.img_gif);
        getActivity().setTitle("Add Friend");
        ((TextView) v.findViewById(R.id.lbl_instructions)).setText("Tap your friends NFC enabled device.\n\nMake sure your friend is on the share TimeTable screen.");
        view.loadDataWithBaseURL("file:///android_asset/","<html><center><img src=\"nfc_animation.gif\"></html>","text/html","utf-8","");
        return v;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_fb_login, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_fb_cancel:
                ((Home) getActivity()).disableNdefExchangeMode();
                getActivity().setTitle("Friends");
                ((Home) getActivity()).selectItem_Async(3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
