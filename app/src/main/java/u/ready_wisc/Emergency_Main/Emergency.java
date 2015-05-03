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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import u.ready_wisc.Config;
import u.ready_wisc.CountyPicker;
import u.ready_wisc.MenuActivity;
import u.ready_wisc.R;
import u.ready_wisc.myAdapter;

/**
 * Created by OZAN on 3/15/2015.
 *
 * kjljl
 */
public class Emergency extends ActionBarActivity {

    //Set boolean flag when torch is turned on/off
    private boolean isFlashOn = false;
    //Create camera object to access flashlight
    private Camera camera = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_info_layout);

        String[] disasterList = {"Emergency Map", "Shelters", "Disaster Info", "Volunteer", "Report Damage", "Social Media", "Flashlight", "SOS Tone"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        theListView.setAdapter(disasterAdapt);


        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));

                Context context = getApplicationContext();
                PackageManager pm = context.getPackageManager();

                if (x.equals("Report Damage")) {
                    Intent i = new Intent(Emergency.this, DamageReports.class);
                    Emergency.this.startActivity(i);
                }

                if (x.equals("Emergency Map")) {
                    //Calls emergency map
                    Uri uri = Uri.parse(Config.EMERGENCY_MAP_URL + CountyPicker.countyName + "+wi");
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
                if (x.equals("SOS Tone")){

                    if (!MenuActivity.isSosToneOn) {

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
                    }
                    else{

                        //stops looping sound
                        Log.d("Sound test","Stopping sound");
                        MenuActivity.mp.setLooping(false);
                        MenuActivity.mp.pause();
                        MenuActivity.isSosToneOn = false;
                    }
                }

                //flashlight toggles on off as pressed
                if (x.equals("Flashlight")){

                    //check to see if device has a camera with flash
                    if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                        Log.e("err", "Device has no camera!");
                        //Return from the method, do nothing after this code block
                        return;
                    }
                    // if camera has flash toggle on and off
                    else {
                        // boolean to check status of camera flash
                        if (!isFlashOn) {

                            //if flash is off, toggle boolean to on and turn on flash
                            isFlashOn = true;
                            camera = Camera.open();
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            camera.setParameters(parameters);
                            camera.startPreview();

                        } else {

                            //if flash is on turn boolean to false and turn off flash
                            isFlashOn = false;
                            camera.stopPreview();
                            camera.release();
                            camera = null;

                        }
                    }
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_disaster_types, menu);
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


}

