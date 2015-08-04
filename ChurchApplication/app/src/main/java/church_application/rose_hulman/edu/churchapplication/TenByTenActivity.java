package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.content.Intent;
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
        getActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = (TextView)findViewById(R.id.daily_scripture_text);
        textView.setText("Scripture text/daily reading/verse references");
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }


}
