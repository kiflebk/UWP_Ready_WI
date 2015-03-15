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
public class Terrorism extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terrorism_info);
        t = (TextView) findViewById(R.id.textViewTerrorism);
        t.setText(Html.fromHtml("<body> <!-- TECHNOLOGICAL THREATS -->\n" +

                "                \n" +
                "                <!-- Hazardous Materials Threats -->\n" +
                "                    <h3>Technological Threats - Hazardous Materials</h3>\n" +
                "                    \n" +
                "                    \n" +
                "                    <strong>Wisconsin has many potential sources for technological threats:</strong><br /><br />\n" +
                "                    <ul>\n" +
                "                        <li> 3 active nuclear power plants </li><br />\n" +
                "                        <li> 3,678 miles of railroad tracks</li><br />\n" +
                "                        <li> 111,517 miles of roads and highways</li><br />\n" +
                "                        <li> 150-200 airports</li><br />\n" +
                "                        <li> Numerous active harbors along Lakes Superior and Michigan and the Mississippi River.</li><br /><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <strong>Chemicals are found everywhere. They:</strong><br /><br />\n" +
                "                    <ul>\n" +
                "                        <li> Purify drinking water</li><br />\n" +
                "                        <li> Increase crop production</li><br />\n" +
                "                        <li> Simplify household chores</li><br /><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    But chemicals also can be hazardous to humans or the environment if used or released improperly.\n" +
                "                    <br /><br />\n" +
                "                    <strong>Hazards can occur during:</strong><br />\n" +
                "                    <ul>\n" +
                "                        <li> Production</li><br />\n" +
                "                        <li> Storage</li><br />\n" +
                "                        <li> Transportation</li><br />\n" +
                "                        <li> Use</li><br />\n" +
                "                        <li> Disposal</li><br /><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    You and your community are at risk if a chemical is used unsafely or released \n" +
                "                    in harmful amounts into the environment where you live, work, or play. <br /><br />\n" +
                "                    \n" +
                "                    <strong>Hazardous materials in various forms can cause:</strong><br /><br />\n" +
                "                    <ul>\n" +
                "                        <li> Death</li><br />\n" +
                "                        <li> Serious injury</li><br />\n" +
                "                        <li> Long-lasting health effects</li><br />\n" +
                "                        <li> Damage to buildings, homes, and other property. </li><br /><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    Many products containing hazardous chemicals are used and stored in homes routinely. These products are \n" +
                "                    also shipped daily on the nation's highways, railroads, waterways, and pipelines.\n" +
                "                    <br /><br />\n" +
                "                    Chemical manufacturers are one source of hazardous materials, but there are many others, including service \n" +
                "                    stations, hospitals, and hazardous materials waste sites. \n" +
                "                    <br /><br />\n" +
                "                    Varying quantities of hazardous materials are manufactured, used, or stored at an estimated 4.5 \n" +
                "                    million facilities in the United States--from major industrial plants to local dry cleaning \n" +
                "                    establishments or gardening supply stores.\n" +
                "                    <br /><br /> \n" +
                "                    <strong>Hazardous materials come in the form of:</strong><br /><br />\n" +
                "                    <ul>\n" +
                "                        <li> Explosives</li><br />\n" +
                "                        <li> Flammable and combustible substances</li><br />\n" +
                "                        <li> Poisons</li><br />\n" +
                "                        <li> Radioactive materials. </li><br /><br />\n" +
                "                     </ul>\n" +
                "                     \n" +
                "                     These substances are most often released as a result of transportation accidents or because \n" +
                "                     of chemical accidents in plants.<br />\n" +
                "                     \n" +
                "                    <h4>How to prepare for a Hazardous Materials Emergency</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> 1. Determine how close you are to freeways, railroads or factories which may produce or transport \n" +
                "                        toxic materials. </li><br />\n" +
                "                        <li> 2. Be prepared to evacuate &#150; for hours, days, or even weeks:</li><br /><br />\n" +
                "                        <ul>\n" +
                "                            <li> <strong>A.</strong> Prepare a &#147;go kit&#148; for yourself and your loved ones</li><br />\n" +
                "                            <li> <strong>B.</strong> Develop an emergency plan and practice it</li><br />\n" +
                "                            <li> <strong>C.</strong> Create plans and kits for your pets</li><br /><br />\n" +
                "                        </ul>\n" +
                "                        <li> 3. Have materials available to seal off your residence from airborne contamination in the event you are told \n" +
                "                            to &quot;shelter in place&quot;</li>\n" +
                "                    </ul>\n" +
                "                        \n" +
                "                    <h4>What to do During a Hazardous Materials Incident</h4>\n" +
                "                    \n" +
                "                    Listen to local radio or television stations for detailed information and instructions. Follow the \n" +
                "                    instructions carefully. You should stay away from the area to minimize the risk of contamination. \n" +
                "                    Remember that some toxic chemicals are odorless.\n" +
                "                    \n</div> </body>\n" +
                "            </div>\n </body>"));
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