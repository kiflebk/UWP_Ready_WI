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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {
    static ReportsDatabaseHelper mDatabaseHelper;
    static Context ctx;
    private Button DisplayButton, UpdateButton, getDBButton;

    protected static void addUser(String name, String email, long dateOfBirthMillis) {

        ContentValues values = new ContentValues();

        values.put(ReportsDatabaseHelper.COL_JSON, name);

        if (email != null) {

            values.put(ReportsDatabaseHelper.COL_EMAIL, email);

        }

        if (dateOfBirthMillis != 0) {

            values.put(ReportsDatabaseHelper.COL_DOB, dateOfBirthMillis);

        }

        try {

            mDatabaseHelper.insert(mDatabaseHelper.TABLE_USERS, values);

        } catch (ReportsDatabaseHelper.NotValidException e) {

            Log.e("DB Error:", "Unable to insert into DB.");

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mDatabaseHelper = new ReportsDatabaseHelper(this);
        UpdateButton = (Button) findViewById(R.id.UpdateButton);
        DisplayButton = (Button) findViewById(R.id.DisplayButton);
        getDBButton = (Button) findViewById(R.id.getDBButton);
        final EditText name = (EditText) findViewById(R.id.editText);
        final EditText email = (EditText) findViewById(R.id.editText2);

        ctx = this;

        addUser(null, null, 0);

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameData = name.getText().toString();
                String emailData = email.getText().toString();
                addUser(nameData, emailData, 0);
                name.setText("");
                email.setText("");
            }
        });

        DisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = mDatabaseHelper.query(ReportsDatabaseHelper.TABLE_USERS, ReportsDatabaseHelper.COL_JSON);

                String[] from = new String[]{ReportsDatabaseHelper.COL_JSON, ReportsDatabaseHelper.COL_EMAIL};

                int[] to = {android.R.id.text1, android.R.id.text2};

                final SimpleCursorAdapter adapter = new SimpleCursorAdapter(ctx, android.R.layout.simple_list_item_2, c, from, to, 0);

                final ListView listView = (ListView) findViewById(R.id.listView);

                listView.setAdapter(adapter);
            }
        });

        //click listener for the new button to update from the web database
        getDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //this string can be dynamically allocated later, I hardcoded just for the proof of concept
                //String[][] webNames = new String[3][3];

                //new runnable object used to pull the data from the web.
                //android will only let you do json calls from a thread other
                //than the main


                DBUpdateFromWeb foo = new DBUpdateFromWeb();
                Thread t = new Thread(foo);
                t.start();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
