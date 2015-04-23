package u.ready_wisc.Emergency_Main;

import android.preference.PreferenceActivity;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Copyright [2015] [University of Wisconsin - Parkside]
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class PutData implements Runnable {
    String mainURL;
    JSONObject mainJSON;

    public PutData(String url, JSONObject json){
        mainURL = url;
        mainJSON = json;
    }

    //TODO FIX THIS
    @Override
    public void run() {

        try {
            Log.i("Thread JSON:", mainJSON.toString());
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.joshuaolufs.com/php/query_damageReports_insert.php");
            StringEntity se = new StringEntity(mainJSON.toString());
            Log.i("String Entity", mainJSON.toString());
            httppost.setEntity(se);
            //httppost.setEntity(new ByteArrayEntity(mainJSON.toString().getBytes("UTF8")));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            Log.i("HTTP Response", EntityUtils.toString(entity));

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection" + e.toString());
        }
    }
}
