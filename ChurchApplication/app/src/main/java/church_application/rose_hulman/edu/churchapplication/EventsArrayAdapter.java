package church_application.rose_hulman.edu.churchapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.appspot.unhindered_student_ministries.ministry.model.Event;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
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
        titleTextView.setInputType(InputType.TYPE_CLASS_TEXT);
        titleTextView.setHorizontalFadingEdgeEnabled(true);
        titleTextView.setText(getItem(position).getTitle());
        TextView dateTextView = (TextView) view.findViewById(android.R.id.text1);
        dateTextView.setInputType(InputType.TYPE_CLASS_TEXT);
        dateTextView.setHorizontalFadingEdgeEnabled(true);
        Date date;
        String newDateString  = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        try{
            date = df.parse(getItem(position).getDate() + " " + getItem(position).getTime());
            SimpleDateFormat newDf = new SimpleDateFormat("MMMM dd, yyyy hh:mma");
            newDateString = newDf.format(date);

        }
        catch (Exception e) {
            Log.e("Unhindered", "Error parsing date");
        }
        dateTextView.setText(newDateString);
        return view;
    }

}
