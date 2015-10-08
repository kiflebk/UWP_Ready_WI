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


package u.readybadger.Emergency_Main;

import android.util.Log;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import u.readybadger.Config;


//Runnable object used to submit damage report to server using HTTP POST

public class PutData implements Runnable {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    // JSON object is created in DamageReports.java
    String mainJSON;
    String dataAccepted;

    public PutData(String json) {

        mainJSON = json;
    }

    private static String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String getDataAccepted() {
        return dataAccepted;
    }

    @Override
    public void run() {
        try {

            // Sends JSON object to the damage report URL
            //TODO Change the type of entity from string to "multipart" to add pics
            dataAccepted = post(Config.DAMAGE_REPORT_URL, mainJSON);
            Log.e("Post Test", dataAccepted);

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
    }
}
