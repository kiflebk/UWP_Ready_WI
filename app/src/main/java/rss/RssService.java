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
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import u.ready_wisc.Config;
import u.ready_wisc.County;

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
        County primaryCounty = Config.COUNTIES.get(countyName);
        if (primaryCounty != null) {
            RSS_SUFFIX = primaryCounty.getCode();
            Log.d(SyncStateContract.Constants.DATA, "Service started");
            this.intent = intent;
            startRss();
        }
        else {
            Toast.makeText(this,"County Error",Toast.LENGTH_SHORT).show();
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
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
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
