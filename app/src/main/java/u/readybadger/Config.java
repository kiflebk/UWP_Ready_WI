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

package u.readybadger;

//Config file to handle data for rest of app

import u.readybadger.Counties.County;

public class Config {

    private Config() {
        throw new AssertionError();
    }

    public static final String RIVER_LINK = "http://water.weather.gov/ahps2/ahps_kml.php?state=wi&fcst_type=obs";

    //        TODO URLs are for dev server, must be changed to new address if dev server is being used.
//   public static final String DAMAGE_REPORT_URL = "http://www.joshuaolufs.com/php/query_damageReports_insert.php?";
//
//    public static final String DB_UPDATE_URL = "http://www.joshuaolufs.com/php/query_Contacts_select.php?x=";
//
//    public static final String SHELTER_UPDATE_URL = "http://www.joshuaolufs.com/php/query_Shelters_select.php?x=";
    // URL to the production server
//    public static final String DAMAGE_REPORT_URL = "http://joshuaolufs.com/projects/ReadyBadger/api/index.php/Damagereport/newreport";
    public static final String DAMAGE_REPORT_URL = "http://www.eagleweather.com/readyBadger//php/query_damageReports_insert.php?";
    public static final String DB_UPDATE_URL = "http://www.eagleweather.com/readyBadger//php/query_Contacts_select.php?x=";
    public static final String SHELTER_UPDATE_URL = "http://www.eagleweather.com/readyBadger//php/query_Shelters_select.php?x=";
    public static final String EMERGENCY_MAP_URL = "https://www.google.com/maps/search/emergency+";
    public static final String GET_KIT_URL = "http://readywisconsin.wi.gov/kit/GetKit.asp";
    public static final String MAKE_PLAN_URL = "http://readywisconsin.wi.gov/Plan/Plan.asp?maintab=0";

    // URL for WisDOT Alert
    public static final String WISDOT_URL = "http://www.511wi.gov/web/api/alerts?key=b67502601891425ea4da2f825860c1fa&format=json";

    public static County countyPrim;


    //Disaster Type Links for more INFO
    public static final String BOMB_LINK = "http://emilms.fema.gov/is906/assets/ocso-bomb_threat_samepage-brochure.pdf";
    public static final String HEAT_LINK = "http://readywisconsin.wi.gov/media/pdf/Extreme_Heat.pdf";
    public static final String FIRE_lINK = "http://readywisconsin.wi.gov/media/pdf/Fire.pdf";
    public static final String FLOOD_LINK = "http://readywisconsin.wi.gov/media/pdf/Flooding.pdf";
    public static final String THUNDER_LINK = "http://readywisconsin.wi.gov/media/pdf/Thunderstorms.pdf";
    public static final String WINTER_LINK = "http://readywisconsin.wi.gov/media/pdf/Winter_Handout.pdf";

    // Disaster Type INFO
    public static final String TORNADO_INFO;

    static {
        TORNADO_INFO = ("<body><!-- TORNADOES -->\n" +
                "                <div id=\"tcontent15\" class=\"tabcontentsub\">\n" +
                "                    <h3>Know the Terms</h3>\n" +
                "                    \n" +
                "\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <strong>Tornado Watch</strong><br />\n" +
                "                            Tornadoes are possible. Remain alert for approaching storms. Watch the sky and stay tuned to \n" +
                "                            a commercial radio, or television for information.<br /><br />\n" +
                "                        <strong>Tornado Warning</strong><br />\n" +
                "                            A tornado has been sighted or indicated by weather radar. Take shelter immediately.<br /><br />\n" +
                "                    </ul>\n" +
                "                    \n" +
                "                    <h4> 9 Tornado Facts</h4>\n" +
                "\n" +
                "                    <ul>\n" +
                "                        <li> 1. Wisconsin averages 23 tornadoes a year.<br /li><br></br>\n" +
                "                        <li> 2. The peak tornado season in Wisconsin is April to August, but tornadoes can occur any time of year.<br /li><br></br>\n" +
                "                        <li> 3. Tornadoes can occur any time during the day or night, but are most frequent between 4 p.m. and 9 p.m.<br /li><br></br>\n" +
                "                        <li> 4. About 80% of tornadoes that hit Wisconsin are relatively weak, with winds under 100 mph.  Only 1% are violent with winds over 200 mph.<br /li><br></br>\n" +
                "                        <li> 5. They may strike quickly, with little or no warning. <br /li><br></br>\n" +
                "                        <li> 6. They may appear nearly transparent until dust and debris are picked up or a cloud forms in the funnel. <br /li><br></br>\n" +
                "                        <li> 7. The average tornado moves southwest to northeast, but tornadoes have been known to move in any direction. <br /li><br></br>\n" +
                "                        <li> 8. The average forward speed of a tornado is 30 MPH, but may vary from stationary to 70 MPH. <br /li><br></br>\n" +
                "                        <li> 9. Waterspouts are tornadoes that form over water.<br /li><br></br>\n" +
                "            \n" +
                "                    </ul>\n" +
                "\n" +
                "                    <h4>What to do Before a Tornado</h4>\n" +
                "\n" +
                "                    Be alert to changing weather conditions.<br /li><br></br>\n" +
                "\n" +
                "                    <ul>\n" +
                "                        <li> Listen to a commercial radio or television newscasts for information.<br /li><br></br>\n" +
                "\n" +
                "                        <li> Look for approaching storms <br /li><br></br>\n" +
                "\n" +
                "                        <li> Look for the following danger signs: <br /li><br></br>\n" +
                "\n" +
                "                        <ul>\n" +
                "                            <li> 1. Dark, often greenish sky </li><br></br>\n" +
                "                            <li> 2. Large hail </li><br></br>\n" +
                "                            <li> 3. A large, dark, low-lying cloud (particularly if rotating)</li><br></br>\n" +
                "                            <li> 4. Loud roar, similar to a freight train. </li><br></br>\n" +
                "                        <br /ul>\n" +
                "                    </ul>\n" +
                "\n" +
                "                    If you see approaching storms or any of the danger signs, be prepared to take shelter immediately.\n" +
                "\n" +
                "                </div>\n </body>");
    }

