package u.ready_wisc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by OZAN on 3/13/2015.
 *
 * This class takes a string array and calls myAdapter class to provide a layout of a listview with
 * icons next to each list item as well as enable each item to be clickable.
 */
public class DisastersType extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_info_layout);


        String[] disasterList = {"Tornado", "Winter Storm", "Flood", "Fire/Wildfire", "Power Outage", "Public Health Emergency", "Terrorism", "Bomb Threats"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        theListView.setAdapter(disasterAdapt);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));
                String disasterPicked = "You selected " + x;
                Toast.makeText(DisastersType.this, disasterPicked, Toast.LENGTH_SHORT).show();

                if (x == "Tornado") {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(DisastersType.this, Tornado.class);
                    DisastersType.this.startActivity(i);
                }//else if(x == "Winter Storm"){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(DisastersType.this, WinterStorm.class);
//                    DisastersType.this.startActivity(i);
//                }else if(x == "Flood"){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(DisastersType.this, Flood.class);
//                    DisastersType.this.startActivity(i);
//                }else if(x == "Fire/Wildfire"){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(DisastersType.this, Fire.class);
//                    DisastersType.this.startActivity(i);
//                }else if(x == "Power Outage"){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(DisastersType.this, PowerOutage.class);
//                    DisastersType.this.startActivity(i);
//                }else if(x == "Public Health Emergency"){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(DisastersType.this, PublicHealthEmergency.class);
//                    DisastersType.this.startActivity(i);
//                }else if(x == "Bomb Threats"){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(DisastersType.this, BombThreat.class);
//                    DisastersType.this.startActivity(i);
//                }else if(x == "Terrorism"){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(DisastersType.this, Terrorism.class);
//                    DisastersType.this.startActivity(i);
//                }


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
