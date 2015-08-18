package church_application.rose_hulman.edu.churchapplication;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

/**
 * Created by johnsoaa on 8/17/2015.
 */
public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }
    private void showNotification(Context context) {
        // make query to display scripture reading in notification

        //  TODO set optional action to launch app
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Did you check out today's scripture?")
                .setContentText("Alarm text")
                .setSmallIcon(R.drawable.ic_launcher)
                .build();
        NotificationManagerCompat.from(context).notify(0, notification);
    }
}
