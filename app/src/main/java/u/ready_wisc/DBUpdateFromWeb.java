/*
*
*  Copyright 2015 University of Wisconsin - Parkside
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*
*
*/

package u.ready_wisc;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
        JSONArray jArray = null;

        String result = null;

        StringBuilder sb = null;

        InputStream is = null;

        String[][] ct_name = new String[3][3];

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        // here we try to create a new http client to connect to the .php database query
        try {
            HttpClient httpclient = new DefaultHttpClient();

            /* the web end is set up with a php script to query the database
                asking for the info we need.  The url listed will display
                the results in JSON format for java to read.
             */
            HttpPost httppost = new HttpPost("http://piela.co/database/");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
        //convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");

            String line = "0";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }


        //converts JSON object to the string array we need
        try {
            jArray = new JSONArray(result);
            JSONObject json_data = null;

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

