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


package u.ready_wisc.Emergency_Main;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


 //Runnable object used to submit damage report to server using HTTP GET
 

public class PutData implements Runnable {

    // JSON object is created in DamageReports.java
    JSONObject mainJSON;
    String dataAccepted;

    public PutData(JSONObject json) {

        mainJSON = json;
    }

    public String getDataAccepted(){
        return dataAccepted;
    }

    //TODO for future versions: GET needs to be changed to POST.  Data needs to be encrypted before being sent
    @Override
    public void run() {

        // char holds the \ character to eliminate from the URL header
        char backspace = (char) 92;

        //
        try {
            Log.i("Thread JSON:", mainJSON.toString());
            HttpClient httpclient = new DefaultHttpClient();

            String url = "http://www.joshuaolufs.com/php/query_damageReports_insert.php?" + mainJSON.toString().replace('{', ' ').replace('}',' ').replace(backspace, ' ').trim().replace('"', ' ').replace(" ", "").replace(':','=').replace(',','&');

            Log.i("HTTP URL:", url);
            HttpPost httppost = new HttpPost(url) ;

            // The following code needs to be fixed to send the DamageReports JSON object
            // as a POST.

           //StringEntity se = new StringEntity(mainJSON.toString());
            //Log.i("String Entity", mainJSON.toString());
            //httppost.setEntity(se);

            // As the system currently works, the two responses from the server are:
            // 1 - pass, 0 - fail
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            dataAccepted = EntityUtils.toString(entity);
            Log.i("HTTP Response", dataAccepted);

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
    }
}
