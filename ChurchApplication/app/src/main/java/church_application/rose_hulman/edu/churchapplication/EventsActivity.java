package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class EventsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }
}
