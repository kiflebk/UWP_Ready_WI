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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
        final Spinner resourceSpinner = (Spinner) findViewById(R.id.resourceSpinner);

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

        resourceList = rDBHelper.getDataFromCounty(county); // Retrieve resources from database based on county and add them to an array

        // Set ListView to display the data retrieved from the database using specific adapter
        ResourceAdapter adapter = new ResourceAdapter(this, resourceList);
        resourcesListView.setAdapter(adapter);

        // When a resource type is selected in the spinner, display only those resources
        resourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String resource = parent.getItemAtPosition(position).toString(); // Retrieve type
                resourceList = rDBHelper.getDataFromType(county, resource); // Retrieve resources based on county and type

                // Set ListView to display the data retrieved from the database using specific adapter
                ResourceAdapter adapter = new ResourceAdapter(ResourcesActivity.this, resourceList);
                resourcesListView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Sets action to be carried out when resource item is clicked
        // If usePhone is true, loads the dialer with the selected resource's number selected
        // If usePhone is false, loads Google Maps with the resource's address searched
        resourcesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ResourceItem clickedItem = (ResourceItem) parent.getItemAtPosition(position); // Get item

                if(usePhone) {
                    String phone = clickedItem.getPhone(); // Get phone number
                    phone.replaceAll("[^0-9]", ""); // Format phone number

                    // Create and start intent
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(callIntent);
                } else {
                    String address = clickedItem.getAddress(); // Get address
                    address.replaceAll("\\s", "+"); // Format address

                    // Create and start intent
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
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
