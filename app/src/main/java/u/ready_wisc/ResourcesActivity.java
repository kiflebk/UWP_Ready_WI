package u.ready_wisc;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Spinner;

import com.pushbots.push.utils.Log;

import java.util.ArrayList;
import java.util.List;


public class ResourcesActivity extends ActionBarActivity {
    static String county = "";
    ArrayList<ResourceItem> resourceList;
    ListView resourcesListView;
    Button callButton;
    Spinner resourceSpinner;
    static MyDatabaseHelper rDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        rDBHelper = new MyDatabaseHelper(this);

        final ListView resourcesListView = (ListView) findViewById(R.id.resourcesListView);
        final Spinner resourceSpinner = (Spinner) findViewById(R.id.resourceSpinner);

        if(county.isEmpty()) {
            Intent i = getIntent();
            county = i.getStringExtra("county");
        }

        Button callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:911"));
                startActivity(i);
            }
        });

        //Database query to populate listview
        //Need local DB + working activity
        resourceList = rDBHelper.getDataFromCounty(county);
        ResourceAdapter adapter = new ResourceAdapter(this, resourceList);
        resourcesListView.setAdapter(adapter);

        resourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String resource = parent.getItemAtPosition(position).toString();
                resourceList = rDBHelper.getDataFromType(county,resource);
                ResourceAdapter adapter = new ResourceAdapter(ResourcesActivity.this, resourceList);
                resourcesListView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("county",county);
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
}
