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

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import u.ready_wisc.R;

/**
 * Created by nathaneisner on 7/7/15.
 */
public class RiverGauge extends AppCompatActivity {
    private GoogleMap map;
    private final HashMap<String, MarkerOptions> markerTypes = new HashMap<>();
    private List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_gauge);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });
        setCamera();
        String path = getFilesDir().getPath() + "/rivers.kml";
        new DrawRiverInfo().execute(path);
    }

    private void setCamera() {
        LatLng centerWI = new LatLng(44.7862968, -89.8267049);
        CameraPosition focus = new CameraPosition.Builder().target(centerWI)
                .zoom(7)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(focus));
    }

    @Override
    protected void onResume() {
        super.onResume();

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(RiverGauge.this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(status, RiverGauge.this, 1);
                if (errorDialog != null) errorDialog.show();
            } else
                Toast.makeText(this, "Google Play Services not found", Toast.LENGTH_SHORT).show();
        }
    }

    private class DrawRiverInfo extends AsyncTask<String, Void, Void> {
        private final String ns = null;
        private List<RiverItem> items = new ArrayList<>();
        MaterialDialog materialDialog;

        @Override
        protected void onPreExecute() {
            materialDialog = new MaterialDialog.Builder(RiverGauge.this)
                    .progress(true, 100)
                    .title("Loading River Information")
                    .build();
            materialDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                InputStream inputStream = new FileInputStream(params[0]);
                parse(inputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            materialDialog.hide();
            drawItems(items);
        }

        /* Parse the KML file for river information */
        private void parse(InputStream inputStream) throws XmlPullParserException, IOException {
            XmlPullParserFactory factory = null;
            XmlPullParser parser = null;
            String id = "";
            String text = "";
            try {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                parser = factory.newPullParser();
                parser.setInput(inputStream, null);
                int eventType = parser.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            //style is the types of markers, located in the begining of the kml
                            if (tagname.equalsIgnoreCase("style")) {
                                id = parser.getAttributeValue(ns, "id");
                                while (tagname == null || !tagname.equalsIgnoreCase("href")) {
                                    eventType = parser.next();
                                    tagname = parser.getName();
                                }
                                //get the text of the href
                                eventType = parser.next();
                                text = parser.getText();
                                //download the icon at the url (href)
                                //make folder if not there
                                File folder = new File(Environment.getExternalStorageDirectory()
                                        + "/markers");
                                File file;
                                boolean success = true;
                                if (!folder.exists()) {
                                    success = folder.mkdir();
                                }
                                if (success) {
                                    //save the icon in the folder
                                    file = new File(Environment.getExternalStorageDirectory()
                                            + "/markers", id + ".png");
                                } else {
                                    Log.e("RiverGauge", "SOMETHING WRONG WITH MARKER FILES");
                                    break;
                                }
                                //download icon
                                FileDownloader.downloadFile(text, file);
                                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                                //resize slightly bigger
                                Bitmap newbm = Bitmap.createScaledBitmap(bm, 25, 25, false);
                                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(newbm);
                                MarkerOptions mO = new MarkerOptions().icon(icon);
                                markerTypes.put(id, mO);
                            }
                            if (tagname.equalsIgnoreCase("placemark")) {
                                isAPlacemark(parser);
                            }
                            break;
//                        case XmlPullParser.TEXT:
//                            text = parser.getText();
//                            break;
//                        case XmlPullParser.END_TAG:
//                            if (tagname.equalsIgnoreCase("style")) {
//                                //style is over
//                            }
//                            break;
                        default:
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* If the parser is at a placemark, parse placemark object */
        private void isAPlacemark(XmlPullParser parser) throws XmlPullParserException, IOException {
            parser.next();
            String tagname = parser.getName();
            RiverItem riverItem = new RiverItem();
            while (tagname == null || !tagname.equalsIgnoreCase("placemark")) {
                int eventType = parser.getEventType();
                if (tagname != null && eventType != XmlPullParser.END_TAG) {
                    if (tagname.equalsIgnoreCase("name")) {
                        parser.next();
                        riverItem.setName(parser.getText());
                    } else if (tagname.equalsIgnoreCase("description")) {
                        parser.next();
                        riverItem.setDesc(parser.getText());
                    } else if (tagname.equalsIgnoreCase("styleurl")) {
                        parser.next();
                        riverItem.setStyle(parser.getText().substring(1));
                    } else if (tagname.equalsIgnoreCase("coordinates")) {
                        parser.next();
                        String coord = parser.getText();
                        String[] latlng = coord.split(",");
                        LatLng latLng = new LatLng(Double.valueOf(latlng[1])
                                , Double.valueOf(latlng[0]));
                        riverItem.setLatLng(latLng);
                    }
                }
                parser.next();
                tagname = parser.getName();
            }
            items.add(riverItem);
        }
    }

    /* Draw all river markers on map */
    private void drawItems(List<RiverItem> list) {
        for (RiverItem riverItem : list) {
            MarkerOptions markerOptions = markerTypes.get(riverItem.getStyle());
            markerOptions.title(riverItem.getName());
            markerOptions.position(riverItem.getLatLng());
            markerOptions.visible(true);
            //snippet can be the desc in the future
            //markerOptions.snippet(riverItem.getDesc());
            markers.add(map.addMarker(markerOptions));
        }
    }
}