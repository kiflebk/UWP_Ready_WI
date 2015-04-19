package u.ready_wisc.BePrepared;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.Toast;

import edu.parkside.cs.checklist.Checklist;
import u.ready_wisc.Emergency_Main.Volunteer;
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


        String[] disasterList = {"Build A Kit", "Make A Plan", "Volunteer", "Custom List"};

        final ListAdapter disasterAdapt = new myAdapter(this, disasterList);

        final ListView theListView = (ListView) findViewById(R.id.disasterListView);

        theListView.setAdapter(disasterAdapt);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String x = String.valueOf(parent.getItemAtPosition(position));
                String disasterPicked = "You selected " + x;
                Toast.makeText(Prep_Main.this, disasterPicked, Toast.LENGTH_SHORT).show();

                if (x.equals("Custom List")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    Intent i = new Intent(Prep_Main.this, Checklist.class);
                    Prep_Main.this.startActivity(i);
                } else if (x.equals("Make A Plan")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Prep_Main.this, Plan.class);
//                    Prep_Main.this.startActivity(i);
                    AlertDialog dialog = buildDialog(x);
                    dialog.show();
                } else if (x.equals("Volunteer")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//TODO Add volunteer activity
//                    Intent i = new Intent(Prep_Main.this, Volunteer.class);
//                    Prep_Main.this.startActivity(i);
                } else if (x.equals("Build A Kit")) {
                    //I hope this works!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                    Intent i = new Intent(Prep_Main.this, Build_Kits.class);
//                    Prep_Main.this.startActivity(i);
                    AlertDialog dialog = buildDialog(x);
                    dialog.show();
                }// else if (x.equals("Emergency Map")) {
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

    private AlertDialog buildDialog(String type){
        int list = 0, length = 0;
        if (type.equals("Build A Kit"))
            list = R.array.buildList;
        else if (type.equals("Make A Plan"))
            list = R.array.planList;
        AlertDialog.Builder builder = new AlertDialog.Builder(Prep_Main.this);
        builder.setTitle(R.string.supplyType)
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Prep_Main.this, "You chose " + which, Toast.LENGTH_SHORT).show();
                    }
                });
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