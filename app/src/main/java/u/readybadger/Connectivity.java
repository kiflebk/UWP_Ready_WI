package u.readybadger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple static connectivity class to test Internet connectivity
 * Created by nathaneisner on 10/6/15.
 */
public class Connectivity {
    private static boolean online = false;
    public static boolean isOnline(Context context) {
        new ConnectionExecTask(context).execute();
        return online;
    }


    private static class ConnectionExecTask extends AsyncTask<Void, Void, Boolean> {
        private Context ctx;
        public ConnectionExecTask(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            Log.d("connection task", "the connection is going to be tested");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean connected = false;
            ConnectivityManager connection = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connection.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                try {
                    URL url = new URL("http://clients3.google.com/generate_204");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(10 * 1000);          // 10 s.
                    urlc.connect();
                    if (urlc.getResponseCode() == 204) {        // 204 = "OK" code (http connection is fine).
                        Log.wtf("Connection", "Success !");
                        connected = true;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {connected = false;}

            return connected;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            online = result;
        }
    }
}