    public static final String WINTER_STORM;

    static {
        WINTER_STORM = ("<body> <!-- WINTER STORMS -->\n" +
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
                "                </div>\n </body>");
    }

    public static final String THUNDER_STORM;

    static {
        THUNDER_STORM = ("<body>                <!-- THUNDERSTORMS -->\n" +
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
                "                    </ul>  </body>");
    }

    public static final String TERRORISM;

    static {
        TERRORISM = ("<body> <!-- TECHNOLOGICAL THREATS -->\n" +

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
                "            </div>\n </body>");
    }

    public static final String PHE;

    static {
        PHE = ("<body>             <!-- PANDEMIC -->\n" +
                "            <div id=\"tcontent3\" class=\"tabcontentmain\">\n" +
                "                <h3>Influenza Pandemic</h3>\n" +
                "                \n" +
                "                <div class=\"statImgBlock\">\n" +
                "                    <div style=\"margin-left: auto; margin-right: auto; width: 200px;\">\n" +
                "                    <script type=\"text/javascript\" src=\"http://transparency.cit.nih.gov/widgets/swinelinks.cfm?javascript\"></script><noscript><iframe src=\"http://transparency.cit.nih.gov/widgets/swinelinks.cfm\" name=\"swineframe\" frameborder=\"0\" id=\"swineframe\" scrolling=\"no\" height=\"160\" width=\"198\" marginheight=\"0\" marginwidth=\"0\"></iframe></noscript>\n" +
                "                    </div>\n" +
                "                \n" +
                "                An influenza (flu) pandemic is a global outbreak of a new flu virus that can spread easily from person to person. \n" +
                "                Like the seasonal flu many people experience every year, pandemic flu will probably spread by infected people \n" +
                "                coughing or sneezing and by touching an infected surface. Unlike seasonal flu, people will have little immunity \n" +
                "                to the new flu virus that causes a pandemic, and many more people will get sick.<br /><br />\n" +
                "                \n" +
                "                Wisconsin is taking an aggressive approach to preparing for  a pandemic flu outbreak \n" +
                "                in humans or animals. <br /><br />\n" +
                "                \n" +
                "                <h4>What To Expect In An Influenza Pandemic</h4>\n" +
                "                \n" +
                "                When a flu pandemic occurs, it will likely be a prolonged and widespread outbreak that could require \n" +
                "                temporary changes in many parts of our everyday lives, including schools, work, transportation and \n" +
                "                other public services. Being informed and prepared for what will happen will decrease your risk. \n" +
                "                The following are some situations to expect in a flu pandemic:<br > <br></br>\n" +
                "                \n" +
                "                <ul>\n" +
                "                    <li> 1. Hospitals and doctors might be overwhelmed with sick patients. </li><br></br>\n" +
                "                    <li> 2. Schools and businesses might close to keep the virus from spreading or because too many people are sick. </li><br></br>\n" +
                "                    <li> 3. Essential supplies and services may become limited or unavailable. </li><br></br>\n" +
                "                    <li> 4. Travel and public gatherings might be limited to keep the virus from spreading. </li><br></br>\n" +
                "                    <li> 5. Public health officials may suggest using isolation or quarantine measures to control the spread of infection. </li><br></br>\n" +
                "                    <li> 6. There may not be a vaccine to protect people against the pandemic flu. </li><br></br>\n" +
                "                    <li> 7. Antiviral medicines may be in limited supply. If vaccines or antiviral medicines are available, you may be<br></br>\n" +
                "                        asked to go to a certain community location to get vaccinated or receive the medicine. </li><br></br>\n" +
                "                    <li> 8. The pandemic could last a long time. Sometimes, there are several waves of illness that occur over a series\n" +
                "                        of months or even more than a year. <br /li><br></br>\n" +
                "                </ul>\n" +
                "                <h4>What To Do</h4>\n" +
                "                \n" +
                "                Every Wisconsinite has an important role to play in preparing for a flu pandemic and helping \n" +
                "                to prevent the spread of influenza:<br> <br></br>\n" +
                "                \n" +
                "                <ul>\n" +
                "                    <li> 1. Get an emergency supply kit that includes enough provisions for you and your family to live on for a\n" +
                "                        minimum of three days. </li><br></br>\n" +
                "                    <li> 2. Make an emergency plan for you and your family. </li><br></br>\n" +
                "                    <li> 3. Practice good hygiene and wash your hands frequently. </li><br></br>\n" +
                "                    <li> 4. Cover your nose and mouth when coughing or sneezing. </li><br></br>\n" +
                "                    <li> 5. Regularly clean surfaces that are touched by multiple people. </li><br></br>\n" +
                "                    <li> 6. Stay home from work or school when you are sick. </li><br></br>\n" +
                "                    <li> 7. Stay healthy by eating a balanced diet, getting regular exercise and getting enough rest. </li><br></br>\n" +
                "                    <li> 8. Get a yearly flu vaccination, especially if you are at high risk for flu complications. </li><br></br>\n" +
                "                    <li> 9. Discuss individual health concerns with your doctor. </li><br></br>\n" +
                "                    <li> 10. Plan to help your family, friends and neighbors, especially those who live alone or may need assistance in an emergency. </li><br></br>\n" +
                "                    <li> 11. Stay informed about pandemic influenza and be prepared to respond.<br /li><br></br>\n" +
                "                </ul>\n" +
                "            </div> </body>");
    }

