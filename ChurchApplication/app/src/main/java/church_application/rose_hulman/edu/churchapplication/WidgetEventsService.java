package church_application.rose_hulman.edu.churchapplication;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.appspot.unhindered_student_ministries.ministry.Ministry;
import com.appspot.unhindered_student_ministries.ministry.model.Event;
import com.appspot.unhindered_student_ministries.ministry.model.EventCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by burchtm on 8/12/2015.
 */
public class WidgetEventsService extends RemoteViewsService{
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new EventsRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    class EventsRemoteViewsFactory implements
            RemoteViewsService.RemoteViewsFactory {
        public ArrayList<Event> mWidgetItems = new ArrayList<Event>();
        private Context mContext;
        private int mAppWidgetId;
        static final String KEY_EVENT_TITLE = "KEY_EVENT_TITLE";
        static final String KEY_EVENT_DATE = "KEY_EVENT_DATE";
        static final String KEY_EVENT_DESCRIPTION = "KEY_EVENT_DESCRIPTION";


        public EventsRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            onDataSetChanged();
        }

        @Override
        public void onDataSetChanged()
        {
            updateEvents();
        }

        @Override
        public int getCount()
        {
            return mWidgetItems.size();
        }

        @Override
        public RemoteViews getViewAt(int position)
        {
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            rv.setTextViewText(R.id.text1, mWidgetItems.get(position).getTitle());

            Date date;
            String newDateString  = null;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            try{
                date = df.parse(mWidgetItems.get(position).getDate() + " " + mWidgetItems.get(position).getTime());
                SimpleDateFormat newDf = new SimpleDateFormat("MMMM dd, yyyy hh:mma");
                newDateString = newDf.format(date);

            }
            catch (Exception e) {
                Log.e("Unhindered", "Error parsing date");
            }

            rv.setTextViewText(R.id.text2, newDateString);
            Intent eventDetailsIntent = new Intent();
            eventDetailsIntent.putExtra(KEY_EVENT_TITLE, mWidgetItems.get(position).getTitle());
            eventDetailsIntent.putExtra(KEY_EVENT_DATE, newDateString);
            eventDetailsIntent.putExtra(KEY_EVENT_DESCRIPTION, mWidgetItems.get(position).getDescription());
            eventDetailsIntent.putExtra("determine", "event");
            rv.setOnClickFillInIntent(R.id.widgetItemButton, eventDetailsIntent);
            return rv;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public void onDestroy(){
        }

        @Override
        public RemoteViews getLoadingView()
        {
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
            rv.setTextViewText(R.id.text1, getString(R.string.loading_events));
            rv.setTextViewText(R.id.text2, getString(R.string.please_wait));
            return rv;
        }

        @Override
        public int getViewTypeCount()
        {
            return 1;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        public void setItems(List<Event> list)
        {
            mWidgetItems = new ArrayList<Event>();
            mWidgetItems.addAll(list);
        }

        private void updateEvents() {
            QueryForEventsTask task = new QueryForEventsTask().withData(mContext, mAppWidgetId, this);
            task.execute();
            try {
                task.get(10, TimeUnit.SECONDS);
            } catch (Exception e){
                Log.e("Unhindered", "Timeout");
            }
        }

    }

    class QueryForEventsTask extends AsyncTask<Void, Void, EventCollection> {

        private EventsRemoteViewsFactory mFactory;
        private Ministry mService;
        private Context mContext;
        private int mAppWidgetId;

        @Override
        protected EventCollection doInBackground(Void... params) {
            Ministry.Builder builder = new Ministry.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
            mService = builder.build();
            builder.setApplicationName(getString(R.string.app_name));
            EventCollection events = null;
            try {
                Ministry.Event.List query = mService.event().list();
                query.setLimit(50L);
                query.setOrder("-date,-time");
                events =  query.execute();
            } catch(java.io.IOException e) {
                Log.e("Unhindered", "Failed loading " + e);
            }
            return events;
        }

        public QueryForEventsTask withData(Context context, int id, EventsRemoteViewsFactory factory)
        {
            mFactory = factory;
            mContext = context;
            mAppWidgetId = id;
            return this;
        }

        @Override
        protected void onPostExecute(EventCollection result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.e("Unhindered", "Failed loading, result is null");
                return;
            }
            mFactory.setItems(result.getItems());
        }

    }
}