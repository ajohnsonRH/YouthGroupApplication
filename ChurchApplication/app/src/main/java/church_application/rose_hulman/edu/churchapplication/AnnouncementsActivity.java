package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.app.ListActivity;
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

}
