package u.ready_wisc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
    ImageButton prepareMenuButton, emergMenuButton, sosMenuButton, resourceMenuButton;
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
        if (savedInstanceState == null) {
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
        resourceMenuButton = (ImageButton) findViewById(R.id.disasterMenuButton);
        sosMenuButton = (ImageButton) findViewById(R.id.SOSMenubutton);


        disasterButton.setOnClickListener(this);
        resourcesbutton.setOnClickListener(this);
        reportButton.setOnClickListener(this);
        checklistButton.setOnClickListener(this);
        prepareMenuButton.setOnClickListener(this);
        emergMenuButton.setOnClickListener(this);
        resourceMenuButton.setOnClickListener(this);
        sosMenuButton.setOnClickListener(this);

//        resourcesbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MenuActivity.this, ResourcesActivity.class);
//                MenuActivity.this.startActivity(i);
//            }
//        });
//
//        reportButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MenuActivity.this, Emergency.class);
//                MenuActivity.this.startActivity(i);
//            }
//        });
//
//        disasterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MenuActivity.this, DisastersType.class);
//                MenuActivity.this.startActivity(i);
//            }
//        });
//
//        checklistButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MenuActivity.this, Prep_Main.class);
//                startActivity(i);
//            }
//        });

    }

    @Override
    public void onClick(View v)
    {
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
            else{

                //stops looping sound
                Log.d("Sound test", "Stopping sound");
                mp.setLooping(false);
                mp.pause();
                sosTone = false;
            }
        }
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
