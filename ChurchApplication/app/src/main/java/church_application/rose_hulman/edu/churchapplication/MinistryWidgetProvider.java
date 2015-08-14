package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.appspot.unhindered_student_ministries.ministry.Ministry;
import com.appspot.unhindered_student_ministries.ministry.model.AnnouncementCollection;
import com.appspot.unhindered_student_ministries.ministry.model.EventCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

/**
 * Created by burchtm on 8/12/2015.
 */
public class MinistryWidgetProvider extends AppWidgetProvider{

    private Ministry mService;
    private static Context mContext;
    private static ListView mEventsList;
    private static ListView mAnnouncementsList;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent1 = new Intent(context, WidgetAnnouncementsService.class);
            // Add the app widget ID to the intent extras.
            intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[0]);
            intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
            intent1.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent2 = new Intent(context, WidgetEventsService.class);
            // Add the app widget ID to the intent extras.
            intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[0]);
            intent2.setData(Uri.parse(intent2.toUri(Intent.URI_INTENT_SCHEME)));
            intent2.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(appWidgetIds[0], R.id.widgetAnnouncementsListView, intent1);
            rv.setRemoteAdapter(appWidgetIds[0], R.id.widgetEventsListView, intent2);

            Intent intent3 = new Intent(context, MinistryWidgetProvider.class);
            intent3.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            intent3.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            PendingIntent pendingServiceIntent1 = PendingIntent.getBroadcast(context, 0, intent3, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.widgetSyncButton, pendingServiceIntent1);

            final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pendingServiceIntent1);
            long interval = 1000*60*30;
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pendingServiceIntent1);

            context.startService(intent1);
            context.startService(intent2);
            appWidgetManager.updateAppWidget(appWidgetIds[0], rv);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE))
        {
            int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[0], R.id.widgetAnnouncementsListView);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[0], R.id.widgetEventsListView);
        }

        super.onReceive(context, intent);
    }
}
