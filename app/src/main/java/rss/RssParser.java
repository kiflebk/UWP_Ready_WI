package rss;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rss.RssItem;

/**
 * Created by piela_000 on 3/1/2015.
 */
public class RssParser {

    // We don't use namespaces
    private final String ns = null;

    public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }

    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "feed");
        String title = null;
        String link = null;
        String desc = null;
        List<RssItem> items = new ArrayList<RssItem>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("id")) {
                link = readLink(parser);
            }
              else if (name.equals("summary")){
                desc = readDesc(parser);
            }
            if (title != null && link != null) {
                if (desc == null){
                    desc = "No current weather warnings/advisories.";
                }
                RssItem item = new RssItem(title, link, desc);
                if( !(item.getTitle().contains("Watches, Warnings and Advisories"))) {
                    items.add(item);
                    title = null;
                    link = null;
                    desc = null;
                }
                else{
                    title = null;
                    link = null;
                    desc = null;
                }
            }
        }
        return items;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "id");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "id");
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private String readDesc(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String desc = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return desc;
    }
}