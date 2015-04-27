package u.ready_wisc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.pushbots.push.Pushbots;

import java.io.IOException;

import edu.parkside.cs.checklist.Checklist;
import rss.RssFragment;
import u.ready_wisc.BePrepared.Prep_Main;
import u.ready_wisc.Emergency_Main.Emergency;
import u.ready_wisc.disasterTypes.DisastersType;

public class MenuActivity extends ActionBarActivity implements View.OnClickListener{
    Button resourcesbutton, reportButton, checklistButton, disasterButton;
    ImageButton prepareMenuButton, emergMenuButton, sosMenuButton, flashlightButton;
    private boolean sosTone = false;
    private boolean isFlashOn = false;
    private Camera camera = null;
    Context context;
    MediaPlayer mp;
    PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        Pushbots.sharedInstance().init(this); // was added for pushbots

        // RSS activity isn't called if device has no network connection
        if ((savedInstanceState == null) && isOnline()) {
            addRssFragment();
        }

        context = getApplicationContext();
        pm = context.getPackageManager();
        mp = MediaPlayer.create(context, R.raw.sos_sound);
        disasterButton = (Button) findViewById(R.id.typeDisasterButton);
        resourcesbutton = (Button) findViewById(R.id.disasterResourcesButton);
        reportButton = (Button) findViewById(R.id.reportDamageButton);
        checklistButton = (Button) findViewById(R.id.prepareButton);
        prepareMenuButton = (ImageButton) findViewById(R.id.prepareMenuButton);
        emergMenuButton = (ImageButton) findViewById(R.id.emergencyMenuButton);
        sosMenuButton = (ImageButton) findViewById(R.id.SOSMenubutton);
        flashlightButton = (ImageButton) findViewById(R.id.FlashlightMenuButton);



        disasterButton.setOnClickListener(this);
        flashlightButton.setOnClickListener(this);
        reportButton.setOnClickListener(this);
        checklistButton.setOnClickListener(this);
        prepareMenuButton.setOnClickListener(this);
        emergMenuButton.setOnClickListener(this);
        sosMenuButton.setOnClickListener(this);

        resourcesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent old = getIntent();
                Intent i = new Intent(MenuActivity.this, ResourcesActivity.class);
                i.putExtra("county",old.getStringExtra("county"));
                MenuActivity.this.startActivity(i);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, Emergency.class);
                MenuActivity.this.startActivity(i);
            }
        });

        disasterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, DisastersType.class);
                MenuActivity.this.startActivity(i);
            }
        });

        checklistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, Prep_Main.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v)
    {
        //TODO fix action bar or disable completely
        if ((v.getId() == (R.id.prepareButton)) || (v.getId() == (R.id.prepareMenuButton))) {
            Intent i = new Intent(MenuActivity.this, Prep_Main.class);
            startActivity(i);
        } else if (v.getId() == (R.id.reportDamageButton) || v.getId() == (R.id.emergencyMenuButton)){
            Intent i = new Intent(MenuActivity.this, Emergency.class);
            MenuActivity.this.startActivity(i);
        } else if (v.getId() == (R.id.disasterResourcesButton) || v.getId() == (R.id.disasterMenuButton)) {
            Intent i = new Intent(MenuActivity.this, ResourcesActivity.class);
            MenuActivity.this.startActivity(i);
        } else if (v.getId() == (R.id.typeDisasterButton)) {
            Intent i = new Intent(MenuActivity.this, DisastersType.class);
            MenuActivity.this.startActivity(i);
        } else if (v.getId() == (R.id.SOSMenubutton)) {
            if (!sosTone) {

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

        } else if (v.getId() == (R.id.FlashlightMenuButton)) {
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
        else{

            //stops looping sound
            Log.d("Sound test", "Stopping sound");
            mp.setLooping(false);
            mp.pause();
            sosTone = false;
        }
    }

    // returns true or false based on if device has an internet connection.
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void addRssFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RssFragment fragment = new RssFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }


}