    public static final String POWER_OUT;

    static {
        POWER_OUT = ("<body> <h1 class=\"large\">Power outage</h1><br>\n" +
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
                "<br><br><br><br>\n </body>");
    }

    public static final String FLOOD;

    static {
        FLOOD = ("<body>                 <!-- FLOODING -->\n" +
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
                "                </div> </body>");
    }

    public static final String FIRE;

    static {
        FIRE = ("<body><!-- HOUSE FIRE -->\n" +
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
                "                    <br /><br />\n </body>");
    }

    public static final String HEAT;

    static {
        HEAT = ("<body> <!-- EXTREME HEAT -->\n" +
                "                <div id=\"tcontent12\" class=\"tabcontentsub\">\n" +
                "                    <h3>Extreme Heat</h3>\n" +
                "                    \n" +
                "                    \n" +
                "                    <strong>Know the Terms</strong><br /><br />\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <strong>Heat Wave</strong><br />\n" +
                "                        Prolonged period of excessive heat, often combined with excessive humidity.<br /><br />\n" +
                "                        <strong>Heat Index</strong><br />\n" +
                "                        A number in degrees Fahrenheit (F) that tells how hot it feels when relative humidity is added to the air \n" +
                "                        temperature. Exposure to full sunshine can increase the heat index by 15 degrees.<br /><br />\n" +
                "                        <strong>Heat Cramps</strong><br />\n" +
                "                        Muscular pains and spasms due to heavy exertion. Although heat cramps are the least severe, they are often \n" +
                "                        the first signal that the body is having trouble with the heat.<br /><br />\n" +
                "                        <strong>Heat Exhaustion</strong><br />\n" +
                "                        Typically occurs when people exercise heavily or work in a hot, humid place where body fluids are lost \n" +
                "                        through heavy sweating. Blood flow to the skin increases, causing blood flow to decrease to the vital organs. \n" +
                "                        This results in a form of mild shock. If not treated, the victim&#146;s condition will worsen. Body temperature will \n" +
                "                        keep rising and the victim may suffer heat stroke.<br />\n" +
                "                    </ul>\n" +
                "\n" +
                "                    <strong>Heat Stroke</strong><br />\n" +
                "                    A life-threatening condition. The victim&#146;s temperature control system, which produces sweating to cool the body, \n" +
                "                    stops working. The body temperature can rise so high that brain damage and death may result if the body is not \n" +
                "                    cooled quickly.<br /><br />\n" +
                "\n" +
                "                    <strong>Sun Stroke</strong><br />\n" +
                "                    Another term for heat stroke.<br /><br />\n" +
                "\n" +
                "                    <strong>Heat Facts</strong><br /><br />\n" +
                "                    Summer heat waves are the biggest weather-related killers in Wisconsin for the past 50 years, far\n" +
                "                    exceeding tornado and other storm-related deaths.  In 1995, two major killer heat waves affected\n" +
                "                    most of Wisconsin resulting in 154 heat-related deaths and over 300 heat-related illnesses.<br /><br />\n" +
                "\n" +
                "                    Citizens of the State of Wisconsin can be seriously affected by severe heat, and it is essential\n" +
                "                    that we increase awareness of the dangers of heat waves and the protective actions which can be\n" +
                "                    taken by citizens.<br /><br />\n" +
                "\n" +
                "                    <h3>National Weather Service Heat - Wave Program in Wisconsin</h3>\n" +
                "                    \n" +
                "                    <strong>Outlook Statement</strong><br /><br />\n" +
                "                    Issued 2 to 7 days in advance of when Heat Advisory or Excessive Heat Warning conditions are anticipated.\n" +
                "                    Issued as a Hazardous Weather Statement (HWO). Broadcasted on NOAA Weather Radio All Hazards, and posted on \n" +
                "                    NWS web sites (www.weather/gov).<br /><br />\n" +
                "                    <strong>Heat Advisory</strong><br /><br />\n" +
                "                    Issued 6 to 24 hours in advance of any 24-hour period in which daytime heat index (HI) values are expected\n" +
                "                    to be 105-110 for 3 hours or more and night-time HI values will be 75 or higher. For west-central and northwest\n" +
                "                    Wisconsin, numbers are 105-114 and 80, respectively.<br /><br />\n" +
                "                    <strong>Excessive Heat Watch</strong><br /><br />\n" +
                "                    Issued generally 12 to 48 hours in advance of any 24-hour period in which daytime heat index (HI) values\n" +
                "                    are expected to exceed 110 for 3 hours or more and night-time HI values will be 80 or higher. For west-central\n" +
                "                    and northwest Wisconsin, numbers are 115 or higher, and 80 or higher, respectively.<br /><br />\n" +
                "                    <strong>Excessive Heat Warning</strong><br /><br />\n" +
                "                    Issued 6 to 24 hours in advance of any 24-hour period in which daytime heat index (HI) values are\n" +
                "                    expected to exceed 110 for 3 hours or more and night-time HI values will be 80 or higher. For west-central\n" +
                "                    and northwest Wisconsin, numbers are 115 or higher, and 80 or higher, respectively.<br /><br />\n" +
                "                    <strong>During a Heat Emergency</strong><br /><br />\n" +
                "                    What you should do if the weather is extremely hot:<br /><br />\n" +
                "                    \n" +
                "                    <ul>\n" +
                "                        <strong>Never leave children, disabled persons, or pets in a parked car &#150; even\n" +
                "                        briefly.</strong> Temperatures in a car can become life threatening within minutes.<br /><br />\n" +
                "                        \n" +
                "                        <strong>Keep your living space cool.</strong> Cover windows to keep the sun from shining\n" +
                "                        in. If you don&#146;t have an air conditioner, open windows to let air circulate.\n" +
                "                        When it&#146;s hotter than 95 degrees, use fans to blow hot air out of the window,\n" +
                "                        rather than to blow hot air on to your body. Basements or ground floors are\n" +
                "                        often cooler than upper floors.<br /><br />\n" +
                "                        \n" +
                "                        <strong>Slow down and limit physical activity.</strong> Plan outings or exertion for the\n" +
                "                        early morning or after dark, when temperatures are cooler.<br /><br />\n" +
                "\n" +
                "                        <strong>Drink plenty of water and eat lightly.</strong> Don&#146;t wait for thirst, but instead drink\n" +
                "                        plenty of water throughout the day. Avoid alcohol or caffeine and stay away\n" +
                "                        from hot, heavy meals.<br /><br />\n" +
                "\n" +
                "                        <strong>Wear lightweight, loose-fitting, light colored clothing.</strong> Add a hat or\n" +
                "                        umbrella to keep your head cool&#133;and don&#146;t forget sunscreen!<br /><br />\n" +
                "\n" +
                "                        <strong>Don&#146;t stop taking medication unless your doctor says you should.</strong> Take\n" +
                "                        extra care to stay cool, and ask your doctor or pharmacist for any special heat\n" +
                "                        advice.<br /><br />\n" +
                "                        \n" +
                "                        <strong>Taking a cool shower or bath will cool you down.</strong><br />In fact, you will cool down faster than \n" +
                "                        you will in an air-conditioned room!  Also, applying cold wet rags to the neck, head and limbs will cool \n" +
                "                        down the body quickly.<br /><br />\n" +
                "                    </ul>\n" +
                "\n" +
                "                    <strong>People at higher risk of a heat related illness include:</strong><br />\n" +
                "                    <ul>\n" +
                "                        <li> 1. Older adults</li><br />\n" +
                "                        <li> 2. Infants and young children</li><br />\n" +
                "                        <li> 3. People will chronic heart or lung problems</li><br />\n" +
                "                        <li> 4. People with disabilities</li><br />\n" +
                "                        <li> 5. Overweight persons</li><br />\n" +
                "                        <li> 6. Those who work outdoors or in hot settings</li><br />\n" +
                "                        <li> 7. Users of some medications, especially those taken for mental disorders,</li><br />\n" +
                "                        <li> 8. Movement disorder, allergies, depression, and heart or circulatory problems</li><br />\n" +
                "                        <li> 9. People that are isolated who don&#146;t know when or how to cool off &#150; or when to call for help</li><br /><br />\n" +
                "                    </ul>\n" +
                "\n" +
                "                    <strong>For additional information about heat awareness, contact your local public health\n" +
                "                    department or county emergency management director.</strong>\n" +
                "                </div>\n </body>");
    }

