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

import u.ready_wisc.Config;
import u.ready_wisc.R;


//TODO Use this class to refactor DisasterTypes down to one class

public class InfoDisplay extends ActionBarActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_display);

        t = (TextView) findViewById(R.id.infoTextView);
        Intent i = getIntent();
        String infoType = i.getStringExtra("infoType");
        String infoLink = null;
        Button factSheetButton = (Button) findViewById(R.id.factSheetButton);

        switch (infoType) {
            case "bomb":
                t.setText(Html.fromHtml(Config.BOMB_THREAT));
                infoLink = Config.BOMB_LINK;
                break;
            case "heat":
                t.setText(Html.fromHtml(Config.HEAT));
                infoLink = Config.HEAT_LINK;
                break;
            case "fire":
                t.setText(Html.fromHtml(Config.FIRE));
                infoLink = Config.FIRE_lINK;
                break;
            case "flood":
                t.setText(Html.fromHtml(Config.FLOOD));
                infoLink = Config.FLOOD_LINK;
                break;
            case "power":
                t.setText(Html.fromHtml(Config.POWER_OUT));
                break;
            case "public":
                t.setText(Html.fromHtml(Config.PHE));
                break;
            case "terror":
                t.setText(Html.fromHtml(Config.TERRORISM));
                break;
            case "thunder":
                t.setText(Html.fromHtml(Config.THUNDER_STORM));
                infoLink = Config.THUNDER_LINK;
                break;
            case "tornado":
                t.setText(Html.fromHtml(Config.TORNADO_INFO));
                break;
            case "winter":
                t.setText(Html.fromHtml(Config.WINTER_STORM));
                infoLink = Config.WINTER_LINK;
                break;
            default:
                break;
        }

        final String finalInfoLink = infoLink;

        if (finalInfoLink.equals(null)) {
            factSheetButton.setVisibility(View.INVISIBLE);
        }

        factSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(finalInfoLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_display, menu);
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
