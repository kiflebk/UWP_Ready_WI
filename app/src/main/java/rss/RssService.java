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

/**
 * Created by piela_000 on 3/1/2015.
 * RssService class extends IntenctService to read from url in seperate thread
 */
public class RssService extends IntentService {

    //private static final String RSS_LINK = "http://feedmix.novaclic.com/atom2rss.php?source=http://alerts.weather.gov/cap/wi.atom";
    //private static final String RSS_LINK = "http://alerts.weather.gov/cap/wi.atom";
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
        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle);
    }

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
