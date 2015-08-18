package church_application.rose_hulman.edu.churchapplication;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.appspot.unhindered_student_ministries.ministry.Ministry;
import com.appspot.unhindered_student_ministries.ministry.model.Announcement;
import com.appspot.unhindered_student_ministries.ministry.model.AnnouncementCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by burchtm on 8/12/2015.
 */
public class WidgetAnnouncementsService extends RemoteViewsService {
    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AnnouncementsRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class AnnouncementsRemoteViewsFactory implements
            RemoteViewsService.RemoteViewsFactory {
        public ArrayList<Announcement> mWidgetItems = new ArrayList<Announcement>();
        private Context mContext;
        private int mAppWidgetId;
        static final String KEY_ANNOUNCEMENT_TITLE = "KEY_ANNOUNCEMENT_TITLE";
        static final String KEY_ANNOUNCEMENT_DESCRIPTION = "KEY_ANNOUNCEMENT_DESCRIPTION";

        public AnnouncementsRemoteViewsFactory(Context context, Intent intent) {
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
            updateAnnouncements();
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
            rv.setTextViewText(R.id.text2, mWidgetItems.get(position).getDescription());
            Intent announcementDetailsIntent = new Intent();
            announcementDetailsIntent.putExtra(KEY_ANNOUNCEMENT_TITLE, mWidgetItems.get(position).getTitle());
            announcementDetailsIntent.putExtra(KEY_ANNOUNCEMENT_DESCRIPTION, mWidgetItems.get(position).getDescription());
            announcementDetailsIntent.putExtra("determine", "announcement");
            rv.setOnClickFillInIntent(R.id.widgetItemButton, announcementDetailsIntent);
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
            rv.setTextViewText(R.id.text1, getString(R.string.loading_announcements));
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

        public void setItems(List<Announcement> list)
        {
            mWidgetItems = new ArrayList<Announcement>();
            mWidgetItems.addAll(list);
        }

        private void updateAnnouncements() {
            QueryForAnnouncementsTask task = new QueryForAnnouncementsTask().withData(mContext, mAppWidgetId, this);
            task.execute();
            try {
                task.get(10, TimeUnit.SECONDS);
            } catch (Exception e){
                Log.e("Unhindered", "Timeout");
            }
        }

    }

    class QueryForAnnouncementsTask extends AsyncTask<Void, Void, AnnouncementCollection> {

        private AnnouncementsRemoteViewsFactory mFactory;
        private Ministry mService;
        private Context mContext;
        private int mAppWidgetId;

        @Override
        protected AnnouncementCollection doInBackground(Void... params) {
            Ministry.Builder builder = new Ministry.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
            builder.setApplicationName(getString(R.string.app_name));
            mService = builder.build();
            AnnouncementCollection announcements = null;
            try {
                Ministry.Announcement.List query = mService.announcement().list();
                query.setLimit(50L);
                query.setOrder("-last_touch_date_time");
                announcements =  query.execute();
            } catch(java.io.IOException e) {
                Log.e("Unhindered", "Failed loading " + e);
            }
            return announcements;
        }

        public QueryForAnnouncementsTask withData(Context context, int id, AnnouncementsRemoteViewsFactory factory)
        {
            mFactory = factory;
            mContext = context;
            mAppWidgetId = id;
            return this;
        }

        @Override
        protected void onPostExecute(AnnouncementCollection result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.e("Unhindered", "Failed loading, result is null");
                return;
            }
            mFactory.setItems(result.getItems());
        }

    }
}