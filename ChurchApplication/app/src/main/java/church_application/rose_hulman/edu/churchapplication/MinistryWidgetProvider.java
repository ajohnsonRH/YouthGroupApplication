package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
        for (int i = 0; i < appWidgetIds.length; ++i) {

            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent1 = new Intent(context, WidgetAnnouncementsService.class);
            // Add the app widget ID to the intent extras.
            intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
            // Set up the intent that starts the StackViewService, which will
            // provide the views for this collection.
            Intent intent2 = new Intent(context, WidgetEventsService.class);
            // Add the app widget ID to the intent extras.
            intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent2.setData(Uri.parse(intent2.toUri(Intent.URI_INTENT_SCHEME)));
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            rv.setRemoteAdapter(appWidgetIds[i], R.id.widgetAnnouncementsListView, intent1);
            rv.setRemoteAdapter(appWidgetIds[i], R.id.widgetEventsListView, intent2);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
