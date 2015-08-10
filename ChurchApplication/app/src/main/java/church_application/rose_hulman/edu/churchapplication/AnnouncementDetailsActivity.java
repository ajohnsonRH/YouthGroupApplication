package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity for displaying an announcements details.
 */
public class AnnouncementDetailsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = this.getIntent();
        String title = data.getStringExtra(AnnouncementsActivity.KEY_ANNOUNCEMENT_TITLE);
        String description = data.getStringExtra(AnnouncementsActivity.KEY_ANNOUNCEMENT_DESCRIPTION);

        TextView titleView = (TextView) findViewById(R.id.announcementTitleTextView);
        titleView.setText(title);
        TextView descriptionView = (TextView) findViewById(R.id.announcementDescriptionTextView);
        descriptionView.setText(description);
    }

}
