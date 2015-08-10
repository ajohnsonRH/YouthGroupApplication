package church_application.rose_hulman.edu.churchapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by johnsoaa on 8/10/2015.
 */
public class AsyncScriptureTask extends AsyncTask<Void, Void, Void> {
    //private String httpget = "http://www.esvapi.org/v2/rest/passageQuery?key=IP&passage=";
    private String testhttpget = "http://labs.bible.org/api/?passage=";
    private String input;
    public  AsyncScriptureTask(String verses){
        testhttpget+=verses;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        URL url;
        String input = "";
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(testhttpget);

            urlConnection = (HttpURLConnection) url
                    .openConnection();
            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                input += current;
            }
            setInput(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }
        return null;
    }



    protected void onPreExecute() {
        //display progress dialog.

    }

    protected void onPostExecute(Void result) {
        // dismiss progress dialog and update ui
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }
}
