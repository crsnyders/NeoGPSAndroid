package za.co.crsnyders.neogps;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by csnyders on 2016/02/29.
 */
public class PostService {

    private HttpURLConnection connection;
    private HttpURLConnection urlConnection;

    private OutputStreamWriter writer;
    public PostService() {


        try {
            URL url = new URL("http://192.168.4.1:80");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            writer = new OutputStreamWriter(out);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }

    public void writeString(String ledString){
        try{
        writer.write("{\"leds\": "+ledString+"}");
            writer.flush();
    } catch (Exception e) {
    } finally {
        urlConnection.disconnect();
    }
    }

}
