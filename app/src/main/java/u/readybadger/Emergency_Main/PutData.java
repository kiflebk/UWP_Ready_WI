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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import u.readybadger.Config;


//Runnable object used to submit damage report to server using HTTP POST

public class PutData implements Runnable {

    // JSON object is created in DamageReports.java
    String mainJSON;
    String dataAccepted;

    public PutData(String json) {

        mainJSON = json;
    }

    public String getDataAccepted() {
        return dataAccepted;
    }

    @Override
    public void run() {
        try {

            // Sends JSON object to the damage report URL
            //TODO Change the type of entity from string to "multipart" to add pics
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.DAMAGE_REPORT_URL);
            StringEntity se = new StringEntity(mainJSON);
            Log.e("Post Test", mainJSON);
            httppost.setEntity(se);

            // As the system currently works, the two responses from the server are:
            // 1 - pass, 0 - fail
            //TODO get validation for successful post requests
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            //dataAccepted = EntityUtils.toString(entity);
            Log.e("Post Test", dataAccepted);

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
    }
}
