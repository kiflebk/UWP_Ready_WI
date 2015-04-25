package u.ready_wisc;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by piela_000 on 2/14/2015.  Class is used as a thread object to
 * query the web database and return the results as a 2d array to the caller
 */
public class DBUpdateFromWeb implements Runnable{


    @Override
    //run thread calls the helper which will update the SQLite db
    public void run() {
        updateLocalDB();
    }


    //main thread to complete query and return results
    public static void updateLocalDB() {
        JSONArray jArray;

        String result = null;

        StringBuilder sb;

        InputStream is = null;

        String[][] ct_name = new String[3][3];

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        // here we try to create a new http client to connect to the .php database query
        try {
            HttpClient httpclient = new DefaultHttpClient();

            /* the web end is set up with a php script to query the database
                asking for the info we need.  The url listed will display
                the results in JSON format for java to read.
             */
            HttpPost httppost = new HttpPost("http://joshuaolufs.com/");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }

        //convert response to string
        try {
            assert is != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            sb = new StringBuilder();
            sb.append(reader.readLine()).append("\n");

            String line;
            while (null != (line = reader.readLine())) {
                sb.append(line).append("\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }


        //converts JSON object to the string array we need
        try {
            jArray = new JSONArray(result);
            JSONObject json_data;

            //ct_name = new String[jArray.length()];
            for (int i = 0; i < jArray.length(); i++) {
                json_data = jArray.getJSONObject(i);
                ct_name[i][0] = json_data.getString("name"); //gets the name category of the json string
                ct_name[i][1] = json_data.getString("email"); //gets the email column

                // inserts data into the local database
                SplashActivity.addUser(ct_name[i][0], ct_name[i][1], 0);
            }
        } catch (JSONException e1) {
                Log.e("IDK","Database Problem");
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }
}

