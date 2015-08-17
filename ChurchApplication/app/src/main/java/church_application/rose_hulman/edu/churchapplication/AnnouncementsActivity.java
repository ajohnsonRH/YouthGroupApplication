package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.appspot.unhindered_student_ministries.ministry.Ministry;
import com.appspot.unhindered_student_ministries.ministry.model.Announcement;
import com.appspot.unhindered_student_ministries.ministry.model.AnnouncementCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;


public class AnnouncementsActivity extends ListActivity {

    private Ministry mService;
    static final String KEY_ANNOUNCEMENT_TITLE = "KEY_ANNOUNCEMENT_TITLE";
    static final String KEY_ANNOUNCEMENT_DESCRIPTION = "KEY_ANNOUNCEMENT_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Ministry.Builder builder = new Ministry.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        mService = builder.build();

        updateAnnouncements();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        final Announcement currentAnnouncement = (Announcement) getListAdapter().getItem(position);
        Intent announcementDetailsIntent = new Intent(this, AnnouncementDetailsActivity.class);
        announcementDetailsIntent.putExtra(KEY_ANNOUNCEMENT_TITLE, currentAnnouncement.getTitle());
        announcementDetailsIntent.putExtra(KEY_ANNOUNCEMENT_DESCRIPTION, currentAnnouncement.getDescription());
        this.startActivity(announcementDetailsIntent);
        super.onListItemClick(l, v, position, id);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_announcements, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                // sync
                updateAnnouncements();
                return true;
            default:
                onBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_announcements);
//        final ListView listView = (ListView) findViewById(R.id.announcement_ListView);
//
//    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateAnnouncements() {
        (new QueryForAnnouncementsTask()).execute();
    }

    class QueryForAnnouncementsTask extends AsyncTask<Void, Void, AnnouncementCollection> {

        @Override
        protected AnnouncementCollection doInBackground(Void... params) {
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

        @Override
        protected void onPostExecute(AnnouncementCollection result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.e("Unhindered", "Failed loading, result is null");
                return;
            }

            AnnouncementsArrayAdapter adapter = new AnnouncementsArrayAdapter(AnnouncementsActivity.this,
                    android.R.layout.simple_expandable_list_item_2, android.R.id.text1, result.getItems());
            setListAdapter(adapter);
        }

    }

}
