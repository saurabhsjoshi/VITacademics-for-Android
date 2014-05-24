package com.karthikb351.vitinfo2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;
import com.karthikb351.vitinfo2.objects.DataHandler;
import com.karthikb351.vitinfo2.objects.Subject;
import com.karthikb351.vitinfo2.objects.TimeTableFiles.TTSlot;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by saurabh on 5/23/14.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            NotificationManager notificationManager;
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Gson gson = new Gson();
            TTSlot slt = gson.fromJson(intent.getStringExtra("SLOT"), TTSlot.class);

            Intent open_intent = new Intent(context, SubjectDetails.class);
            open_intent.putExtra("clsnbr", slt.clsnbr);
            PendingIntent pIntent = PendingIntent.getActivity(context, 4, open_intent, PendingIntent.FLAG_CANCEL_CURRENT);

            Subject sub = new DataHandler(context).getSubject(slt.clsnbr);
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.getDefault());



            Notification n = new NotificationCompat.Builder(context)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentTitle(sub.title)
                    .setContentIntent(pIntent)
                    .setContentText(timeFormat.format(slt.frm_time.getTime())+ ", " + slt.venue)
                    .setSmallIcon(R.drawable.ic_action_event)
                    .build();

            notificationManager.notify(0, n);

        }catch (Exception e){e.printStackTrace();}
    }
}
