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

import rss.RssFragment;
import u.ready_wisc.R;

/**
 * Created by OZAN on 3/14/2015.
 *
 * EXTREME HEAT INFO
 */
public class ExtremeHeat extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extreme_heat_info);
        t = (TextView) findViewById(R.id.textViewExtremeHeat);
        t.setText(Html.fromHtml("<body> <!-- EXTREME HEAT -->\n" +
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
