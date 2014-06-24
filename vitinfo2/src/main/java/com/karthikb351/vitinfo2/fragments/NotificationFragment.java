package com.karthikb351.vitinfo2.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.karthikb351.vitinfo2.Application;
import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapters.NotificationListAdapter;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.PushMessage;

import org.json.JSONObject;

import java.util.ArrayList;

import de.timroes.android.listview.EnhancedListView;

;

/**
 * Created by saurabh on 5/15/14.
 */
public class NotificationFragment extends Fragment {
    private EnhancedListView listView;
    private TextView lbl_latest_title;
    private TextView lbl_latest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Tracker t = ((Application) getActivity().getApplication()).getTracker(Application.TrackerName.GLOBAL_TRACKER);
        t.setScreenName("Notification Fragment");
        t.send(new HitBuilders.AppViewBuilder().build());

        View v = inflater.inflate(R.layout.fragment_notification,container, false);
        listView = (EnhancedListView) v.findViewById(R.id.enhanced_list);
        lbl_latest = (TextView) v.findViewById(R.id.txt_latest);
        lbl_latest_title = (TextView) v.findViewById(R.id.lbl_latest_title);
        new Load_Data().execute();
        return v;
    }


    private class Load_Data extends AsyncTask<Void,Void,Void>{
        private ArrayList<PushMessage> msgs;

        protected void onPreExecute(){
            msgs = new ArrayList<PushMessage>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            msgs = new DataHandler(getActivity()).getAllPushMessage();
            return null;
        }

        protected void onPostExecute(Void voids){
            try{
                String msg = new JSONObject(new DataHandler(getActivity()).getServerStatus()).getString("msg_content");
                String title = new JSONObject(new DataHandler(getActivity()).getServerStatus()).getString("msg_title");

                lbl_latest.setText(msg);
            }catch (Exception ignore){}

            final NotificationListAdapter mAdapter = new NotificationListAdapter(getActivity(), msgs);
            listView.setAdapter(mAdapter);
            listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {

                /**
                 * This method will be called when the user swiped a way or deleted it via
                 * {@link de.timroes.android.listview.EnhancedListView#delete(int)}.
                 *
                 * @param listView The {@link de.timroes.android.listview.EnhancedListView} the item has been deleted from.
                 * @param position The position of the item to delete from your adapter.
                 * @return An {@link de.timroes.android.listview.EnhancedListView.Undoable}, if you want
                 *      to give the user the possibility to undo the deletion.
                 */
                @Override
                public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                    final PushMessage f = mAdapter.getItem(position);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            new DataHandler(getActivity()).deletePushMessage(f);
                        }};
                    new Thread(runnable).start();
                    mAdapter.remove(f);
                    Toast.makeText(getActivity(),"Push Message has been deleted.", Toast.LENGTH_SHORT).show();
                    return null;
                }
            });
            listView.enableSwipeToDismiss();
            listView.setSwipeDirection(EnhancedListView.SwipeDirection.START);
        }
    }
}
