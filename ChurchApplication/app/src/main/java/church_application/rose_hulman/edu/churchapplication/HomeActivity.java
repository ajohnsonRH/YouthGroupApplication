package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;

public class HomeActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((ImageButton) findViewById(R.id.aboutUsButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.tenbytenButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.announcementsButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.eventsButton)).setOnClickListener(this);
        Log.e("READY FOR TAKE OFF", "Button Listeners have been set");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutUsButton:
                Log.e("Button Click", "Launch AboutUsActivity");
                Intent aboutIntent = new Intent(this, AboutUsActivity.class);
                this.startActivity(aboutIntent);
                break;
            case R.id.tenbytenButton:
                Log.e("Button Click", "Launch TenByTenActivity");
                Intent tenByTenIntent = new Intent(this, TenByTenActivity.class);
                this.startActivity(tenByTenIntent);
                break;
            case R.id.announcementsButton:
                Log.e("Button Click", "Launch AnnouncementsActivity");
                Intent announcementsIntent = new Intent(this, AnnouncementsActivity.class);
                this.startActivity(announcementsIntent);
                break;
            case R.id.eventsButton:
                Log.e("Button Click", "Launch EventsActivity");
                Intent eventsIntent = new Intent(this, EventsActivity.class);
                this.startActivity(eventsIntent);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
