package church_application.rose_hulman.edu.churchapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;


public class AboutUsActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ((ImageButton)findViewById(R.id.contact_Pastor_Michelle_Button)).setOnClickListener(this);
        ((ImageButton)findViewById(R.id.contact_Pastor_Ryan_Button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.contact_Pastor_Ryan_Button:
                Log.e("About Us Activity", "Contacting Pastor pastor_ryan");
                email("pastorryan@ministry.com");
                break;
            case R.id.contact_Pastor_Michelle_Button:
                Log.e("About Us Activity", "Contacting Pastor pastor_michelle");
                email("pastormichelle@ministry.com");
                break;
        }
    }

    private void email(String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailAddress, null));
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        onBackPressed();
        return true;
    }
}
