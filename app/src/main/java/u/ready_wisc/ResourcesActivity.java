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

import java.util.ArrayList;
import java.util.List;


public class ResourcesActivity extends ActionBarActivity {
    String county;
    ArrayList<ResourceItem> resourceList;
    ListView resourcesListView;
    Spinner countySpinner;
    Button callButton;

    //For Ben:
    //db.execSQL("CREATE TABLE resources (county VARCHAR(20), name VARCHAR(50) PRIMARY KEY, address VARCHAR(20), phone INTEGER, other VARCHAR(50), type VARCHAR(10)");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        final ListView resourcesListView = (ListView) findViewById(R.id.resourcesListView);
        final Spinner countySpinner = (Spinner) findViewById(R.id.countySpinner);
        Intent i = getIntent();
        county = i.getStringExtra("county");
        Button callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:911"));
                startActivity(i);
            }
        });
//        //Database query to populate listview
//        //Need local DB + working activity
//        SQLiteDatabase resourceDB = this.getReadableDatabase();
//        String query = "SELECT * FROM resources WHERE COUNTY=\"" + county + "\"";
//        Cursor result = resourceDB.rawQuery(query, null);
//        if(result.moveToFirst()){
//            do{
//                ResourceItem item = new ResourceItem();
//                item.setName(result.getString(1));
//                item.setAddress(result.getString(2));
//                item.setPhone(result.getString(3));
//                item.setOther(result.getString(4));
//                item.setType(result.getString(5));
//                resourceList.add(item);
//            } while (result.moveToFirst());
//        }
//        ResourceAdapter adapter = new ResourceAdapter(this, resourceList);
//        resourcesListView.setAdapter(adapter);
//        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                county = countySpinner.getItemAtPosition(position).toString();
//                SQLiteDatabase resourceDB = this.getReadableDatabase();
//                String query = "SELECT * FROM resources WHERE COUNTY=\"" + county + "\"";
//                Cursor result = resourceDB.rawQuery(query, null);
//                if(result.moveToFirst()){
//                    do{
//                        ResourceItem item = new ResourceItem();
//                        item.setName(result.getString(1));
//                        item.setAddress(result.getString(2));
//                        item.setPhone(result.getString(3));
//                        item.setOther(result.getString(4));
//                        item.setType(result.getString(5));
//                        resourceList.add(item);
//                    } while (result.moveToFirst());
//                }
//                ResourceAdapter adapter = new ResourceAdapter(ResourcesActivity.this, resourceList);
//                resourcesListView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
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
