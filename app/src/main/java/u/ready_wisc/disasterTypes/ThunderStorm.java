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
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import u.ready_wisc.R;

/**
 * Created by OZAN on 3/14/2015.
 * Displays infro about ThunderStorms
 */
public class ThunderStorm extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thunder_storm);
        t = (TextView) findViewById(R.id.textViewThunderStorm);
        t.setText(Html.fromHtml("<body>                <!-- THUNDERSTORMS -->\n" +
                "                <div id=\"tcontent14\" class=\"tabcontentsub\">\n" +
                "                    <h3>Thunderstorms - Know the Terms</h3>\n" +
                "                    \n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <strong>Severe Thunderstorm Watch</strong><br />\n" +
                "                        Tells you when and where severe thunderstorms are likely to occur. Watch the sky and stay tuned to a \n" +
                "                        commercial radio, or television for information.<br /><br />\n" +
                "                        \n" +
                "                        <strong>Severe Thunderstorm Warning</strong><br />\n" +
                "                        Issued when severe weather has been reported by spotters or indicated by radar. Warnings indicate \n" +
                "                        imminent danger to life and property to those in the path of the storm.\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h3>What to Do Before a Thunderstorm</h3>\n" +
                "                    \n" +
                "                    <strong>To prepare for a thunderstorm, you should do the following:</strong><br /><br />\n" +
                "                    <ul>\n" +
                "                        <li>Consider how a disaster might affect your individual needs.</li><br />\n" +
                "                        <li>Remove dead or rotting trees and branches that could fall and cause injury or damage\n" +
                "                            during a severe thunderstorm.</li><br /><br />\n" +
                "                    </ul>\n" +
                "\n" +
                "                    <strong>The following are guidelines for what you should do if a thunderstorm is likely in your area:</strong><br /><br />\n" +
                "                    <ul>\n" +
                "                        <li> 1. Postpone outdoor activities.</li><br />\n" +
                "                        <li> 2. Get inside a home, building, or hard top automobile (not a convertible). Although you may be injured \n" +
                "                        if lightning strikes your car, you are much safer inside a vehicle than outside.</li><br />\n" +
                "                        <li> 3. Remember, rubber-soled shoes and rubber tires provide NO protection from lightning. However, the \n" +
                "                        steel frame of a hard-topped vehicle provides increased protection if you are not touching metal.</li><br />\n" +
                "                        <li> 4. Secure outdoor objects that could blow away or cause damage.</li><br />\n" +
                "                        <li> 5. Shutter windows and secure outside doors. If shutters are not available, close window blinds, shades, \n" +
                "                            or curtains.</li><br />\n" +
                "                        <li> 6. Avoid showering or bathing. Plumbing and bathroom fixtures can conduct electricity.</li><br />\n" +
                "                        <li> 7. Use a corded telephone only for emergencies. Cordless and cellular telephones are safe to use.</li><br />\n" +
                "                        <li> 8. Unplug appliances and other electrical items such as computers and turn off air conditioners. Power \n" +
                "                            surges from lightning can cause serious damage.</li><br />\n" +
                "                        <li> 9. Use your battery-operated NOAA Weather Radio for updates from local officials.</li><br />\n" +
                "                        <li>10. Remember that lightning can strike as far as 10 miles from the area where it is raining. That's\n" +
                "                            about the distance you can hear thunder. If you can hear thunder, you are within striking distance.\n" +
                "                            Seek safe shelter immediately.</li><br /><br />\n" +
                "                    </ul>\n" +
                "\n" +
                "                    <strong>Avoid the following:</strong><br /><br />\n" +
                "                    <ul>\n" +
                "                        <li> 1. Natural lightning rods such as a tall, isolated tree in an open area.</li><br />\n" +
                "                        <li> 2. Hilltops, open fields, the beach, or a boat on the water.</li><br />\n" +
                "                        <li> 3. Isolated sheds or other small structures in open areas.</li><br />\n" +
                "                        <li> 4. Anything metal&#151;tractors, farm equipment, motorcycles, golf carts, golf clubs, and bicycles. </li><br />\n" +
                "                    </ul>  </body>"));

        Button getDesc = (Button) findViewById(R.id.thunderDesc);
        //Intent intent = getIntent();
        final String link = "http://readywisconsin.wi.gov/media/pdf/Thunderstorms.pdf";

        //setContentView(textView);

        getDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_disaster_types, menu);
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

