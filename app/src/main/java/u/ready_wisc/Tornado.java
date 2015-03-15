package u.ready_wisc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by OZAN on 3/14/2015.
 */
public class Tornado extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tornado_info);
        t = (TextView) findViewById(R.id.textViewTornado);
        t.setText(Html.fromHtml("<body><!-- TORNADOES -->\n" +
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
//                "                    \n" +
//                "                    Tornadoes are nature&#146;s most violent storms. Spawned from powerful thunderstorms, tornadoes can cause \n" +
//                "                    fatalities and devastate a neighborhood in seconds. A tornado appears as a rotating, funnel-shaped cloud that \n" +
//                "                    extends from a thunderstorm to the ground with whirling winds that can reach 300 miles per hour. Damage \n" +
//                "                    paths can be in excess of one mile wide and 50 miles long. Every state is at some risk from this hazard.\n" +
//                "                    <br /><br />\n" +
//                "                    Some tornadoes are clearly visible, while rain or nearby low-hanging clouds obscure others. Occasionally, \n" +
//                "                    tornadoes develop so rapidly that little, if any, advance warning is possible.\n" +
//                "                    <br /><br />\n" +
//                "                    Before a tornado hits, the wind may die down and the air may become very still. A cloud of debris can mark \n" +
//                "                    the location of a tornado even if a funnel is not visible. Tornadoes generally occur near the trailing edge \n" +
//                "                    of a thunderstorm. It is not uncommon to see clear, sunlit skies behind a tornado.\n" +
//                "                    <br /><br />\n" +
//                "                    The following are facts about tornadoes:\n" +
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


