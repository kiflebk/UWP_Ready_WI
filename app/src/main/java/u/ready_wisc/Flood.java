package u.ready_wisc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by OZAN on 3/14/2015.
 */
public class Flood extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flood_info);
        t = (TextView) findViewById(R.id.textViewFlood);
        t.setText(Html.fromHtml("<body>                 <!-- FLOODING -->\n" +
                "                <div id=\"tcontent13\" class=\"tabcontentsub\">\n" +
                "                    \n" +
                "                    <h3>Flooding - Know the Terms</h3>\n" +
                "                    \n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <strong>Flood Watch:</strong><br />\n" +
                "                        Flooding is possible. Tune in to NOAA Weather Radio, commercial radio, or television for information.<br /><br />\n" +
                "                        <strong>Flash Flood Watch:</strong><br />\n" +
                "                        Flash flooding is possible. Be prepared to move to higher ground; listen to NOAA Weather Radio,\n" +
                "                        commercial radio, or television for information.<br /><br />\n" +
                "                        <strong>Flood Warning:</strong><br />\n" +
                "                        Flooding is occurring or will occur soon; if advised to evacuate, do so immediately.<br /><br />\n" +
                "                        <strong>Flash Flood Warning:</strong><br />\n" +
                "                        A flash flood is occurring; seek higher ground on foot immediately.\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>During a Flood</h4>\n" +
                "                    <strong>If a flood is likely in your area, you should:</strong><br />\n" +
                "                    <ul>\n" +
                "                        <li> 1. Listen to the radio or television for information. </li><br />\n" +
                "                        <li> 2. Be aware that flash flooding can occur. If there is any possibility of a flash flood, move\n" +
                "                            immediately to higher ground. Do not wait for instructions to move. </li><br />\n" +
                "                        <li> 3. Be aware of streams, drainage channels, canyons, and other areas known to flood suddenly. Flash\n" +
                "                            floods can occur in these areas with or without such typical warnings as rain clouds or heavy rain. </li><br /><br />\n" +
                "                    </ul>\n" +
                "\n" +
                "                    <strong>If you must prepare to evacuate, you should do the following:</strong><br />\n" +
                "                    <ul>\n" +
                "                        <li> 1. Secure your home. If you have time, bring in outdoor furniture. Move essential items to an upper floor. </li><br />\n" +
                "                        <li> 2. Turn off utilities at the main switches or valves if instructed to do so. Disconnect electrical appliances.\n" +
                "                            Do not touch electrical equipment if you are wet or standing in water. </li><br /><br />\n" +
                "                    </ul>\n" +
                "\n" +
                "                    <strong>If you have to leave your home, remember these evacuation tips:</strong><br />\n" +
                "                    <ul>\n" +
                "                        <li> 1. Do not walk through moving water. Six inches of moving water can make you fall. If you have to walk in \n" +
                "                            water, walk where the water is not moving. Use a stick to check the firmness of the ground in front of you. </li><br />\n" +
                "                        <li> 2. Do not drive into flooded areas. If floodwaters rise around your car, abandon the car and move to \n" +
                "                            higher ground if you can do so safely. You and the vehicle can be quickly swept away. </li><br /><br />\n" +
                "                    </ul>\n" +
                "\n" +
                "                    <h4>Driving Flood Facts</h4>\n" +
                "\n" +
                "                    <strong>The following are important points to remember when driving in flood conditions:</strong><br />\n" +
                "                    <ul>\n" +
                "                        <li> 1. Six inches of water will reach the bottom of most passenger cars causing loss of control and possible stalling.</li><br />\n" +
                "                        <li> 2. A foot of water will float many vehicles. </li><br />\n" +
                "                        <li> 3. Two feet of rushing water can carry away most vehicles including sport utility vehicles (SUV&#146;s) and pick-ups. </li><br />\n" +
                "                    </ul>\n" +
                "                </div> </body>"));
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