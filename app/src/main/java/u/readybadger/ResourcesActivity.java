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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


public class ResourcesActivity extends AppCompatActivity {
    static final boolean usePhone = true;
    static String county = "";
    static MyDatabaseHelper rDBHelper;
    ArrayList<ResourceItem> resourceList;
    ListView resourcesListView;
    Button callButton;
    Spinner resourceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        rDBHelper = new MyDatabaseHelper(this); // Get database helper for queries

        // Retrieve widgets
        final ListView resourcesListView = (ListView) findViewById(R.id.resourcesListView);

        Intent i = getIntent();
        county = i.getStringExtra("county");

        // Set 911 button to load the dialer with "911" pre-loaded when pressed
        Button callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:911"));
                startActivity(i);
            }
        });

        // Retrieve resources from database based on county and add them to an array
        resourceList = rDBHelper.getDataFromCounty(county);

        // Set ListView to display the data retrieved from the database using specific adapter
        ResourceAdapter adapter = new ResourceAdapter(this, resourceList);
        resourcesListView.setAdapter(adapter);

        ImageButton hospitalButton = (ImageButton) findViewById(R.id.hospitalButton);
        ImageButton sheriffButton = (ImageButton) findViewById(R.id.sheriffButton);
        ImageButton fireButton = (ImageButton) findViewById(R.id.fireButton);

        hospitalButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resource = "Hospital";
                // Retrieve resources based on county and type
                resourceList = rDBHelper.getDataFromType(county, resource);

                // Set ListView to display the data retrieved from the database using specific adapter
                ResourceAdapter adapter = new ResourceAdapter(ResourcesActivity.this, resourceList);
                resourcesListView.setAdapter(adapter);
            }
        });

        sheriffButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resource = "Sheriff";
                // Retrieve resources based on county and type
                resourceList = rDBHelper.getDataFromType(county, resource);

                // Set ListView to display the data retrieved from the database using specific adapter
                ResourceAdapter adapter = new ResourceAdapter(ResourcesActivity.this, resourceList);
                resourcesListView.setAdapter(adapter);
            }
        });

        fireButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resource = "Fire";
                // Retrieve resources based on county and type
                resourceList = rDBHelper.getDataFromType(county, resource);

                // Set ListView to display the data retrieved from the database using specific adapter
                ResourceAdapter adapter = new ResourceAdapter(ResourcesActivity.this, resourceList);
                resourcesListView.setAdapter(adapter);
            }
        });

    }

    @Override
    // Saves the county taken from the intent when user leaves the view
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("county", county);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //TODO add options menu
        // getMenuInflater().inflate(R.menu.menu_main, menu);
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
