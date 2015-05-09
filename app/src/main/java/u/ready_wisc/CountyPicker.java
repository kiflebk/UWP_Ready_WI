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

/**
 * Copyright [2015] [University of Wisconsin - Parkside]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class CountyPicker extends Activity implements AdapterViewCompat.OnItemSelectedListener {


    Spinner counties;
    Button pick;
    public static String countyIdCode;
    public static String countyName;
    public static String appID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county_picker);


        counties = (Spinner) findViewById(R.id.spinner);
        pick = (Button) findViewById(R.id.button);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.county, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counties.setAdapter(adapter);


        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countyInt = counties.getSelectedItemPosition();

                // County codes are located in config file for
                // easy updating and additions
                if (countyInt == 0) {
                    countyIdCode = Config.RSS_DANE;
                    countyName= "Dane";
                    appID = "553feb2717795996028b457e";
                }else if (countyInt == 1) {
                    countyIdCode = Config.RSS_JEFFERSON;
                    countyName= "Jefferson";
                    appID = "553fec3217795996028b4580";
                }else if (countyInt == 2) {
                    countyIdCode = Config.RSS_KENOSHA;
                    countyName= "Kenosha";
                    appID = "553fe6fd177959ac0e8b457f";
                }else if (countyInt == 3) {
                    countyIdCode = Config.RSS_MILWAUKEE;
                    countyName= "Milwaukee";
                    appID = "553fed2c17795996028b4584";
                }else if (countyInt == 4) {
                    countyIdCode = Config.RSS_OZAUKEE;
                    countyName= "Ozaukee";
                    appID = "553ff12617795996028b4585";
                }else if (countyInt == 5) {
                    countyIdCode = Config.RSS_RACINE;
                    countyName= "Racine";
                    appID = "553fea39177959ac0e8b4581";
                }else if (countyInt == 6) {
                    countyIdCode = Config.RSS_ROCK;
                    countyName= "Rock";
                    appID = "553ff3bb1779597b408b4569";
                }else if (countyInt == 7) {
                    countyIdCode = Config.RSS_SAUK;
                    countyName= "Sauk";
                    appID = "553ff53617795912158b457a";
                }else if (countyInt == 8) {
                    countyIdCode = Config.RSS_WALWORTH;
                    countyName= "Walworth";
                    appID = "553ff6e91779597b408b4572";
                }else if (countyInt == 9) {
                    countyIdCode = Config.RSS_WASHINGTON;
                    countyName= "Washington";
                    appID = "553ff97e1779597b408b4574";
                }else if (countyInt == 10) {
                    countyIdCode = Config.RSS_WAUKESHA;
                    countyName= "Waukesha";
                    appID = "553ffa3f17795912158b4584";
                }

                if (isOnline()) {
                    SplashActivity.dbUpdate();
                }

                startUI(getCurrentFocus());
            }
        });

    }

    // returns true or false based on if device has an internet connection.
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void startUI(View v){

        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("county", countyName);

        startActivity(intent);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_county_picker, menu);
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

    @Override
    public void onItemSelected(AdapterViewCompat<?> adapterViewCompat, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterViewCompat<?> adapterViewCompat) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        // saving the county in the preferences for all future uses
        SharedPreferences settings = getSharedPreferences(SplashActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("countyIdCode", countyIdCode);
        editor.putString("countyName", countyName);

        editor.apply();
    }
}
