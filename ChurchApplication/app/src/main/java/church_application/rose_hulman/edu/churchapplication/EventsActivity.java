package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.appspot.unhindered_student_ministries.ministry.Ministry;
import com.appspot.unhindered_student_ministries.ministry.model.Announcement;
import com.appspot.unhindered_student_ministries.ministry.model.AnnouncementCollection;
import com.appspot.unhindered_student_ministries.ministry.model.Event;
import com.appspot.unhindered_student_ministries.ministry.model.EventCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;


public class EventsActivity extends ListActivity {

    private Ministry mService;
    static final String KEY_EVENT_TITLE = "KEY_EVENT_TITLE";
    static final String KEY_EVENT_DATE = "KEY_EVENT_DATE";
    static final String KEY_EVENT_DESCRIPTION = "KEY_EVENT_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Ministry.Builder builder = new Ministry.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        mService = builder.build();

        updateEvents();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        final Event currentEvent = (Event) getListAdapter().getItem(position);
        Intent eventDetailsIntent = new Intent(this, EventDetailsActivity.class);
        eventDetailsIntent.putExtra(KEY_EVENT_TITLE, currentEvent.getTitle());
        eventDetailsIntent.putExtra(KEY_EVENT_DATE, currentEvent.getDate());
        eventDetailsIntent.putExtra(KEY_EVENT_DESCRIPTION, currentEvent.getDescription());
        this.startActivity(eventDetailsIntent);
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                // sync
                updateEvents();
                return true;
            default:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateEvents() {
        (new QueryForEventsTask()).execute();
    }

    class QueryForEventsTask extends AsyncTask<Void, Void, EventCollection> {

        @Override
        protected EventCollection doInBackground(Void... params) {
            EventCollection events = null;
            try {
                Ministry.Event.List query = mService.event().list();
                query.setLimit(50L);
                query.setOrder("-last_touch_date_time");
                events =  query.execute();
            } catch(java.io.IOException e) {
                Log.e("Unhindered", "Failed loading " + e);
            }
            return events;
        }

        @Override
        protected void onPostExecute(EventCollection result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.e("Unhindered", "Failed loading, result is null");
            }

            EventsArrayAdapter adapter = new EventsArrayAdapter(EventsActivity.this,
                    android.R.layout.simple_expandable_list_item_2, android.R.id.text1, result.getItems());
            setListAdapter(adapter);
        }

    }

}