    public static final String BOMB_THREAT;

    static {
        BOMB_THREAT = ("<body><strong>BOMB THREATS</strong><br />\n" +
                "\n" +
                "<p> Most bomb threats are received by phone. Bomb threats are serious until proven " +
                "otherwise. Act quickly, but \n" +
                "remain calm and obtain information with the checklist from the download link below\n" +
                "</p><br /> \n" +
                "<strong>WHO TO CONTACT </strong><br />\n" +
                "• Follow your local guidelines<br />\n" +
                "• Federal Protective Service (FPS) Police\n" +
                " 1-877-4-FPS-411 (1-877-437-7411)<br />" +
                "• 911<br /><br /><br />\n" +

                "Information Provided by the Department Of Homeland Security<br /><br />\n" +
                "<ul>\n" +
                "</body>");
    }


    public static final String SANITATION_SUPP;

    static {
        SANITATION_SUPP = ("<body><div id=\"tcontent16\" class=\"tabcontentsub\">\n" +
                "<h3>Sanitation Supplies</h3>\n" +
                "\n" +
                "\n" +
                "<ul>\n" +
                "<li>   1. Toilet paper                 </li><br />\n" +
                "<li>   2. Soap, liquid detergent       </li><br />\n" +
                "<li>   3. Feminine supplies and personal hygiene items                 </li><br />\n" +
                "<li>   4. Plastic garbage bags, ties (for personal sanitation uses)    </li><br />\n" +
                "<li>   5. Plastic bucket with tight lid    </li><br />\n" +
                "<li>   6. Disinfectant                     </li><br />\n" +
                "<li>   7. Household chlorine bleach        </li><br />\n" +
                "</ul>\n" +
                "</div>\n</body>");
    }


