package com.karthikb351.vitinfo2.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.karthikb351.vitinfo2.R;
import com.karthikb351.vitinfo2.adapters.NotificationListAdapter;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.PushMessage;

import java.util.ArrayList;

import de.timroes.android.listview.EnhancedListView;

;

/**
 * Created by saurabh on 5/15/14.
 */
public class NotificationFragment extends Fragment {
    private EnhancedListView listView;
    private TextView lbl_empty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification,container, false);
        listView = (EnhancedListView) v.findViewById(R.id.enhanced_list);
        lbl_empty = (TextView) v.findViewById(R.id.lbl_empty);
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
            final NotificationListAdapter mAdapter = new NotificationListAdapter(getActivity(), msgs);
            listView.setAdapter(mAdapter);
            if(msgs.size() == 0)
                lbl_empty.setVisibility(View.VISIBLE);
            else
                lbl_empty.setVisibility(View.GONE);

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
