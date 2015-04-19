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
 *
 * Info about BombThreats
 */
public class BombThreat extends ActionBarActivity {
    TextView t;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bombthreat_info);

        t = (TextView) findViewById(R.id.textViewBombThreat);
        t.setText(Html.fromHtml("<body><strong>BOMB THREATS</strong><br />\n" +
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
                "</body>"));


        Button getDesc = (Button) findViewById(R.id.bombFactButton);
        final String link = "http://emilms.fema.gov/is906/assets/ocso-bomb_threat_samepage-brochure.pdf";

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