package u.ready_wisc.Emergency_Main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import u.ready_wisc.R;
import u.ready_wisc.RssActivity;
import u.ready_wisc.myAdapter;

/**
 * Created by OZAN on 3/15/2015.
 *
 * kjljl
 */
public class Emergency extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster_info_layout);


        String[] disasterList = {"Emergency Map", "Shelters", "Disaster Info", "Volunteer", "Report Damage", "Social Media", "Flashlight", "SOS Tone"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        theListView.setAdapter(disasterAdapt);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));
                String disasterPicked = "You selected " + x;
                //Toast.makeText(Emergency.this, disasterPicked, Toast.LENGTH_SHORT).show();

                if (x.equals("Report Damage")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(Emergency.this, DamageReports.class);
                    Emergency.this.startActivity(i);
                }

                if (x.equals("Flashlight")){

                    if ( getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                        Camera cam = Camera.open();
                        Camera.Parameters p = cam.getParameters();
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        cam.setParameters(p);
                        cam.startPreview();
                    }
                }
//                 else if (x.equals("Shelters")) {
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, Shelters.class);
//                    Emergency.this.startActivity(i);
//                } else if (x.equals("Disaster Info")) {
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, RssActivity.class);
//                    Emergency.this.startActivity(i);
//                } else if (x.equals("Volunteer")) {
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, Volunteer.class);
//                    Emergency.this.startActivity(i);
//                } else if (x.equals("Emergency Map")) {
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, Emergency_Map.class);
//                    Emergency.this.startActivity(i);
//                } else if(x.equals("Social Media")){
//                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Emergency.this, Social_Media.class);
//                    Emergency.this.startActivity(i);
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

