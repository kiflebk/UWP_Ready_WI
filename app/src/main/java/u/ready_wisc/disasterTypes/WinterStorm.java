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
 * Displays info about winter storms
 */
public class WinterStorm extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winterstorm_info);
        t = (TextView) findViewById(R.id.textViewWinterStorm);
        t.setText(Html.fromHtml("<body> <!-- WINTER STORMS -->\n" +
                "                <div id=\"tcontent16\" class=\"tabcontentsub\">\n" +
                "                <h3>Winter Awareness</h3>\n" +
                "                    <h3>Know the Terms</h3>\n" +
                "                    <ul>\n" +
                "                        <strong>Freezing Rain</strong><br />\n" +
                "                        Rain that freezes when it hits the ground, creating a coating of ice on roads, walkways, trees, and power lines.\n" +
                "                        <br /><br />\n" +
                "                        <strong>Sleet</strong><br />\n" +
                "                        Rain that turns to ice pellets before reaching the ground. Sleet also causes moisture on roads to freeze and\n" +
                "                        become slippery.\n" +
                "                        <br /><br />\n" +
                "                        <strong>Winter Storm Watch</strong><br />\n" +
                "                        A winter storm is possible in your area. Tune in to NOAA Weather Radio, commercial radio, or television for\n" +
                "                        more information.\n" +
                "                        <br /><br />\n" +
                "                        <strong>Winter Storm Warning</strong><br />\n" +
                "                        A winter storm is occurring or will soon occur in your area.\n" +
                "                        <br /><br />\n" +
                "                        <strong>Blizzard Warning</strong><br />\n" +
                "                        Sustained winds or frequent gusts to 35 miles per hour or greater and considerable amounts of falling or blowing\n" +
                "                        snow (reducing visibility to less than a quarter mile) are expected to prevail for a period of three hours or longer.\n" +
                "                        <br /><br />\n" +
                "                        <strong>Frost/Freeze Warning</strong><br />\n" +
                "                        Below freezing temperatures are expected.\n" +
                "                        <br /><br />        \n" +
                "                        <strong>Frostbite</strong><br />\n" +
                "                        Damage to body tissue caused by extreme cold.  A wind chill of -20o Fahrenheit could cause frostbite in just\n" +
                "                        15 minutes or less.  Frostbite causes a loss of feeling and a white or pale appearance in extremities such as\n" +
                "                        fingers, toes, ear tips or the tip of the nose.  If symptoms are detected &#150; Seek medical care immediately!\n" +
                "                        <br /><br />\n" +
                "                        <strong>Hypothermia</strong><br />\n" +
                "                        A condition that develops when the body temperature drops below 95oF.  It is very deadly.  Warning signs\n" +
                "                        include uncontrollable shivering, disorientation, slurred speech and drowsiness.  Seek medical care immediately!\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Before Winter Storms and Extreme Cold</h4>\n" +
                "                    \n" +
                "                    <strong> Add the following supplies to your disaster supplies kit:</strong><br></br>\n" +
                "                    <ul>\n" +
                "                        <li> 1.Rock salt to melt ice on walkways </li><br></br>\n" +
                "                        <li> 2.Sand to improve traction </li><br></br>\n" +
                "                        <li> 3.Snow shovels and other snow removal equipment. </li><br></br>\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Prepare your home and family</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> Prepare for possible isolation in your home by having sufficient heating fuel; regular fuel sources may be\n" +
                "                            cut off. For example, store a good supply of dry, seasoned wood for your fireplace or wood-burning stove.</li><br></br>\n" +
                "                        <li> Keep fire extinguishers on hand, and make sure everyone in your house knows how to use them. House fires\n" +
                "                            pose an additional risk, as more people turn to alternate heating sources without taking the necessary safety\n" +
                "                            precautions.</li><br></br>\n" +
                "                        <li> Learn how to shut off water valves (in case a pipe bursts).</li><br></br>\n" +
                "                        <li> Know ahead of time what you should do to help elderly or disabled friends, neighbors or employees.</li>\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>Prepare your car</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li><strong> Check or have a mechanic check the following items on your car:</strong></li><br />\n" +
                "                        <ul>\n" +
                "                            <li> 1. Antifreeze levels - ensure they are sufficient to avoid freezing.</li><br></br>\n" +
                "                            <li> 2. Battery and ignition system - should be in top condition and battery terminals should be clean.</li><br></br>\n" +
                "                            <li> 3. Brakes - check for wear and fluid levels.</li><br></br>\n" +
                "                            <li> 4. Exhaust system - check for leaks and crimped pipes and repair or replace as necessary. <em>Carbon monoxide \n" +
                "                                is deadly and usually gives no warning</em>.</li><br></br>\n" +
                "                            <li> 5. Fuel and air filters - replace and keep water out of the system by using additives and maintaining a\n" +
                "                                full tank of gas.</li><br></br>\n" +
                "                            <li> 6. Heater and defroster - ensure they work properly.</li><br></br>\n" +
                "                            <li> 7. Lights and flashing hazard lights - check for serviceability.</li><br></br>\n" +
                "                            <li> 8. Oil - check for level and weight. Heavier oils congeal more at low temperatures and do not lubricate as well.</li><br></br>\n" +
                "                            <li> 9. Thermostat - ensure it works properly.</li><br></br>\n" +
                "                            <li> 10. Windshield wiper equipment - repair any problems and maintain proper washer fluid level.<br /li><br></br>\n" +
                "                        </ul>\n" +
                "                        <li> Install good winter tires. Make sure the tires have adequate tread. All-weather radials are usually adequate\n" +
                "                            for most winter conditions. However, some jurisdictions require that to drive on their roads, vehicles must\n" +
                "                            be equipped with chains or snow tires with studs.</li><br />\n" +
                "                        <li> Maintain at least a half tank of gas during the winter season.</li><br /><br />\n" +
                "                        </ul>\n" +
                "                        \n" +
                "                        <h4>On the road</h4>\n" +
                "                        <ul>\n" +
                "                            <li> 1. Check the roads: Before you leave. " +
                "                            <li> Feel the roads: When you first start out, accelerate carefully to test wheel spin and brake gently to test skidding.<br /><br />\n" +
                "                            <li> 2. Be gentle: Use the accelerator and brakes slowly to maintain control of your vehicle. Fast acceleration can make wheels spin on ice and snow. Stepping too hard on the break with lock them and cause loss of steering control.<br /><br />\n" +
                "                            <li> 3. See and be seen: Clear frost and snow off all windows, mirrors, lights and reflectors. Make sure you car has good wiper blades and an ample supply of windshild washer fluid. Using your headlights is also a good way to be seen especially in poor visibility conditions.<br /><br />\n" +
                "                            <li> 4. Increase your following distance: Ice and snow can multiply your stopping distance up to 10 times.<br /><br />\n" +
                "                            <li> 5. Make turns slowly and gradually: Heavily traveled intersections become &#147;polished&#148; and slick. Break before you come to a curve and not while you are in it.<br /><br />\n" +
                "                            <li> 6. Turn in the direction of the skid: If the rear of your car begins to slide, turn into the direction of the skid. Expect a second skid as the vehicle straightens out. <br /><br />\n" +
                "                            <li> 7. Scattered slippery areas: Icy spots on the road surface can cause loss of steering control. Don&#146;t use your break. Take your foot off the gas and steer as straight at you can until your car slows to a safe speed.<br /><br />\n" +
                "                            <li> 8. Avoid a crash: In an emergency situation you may need to steer your car off the road and into a ditch or snow bank. You may get stuck but you&#146;ll avoid a crash.<br /><br />\n" +
                "                            <li> 9. Wear your seatbelt and make sure kids are properly secured.<br /><br />\n" +
                "                            <li> 10. Keep gas in the tank: Have at least ½ tank of gas in your car in case you are stranded or stuck.<br /><br />\n" +
                "                            <li> 11. Have a winter emergency kit: Keep a kit in your vehicle with candles and matches, a flashlight, pocket knife, \n" +
                "                            snacks, a cell phone adapter, a blanket and extra clothing.<br /><br />\n" +
                "                        </ul>\n" +
                "                        \n" +
                "                        <h4>Get A Kit</h4>\n" +
                "                        <ul>\n" +
                "                        <li> <strong>Place a winter emergency kit in each car that includes:</strong> </li><br /><br />\n" +
                "                        <ul>\n" +
                "                            <li> 1. a shovel </li><br />\n" +
                "                            <li> 2. windshield scraper and small broom </li><br />\n" +
                "                            <li> 3. flashlight </li><br />\n" +
                "                            <li> 4. battery powered radio </li><br />\n" +
                "                            <li> 5. extra batteries </li><br />\n" +
                "                            <li> 6. water </li><br />\n" +
                "                            <li> 7. snack food </li><br />\n" +
                "                            <li> 8. matches </li><br />\n" +
                "                            <li> 9. extra hats, socks and mittens </li><br />\n" +
                "                            <li> 10. First aid kit with pocket knife </li><br />\n" +
                "                            <li> 11. Necessary medications </li><br />\n" +
                "                            <li> 12. blanket(s) </li><br />\n" +
                "                            <li> 13. tow chain or rope </li><br />\n" +
                "                            <li> 14. road salt and sand </li><br />\n" +
                "                            <li> 15. booster cables </li><br />\n" +
                "                            <li> 16. emergency flares </li><br />\n" +
                "                            <li> 17. fluorescent distress flag </li><br /><br />\n" +
                "                        </ul>\n" +
                "                    </ul>\n" +
                "                    <h4>Make a Plan</h4>\n" +
                "                    <ul>\n" +
                "                        <li> Make a Family Emergency Plan. Your family may not be together when disaster strikes, so it is important to know how you will contact one another, how you will get back together and what you will do in case of an emergency. <br />\n" +
                "                        <li> Plan places where your family will meet, both within and outside of your immediate neighborhood. <br /><br />\n" +
                "                        <li> It may be easier to make a long-distance phone call than to call across town, so an out-of-town contact may be in a better position to communicate among separated family members. <br /><br />\n" +
                "                        <li> You may also want to inquire about emergency plans at places where your family spends time: work, daycare and school. If no plans exist, consider volunteering to help create one. <br />\n" +
                "                    </ul>\n" +
                "                    <h4>Dress for the Weather</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> 1. Wear several layers of loose fitting, lightweight, warm clothing rather than one layer of heavy clothing.\n" +
                "                            The outer garments should be tightly woven and water repellent.</li><br />\n" +
                "                        <li> 2. Wear mittens, which are warmer than gloves.</li><br />\n" +
                "                        <li> 3. Wear a hat.</li><br />\n" +
                "                        <li> 4. Cover your mouth with a scarf to protect your lungs. </li><br />\n" +
                "                    </ul>\n" +
                "                    <h3>During a Winter Storm</h3>\n" +
                "                    \n" +
                "                    <h4>Guidelines</h4>\n" +
                "                    <ul>\n" +
                "                        <li> Listen to your radio for weather reports and emergency information.</li><br /><br />\n" +
                "                        <li> Eat regularly and drink ample fluids, but avoid caffeine and alcohol.</li><br /><br />\n" +
                "                        <li> Conserve fuel, if necessary, by keeping your residence cooler than normal. Temporarily close off heat to some rooms.</li><br /><br />\n" +
                "                        <li> If the pipes freeze, remove any insulation or layers of newspapers and wrap pipes in rags. Completely open\n" +
                "                            all faucets and pour hot water over the pipes, starting where they were most exposed to the cold (or where\n" +
                "                            the cold was most likely to penetrate).</li><br /><br />\n" +
                "                        <li> Maintain ventilation when using kerosene heaters to avoid build-up of toxic fumes. Refuel kerosene heaters\n" +
                "                            outside and keep them at least three feet from flammable objects. </li><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>If you are outdoors</h4>\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <li> Overexertion is dangerous.  Cold weather puts an added strain on the heart.  Unaccustomed exercise such as\n" +
                "                            shoveling snow or pushing a car can bring on a heart attack or make an existing medical condition worse.</li><br /><br />\n" +
                "                        <li> Cover your mouth. Protect your lungs from extremely cold air by covering your mouth when outdoors. Try not to \n" +
                "                            speak unless absolutely necessary.</li><br /><br />\n" +
                "                        <li> Keep dry. Change wet clothing frequently to prevent a loss of body heat. Wet clothing loses all of its \n" +
                "                            insulating value and transmits heat rapidly.</li><br /><br />\n" +
                "                        <li> <strong>If symptoms of hypothermia are detected:</strong></li><br /><br />\n" +
                "                        <ul>\n" +
                "                            <li> 1. get the victim to a warm location </li><br />\n" +
                "                            <li> 2. remove wet clothing </li><br />\n" +
                "                            <li> 3. put the person in dry clothing and wrap their entire body in a blanket </li><br />\n" +
                "                            <li> 4. warm the center of the body first </li><br />\n" +
                "                            <li> 5. give warm, non-alcoholic or non-caffeinated beverages if the victim is conscious </li><br />\n" +
                "                            <li> 6. get medical help as soon as possible.</li><br />\n" +
                "                        </ul> \n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4>If you are driving</h4>\n" +
                "\n" +
                "                    <ul>\n" +
                "                        <li> Drive only if it is absolutely necessary. If you must drive, consider the following:</li><br />\n" +
                "                        <li> 1. Travel in the day, don&#146;t travel alone, and keep others informed of your schedule.</li><br />\n" +
                "                        <li> 2. Stay on main roads; avoid back road shortcuts.</li><br />\n" +
                "                        <li> 3. <strong>If a blizzard traps you in the car:</strong></li><br /><br />\n" +
                "                        <ul>\n" +
                "                            <li><strong> A.</strong> Pull off the highway. Turn on hazard lights and hang a distress flag from the radio antenna or window.</li><br />\n" +
                "                            <li><strong> B.</strong> Remain in your vehicle where rescuers are most likely to find you. </li><br />\n" +
                "                            <li><strong> C.</strong> Run the engine and heater about 10 minutes each hour to keep warm. When the engine is running, open a downwind window slightly for ventilation and periodically clear snow from the exhaust pipe. This will protect you from possible carbon monoxide poisoning.</li><br />\n" +
                "                            <li> <strong>D.</strong> Exercise to maintain body heat, but avoid overexertion. In extreme cold, use road maps, seat covers, and floor mats for insulation. Huddle with passengers and use your coat for a blanket.</li><br />\n" +
                "                            <li> <strong>E.</strong> Take turns sleeping. One person should be awake at all times to look for rescue crews.</li<br />\n" +
                "                            <li> <strong>F.</strong> Drink fluids to avoid dehydration.</li><br />\n" +
                "                            <li> <strong>G.</strong> Be careful not to waste battery power. Balance electrical energy needs - the use of lights, heat, and radio - with supply.</li><br />\n" +
                "                            <li><strong> H.</strong> Turn on the inside light at night so work crews or rescuers can see you.</li><br />\n" +
                "                            <li> <strong>I.</strong> If stranded in a remote area, stomp large block letters in an open area spelling out HELP or SOS and line with rocks or tree limbs to attract the attention of rescue personnel who may be surveying the area by airplane.</li><br />\n" +
                "                            <li> <strong>J.</strong> Leave the car and proceed on foot - if necessary - once the blizzard passes. </li><br />\n" +
                "                        </ul>\n" +
                "                    </ul>\n" +
                "                </div>\n </body>"));
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