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

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import u.ready_wisc.CountyPicker;
import u.ready_wisc.SplashActivity;

//Parses the XML RSS that is returned and creates separate RSS objects
// with the data.

public class RssParser {

    // We don't use namespaces
    private final String ns = null;

    // sets features of parser to match RSS XML and reads through inputStream to get all objects
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

    //Uses XML tags to separate data into proper categories
    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "feed");
        String title = null;
        String link = null;
        String desc = null;
        List<RssItem> items = new ArrayList<>();

        // parses until end of document creating new objects for each XML tagged object
        // and assigns attributes from the XML
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch (name) {
                case "title":
                    title = readTitle(parser);
                    break;
                case "id":
                    link = readLink(parser);
                    break;
                case "summary":
                    desc = readDesc(parser);
                    break;
            }
        }
        // Checks for the case where there are no weather advisories and
        // adds the proper description for the item to show if the object
        // is selected from the list
        if (title != null && link != null) {
            if (desc == null){
                title += " for " + CountyPicker.countyName + " County";
                desc = "No current weather warnings/advisories.";
            }
            RssItem item = new RssItem(title, link, desc);

            // The RSS header always contains the first tag of "Watches, Warnings, and Advisories
            // this tag is removed since it is not needed
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
