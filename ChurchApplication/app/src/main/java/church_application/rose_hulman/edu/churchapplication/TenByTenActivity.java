package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.appspot.unhindered_student_ministries.ministry.Ministry;
import com.appspot.unhindered_student_ministries.ministry.model.DailyVerse;
import com.appspot.unhindered_student_ministries.ministry.model.EventCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TenByTenActivity extends Activity {
    private TextView textView;

    private Ministry mService;
    static final String KEY_EVENT_TITLE = "KEY_EVENT_TITLE";
    static final String KEY_EVENT_DATE = "KEY_EVENT_DATE";
    static final String KEY_EVENT_DESCRIPTION = "KEY_EVENT_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_by_ten);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (TextView) findViewById(R.id.daily_scripture_text);
        Ministry.Builder builder = new Ministry.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        mService = builder.build();
        String verse = "John 3:1-8"; //Replace with call to database
        queryForDailyScripture();

    }

    private void parseXMLforDisplay(String input) {
        input = input.replace("<b>", " ");
        input = input.replace("</b>", "");
        textView.setText(input);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

    public void queryForDailyScripture() {
        new QueryForEventsTask().execute();
    }


    class QueryForEventsTask extends AsyncTask<Void, Void, DailyVerse> {

        @Override
        protected DailyVerse doInBackground(Void... params) {
            DailyVerse verse = null;
            try {
                Ministry.Verse.Today query = mService.verse().today(); //TODO Fix query 503 error
                verse = query.execute();
            } catch (java.io.IOException e) {
                Log.e("Unhindered", "Failed loading " + e);
            }
            return verse;
        }

        @Override
        protected void onPostExecute(DailyVerse result) {
            super.onPostExecute(result);
            if (result == null) {
                Log.e("Unhindered", "Failed loading, result is null");
                return;
            }
            //TODO UPDATE UI
            String end = result.toString();
            ((TextView) findViewById(R.id.scripture_title)).setText(result.toString());
            AsyncScriptureTask asyncTask = new AsyncScriptureTask(result.toString().replace(" ", "%20"));
            asyncTask.execute();
            while (asyncTask.getInput() == null) {
            } //TODO figure out a better way to do this
            parseXMLforDisplay(asyncTask.getInput());
        }

    }

}
