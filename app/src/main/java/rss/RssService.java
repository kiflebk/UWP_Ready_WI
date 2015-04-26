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

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;

import u.ready_wisc.CountyPicker;


 //RSS service reads the RSS feed and sends the data to be parsed

public class RssService extends IntentService {

    // string is appended with the county code based on user county selection
    // county codes can be found at https://alerts.weather.gov/cap/wi.php?x=3
    // TODO county codes will need to be added for all counties as app is expanded
    private static final String RSS_LINK = "https://alerts.weather.gov/cap/wwaatmget.php?x=" + CountyPicker.countyIdCode;
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";


    public RssService() {
        super("RssService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(SyncStateContract.Constants.DATA, "Service started");
        List<RssItem> rssItems = null;
        Log.d("RSS Link", RSS_LINK);
        try {
            RssParser parser = new RssParser();
            rssItems = parser.parse(getInputStream(RSS_LINK));
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
