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


package u.readybadger;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by piela_000 on 2/14/2015.  Class is used as a thread object to
 * query the web database and return the results as a 2d array to the caller
 */
public class DBUpdateFromWeb implements Runnable {


    //main thread to complete query and return results
    public static void updateLocalDB() {
        JSONArray jArray2;

        String result = null;

        String result2 = null;

        StringBuilder sb;

        StringBuilder sb2;

        InputStream is = null;

        InputStream is2 = null;

        String[][] ct_name = new String[3][5];

        // here we try to create a new http client to connect to the .php database query
        try {
            //the web end is set up with a php script to query the database
            //  asking for the info we need.  The url listed will display
            //the results in JSON format for java to read.

            Log.i("DB Update", Config.DB_UPDATE_URL + Config.countyPrim.getCode());
            is = httpResponse(Config.DB_UPDATE_URL + Config.countyPrim.getCode())
                    .body().byteStream();

            Log.i("DB Update", Config.SHELTER_UPDATE_URL + Config.countyPrim.getCode());
            is2 = httpResponse(Config.SHELTER_UPDATE_URL + Config.countyPrim.getCode())
                    .body().byteStream();

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
            Log.i("DB Update", result);

            assert is2 != null;
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2, "iso-8859-1"), 8);
            sb2 = new StringBuilder();
            sb2.append(reader2.readLine()).append("\n");

            String line2;
            while (null != (line2 = reader2.readLine())) {
                sb2.append(line2).append("\n");
            }
            is2.close();
            result2 = sb2.toString();
            Log.i("DB Update", result2);

        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }


        //converts JSON object to the string array we need
        try {
            jArray2 = new JSONArray(result2);
            JSONObject json_data;
            JSONObject json_data2;

            json_data = new JSONObject(result);
            ct_name[0][0] = json_data.getString("vol_name_of_org");
            ct_name[0][1] = json_data.getString("vol_phone_number");
            ct_name[0][2] = json_data.getString("vol_email_add");
            ct_name[0][3] = json_data.getString("vol_website");

            ct_name[2][0] = json_data.getString("soc_facebook");
            ct_name[2][1] = json_data.getString("soc_twit");
            ct_name[2][2] = json_data.getString("soc_extra");

            Log.i("DB Update", ct_name[0][0]);
            // inserts data into the local database
            SplashActivity.addUser(ct_name);

            for (int i = 0; i < jArray2.length(); i++) {
                json_data2 = jArray2.getJSONObject(i);

                ct_name[1][0] = json_data2.getString("shel_add");
                ct_name[1][1] = json_data2.getString("shel_city");
                ct_name[1][2] = json_data2.getString("shel_phone");
                ct_name[1][3] = json_data2.getString("shel_PIC");
                ct_name[1][4] = json_data2.getString("shel_org");

                Log.i("DB Update", ct_name[0][0]);
                // inserts data into the local database
                SplashActivity.addUser2(ct_name);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
            Log.e("DB Update", "Database Problem");
        }
    }

    @Override
    //run thread calls the helper which will update the SQLite db
    public void run() {
        updateLocalDB();
    }

    /**
     * Get Http Response
     * @param url
     * @return
     * @throws IOException
     */
    private static Response httpResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request).execute();
    }
}

