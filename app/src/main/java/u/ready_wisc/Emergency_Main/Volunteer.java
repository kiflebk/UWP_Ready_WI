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

package u.ready_wisc.Emergency_Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import u.ready_wisc.R;
import u.ready_wisc.VolunteerDBHelper;
import u.ready_wisc.VolunteerItem;

/**
 * Created by OZAN on 3/15/2015.
 */
public class Volunteer extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ArrayList<VolunteerItem> volunteerList;
    ListView volunteerView;
    static VolunteerDBHelper vdbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        vdbHelper = new VolunteerDBHelper(this);

        volunteerView = (ListView) findViewById(R.id.volunteerListView);

        //Database query to populate listview
        //Need local DB + working activity
        volunteerList = vdbHelper.getVolunteerData();
        VolunteerAdapter adapter = new VolunteerAdapter(this, volunteerList);
        volunteerView.setAdapter(adapter);

        volunteerView.setClickable(true);
        volunteerView.setOnItemClickListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resources, menu);
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

    @Override
    // If a listitem is clicked, the details of the item are loaded into a seperate intent and started
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VolunteerAdapter adapter = (VolunteerAdapter) parent.getAdapter();
        VolunteerItem item = (VolunteerItem) adapter.getItem(position);

        if (!item.getUrl().equals(" ")) {
            Uri uri;

            if(!item.getUrl().contains("http://")){
                uri = Uri.parse("http://" + item.getUrl());
            } else {
                uri = Uri.parse(item.getUrl());
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

}
