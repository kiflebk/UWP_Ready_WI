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


package u.ready_wisc.BePrepared;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import edu.parkside.cs.checklist.Checklist;
import u.ready_wisc.Emergency_Main.Volunteer;
import u.ready_wisc.R;
import u.ready_wisc.myAdapter;

/**
 * Created by OZAN on 3/15/2015.
 * <p/>
 * hhljl
 */
public class Prep_Main extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_info_layout);


        String[] disasterList = {"Basic Kit Supplies", "Make A Plan", "Volunteer", "Custom List"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        theListView.setAdapter(disasterAdapt);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));

                switch (x) {
                    case "Custom List":
                        Intent i = new Intent(Prep_Main.this, Checklist.class);
                        Prep_Main.this.startActivity(i);
                        break;
                    case "Make A Plan":
//                        AlertDialog dialog = buildDialog(x);
//                        dialog.show();
                        i = new Intent(Prep_Main.this, PrepWebView.class);
                        i.putExtra("webview", "plan");
                        startActivity(i);
                        break;

                    case "Volunteer":
                        i = new Intent(Prep_Main.this, Volunteer.class);
                        Prep_Main.this.startActivity(i);
                        break;
                    case "Basic Kit Supplies": {
//                        AlertDialog dialog = buildDialog(x);
//                        dialog.show();
                        i = new Intent(Prep_Main.this, PrepWebView.class);
                        i.putExtra("webview", "kit");
                        startActivity(i);
                        break;
                    }
                }
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
