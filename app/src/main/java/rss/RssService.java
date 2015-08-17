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


package rss;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.SyncStateContract;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import u.ready_wisc.Config;
import u.ready_wisc.Counties.Counties;
import u.ready_wisc.Counties.County;

//RSS service reads the RSS feed and sends the data to be parsed
public class RssService extends IntentService {

    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";
    private static String RSS_SUFFIX;
    private static final String RSS_LINK = "https://alerts.weather.gov/cap/wwaatmget.php?x=";
    private String countyName;
    private Intent intent;


    public RssService() {
        super("RssService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        countyName = intent.getStringExtra("county");
        County county = Counties.ALL.get(countyName);
        if (county != null) {
            RSS_SUFFIX = county.getCode() + "&y=0";
            Log.d(SyncStateContract.Constants.DATA, "Service started");
            this.intent = intent;
            startRss();
        }
        else {
            //Toast.makeText(this,"County Error",Toast.LENGTH_SHORT).show();
        }

    }

    private void startRss() {
        List<RssItem> rssItems = null;
        Log.d("RSS Link", RSS_LINK + RSS_SUFFIX);
        try {
            RssParser parser = new RssParser(countyName);
            rssItems = parser.parse(getInputStream(RSS_LINK + RSS_SUFFIX));
        } catch (XmlPullParserException | IOException e) {
            Log.w(e.getMessage(), e);
        }
        // WisDOT Integration
        checkDOTAlerts(rssItems);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

    // Checks to see if there are any road conditions / alerts
    private List<RssItem> checkDOTAlerts(List<RssItem> rssItems) {
        try {
            String jsonStr = httpRequest();
            // Checks if the json isn't empty
            if (!jsonStr.equals("[]")) {
                Log.e("Alert Test", jsonStr);
                // WisDOT produces an extra set of brackets, which are stripped here
                jsonStr = jsonStr.substring(1, jsonStr.length()-1);
                JSONObject wisDOT = new JSONObject(jsonStr);
                JSONArray counties = wisDOT.getJSONArray("CountyNames");
                Boolean alertCheck = false;
                for (int i = 0; i < counties.length(); i++) {
                    if (counties.get(i).equals(countyName)) {
                        alertCheck = true;
                    }
                }
                if (alertCheck) {
                    String message = wisDOT.getString("Message");
                    rssItems.add(new RssItem("Road Alert: " + message, "", ""));
                } else {
                    rssItems.add(new RssItem("No road alerts.", "" , ""));
                }
            } else {
                rssItems.add(new RssItem("No road alerts.", "" , ""));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.e("JSON Error", e.toString());
        }
        return rssItems;
    }

    //The actual HTTP request for the json string
    public String httpRequest () throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Config.WISDOT_URL);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        String jsonStr = EntityUtils.toString(httpEntity);
        return jsonStr;
    }

    // opens URL stream to read RSS
    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.w(SyncStateContract.Constants.DATA, "Exception while retrieving the input stream", e);
            return null;
        }
    }
}
