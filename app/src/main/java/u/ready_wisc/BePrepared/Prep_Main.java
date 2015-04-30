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

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import u.ready_wisc.BasicKit.Bedding;
import u.ready_wisc.BasicKit.Essentials;
import u.ready_wisc.BasicKit.FirstAid;
import u.ready_wisc.BasicKit.Food;
import u.ready_wisc.BasicKit.SanitationSupp;
import u.ready_wisc.BasicKit.Water;
import u.ready_wisc.R;
import u.ready_wisc.myAdapter;

/**
 * Created by OZAN on 3/15/2015.
 *
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
                String disasterPicked = "You selected " + x;
                //Toast.makeText(Prep_Main.this, disasterPicked, Toast.LENGTH_SHORT).show();

                switch (x) {
                    case "Custom List":
                        Intent i = new Intent(Prep_Main.this, Checklist.class);
                        Prep_Main.this.startActivity(i);
                        break;
                    case "Make A Plan": {
                        AlertDialog dialog = buildDialog(x);
                        dialog.show();
                        break;
                    }
                    case "Volunteer":
                        //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//TODO Add volunteer activity
//                    Intent i = new Intent(Prep_Main.this, Volunteer.class);
//                    Prep_Main.this.startActivity(i);
                        break;
                    case "Basic Kit Supplies": {
                        AlertDialog dialog = buildDialog(x);
                        dialog.show();
                        break;
                    }
                }
            }
        });
    }

    private AlertDialog buildDialog(String type){
        int list = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(Prep_Main.this);
        if (type.equals("Basic Kit Supplies")) {
            list = R.array.buildList;
            builder.setTitle(R.string.supplyType);
            // Open the activity that the user picks from the Basic Kit Supplies list
            builder.setItems(list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i;

                    switch(which){
                        case 0:
                            i = new Intent(Prep_Main.this, Essentials.class);
                            Prep_Main.this.startActivity(i);
                            break;
                        case 1:
                            i = new Intent(Prep_Main.this, Water.class);
                            Prep_Main.this.startActivity(i);
                            break;
                        case 2:
                            i = new Intent(Prep_Main.this, Food.class);
                            Prep_Main.this.startActivity(i);
                            break;
                        case 3:
                            i = new Intent(Prep_Main.this, FirstAid.class);
                            Prep_Main.this.startActivity(i);
                            break;
                        case 4:
                            i = new Intent(Prep_Main.this, Bedding.class);
                            Prep_Main.this.startActivity(i);
                            break;
                        case 5:
                            i = new Intent(Prep_Main.this, SanitationSupp.class);
                            Prep_Main.this.startActivity(i);
                            break;
                    }
                    //Toast.makeText(Prep_Main.this, "You chose " + which, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (type.equals("Make A Plan")) {
            list = R.array.planList;
            builder.setTitle(R.string.DisasterPlan);
        }
//        builder.setItems(list, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(Prep_Main.this, "You chose " + which, Toast.LENGTH_SHORT).show();
//                    }
//                });
        return builder.create();
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
