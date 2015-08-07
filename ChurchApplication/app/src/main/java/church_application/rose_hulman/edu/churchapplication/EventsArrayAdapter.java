package church_application.rose_hulman.edu.churchapplication;

import java.util.List;

import com.appspot.unhindered_student_ministries.ministry.model.Event;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventsArrayAdapter extends ArrayAdapter<Event> {

    public EventsArrayAdapter(Context context, int resource, int textViewResourceId, List<Event> events) {
        super(context, resource, textViewResourceId, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView titleTextView = (TextView) view.findViewById(android.R.id.text2);
        titleTextView.setText(getItem(position).getTitle());
        TextView dateTextView = (TextView) view.findViewById(android.R.id.text1);
        dateTextView.setText(getItem(position).getDate() + " " + getItem(position).getTime());
        return view;
    }

}
