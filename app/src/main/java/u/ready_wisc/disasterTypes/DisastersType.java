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


package u.ready_wisc.disasterTypes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import u.ready_wisc.R;
import u.ready_wisc.myAdapter;

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


        String[] disasterList = {"Tornado", "Thunderstorms", "Winter Storm", "Extreme Heat", "Flood", "Fire/Wildfire", "Power Outage", "Public Health Emergency", "Terrorism", "Bomb Threats"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        theListView.setAdapter(disasterAdapt);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));
                String disasterPicked = "You selected " + x;
                //Toast.makeText(DisastersType.this, disasterPicked, Toast.LENGTH_SHORT).show();

                if (x.equals("Tornado")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, Tornado.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Winter Storm")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, WinterStorm.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Flood")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, Flood.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Fire/Wildfire")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, Fire.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Power Outage")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, PowerOutage.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Public Health Emergency")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, PublicHealthEmergency.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Bomb Threats")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, BombThreat.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Terrorism")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, Terrorism.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Thunderstorms")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, ThunderStorm.class);
                    DisastersType.this.startActivity(i);
                } else if (x.equals("Extreme Heat")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, ExtremeHeat.class);
                    DisastersType.this.startActivity(i);
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