    public static final String ESSENTIALS;

    static {
        ESSENTIALS = ("<div id=\"tcontent11\" class=\"tabcontentsub\">\n" +
                "\t\t\t\t"

                + "<h3> Essentials  </h3>\n" +

                "\t\t\t\t"

                + " Start out thinking about the basics of survival &#150; fresh water, food, safety, warmth, sanitation" +

                " and clean air.\n" +

                "\t\t\t\t\n" +
                "\t\t\t\t"

                + "<h4> For Everyone  </h4>\n" +

                "\t\t\t\t<ul>\n" +
                "\t\t\t\t\t"

                + "<li> Water (1 gallon per person per day for 3 days) </li>\n" +

                "\t\t\t\t\t"
                + ""
                + "<li> Food that does not need electricity for storage or preparation  </li>\n" +

                "\t\t\t\t\t"

                + " <li> Manual can opener (if kit contains canned food)     </li>\n" +

                "\t\t\t\t\t"
                + ""
                + "<li> Battery-powered NOAA Weather Radio and a commercial radio or hand crank radio   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Flashlights and extra batteries </li>\n" +

                "\t\t\t\t\t"

                + "<li> Sleeping bag or warm blanket for each person    </li>\n" +

                "\t\t\t\t\t"

                + "<li> First aid kit and emergency medical reference manual    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Prescription medications and eyewear    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Mess kits, paper cups, plates and plastic utensils, paper towels, moist towelettes, garbage bags and ties   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Complete change of clothing, including a long-sleeved shirt, long pants, socks and sturdy shoes </li>\n" +

                "\t\t\t\t"
                + "</ul>\n" +
                "\t\t\t\t\n" +

                "\t\t\t\t"

                + "<h4 class=\"cb\">    For Baby    </h4>\n" +

                "\t\t\t\t"
                + "<ul>\n" +
                "\t\t\t\t\t"

                + "<li> Formula </li>\n" +

                "\t\t\t\t\t"

                + "<li> Bottled water to mix with formula and to wash bottles</li>\n" +
                "\t\t\t\t\t"

                + "<li> Bottles </li>\n" +

                "\t\t\t\t\t"

                + "<li> Blankets (both emergency blankets and receiving blankets)   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Diapers &#150; keep the diaper size current </li>\n" +

                "\t\t\t\t\t"

                + "<li> Disposable wipes    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Copy of a current shot record   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Bath towels and wash cloths </li>\n" +

                "\t\t\t\t\t"

                + "<li> Burp cloths, bibs   </li>\n" +

                "\t\t\t\t\t"
                + "<li> Cotton swabs    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Medications </li>\n" +

                "\t\t\t\t\t"

                + "<li> Diaper rash ointment    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Binkies and toys    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Cotton swabs    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Medications </li>\n" +

                "\t\t\t\t\t"

                + "<li> Diaper rash ointment    </li>\n" +

                "\t\t\t\t   "

                + "</ul>\n" +

                "\t\t\t\t\n" +
                "\t\t\t\t"

                + "<h4> For Pets    </h4>\n" +

                "\t\t\t\t"

                + "<ul>\n" +

                "\t\t\t\t\t"

                + "<li> Identification tags on collars  </li>\n" +

                "\t\t\t\t\t"

                + "<li> Medications, immunization records   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Food, drinking water, bowls, cat litter/pan and can opener  </li>\n" +

                "\t\t\t\t\t"

                + "<li> Sturdy leashes or carriers to transport pets safely (Carriers should be large enough for the pet to stand up, turn around and lie down) </li>\n" +

                "\t\t\t\t\t"

                + "<li> Towels or blankets  </li>\n" +

                "\t\t\t\t\t"

                + "<li> Current photos of you with your pets    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Feeding schedules, medical conditions, behavior problems, and the name and number of your veterinarian  </li>\n" +

                "\t\t\t\t\t"

                + "<li> Pet beds and toys   </li>\t \n" +
                "\t\t\t\t"
                + "</ul>\n" +
                "\t\t\t\t\n" +

                "\t\t\t\t"

                + "<h4> Additional Items    </h4>\n" +

                "\t\t\t\t"
                + "<ul>\n" +

                "\t\t\t\t\t"

                + "<li> Cash in small denominations or traveler's checks and change </li>\n" +

                "\t\t\t\t\t"

                + "<li> Copies of important family documents, such as insurance policies, identification and bank account records in a waterproof, portable container   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Wrench or pliers to turn off utilities  </li>\n" +

                "\t\t\t\t\t"

                + "<li> Matches in a waterproof container   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Household chlorine bleach and medicine dropper (When diluted nine parts water to one part bleach it can be used to disinfect)   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Dust mask (to help filter contaminated air) and plastic sheeting/duct tape (to shelter where you are)   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Local maps  </li>\n" +

                "\t\t\t\t\t"

                + "<li> Books, games, puzzles or other activities for children  </li>\n" +

                "\t\t\t\t\t"

                + "<li> Paper and pencil    </li>\n" +

                "\t\t\t\t\t"

                + "<li> Fire Extinguisher   </li>\n" +

                "\t\t\t\t\t"

                + "<li> Whistle to signal for help  </li>\n" +

                "\t\t\t\t"
                + "</ul>\n" +
                "\t\t\t"
                + "</div>");
    }


