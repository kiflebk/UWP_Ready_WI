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

package u.readybadger;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.pushbots.push.PBNotificationIntent;
import com.pushbots.push.Pushbots;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rss.RssFragment;
import u.readybadger.BePrepared.Prep_Main;
import u.readybadger.Counties.Counties;
import u.readybadger.Counties.County;
import u.readybadger.Counties.CountyActivity;
import u.readybadger.Emergency_Main.Emergency;
import u.readybadger.Emergency_Main.FlashLight;
import u.readybadger.LocationHandling.LocationService;
import u.readybadger.disasterTypes.DisastersType;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int PERMISSION_REQUEST_CAMERA = 2;
    private static final String[] CAMERA_PERMS = {
            android.Manifest.permission.CAMERA
    };
    private CardView resourcesbutton, reportButton, checklistButton, disasterButton;
    private ImageButton sosMenuButton, flashlightButton;
    private ImageView nextFrag, prevFrag;
    private SwipeRefreshLayout swipeLayout;
    public static boolean isSosToneOn = false;
    Context context;
    public static MediaPlayer mp;
    private Menu actionBarMenu;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private String primaryCounty;
    private Set<String> additionalCounties;
    private SharedPreferences settings;
    private View mLayout;
    private boolean lightClicked = false;

    @Override
    //testing new branch
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.swipe_container);
        settings = this.getSharedPreferences("MyPrefsFile", 0);
        // Google Analytics
        // copy these two lines along with the onStart() and onStop() methods below
        Tracker t = ((AnalyticsApp) getApplication()).getTracker();
        t.send(new HitBuilders.ScreenViewBuilder().build());

        // Loads in the county from the preferences
        setCounties();
        context = getApplicationContext();

        // checks to see if the media player object exists
        // if it does exist a new object is not created
        if (!isSosToneOn)
            mp = MediaPlayer.create(context, R.raw.sos_sound);

        disasterButton = (CardView) findViewById(R.id.typeDisasterButton);
        resourcesbutton = (CardView) findViewById(R.id.disasterResourcesButton);
        reportButton = (CardView) findViewById(R.id.reportDamageButton);
        checklistButton = (CardView) findViewById(R.id.prepareButton);
        sosMenuButton = (ImageButton) findViewById(R.id.SOSMenubutton);
        flashlightButton = (ImageButton) findViewById(R.id.FlashlightMenuButton);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        loadPager();

        disasterButton.setOnClickListener(this);
        flashlightButton.setOnClickListener(this);
        reportButton.setOnClickListener(this);
        checklistButton.setOnClickListener(this);
        sosMenuButton.setOnClickListener(this);
        resourcesbutton.setOnClickListener(this);
        setPushBots();
        //Clear Notification array because they will be shown in fragment
        if (PBNotificationIntent.notificationsArray != null) {
            PBNotificationIntent.notificationsArray = null;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Do you wish to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MenuActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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
                sosMenuButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.volume_high));
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
                sosMenuButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.volume_low));
                //stops looping sound
                Log.d("Sound test", "Stopping sound");
                mp.setLooping(false);
                mp.pause();
                isSosToneOn = false;
            }

        } else if (v.getId() == (R.id.FlashlightMenuButton)) {
            lightClicked = true;
            handleFlashlight();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //TODO add options menu for about, help and settings
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
            Intent intent = new Intent(MenuActivity.this, CountyActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_help) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //creates a list of RSS fragments for the selected counties
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
    protected void onResume() {
        super.onResume();
        setCounties();
        IntentFilter filter = new IntentFilter(MessageService.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(messageStatus, filter);
        //start message service if not running
        if (!isMyServiceRunning(MessageService.class)) {
            Intent mServiceIntent = new Intent(getApplicationContext(), MessageService.class);
            startService(mServiceIntent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save pager position for resuming
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageStatus);
        FlashLight.getInstance(this).stop();
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
        LocationService.requestLocation(this);

        primaryCounty = settings.getString("county", "");
        additionalCounties = settings.getStringSet("counties", null);
        setTitle();
        loadPager();
    }

    //starts pushbots for primary county
    private void setPushBots() {
        County primary = Counties.ALL.get(primaryCounty);
        if (primary != null) {
            String appCode = primary.getAppID();
            Pushbots.sharedInstance().setAppId(appCode);
            Pushbots.sharedInstance().init(this);
            Pushbots.sharedInstance().register();
            Pushbots.sharedInstance().setCustomHandler(PushbotsHandler.class);
        }

    }

    //loads options for the view pager
    public void loadPager() {
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(3);
        //check if there was a previous pager position and go to it else 0
        int resumePos = settings.getInt("pagerPosition", 0);
        mPager.setCurrentItem(resumePos);
    }

    public void movePager(String direction) {
        int current = mPager.getCurrentItem();
        switch (direction) {
            case "prev":
                if (current > 0) {
                    mPager.setCurrentItem(current - 1, true);
                }
                break;
            case "next":
                //if (current < mPager.getChildCount()) {
                mPager.setCurrentItem(current + 1, true);
                //}
                break;
            default:
                break;
        }
    }

    public boolean hasLeftFragment(String name) {
        if (!name.equals(primaryCounty)) {
            return true;
        }
        return false;
    }

    public boolean hasRightFragment(String name) {
        int i = 0;
        if (additionalCounties != null && !additionalCounties.isEmpty()) {
            if (name.equals(primaryCounty) && !additionalCounties.isEmpty()) {
                return true;
            }
            for (String county : additionalCounties) {
                if (county.equals(name) && i < additionalCounties.size() - 1) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    @Override
    public void onRefresh() {
        settings.edit().putInt("pagerPosition", mPager.getCurrentItem()).apply();
        setCounties();
        loadPager();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }

    //pager adapter for the RSS fragments
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        FragmentManager fm;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public RssFragment getItem(int position) {
            List<Fragment> fList = createRssFragmentList();
            return (RssFragment) fList.get(position);
        }

        @Override
        public int getCount() {
            //1 if null or counties +1 to include primary
            return additionalCounties == null ? 1 : additionalCounties.size() + 1;
        }
    }

    //receiver if a message times out refresh pager
    private BroadcastReceiver messageStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("messageCleared", false)) {
                loadPager();
            }
        }
    };

    //checks to see if a service is running
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
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
                        ActivityCompat.requestPermissions(MenuActivity.this,
                                CAMERA_PERMS,
                                PERMISSION_REQUEST_CAMERA);
                    }
                })
                .show();
    }

    private void handleFlashlight() {
        if (canAccessCamera()) {
            if (lightClicked) {
                // boolean to check status of camera flash
                if (!FlashLight.getInstance(this).isOn()) {
                    //if flash is off, toggle boolean to on and turn on flash
                    FlashLight.getInstance(this).toggle();
                    flashlightButton.setImageDrawable(ContextCompat.getDrawable(this,
                            R.drawable.lightbulb_outline));
                } else {
                    //if flash is on turn boolean to false and turn off flash
                    FlashLight.getInstance(this).toggle();
                    flashlightButton.setImageDrawable(ContextCompat.getDrawable(this,
                            R.drawable.lightbulb));
                }
                lightClicked = false;
            }
        } else {
            requestCamera();
        }
    }
}
