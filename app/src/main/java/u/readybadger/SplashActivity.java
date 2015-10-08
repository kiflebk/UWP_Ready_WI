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

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import u.readybadger.Counties.Counties;
import u.readybadger.Counties.CountyActivity;
import u.readybadger.Emergency_Main.PutData;
import u.readybadger.LocationHandling.LocationService;

public class SplashActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private boolean isOnline = false;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static String county = "";
    public static String state = "";
    private static final int PERMISSION_REQUEST_LOCATION = 0;
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    static ReportsDatabaseHelper mDatabaseHelper;
    static VolunteerDBHelper vDBHelper;
    static MyDatabaseHelper rDBHelper;
    boolean splashClose = false;
    boolean recieved = false;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        IntentFilter filter = new IntentFilter(LocationService.ACTION_REQUEST_LOCATION);
        LocalBroadcastManager.getInstance(this).registerReceiver(countyStatus, filter);

        if (canAccessLocation())
            LocationService.requestLocation(this);
        else {
            requestLocation();
        }
        isOnline = Connectivity.isOnline(this);
        if (isOnline) {
            new Thread(databaseHandler()).run();
        }
        Counties.getInstance(this);
//        if (hasPermission(LOCATION_PERMS[0])) {
//            int time = 2000;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Loads in the county from the preferences
//                    settings = getSharedPreferences(PREFS_NAME, 0);
//                    county = settings.getString("county", "");
//                    state = settings.getString("state", "");
//                    if (!county.isEmpty()) {
//                        //set global primary county
//                        Config.countyPrim = Counties.ALL.get(county);
//                    }
//                    if (isOnline) {
//                        new Thread(databaseHandler()).run();
//                    }
//                    if (county.isEmpty() || !state.equals("Wisconsin")) {
//                        Intent intent = new Intent(SplashActivity.this, CountyActivity.class);
//                        SplashActivity.this.startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
//                        SplashActivity.this.startActivity(intent);
//                    }
//                    splashClose = true;
//                }
//            }, time);
//        }
    }

    //handles all database updating
    private Runnable databaseHandler() {
        Runnable x = new Runnable() {
            @Override
            public void run() {
                if (isOnline) {
                    mDatabaseHelper = new ReportsDatabaseHelper(SplashActivity.this);
                    vDBHelper = new VolunteerDBHelper(SplashActivity.this);
                    rDBHelper = new MyDatabaseHelper(SplashActivity.this);
                    rDBHelper.addResourceData();
                    if (Config.countyPrim != null) {
                        dbUpdate();
                    }


                    Cursor damageCur;
                    damageCur = mDatabaseHelper.query(ReportsDatabaseHelper.TABLE_USERS, null);
                    String url;
                    if (damageCur.moveToFirst()) {
                        int placeColumn = damageCur.getColumnIndex(ReportsDatabaseHelper.COL_JSON);
                        url = damageCur.getString(placeColumn);

                        try {
                            putDataToServer(url);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }

                        mDatabaseHelper.onDowngrade(mDatabaseHelper.getReadableDatabase(), 0, 1);
                    }
                } else
                    Toast.makeText(SplashActivity.this, "Network Connectivity Error",
                            Toast.LENGTH_SHORT).show();
            }
        };

        return x;
    }

    protected void onResume() {
        super.onResume();
        if (splashClose)
            SplashActivity.this.finish();
        //start message service if not running
        if (!isMyServiceRunning(MessageService.class)) {
            Intent mServiceIntent = new Intent(getApplicationContext(), MessageService.class);
            startService(mServiceIntent);
        }
    }

    //Method to send data to server via HTTP Post
    public void putDataToServer(String json) throws Throwable {

        String reportAccepted;
        PutData httpGet = new PutData(json);
        Thread t = new Thread(httpGet);
        t.start();
        t.join();

        reportAccepted = httpGet.getDataAccepted();

        if (reportAccepted.equals("1")) {
            Toast.makeText(getApplicationContext(), "Saved Report Submitted Successfully", Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(getApplicationContext(), "Report Not Sent", Toast.LENGTH_LONG).show();

    }

    public static void dbUpdate() {
        vDBHelper.onUpgrade(vDBHelper.getReadableDatabase(), 0, 0);
        DBUpdateFromWeb foo = new DBUpdateFromWeb();
        Thread t = new Thread(foo);
        Log.i("DB Update", "Starting Thread");
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // used to load the volunteer and social media info from the web db into the local db
    // two add user methods exist because there are two PHP scripts we are reading the data
    // from and it was easier to use two methods to handle input.
    protected static void addUser(String[][] data) {
        Log.i("DB Update", "starting addUser");

        ContentValues volValues = new ContentValues();

        ContentValues medValues = new ContentValues();

        volValues.put(VolunteerDBHelper.COL_NAME, data[0][0]);

        if (data[0][1] != null) {
            volValues.put(VolunteerDBHelper.COL_PHONE, data[0][1]);
        }

        if (data[0][2] != null) {
            volValues.put(VolunteerDBHelper.COL_EMAIL, data[0][2]);
        }

        if (data[0][3] != null) {
            volValues.put(VolunteerDBHelper.COL_VOL_URL, data[0][3]);
        }

        try {
            vDBHelper.insert(VolunteerDBHelper.TABLE_VOLUNTEER, volValues);
        } catch (VolunteerDBHelper.NotValidException e) {
            Log.e("DB Error:", "Unable to insert volunteer into DB.");
        }

        medValues.put(VolunteerDBHelper.COL_FACEBOOK, data[2][0]);

        if (data[2][1] != null) {
            medValues.put(VolunteerDBHelper.COL_TWITTER, data[2][1]);
        }

        if (data[2][2] != null) {
            medValues.put(VolunteerDBHelper.COL_EXTRA, data[2][2]);
        }

        try {
            vDBHelper.insert(VolunteerDBHelper.TABLE_MEDIA, medValues);
            Log.e("DB Error", "insert successful");
        } catch (VolunteerDBHelper.NotValidException e) {
            Log.e("DB Error:", "Unable to insert media into DB.");
        }

    }

    // used to load the shelter info from the web db into the local db
    protected static void addUser2(String[][] data) {

        ContentValues shelValues = new ContentValues();

        shelValues.put(VolunteerDBHelper.COL_SHELTER_ADD, data[1][0]);

        if (data[1][1] != null) {
            shelValues.put(VolunteerDBHelper.COL_CITY, data[1][1]);
        }

        if (data[1][2] != null) {
            shelValues.put(VolunteerDBHelper.COL_SHELTER_PHONE, data[1][2]);
        }

        if (data[1][3] != null) {
            shelValues.put(VolunteerDBHelper.COL_PERSON, data[1][3]);
        }

        if (data[1][4] != null) {
            shelValues.put(VolunteerDBHelper.COL_ORG, data[1][4]);
        }

        try {
            vDBHelper.insert(VolunteerDBHelper.TABLE_SHELTER, shelValues);
        } catch (VolunteerDBHelper.NotValidException e) {
            Log.e("DB Error:", "Unable to insert shelters into DB.");
        }

    }

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

    //receiver to get loading status and loading amount
    private BroadcastReceiver countyStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("status")) {
                if (!recieved) {
                    recieved = true;
                    startActivity();
                }
            }
        }
    };

    private void startActivity() {
        int time = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Loads in the county from the preferences
                settings = getSharedPreferences(PREFS_NAME, 0);
                county = settings.getString("county", "");
                state = settings.getString("state", "");
                if (!county.isEmpty()) {
                    //set global primary county
                    Config.countyPrim = Counties.ALL.get(county);
                }
                if (county.isEmpty() || !state.equals("Wisconsin")) {
                    Intent intent = new Intent(SplashActivity.this, CountyActivity.class);
                    SplashActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
                    SplashActivity.this.startActivity(intent);
                }
                splashClose = true;
            }
        }, time);
    }

    private boolean canAccessLocation() {
        return (hasPermission(LOCATION_PERMS[0]));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, perm));
    }

    private void requestLocation() {
        ActivityCompat.requestPermissions(SplashActivity.this,
                LOCATION_PERMS,
                PERMISSION_REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION:
                if (canAccessLocation()) {
                    LocationService.requestLocation(this);
                }
                break;
        }
    }

}
