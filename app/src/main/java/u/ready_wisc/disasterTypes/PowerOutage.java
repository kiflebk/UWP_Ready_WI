package u.ready_wisc.disasterTypes;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import u.ready_wisc.R;

/**
 * Created by OZAN on 3/14/2015.
 * <p/>
 * BLACKOUT INFO
 */
public class PowerOutage extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poweroutage_info);
        t = (TextView) findViewById(R.id.textViewPowerLoss);
        t.setText(Html.fromHtml("<body> <h1 class=\"large\">Power outage</h1><br>\n" +
                "        \n" +
                "<h2>Steps to take</h2>        \n" +
                "<ul>\n" +
                "  <li>Report and get updates on your outage by calling your local electrical company" +
                " (if in Wisconsin call WE at <strong>800-662-4797</strong>.)</li><br /><br />\n" +
                "  <li>Stay away from downed power lines or flooded areas outside.</li><br />\n" +
                "  <li>Stay out of a <a href=\"flooding_in_home.htm\">flooded basement or room</a>. </li><br />\n" +
                "  <li>Keep refrigerator and freezer doors closed as much as possible.</li><br />\n" +
                "  <li>Use manual operation of garage door.</li><br />\n" +
                "  <li>Go to safe shelter if your home is extremely cold or hot.</li><br />\n" +
                "  <li>Use battery-operated lights rather than candles.</li><br /><br />\n" +
                "  <li><a href=\"/outages_safety/using_energy_safely/generators.htm\">Use generators properly</a>.</li><br />\n" +
                "  <li>Unplug or turn off appliances to avoid overloading when power is restored.</li><br />\n" +
                "  <li>Leave a single light on to alert you when power is restored.</li><br />\n" +
                "  <li>If you are the only home without power, check breakers or fuses.</li></ul><br>\n" +
                "\n" +
                "<h2>Food safety</h2>        \n" +
                "<ul>\n" +
                "<li><strong>Frozen</strong></li><br />\n" +
                "<li><p>Thawed or partially thawed food in the freezer may be safely refrozen if it still contains ice crystals or is at 40 °F or below. " +
                "Partial thawing and refreezing may affect the <strong>quality</strong> of some food, but the food will be <strong>safe</strong> " +
                "<strong>to eat.</strong></p></li><br/>\n" +
                "<li><p>If you keep an appliance thermometer in your freezer, it’s easy to tell whether food is safe." +
                " When the power comes back on, check the thermometer. If it reads 40 °F or below, the food is safe and can be refrozen.</p></li><br/>\n" +
                "<li><p>" +
                "<strong>Never taste food to determine its safety!</strong> You can’t rely on appearance or odor to determine whether food is safe.</p></li><br/>\n"
                + "<li> <p><strong>Note:</strong> Always discard any items in the freezer that have come into contact with raw meat juices.</p></li><br/>\n" +


                "<li><strong>Refrigerated</strong></li></ul><br>\n" +
                "\n" +
                "<li> <p>Is food in the refrigerator safe during a power outage? It should be safe as long as power is out <strong>no more than 4 hours</strong>. " +
                "Keep the door closed as much as possible. Discard any perishable food (such as meat, poultry, fish, eggs, and leftovers) that have been above " +
                "40 °F for over 2 hours.</p><li><br /> \n" +
                "<li><p><strong>Never taste food to determine its safety!</strong> You can’t rely on appearance or odor to determine whether food is safe.</p></li><br /> \n" +
                "<li><p><strong>Note:</strong> Always discard any items in the refrigerator that have come into contact with raw meat juices.</p></li><br />\n" +


                "<h2>Cold weather tips</h2>\n" +
                "<p>When power is out, your furnace will not operate. Tips to stay safe:\n" +
                "<ul>\n" +
                "  <li>Wear layers of clothing to keep warm.</li><br/>\n" +
                "  <li>Close blinds or drapes and avoid opening doors to retain heat.</li><br/>\n" +
                "  <li>Use a generator to power your furnace, but <a href=\"/outages_safety/using_energy_safely/generators.htm\">use it properly</a>.</li><br/>\n" +
                "  <li>Use a properly vented fireplace or wood-burning stove, if you have one.</li><br/>\n" +
                "  <li>Do not use outdoor grills, kerosene heaters or camping heaters.</li><br/>\n" +
                "  <li>Go to safe shelter when extremely cold.</li><br/>\n" +
                "  </ul>\n" +
                "<br><br><br><br>\n </body>"));
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