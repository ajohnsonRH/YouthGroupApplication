package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;


public class AnnouncementsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        final ListView listView = (ListView) findViewById(R.id.announcement_ListView);

    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

}