    public static final String WATER;

    static {
        WATER = ("<div id=\"tcontent12\" class=\"tabcontentsub\">\n" +
                "\t\t\t\t"

                + "<h3> Water   </h3>\n" +

                "\t\t\t\n" +
                " You can purchase commercially bottled water. Make sure you check the expiration date.   <br />"

                + "\t\t\t\n"


                + "<h4> If You are Preparing Your Own Containers of Water:  </h4>\n" +

                "\n" +
                "<strong> Purchased food-grade containers </strong><br />\n" +

                "\t\t\t\t"
                + "It&#146;s best to purchase food-grade water storage containers from surplus or camping supplies stores. Before filling with\n" +
                "\t\t\t\t"
                + " water, thoroughly clean the containers with dishwashing soap and water, and rinse completely so there is no residual soap.\n"

                + " Follow directions below for filling the containers with water. <br /><br />\n" +

                "\n"
                + "<strong> Your own containers </strong>   <br />\n" +

                "\t\t\t\t"

                + "Choose two-liter plastic soft drink bottles &#150; not plastic jugs or cardboard containers that have had milk or fruit\n" +
                "juice in them. <br /><br />\n" +


                "\t\t\t\t"

                + "Milk protein and fruit sugars cannot be adequately removed from these containers and provide an environment for bacterial" +
                "growth when water is stored in them.  Cardboard containers also leak easily and are not designed for long-term storage of liquids. Also, do not use glass\n" +
                "containers, because they can break and are heavy.    <br /><br />\n" +

                "\t\t\t\t"

                + "Thoroughly clean the bottles with dishwashing soap and water, and rinse completely so there is no residual soap. <br /><br />\n" +

                "\t\t\t\t"

                + "Sanitize the bottles by adding a solution of 1 teaspoon of non-scented liquid household chlorine bleach to a quart of water. \n" +
                "Swish the sanitizing solution in the bottle so that it touches all surfaces. After sanitizing the bottle, thoroughly\n" +
                "rinse out the sanitizing solution with clean water.\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t"
                + "<h4 class=\"cb\">    Filling Water Containers    </h4>\n" +

                "\t\t\t\t"

                + "Fill the bottle to the top with regular tap water.  If the tap water has been commercially treated from a water utility\n" +
                "with chlorine, you do not need to add anything else to the water to keep it clean. <br /><br />\n" +

                "\t\t\t\t"

                + "<ul style=\"list-style-position: inside;\">"

                + "If the water you are using comes from a well or water source that is not treated with chlorine:<br /><br />\n" +

                "<li>1. Add two drops of non-scented liquid household chlorine bleach to the water. </li><br />\n" +

                "<li>2. Tightly close the container using the original cap. </li><br />\n" +


                "<li>3. Be careful not to contaminate the cap by touching the inside of it with your finger.    </li><br />\n" +

                "<li>4. Place a date on the outside of the container so that you know when you filled it.   </li><br />\n" +

                "<li>5. Store in a cool, dark place.    </li><br />\n" +
                "<li>6. Replace the water every six months if not using commercially bottled water. </li>\n" +

                "</ul>\n" +
                "\t\t\t"

                + "</div>");
    }


    public static final String FOOD;

    static {
        FOOD = ("<div id=\"tcontent13\" class=\"tabcontentsub\">\n" +
                "\t\t\t\t"

                + "<h3> Food    </h3>" +

                "Store at least a three-day supply of non-perishable food. Select foods that require no refrigeration, preparation or\n" +
                "cooking and little or no water. <br /><br />\n" +

                "\n" +

                "If you must heat food, pack a can of sterno. If you use a barbecue grill for cooking, do not use it indoors. <br /><br />\n" +

                "\n" +
                "Select food items that are compact and lightweight. Avoid foods that will make you thirsty. Choose salt-free crackers," +
                "whole grain cereals, and canned foods with high liquid content: \n" +

                "<br />"

                + "<ul> \n" +

                "<br />"

                + "<li>1. Ready-to-eat canned meats, fruits and vegetables <br />\n" +

                "<li>2. Canned juices, milk, soup (if powdered, store extra water) <br />\n" +

                "<li>3. Staples--sugar, salt, pepper <br />\n" +

                "<li>4. High energy foods--peanut butter, jelly, crackers, granola bars, trail mix<br /> \n" +

                "<li>5. Vitamins<br /> \n" +

                "<li>6. Foods for infants, elderly persons or persons with special dietary needs<br /> \n" +
                "<li>7. Comfort/stress foods--cookies, hard candy, sweetened cereals, lollipops, instant coffee, tea bags <br />\n" +

                "\t\t\t\t"

                + "</ul>    <span class=\"cb\"> </span>\n" +

                "\t\t\t"

                + "</div>");

    }


