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
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import u.ready_wisc.Emergency_Main.PutData;


public class SplashActivity extends ActionBarActivity {

    static ReportsDatabaseHelper mDatabaseHelper;
    boolean splashClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabaseHelper = new ReportsDatabaseHelper(this);
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

        if (isOnline()) {
//TODO implement webDB update feature
//            DBUpdateFromWeb foo = new DBUpdateFromWeb();
//            Thread t = new Thread(foo);
//            t.start();
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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

                mDatabaseHelper.onUpgrade(mDatabaseHelper.getReadableDatabase(), 0, 1);
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

/*    protected static void addUser(String name, String email, long dateOfBirthMillis) {

        //mDatabaseHelper.onUpgrade(mDatabaseHelper.getReadableDatabase(), 0, 1);

        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.COL_JSON, name);

        if (email != null) {

            values.put(MyDatabaseHelper.COL_EMAIL, email);

        }

        if (dateOfBirthMillis != 0) {

            values.put(MyDatabaseHelper.COL_DOB, dateOfBirthMillis);

        }

        try {

            mDatabaseHelper.insert(MyDatabaseHelper.TABLE_USERS, values);

        } catch (MyDatabaseHelper.NotValidException e) {

            Log.e("DB Error:", "Unable to insert into DB.");

        }

    }*/
}
