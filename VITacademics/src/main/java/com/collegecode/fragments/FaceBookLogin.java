package com.collegecode.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.collegecode.VITacademics.Home;
import com.collegecode.VITacademics.R;

/**
 * Created by saurabh on 5/11/14.
 */
public class FaceBookLogin extends Fragment{
    ImageButton btn_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_facebook_login,container, false);
        btn_login = (ImageButton) v.findViewById(R.id.btn_login);
        
        return v;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_fb_login, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_fb_cancel:
                ((Home) getActivity()).selectItem_Async(3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
