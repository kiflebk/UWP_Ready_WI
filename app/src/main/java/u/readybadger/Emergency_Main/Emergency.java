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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import u.readybadger.AnalyticsApp;
import u.readybadger.Config;
import u.readybadger.Connectivity;
import u.readybadger.MenuActivity;
import u.readybadger.R;
import u.readybadger.myAdapter;

/**
 * Created by OZAN on 3/15/2015.
 * <p/>
 * kjljl
 */
public class Emergency extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int PERMISSION_REQUEST_CAMERA = 2;
    private static final String[] CAMERA_PERMS = {android.Manifest.permission.CAMERA};
    private String countyName;
    private View mLayout;
    private ImageView light;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_info_layout);
        mLayout = findViewById(R.id.content);
        // Google Analytics
        // copy these two lines along with the onStart() and onStop() methods below
        Tracker t = ((AnalyticsApp) getApplication()).getTracker();
        t.send(new HitBuilders.ScreenViewBuilder().build());

        String[] disasterList = {"Emergency Map", "River Levels", "Shelters", "Volunteer", "Report Damage", "Social Media", "Flashlight", "SOS Tone"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
        countyName = settings.getString("county", "");

        theListView.setAdapter(disasterAdapt);


        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));

                Context context = getApplicationContext();
                PackageManager pm = context.getPackageManager();
                ImageView image = (ImageView) view.findViewById(R.id.imageview1);
                if (x.equals("Report Damage")) {
                    Intent i = new Intent(Emergency.this, DamageReports.class);
                    Emergency.this.startActivity(i);
                }

                if (x.equals("River Levels")) {
                    if (Connectivity.isOnline(Emergency.this)) {
                        Intent i = new Intent(Emergency.this, RiverGauge.class);
                        Emergency.this.startActivity(i);
                    } else {
                        Toast.makeText(Emergency.this, "Network Error - Could not load river levels",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                if (x.equals("Emergency Map")) {
                    //Calls emergency map
                    Uri uri = Uri.parse(Config.EMERGENCY_MAP_URL + countyName + "+wi");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }

                if (x.equals("Shelters")) {
                    Intent i = new Intent(Emergency.this, Shelters.class);
                    Emergency.this.startActivity(i);
                }

                if (x.equals("Volunteer")) {
                    Intent i = new Intent(Emergency.this, Volunteer.class);
                    Emergency.this.startActivity(i);
                }

                if (x.equals("Social Media")) {
                    Intent i = new Intent(Emergency.this, Social_Media.class);
                    Emergency.this.startActivity(i);
                }

                //if sos tone button is pressed play sound, if sound is playing pause sound
                //sound will play until button is pressed again, even if app is in background
                if (x.equals("SOS Tone")) {

                    if (!MenuActivity.isSosToneOn) {
                        image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.volume_high));
                        //sets device volume to maximum
                        AudioManager am =
                                (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.setStreamVolume(
                                AudioManager.STREAM_MUSIC,
                                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                                0);

                        //begins looping tone
                        MenuActivity.mp.setLooping(true);
                        MenuActivity.isSosToneOn = true;
                        MenuActivity.mp.start();
                    } else {
                        image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                R.drawable.volume_low));
                        //stops looping sound
                        Log.d("Sound test", "Stopping sound");
                        MenuActivity.mp.setLooping(false);
                        MenuActivity.mp.pause();
                        MenuActivity.isSosToneOn = false;
                    }
                }

                //flashlight toggles on off as pressed
                if (x.equals("Flashlight")) {
                    light = image;
                    handleFlashlight();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //TODO add options menu
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Overridden onStart()/onStop() functions for Google Analytics
    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            handleFlashlight();
        }
    }

    private boolean canAccessCamera() {
        return (hasPermission(CAMERA_PERMS[0]));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, perm));
    }

    private void requestCamera() {
        // Display a SnackBar with an explanation and a button to trigger the request.
        Snackbar.make(mLayout, "Camera Permission Needed for Flashlight",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(Emergency.this,
                                CAMERA_PERMS,
                                PERMISSION_REQUEST_CAMERA);
                    }
                })
                .show();
    }

    private void handleFlashlight() {
        if (canAccessCamera()) {
            // boolean to check status of camera flash
            if (!FlashLight.getInstance(this).isOn()) {
                //if flash is off, toggle boolean to on and turn on flash
                FlashLight.getInstance(this).toggle();
                light.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.lightbulb_outline));
            } else {
                //if flash is on turn boolean to false and turn off flash
                FlashLight.getInstance(this).toggle();
                light.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.lightbulb));
            }
        } else {
            requestCamera();
        }
    }
}

