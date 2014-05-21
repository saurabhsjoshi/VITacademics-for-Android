package com.karthikb351.vitinfo2;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by saurabh on 5/21/14.
 */
public class ProviderWidget extends AppWidgetProvider {

    private static int back =0;

    @Override
    public void onReceive(Context context, Intent intent) {
        back = intent.getIntExtra("back", 0);
        super.onReceive(context, intent);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {


        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                ProviderWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);


        // Build the intent to call the service
        Intent intent = new Intent(context.getApplicationContext(), UpdateWidget.class);
        intent.putExtra("back", back);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        // Update the widgets via the service
        context.startService(intent);
    }
}
