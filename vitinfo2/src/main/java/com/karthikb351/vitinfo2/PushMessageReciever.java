package com.karthikb351.vitinfo2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.PushMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saurabh on 5/15/14.
 */
public class PushMessageReciever extends BroadcastReceiver {

    private static final String TAG = "VITacademicsReciever";
    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            PushMessage msg = new PushMessage();

            msg.title = json.getString("alert");
            msg.message = json.getString("message");

            new DataHandler(context).addPushMessage(msg);
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }
}
