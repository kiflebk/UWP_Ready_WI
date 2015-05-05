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

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import u.ready_wisc.Emergency_Main.PutData;


public class SplashActivity extends ActionBarActivity {

    static ReportsDatabaseHelper mDatabaseHelper;
    static VolunteerDBHelper vDBHelper;
    static MyDatabaseHelper rDBHelper;
    boolean splashClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabaseHelper = new ReportsDatabaseHelper(this);
        vDBHelper = new VolunteerDBHelper(this);
        rDBHelper = new MyDatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        int time = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (CountyPicker.countyIdCode != null) {
                    Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
                    SplashActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, CountyPicker.class);
                    SplashActivity.this.startActivity(intent);
                }

                splashClose = true;
            }
        }, time);

        Cursor resourceCur;
        resourceCur = rDBHelper.query(MyDatabaseHelper.TABLE_USERS, null);
        if (!resourceCur.moveToFirst()) {
            rDBHelper.addResourceData();
        }


        if (isOnline()) {

            if(CountyPicker.countyIdCode != null) {
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
        }

    }

    protected void onResume() {
        super.onResume();
        if (splashClose)
            SplashActivity.this.finish();
    }

    // returns true or false based on if device has an internet connection.
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_activity, menu);
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

        }else
            Toast.makeText(getApplicationContext(), "Report Not Sent", Toast.LENGTH_LONG).show();

    }

    // used to load the volunteer and social media info from the web db into the local db
    // two add user methods exist because there are two PHP scripts we are reading the data
    // from and it was easier to use two methods to handle input.
    protected static void addUser(String[][] data) {

        Log.i("DB Update","starting addUser");

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
}
