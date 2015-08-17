package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class HomeActivity extends Activity implements View.OnClickListener {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((ImageButton) findViewById(R.id.aboutUsButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.tenbytenButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.announcementsButton)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.eventsButton)).setOnClickListener(this);
        Log.e("READY FOR TAKE OFF", "Button Listeners have been set");
        // set the notification
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        // Set the alarm to start at approximately 4:35 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 30);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

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

