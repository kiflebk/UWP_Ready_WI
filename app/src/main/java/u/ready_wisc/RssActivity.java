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

package u.ready_wisc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class RssActivity extends AppCompatActivity {

    TextView textView;
    Button getDesc;
    String link;
    //String countyName;
    String desc;
    String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);

        // Google Analytics
        // copy these two lines along with the onStart() and onStop() methods below
        Tracker t = ((AnalyticsApp) getApplication()).getTracker(AnalyticsApp.TrackerName.APP_TRACKER);
        t.send(new HitBuilders.ScreenViewBuilder().build());


        getDesc = (Button) findViewById(R.id.getDesc);
        Intent intent = getIntent();
        //countyName = intent.getStringExtra("county");
        desc = intent.getStringExtra("desc");
        link = intent.getStringExtra("link");
        title = intent.getStringExtra("title");

        textView = (TextView) findViewById(R.id.messageText);
        textView.setTextSize(20);
        if (desc.isEmpty())
            desc = title;
        textView.setText(desc);
        //setContentView(textView);

        if (desc.equals("No current weather warnings/advisories.") || link.isEmpty()) {
            getDesc.setVisibility(View.INVISIBLE);
        }

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

        //TODO add options menu
        // getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //Overridden onStart()/onStop() functions for Google Analytics
    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

}
