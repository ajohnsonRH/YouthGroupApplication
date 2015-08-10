package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * An activity for displaying an events details
 */
public class EventDetailsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = this.getIntent();
        String title = data.getStringExtra(EventsActivity.KEY_EVENT_TITLE);
        String date = data.getStringExtra(EventsActivity.KEY_EVENT_DATE);
        String description = data.getStringExtra(EventsActivity.KEY_EVENT_DESCRIPTION);

        TextView titleView = (TextView) findViewById(R.id.eventTitleTextView);
        titleView.setText(title);
        TextView dateView = (TextView) findViewById(R.id.eventDateTextView);
        dateView.setText(date);
        TextView descriptionView = (TextView) findViewById(R.id.eventDescriptionTextView);
        descriptionView.setText(description);
    }

}
