package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TenByTenActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_by_ten);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        textView = (TextView) findViewById(R.id.daily_scripture_text);
        String verse = "John 3:1-8"; //Replace with call to database
        ((TextView) findViewById(R.id.scripture_title)).setText(verse);
        AsyncScriptureTask asyncTask = new AsyncScriptureTask(verse.replace(" ", "%20"));
        asyncTask.execute();
        while (asyncTask.getInput() == null) {
        } //TODO figure out a better way to do this
        parseXMLforDisplay(asyncTask.getInput());
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


}
