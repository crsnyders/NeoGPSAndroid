package za.co.crsnyders.neogps;


import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by csnyders on 2016/02/29.
 */
public class PostService extends IntentService {


    private HttpURLConnection urlConnection;

    private OutputStreamWriter writer;

    public PostService() {
        super("PostIntentService");

        try {
            Socket  s = new Socket(Proxy.NO_PROXY);
            s.connect(new InetSocketAddress("192.168.4.1",80));
            s.isConnected();
            writer = new OutputStreamWriter(s.getOutputStream());

            Toast.makeText(this,"Connected to NeoGPS", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
        } finally {
            urlConnection.disconnect();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public void writeString(String ledString) {
        try {
            String message = "{\"leds\": \"" + ledString + "\"}";
            writer.write(message);
            writer.flush();
            System.out.println(message);
        } catch (Exception e) {
        } finally {
            urlConnection.disconnect();
        }
    }

}
