package com.karthikb351.vitinfo2.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.karthikb351.vitinfo2.Application;
import com.karthikb351.vitinfo2.Home;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapters.NotificationListAdapter;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.PushMessage;
import com.karthikb351.vitinfo2.objects.RecyclerViewOnClickListener;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by saurabh on 5/15/14.
 */
public class NotificationFragment extends Fragment {
    private RecyclerView listView;
    //private TextView lbl_latest_title;
    private TextView lbl_latest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Tracker t = ((Application) getActivity().getApplication()).getTracker(Application.TrackerName.GLOBAL_TRACKER);
        t.setScreenName("Notification Fragment");
        t.send(new HitBuilders.AppViewBuilder().build());

        View v = inflater.inflate(R.layout.fragment_notification,container, false);
        listView = (RecyclerView) v.findViewById(R.id.enhanced_list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lbl_latest = (TextView) v.findViewById(R.id.txt_latest);
        //lbl_latest_title = (TextView) v.findViewById(R.id.lbl_latest_title);
        new Load_Data().execute();
        return v;
    }

    private class Load_Data extends AsyncTask<Void,Void,Void> {
        private ArrayList<PushMessage> msgs;

        protected void onPreExecute() {
            msgs = new ArrayList<PushMessage>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            msgs = new DataHandler(getActivity()).getAllPushMessage();
            return null;
        }

        private void showDeleteDialog(final PushMessage m){
            final Handler handler = new Handler();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Display Name");
            builder.setMessage("Delete push message?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            new DataHandler(getActivity()).deletePushMessage(m);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Push Message has been deleted.", Toast.LENGTH_SHORT).show();
                                    ((Home) getActivity()).selectItem_Async(9);
                                }
                            });
                        }};
                    new Thread(runnable).start();
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

        protected void onPostExecute(Void voids) {
            try {
                String msg = new JSONObject(new DataHandler(getActivity()).getServerStatus()).getString("msg_content");
                NotificationListAdapter nla = new NotificationListAdapter(msgs, new RecyclerViewOnClickListener() {
                    @Override
                    public void onClick(final Object obj, boolean isLongPress) {
                        if(!isLongPress)
                            showDeleteDialog((PushMessage) obj);
                    }
                });
                listView.setAdapter(nla);
                listView.setLayoutManager(new LinearLayoutManager(getActivity()));
                listView.setItemAnimator(new DefaultItemAnimator());
                lbl_latest.setText(msg);
            } catch (Exception ignore) {
            }
        }
    }
}