    public static final String FIRSTAID;

    static {
        FIRSTAID = ("<div id=\"tcontent14\" class=\"tabcontentsub\">\n" +
                "\t\t\t\t"

                + "<h3> First Aid   </h3>\n" +

                "\t\t\t\t"
                + "Many injuries are not life threatening and do not require immediate medical attention. Knowing how to treat minor" +
                "injuries can make a difference in an emergency. <br /><br />\n" +

                "\t\t\t\t\n" +
                "Consider taking a first aid class, through the American Red Cross. Also, put together a first aid kit or purchase" +
                "a kit with the following items: <br /><br />\n" +

                "\t\t\t\t\n" +
                "<strong>Things you should have: </strong>\n" +
                "<br /><ul>\n" +

                "<li> 1. Two pairs of Latex, or other sterile gloves (if you are allergic to Latex). </li><br />\n" +
                "<li>2.  Sterile dressings to stop bleeding.     </li><br />\n" +
                "<li>3. Cleansing agent/soap and antibiotic towelettes to disinfect.    </li><br /> \n" +

                "<li>4. Antibiotic ointment to prevent infection.   </li><br />\n" +
                "<li>5. Burn ointment to prevent infection. </li><br />\n" +
                "<li>6. Adhesive bandages in a variety of sizes. </li><br />\n" +
                "<li>7. Eye wash solution to flush the eyes or as general decontaminant. </li><br />\n" +
                "<li>8. Thermometer </li><br /><br />\n" +
                "</ul>\n" +
                "\t\n" +
                "\t\t\t\t"

                + "<span class=\"cb\"> <strong> You may also want to include:  </strong> </span><br />\n" +
                "<ul>\n" +

                "\t\t\t\t\t"

                + "<li>1. Prescription medications you take every day such as insulin, heart medicine and asthma inhalers." +

                "You should periodically rotate medicines to account for expiration dates. </li><br />\n" +
                "<li>2. Prescribed medical supplies such as glucose and blood pressure monitoring equipment and supplies.   </li><br /><br />\n" +
                "</ul>\n" +
                "\t\t\t\t\n" +

                "<strong>Things it may be good to have:</strong><br />\n" +
                "<ul>\n" +

                "\t\t\t\t\t"

                + "<li>1. Scissors </li><br />\n" +
                "<li>2. Tweezers </li><br />\n" +
                "<li>3. Tube of petroleum jelly or other lubricant </li><br /><br />\n" +

                "\t\t\t\t"

                + "</ul>\n" +

                "\t\t\t\t\n" +
                "<strong>Non-prescription drugs:</strong>\n" +
                "\t\t\t\t"

                + "<ul></li><br />\n" +
                "<li>1. Aspirin or non-aspirin pain reliever </li><br />\n" +
                "<li>2. Anti-diarrhea medication </li><br />\n" +
                "<li>3. Antacid (for upset stomach) </li><br />\n" +
                "<li>4. Laxative </li><br />\n" +
                "<li>5. Cold medications </li><br /><br />\n" +
                "</ul>\n" +
                "\t\n" +
                "\t\t\t\t"

                + "<strong>Contact your local American Red Cross chapter to learn more about first aid training. </strong>\n" +
                "\t\t\t"

                + "</div>");
    }


    public static final String BEDDING;

    static {
        BEDDING = ("<div id=\"tcontent15\" class=\"tabcontentsub\">\n" +
                "\t\t\t\t"

                + "<h3> Bedding &amp; Clothing  </h3>\n" +


                "Include at least one complete change of clothing and footwear per person.<br /><br />\n" +
                "<ul>\n" +
                "<li>1. Blankets or sleeping bags for each person   </li><br />" +
                "<li>2. Jacket or coat </li><br />" +
                "<li>3. Long pants </li><br />" +
                "<li>4. Long sleeve shirt</li><br /> " +
                "<li>5. Sturdy shoes or work boots </li><br />" +
                "<li>6. Hat, gloves and scarf </li><br />" +
                "<li>7. Rain gear </li><br />" +
                "<li>8. Thermal underwear </li><br />" +
                "<li>9. Sunglasses </li><br />"

                + "<span class=\"cb\">  </span>\n" +
                "\t\t\t\t"

                + "</ul>\n" +

                "\t\t\t"

                + "</div>\n");
    }

    public static final String ZOMBIE;

