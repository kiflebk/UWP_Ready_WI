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
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import u.readybadger.R;

import u.readybadger.Config;


public class InfoDisplay extends AppCompatActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);

        t = (TextView) findViewById(R.id.infoTextView);
        Intent i = getIntent();
        String infoType = i.getStringExtra("infoType");
        String infoLink = "";
        Button factSheetButton = (Button) findViewById(R.id.factSheetButton);

        switch (infoType) {
            case "bomb":
                t.setText(Html.fromHtml(Config.BOMB_THREAT));
                infoLink = Config.BOMB_LINK;
                setTitle("Bomb Threats");
                break;
            case "heat":
                t.setText(Html.fromHtml(Config.HEAT));
                infoLink = Config.HEAT_LINK;
                setTitle("Extreme Heat");
                break;
            case "fire":
                t.setText(Html.fromHtml(Config.FIRE));
                infoLink = Config.FIRE_lINK;
                setTitle("Fire/Wildfire");
                break;
            case "flood":
                t.setText(Html.fromHtml(Config.FLOOD));
                infoLink = Config.FLOOD_LINK;
                setTitle("Flood");
                break;
            case "power":
                t.setText(Html.fromHtml(Config.POWER_OUT));
                setTitle("Power Outage");
                break;
            case "public":
                t.setText(Html.fromHtml(Config.PHE));
                setTitle("Public Health Emergency");
                break;
            case "hazard":
                t.setText(Html.fromHtml(Config.TERRORISM));
                setTitle("Hazardous Materials");
                break;
            case "thunder":
                t.setText(Html.fromHtml(Config.THUNDER_STORM));
                infoLink = Config.THUNDER_LINK;
                setTitle("Thunderstorms");
                break;
            case "tornado":
                t.setText(Html.fromHtml(Config.TORNADO_INFO));
                setTitle("Tornado");
                break;
            case "winter":
                t.setText(Html.fromHtml(Config.WINTER_STORM));
                infoLink = Config.WINTER_LINK;
                setTitle("Winter Storm");
                break;
            case "zombie":
                t.setText(Html.fromHtml(Config.ZOMBIE));
                setTitle("Zombie Apocalypse");
            default:
                break;
        }

        if (infoLink.isEmpty()) {
            factSheetButton.setVisibility(View.INVISIBLE);
        }

        final String finalInfoLink = infoLink;
        factSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(finalInfoLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
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
