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
 * Info about PHE
 */
public class PublicHealthEmergency extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendemic_info);
        t = (TextView) findViewById(R.id.textViewPendemic);
        t.setText(Html.fromHtml("<body>             <!-- PANDEMIC -->\n" +
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
                "            </div> </body>"));
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