    static {
        ZOMBIE = "<body><!-- ZOMBIE APOCALYPSE -->\n" +
                "   <div>\n" +
                "      <h3>A Brief History of Zombies</h3>\n" +
                "      <p>\n" +
                "         We've all seen at least one movie about flesh-eating zombies taking\n" +
                "         over (my personal favorite is Resident Evil), but where do zombies come\n" +
                "         from and why do they love eating brains so much? The word zombie comes\n" +
                "         from Haitian and New Orleans voodoo origins. Although its meaning has\n" +
                "         changed slightly over the years, it refers to a human corpse\n" +
                "         mysteriously reanimated to serve the undead. Through ancient voodoo\n" +
                "         and folk-lore traditions, shows like the Walking Dead were born.\n" +
                "      </p>\n" +
                "      <p>\n" +
                "         In movies, shows, and literature, zombies are often depicted as being\n" +
                "         created by an infectious virus, which is passed on via bites and\n" +
                "         contact with bodily fluids. Harvard psychiatrist Steven Schlozman\n" +
                "         wrote a (fictional) medical paper on the zombies presented in Night\n" +
                "         of the Living Dead and refers to the condition as Ataxic\n" +
                "         Neurodegenerative Satiety Deficiency Syndrome caused by an infectious\n" +
                "         agent. The Zombie Survival Guide identifies the cause of zombies as a\n" +
                "         virus called solanum. Other zombie origins shown in films include\n" +
                "         radiation from a destroyed NASA Venus probe (as in Night of the\n" +
                "         Living Dead), as well as mutations of existing conditions such as\n" +
                "         prions, mad-cow disease, measles and rabies.In movies, shows, and\n" +
                "         literature, zombies are often depicted as being created by an\n" +
                "         infectious virus, which is passed on via bites and contact with bodily\n" +
                "         fluids. Harvard psychiatrist Steven Schlozman wrote a (fictional)\n" +
                "         medical paper on the zombies presented in Night of the Living Dead and\n" +
                "         refers to the condition as Ataxic Neurodegenerative Satiety Deficiency\n" +
                "         Syndrome caused by an infectious agent. The Zombie Survival Guide\n" +
                "         identifies the cause of zombies as a virus called solanum. Other\n" +
                "         zombie origins shown in films include radiation from a destroyed NASA\n" +
                "         Venus probe (as in Night of the Living Dead), as well as mutations of\n" +
                "         existing conditions such as prions, mad-cow disease, measles and\n" +
                "         rabies.\n" +
                "      </p>\n" +
                "      <p>\n" +
                "         The rise of zombies in pop culture has given credence to the idea that\n" +
                "         a zombie apocalypse could happen. In such a scenario zombies would\n" +
                "         take over entire countries, roaming city streets eating anything\n" +
                "         living that got in their way. The proliferation of this idea has led\n" +
                "         many people to wonder “How do I prepare for a zombie apocalypse?”\n" +
                "         Well, we’re here to answer that question for you, and hopefully share\n" +
                "         a few tips about preparing for real emergencies too!\n" +
                "      </p>\n" +
                "      <h3>Better Safe Than Sorry</h3>\n" +
                "      <p>\n" +
                "         So what do you need to do before zombies…or hurricanes or pandemics\n" +
                "         for example, actually happen? First of all, you should have an\n" +
                "         emergency kit in your house. This includes things like water, food,\n" +
                "         and other supplies to get you through the first couple of days before\n" +
                "         you can locate a zombie-free refugee camp (or in the event of a\n" +
                "         natural disaster, it will buy you some time until you are able to make\n" +
                "         your way to an evacuation shelter or utility lines are restored).\n" +
                "         Below are a few items you should include in your kit, for a full list\n" +
                "         visit the CDC Emergency page.\n" +
                "      </p>\n" +
                "      <ul>\n" +
                "         <li>• Water (1 gallon per person per day)</li><br/>\n" +
                "         <li>• Food (stock up on non-perishable items that you eat regularly)</li><br/>\n" +
                "         <li>• Medications (this includes prescription and non-prescription\n" +
                "            meds)</li><br/>\n" +
                "         <li>• Tools and Supplies (utility knife, duct tape, battery powered radio, etc.)</li><br/>\n" +
                "         <li>• Sanitation and Hygiene (household bleach, soap, towels, etc.)</li><br/>\n" +
                "         <li>• Clothing and Bedding (a change of clothes for each family member\n" +
                "            and blankets)</li><br/>\n" +
                "         <li>• Important documents (copies of your driver’s license, passport,\n" +
                "            and birth certificate to name a few)</li><br/>\n" +
                "         <li>• First Aid supplies (although you’re a goner if a zombie bites you,\n" +
                "            you can use these supplies to treat basic cuts and lacerations that\n" +
                "            you might get during a tornado or hurricane)</li>\n" +
                "      </ul>\n" +
                "      <h3>Never Fear - CDC is Ready</h3>\n" +
                "      <p>\n" +
                "         If zombies did start roaming the streets, CDC would conduct an\n" +
                "         investigation much like any other disease outbreak. CDC would provide\n" +
                "         technical assistance to cities, states, or international partners\n" +
                "         dealing with a zombie infestation. This assistance might include\n" +
                "         consultation, lab testing and analysis, patient management and care,\n" +
                "         tracking of contacts, and infection control (including isolation and\n" +
                "         quarantine). It’s likely that an investigation of this scenario would\n" +
                "         seek to accomplish several goals: determine the cause of the illness,\n" +
                "         the source of the infection/virus/toxin, learn how it is transmitted\n" +
                "         and how readily it is spread, how to break the cycle of transmission\n" +
                "         and thus prevent further cases, and how patients can best be treated.\n" +
                "         Not only would scientists be working to identify the cause and cure of\n" +
                "         the zombie outbreak, but CDC and other federal agencies would send\n" +
                "         medical teams and first responders to help those in affected areas\n" +
                "         (I will be volunteering the young nameless disease detectives for\n" +
                "         the field work).\n" +
                "      </p>\n" +
                "      <p>\n" +
                "         To learn more about how you can prepare for and stay safe during an\n" +
                "         emergency visit: http://emergency.cdc.gov/\n" +
                "      </p>\n" +
                "   </div>\n" +
                "</body>\n";
    }
}
