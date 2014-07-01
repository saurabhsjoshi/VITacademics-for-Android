package com.karthikb351.vitinfo2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.karthikb351.vitinfo2.Home;
import com.karthikb351.vitinfo2.R;

/**
 * Created by saurabh on 7/1/14.
 */
public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map,container, false);
        setHasOptionsMenu(true);

        ((Home) getActivity()).setSupportProgressBarIndeterminateVisibility(true);

        WebView view = (WebView) v.findViewById(R.id.img_map);
        view.setVisibility(View.GONE);


        view.getSettings().setBuiltInZoomControls(true);

        view.getSettings().setLoadWithOverviewMode(true);

        view.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.setVisibility(View.VISIBLE);
                ((Home) getActivity()).setSupportProgressBarIndeterminateVisibility(false);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                ((Home) getActivity()).setSupportProgressBarIndeterminateVisibility(false);
            }
        });

        view.loadUrl("file:///android_asset/vit_map.jpg");

        return v;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_gmaps:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/place/Vellore+Institute+of+Technology/@12.971749,79.159694,17z/data=!3m1!4b1!4m2!3m1!1s0x3bad479f0ccbe067:0xfef222e5f36ecdeb"));
                startActivity(intent);
                break;
        }
        return true;
    }
}
