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
 *
 * FIRE INFO
 */
public class Fire extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_info);
        t = (TextView) findViewById(R.id.textViewFire);
        t.setText(Html.fromHtml("<body><!-- HOUSE FIRE -->\n" +
                "                <div id=\"tcontent17\" class=\"tabcontentsub\">\n" +
                "                    <h3>House Fires</h3>\n" +
                "                    \n" +

                "                    \n" +
                "                    Each year, more than 4,000 Americans die and more than 25,000 are injured in fires, many of which \n" +
                "                    could be prevented. Direct property loss due to fires is estimated at $8.6 billion annually.\n" +
                "                    <br /><br />\n" +
                "                    To protect yourself, it is important to understand the basic characteristics of fire. Fire spreads \n" +
                "                    quickly; there is no time to gather valuables or make a phone call. In just two minutes, a fire \n" +
                "                    can become life-threatening. In five minutes, a residence can be engulfed in flames.\n" +
                "                    <br /><br />\n" +
                "                    Heat and smoke from fire can be more dangerous than the flames. Inhaling the super-hot air can sear your lungs. \n" +
                "                    Fire produces poisonous gases that make you disoriented and drowsy. Instead of being awakened by a fire, you \n" +
                "                    may fall into a deeper sleep. Asphyxiation is the leading cause of fire deaths, exceeding burns by a \n" +
                "                    three-to-one ratio.\n" +
                "                    \n" +
                "                    <h4>How can I protect myself from fire?</h4>\n" +
                "                    <ul>\n" +
                "                        <li> What to do before a fire</li>\n" +
                "                        <li> What to do during a fire</li>\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h3>What to do Before a Fire</h3>\n" +
                "                    \n" +
                "                    The following are things you can do to protect yourself, your family, and your property in the event of a fire:\n" +
                "                    \n" +
                "                    <h4>Smoke Alarms</h4>\n" +
                "                    <ul>\n" +
                "                        <li> 1. Install smoke alarms. Properly working smoke alarms decrease your chances of dying in a fire by half.</li><br />\n" +
                "                        <li> 2. Place smoke alarms on every level of your residence. Place them outside bedrooms on the ceiling or high\n" +
                "                            on the wall (4 to 12 inches from ceiling), at the top of open stairways, or at the bottom of enclosed stairs\n" +
                "                            and near (but not in) the kitchen.</li><br />\n" +
                "                        <li> 3. Test and clean smoke alarms once a month and replace batteries at least once a year. Replace smoke alarms\n" +
                "                            once every 10 years.</li><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Escaping the Fire</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> 1. Review escape routes with your family. Practice escaping from each room.</li><br />\n" +
                "                        <li> 2. Make sure windows are not nailed or painted shut. Make sure security gratings on windows have a fire safety\n" +
                "                            opening feature so they can be easily opened from the inside.</li><br />\n" +
                "                        <li> 3. Consider escape ladders if your residence has more than one level, and ensure that burglar bars and other\n" +
                "                            antitheft mechanisms that block outside window entry are easily opened from the inside.</li><br />\n" +
                "                        <li> 4. Teach family members to stay low to the floor (where the air is safer in a fire) when escaping from a fire.</li><br />\n" +
                "                        <li> 5. Clean out storage areas. Do not let trash, such as old newspapers and magazines, accumulate.</li><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Flammable Items</h4>\n" +
                "                    <ul>\n" +
                "                        <li> 1. Never use gasoline, benzine, naptha, or similar flammable liquids indoors.</li><br />\n" +
                "                        <li> 2. Store flammable liquids in approved containers in well-ventilated storage areas.</li><br />\n" +
                "                        <li> 3. Never smoke near flammable liquids.</li><br />\n" +
                "                        <li> 4. Discard all rags or materials that have been soaked in flammable liquids after you have used them. Safely\n" +
                "                            discard them outdoors in a metal container.</li><br />\n" +
                "                        <li> 5. Insulate chimneys and place spark arresters on top. The chimney should be at least three feet higher than the\n" +
                "                            roof. Remove branches hanging above and around the chimney.</li><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Heating Sources</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> 1. Be careful when using alternative heating sources.</li><br />\n" +
                "                        <li> 2. Check with your local fire department on the legality of using kerosene heaters in your community. Be sure\n" +
                "                            to fill kerosene heaters outside, and be sure they have cooled.</li><br />\n" +
                "                        <li> 3. Place heaters at least three feet away from flammable materials. Make sure the floor and nearby walls are\n" +
                "                            properly insulated.</li><br />\n" +
                "                        <li> 4. Use only the type of fuel designated for your unit and follow manufacturer&#146;s instructions.</li><br />\n" +
                "                        <li> 5. Store ashes in a metal container outside and away from your residence.</li><br />\n" +
                "                        <li> 6. Keep open flames away from walls, furniture, drapery, and flammable items.</li><br />\n" +
                "                        <li> 7. Keep a screen in front of the fireplace.</li><br />\n" +
                "                        <li> 8. Have heating units inspected and cleaned annually by a certified specialist.</li><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Matches and Smoking</h4>\n" +
                "                    <ul>\n" +
                "                        <li> 1. Keep matches and lighters up high, away from children, and, if possible, in a locked cabinet.</li><br />\n" +
                "                        <li> 2. Never smoke in bed or when drowsy or medicated. Provide smokers with deep, sturdy ashtrays. Douse cigarette\n" +
                "                        and cigar butts with water before disposal.</li><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Electrical Wiring</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> 1. Have the electrical wiring in your residence checked by an electrician.</li><br />\n" +
                "                        <li> 2. Inspect extension cords for frayed or exposed wires or loose plugs.</li><br />\n" +
                "                        <li> 3. Make sure outlets have cover plates and no exposed wiring.</li><br />\n" +
                "                        <li> 4. Make sure wiring does not run under rugs, over nails, or across high-traffic areas.</li><br />\n" +
                "                        <li> 5. Do not overload extension cords or outlets. If you need to plug in two or three appliances, get a\n" +
                "                            UL-approved unit with built-in circuit breakers to prevent sparks and short circuits.</li><br />\n" +
                "                        <li> 6. Make sure insulation does not touch bare electrical wiring.</li><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Other</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> 1. Sleep with your door closed.</li><br />\n" +
                "                        <li> 2. Install A-B-C-type fire extinguishers in your residence and teach family members how to use them.</li><br />\n" +
                "                        <li> 3. Consider installing an automatic fire sprinkler system in your residence.</li><br />\n" +
                "                        <li> 4. Ask your local fire department to inspect your residence for fire safety and prevention. </li><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h3>What to do During a Fire</h3>\n" +
                "                    \n" +
                "                    <h4>If your clothes catch on fire, you should:</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> 1. Stop, drop, and roll - until the fire is extinguished. Running only makes the fire burn faster. </li><br /><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <strong>To escape a fire, you should:</strong><br /><br />\n" +
                "                    <ul>\n" +
                "                    <li> 1. Check closed doors for heat before you open them. If you are escaping through a closed door, \n" +
                "                        use the back of your hand to feel the top of the door, the doorknob, and the crack between the door \n" +
                "                        and door frame before you open it. Never use the palm of your hand or fingers to test for heat - burning \n" +
                "                        those areas could impair your ability to escape a fire (i.e., ladders and crawling). </li><br /><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> 2. Crawl low under any smoke to your exit - heavy smoke and poisonous gases collect first along the ceiling.</li><br />\n" +
                "                        <li> 3. Close doors behind you as you escape to delay the spread of the fire.</li><br />\n" +
                "                        <li> 4. Stay out once you are safely out. Do not reenter. Call 9-1-1. </li><br />\n" +
                "                    </ul>\n" +
                "                </div>\n" +
                "                    \n" +
                "                <!-- WILDFIRE -->\n" +
                "                <div id=\"tcontent18\" class=\"tabcontentsub\">\n" +
                "                    <h3>Wildfire</h3>\n" +
                "                    \n" +

                "                    The threat of woodland fires for people living near woodland areas or using recreational facilities \n" +
                "                    in wilderness areas is real. Dry conditions at various times of the year and in various parts of \n" +
                "                    Wisconsin greatly increase the potential for woodland fires.\n" +
                "                    <br /><br />\n" +
                "                    Advance planning and knowing how to protect buildings in these areas can lessen the devastation of a \n" +
                "                    woodland fire.  There are several safety precautions that you can take to reduce the risk of fire losses. \n" +
                "                    Protecting your home from wildfire is your responsibility. To reduce the risk, you'll need to consider \n" +
                "                    the fire resistance of your home, the topography of your property and the nature of the vegetation close by.        \n" +
                "                    <br /><br />\n </body>"));
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