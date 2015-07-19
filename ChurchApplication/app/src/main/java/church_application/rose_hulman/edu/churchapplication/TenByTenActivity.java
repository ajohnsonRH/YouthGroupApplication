package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;


public class TenByTenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_by_ten);
        TextView textView = (TextView)findViewById(R.id.daily_scripture_text);
        textView.setText("Scripture text/daily reading/verse references");
    }


}
