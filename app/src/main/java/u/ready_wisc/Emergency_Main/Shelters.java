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
import u.ready_wisc.ShelterItem;
import u.ready_wisc.VolunteerDBHelper;

/**
 *
 */
public class Shelters extends ActionBarActivity implements AdapterView.OnItemClickListener {

    static VolunteerDBHelper vdbHelper;
    ArrayList<ShelterItem> shelterList;
    ListView shelterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        vdbHelper = new VolunteerDBHelper(this);

        shelterView = (ListView) findViewById(R.id.shelterListView);

        // Database query to populate listview
        // Need local DB + working activity
        shelterList = vdbHelper.getShelterData();

        // if no shelters are found, the default constructor is called to
        // show the no shelters found object
        if (shelterList.isEmpty()) {
            shelterList.add(new ShelterItem());
        }

        ShelterAdapter adapter = new ShelterAdapter(this, shelterList);
        shelterView.setAdapter(adapter);

        // enable the item to be clicked
        shelterView.setClickable(true);
        shelterView.setOnItemClickListener(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    // If a listitem is clicked, the details of the item are loaded into a separate intent and started
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShelterAdapter adapter = (ShelterAdapter) parent.getAdapter();
        ShelterItem item = adapter.getItem(position);

        // the prefix tel: needs to be added to the number to be used by the phone dialer
//        if (!item.getPhone().equals(" ")) {
//            Intent i = new Intent(Intent.ACTION_DIAL);
//            i.setData(Uri.parse("tel:"+item.getPhone()));
//            startActivity(i);
//        }

        //Checks the the url is not empty
        String address = item.getAddress();
        String city = item.getCity();
        Uri uri;

        uri = Uri.parse("http://google.com/#q=" + address + " " + city);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}


