package u.ready_wisc.Emergency_Main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

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
    //Set boolean to false when SOS starts
    private boolean sosTone = false;
    //Create camera object to access flahslight
    private Camera camera = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_info_layout);


        String[] disasterList = {"Emergency Map", "Shelters", "Disaster Info", "Volunteer", "Report Damage", "Social Media", "Flashlight", "SOS Tone"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        theListView.setAdapter(disasterAdapt);

        // create media player object to play sos tone
        final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.sos_sound);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));
                String disasterPicked = "You selected " + x;

                //toast to show what was selected, redundant
                //Toast.makeText(Emergency.this, disasterPicked, Toast.LENGTH_SHORT).show();
                Context context = getApplicationContext();
                PackageManager pm = context.getPackageManager();

                if (x.equals("Report Damage")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(Emergency.this, DamageReports.class);
                    Emergency.this.startActivity(i);
                }

                //if sos tone button is pressed play sound, if sound is playing pause sound
                //sound will play until button is pressed again, even if app is in background
                if (x.equals("SOS Tone")){
                    if (sosTone == false) {

                        //sets device volume to maximum
                        AudioManager am =
                                (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        am.setStreamVolume(
                                AudioManager.STREAM_MUSIC,
                                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                                0);

                        //begins looping tone
                        mp.setLooping(true);
                        sosTone = true;
                        mp.start();
                    }
                    else{

                        //stops looping sound
                        Log.d("Sound test","Stopping sound");
                        mp.setLooping(false);
                        mp.pause();
                        sosTone = false;
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
                        if (isFlashOn == false) {

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
//                 else if (x.equals("Shelters")) {
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, Shelters.class);
//                    Emergency.this.startActivity(i);
//                } else if (x.equals("Disaster Info")) {
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, RssActivity.class);
//                    Emergency.this.startActivity(i);
//                } else if (x.equals("Volunteer")) {
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, Volunteer.class);
//                    Emergency.this.startActivity(i);
//                } else if (x.equals("Emergency Map")) {
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, Emergency_Map.class);
//                    Emergency.this.startActivity(i);
//                } else if(x.equals("Social Media")){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, Social_Media.class);
//                    Emergency.this.startActivity(i);
//                }


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

