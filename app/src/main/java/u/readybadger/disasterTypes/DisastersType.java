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


package u.readybadger.disasterTypes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import u.readybadger.R;

import u.readybadger.myAdapter;

/**
 * Created by OZAN on 3/13/2015.
 * <p/>
 * This class takes a string array and calls myAdapter class to provide a layout of a listview with
 * icons next to each list item as well as enable each item to be clickable.
 */
public class DisastersType extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_info_layout);


        String[] disasterList = {"Tornado", "Thunderstorms", "Winter Storm", "Extreme Heat",
                "Flood", "Fire/Wildfire", "Power Outage", "Public Health Emergency", "Hazardous Materials",
                "Bomb Threats", "Zombie Apocalypse"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        theListView.setAdapter(disasterAdapt);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));

                Intent i = new Intent(DisastersType.this, InfoDisplay.class);
                switch (x) {
                    case "Tornado": {
                        i.putExtra("infoType", "tornado");
                        startActivity(i);
                        break;
                    }
                    case "Winter Storm": {
                        i.putExtra("infoType", "winter");
                        startActivity(i);
                        break;
                    }
                    case "Flood": {
                        i.putExtra("infoType", "flood");
                        startActivity(i);
                        break;
                    }
                    case "Fire/Wildfire": {
                        i.putExtra("infoType", "fire");
                        startActivity(i);
                        break;
                    }
                    case "Power Outage": {
                        i.putExtra("infoType", "power");
                        startActivity(i);
                        break;
                    }
                    case "Public Health Emergency": {
                        i.putExtra("infoType", "public");
                        startActivity(i);
                        break;
                    }
                    case "Bomb Threats": {
                        i.putExtra("infoType", "bomb");
                        startActivity(i);
                        break;
                    }
                    case "Hazardous Materials": {
                        i.putExtra("infoType", "hazard");
                        startActivity(i);
                        break;
                    }
                    case "Thunderstorms": {
                        i.putExtra("infoType", "thunder");
                        startActivity(i);
                        break;
                    }
                    case "Extreme Heat": {
                        i.putExtra("infoType", "heat");
                        startActivity(i);
                        break;
                    }
                    case "Zombie Apocalypse": {
                        i.putExtra("infoType", "zombie");
                        startActivity(i);
                        break;
                    }
                }


            }
        });


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
