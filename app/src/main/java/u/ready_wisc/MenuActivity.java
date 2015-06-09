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

package u.ready_wisc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.pushbots.push.Pushbots;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rss.RssFragment;
import u.ready_wisc.BePrepared.Prep_Main;
import u.ready_wisc.Emergency_Main.Emergency;
import u.ready_wisc.disasterTypes.DisastersType;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button resourcesbutton, reportButton, checklistButton, disasterButton;
    private ImageButton sosMenuButton, flashlightButton;
    public static boolean isSosToneOn = false;
    private boolean isFlashOn = false;
    private Camera camera = null;
    Context context;
    public static MediaPlayer mp;
    private PackageManager pm;
    private Menu actionBarMenu;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private String primaryCounty;
    private Set<String> additionalCounties;
    private SharedPreferences settings;
    private CountyDialog secondD;
    private CountyDialog primaryD;

    @Override
    //testing new branch
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        // Google Analytics
        // copy these two lines along with the onStart() and onStop() methods below
        Tracker t = ((AnalyticsApp) getApplication()).getTracker(AnalyticsApp.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.ScreenViewBuilder().build());

        settings = getSharedPreferences("MyPrefsFile", 0);
        // Loads in the county from the preferences
        setCounties();

        // Code used to start pushbots and change to correct county account
        //TODO: implement multi county pushbots
        setPushBots();
//

        context = getApplicationContext();

        // checks to see if the media player object exists
        // if it does exist a new object is not created
        if (!isSosToneOn)
            mp = MediaPlayer.create(context, R.raw.sos_sound);

        pm = context.getPackageManager();
        disasterButton = (Button) findViewById(R.id.typeDisasterButton);
        resourcesbutton = (Button) findViewById(R.id.disasterResourcesButton);
        reportButton = (Button) findViewById(R.id.reportDamageButton);
        checklistButton = (Button) findViewById(R.id.prepareButton);
        sosMenuButton = (ImageButton) findViewById(R.id.SOSMenubutton);
        flashlightButton = (ImageButton) findViewById(R.id.FlashlightMenuButton);
        loadPager();
        //check if there was a previous pager position and go to it else 0
        int resumePos = settings.getInt("pagerPosition", 0);
        mPager.setCurrentItem(resumePos);


        disasterButton.setOnClickListener(this);
        flashlightButton.setOnClickListener(this);
        reportButton.setOnClickListener(this);
        checklistButton.setOnClickListener(this);
        sosMenuButton.setOnClickListener(this);
        resourcesbutton.setOnClickListener(this);


        primaryD = new CountyDialog(this,
                CountyDialog.PRIMARY_COUNTY);
        secondD = new CountyDialog(this,
                CountyDialog.SECONDARY_COUNTIES);
        primaryD.setNeutralDialog("Additional Counties", secondD);
        secondD.setNeutralDialog("Primary County", primaryD);
    }

    @Override
    public void onClick(View v) {
        if ((v.getId() == (R.id.prepareButton)) || (v.getId() == (R.id.prepareMenuButton))) {
            Intent i = new Intent(MenuActivity.this, Prep_Main.class);
            startActivity(i);
        } else if (v.getId() == (R.id.reportDamageButton) || v.getId() == (R.id.emergencyMenuButton)) {
            Intent i = new Intent(MenuActivity.this, Emergency.class);
            MenuActivity.this.startActivity(i);
        } else if (v.getId() == (R.id.disasterResourcesButton) || v.getId() == (R.id.disasterMenuButton)) {
            Intent i = new Intent(MenuActivity.this, ResourcesActivity.class);
            i.putExtra("county", primaryCounty);
            MenuActivity.this.startActivity(i);
        } else if (v.getId() == (R.id.typeDisasterButton)) {
            Intent i = new Intent(MenuActivity.this, DisastersType.class);
            MenuActivity.this.startActivity(i);
        } else if (v.getId() == (R.id.SOSMenubutton)) {
            if (!isSosToneOn) {

                //sets device volume to maximum
                AudioManager am =
                        (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                am.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                        0);

                //begins looping tone
                mp.setLooping(true);
                isSosToneOn = true;
                mp.start();
            } else {

                //stops looping sound
                Log.d("Sound test", "Stopping sound");
                mp.setLooping(false);
                mp.pause();
                isSosToneOn = false;
            }

        } else if (v.getId() == (R.id.FlashlightMenuButton)) {
            //check to see if device has a camera with flash
            if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

                Log.e("err", "Device has no camera!");
                //Return from the method, do nothing after this code block
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

        //TODO add options menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        actionBarMenu = menu;
        setTitle();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //county button selection in action bar
        if (id == R.id.action_county) {
            // TODO: Figure out a better place for unregister(). Currently will not re-register if the user backs out of CountyPicker without selecting
            // Placing at top of onCreate or in CountyPicker itself will not work, as either unregister() or register() will run twice
            Pushbots.sharedInstance().unRegister();
            primaryD.showDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    //creats a list of RSS fragments for the selected counties
    private List<Fragment> createRssFragmentList() {
        List<Fragment> fList = new ArrayList<>();
        //first add primary county fragment
        RssFragment fragment = new RssFragment();
        fragment.setCounty(primaryCounty);
        fList.add(fragment);
        //add additional if there are some
        if (additionalCounties != null && !additionalCounties.isEmpty()) {
            for (String name : additionalCounties) {
                fragment = new RssFragment();
                fragment.setCounty(name);
                fList.add(fragment);
            }
        }
        return fList;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        settings.edit().putInt("pagerPosition", mPager.getCurrentItem()).apply();
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
    protected void onDestroy() {
        super.onDestroy();
        //clear pager position so when app reopens user will be a primary county
        settings.edit().remove("pagerPosition").apply();
    }

    //sets title of action bar
    public void setTitle() {
        if (actionBarMenu != null) {
            MenuItem countyItem = actionBarMenu.findItem(R.id.action_county);
            String county = primaryCounty;
            if (additionalCounties != null)
                if (additionalCounties.size() > 0) {
                    county = county + "+";
                }
            countyItem.setTitle(county);
        }
    }

    //save county options from the shared preferences
    public void setCounties() {
        settings = getSharedPreferences("MyPrefsFile", 0);
        primaryCounty = settings.getString("county", "");
        additionalCounties = settings.getStringSet("counties", null);
        //set static global for use in db classes
        Config.countyPrim = Config.COUNTIES.get(primaryCounty);
        setTitle();
        loadPager();
    }

    private void setPushBots() {
        String appCode = Config.COUNTIES.get(primaryCounty).getAppID();
        //TODO
        //go through the selected counties and register bot for each one
        //for now just set the primary
        Pushbots.sharedInstance().setAppId(appCode);
        Pushbots.sharedInstance().init(this);
        Pushbots.sharedInstance().register();
        Pushbots.sharedInstance().setCustomHandler(PushbotsHandler.class);
    }

    //loads options for the view pager
    private void loadPager() {
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(3);
    }

    //pager adapter for the RSS fragements
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        FragmentManager fm;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            List<Fragment> fList = createRssFragmentList();
            return fList.get(position);
        }

        @Override
        public int getCount() {
            //1 if null or counties +1 to include primary
            return additionalCounties == null ? 1 : additionalCounties.size() + 1;
        }


    }

}
