package church_application.rose_hulman.edu.churchapplication;

import java.util.List;

import com.appspot.unhindered_student_ministries.ministry.model.Announcement;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AnnouncementsArrayAdapter extends ArrayAdapter<Announcement> {

    public AnnouncementsArrayAdapter(Context context, int resource, int textViewResourceId, List<Announcement> announcements) {
        super(context, resource, textViewResourceId, announcements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView titleTextView = (TextView) view.findViewById(android.R.id.text1);
        titleTextView.setInputType(InputType.TYPE_CLASS_TEXT);
        titleTextView.setHorizontalFadingEdgeEnabled(true);
        titleTextView.setText(getItem(position).getTitle());
        TextView descriptionTextView = (TextView) view.findViewById(android.R.id.text2);
        descriptionTextView.setHorizontalFadingEdgeEnabled(true);
        descriptionTextView.setInputType(InputType.TYPE_CLASS_TEXT);
        descriptionTextView.setText(getItem(position).getDescription());
        return view;
    }

}
