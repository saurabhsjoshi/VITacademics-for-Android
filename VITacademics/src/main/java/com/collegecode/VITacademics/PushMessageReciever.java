package com.collegecode.VITacademics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.collegecode.objects.DataHandler;
import com.collegecode.objects.PushMessage;

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
