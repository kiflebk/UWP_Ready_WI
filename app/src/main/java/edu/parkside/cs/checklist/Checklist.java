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

package edu.parkside.cs.checklist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import u.ready_wisc.R;

/**
 * The checklist activity is the initial point for selection, creation, and deletion of
 * user created checklists.
 * <p/>
 * In the terms of this project checklists are also known as "ready-kits" and "go-kits".
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 */
public class Checklist extends ActionBarActivity {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    public final static String EXTRA_MESSAGE = "edu.parkside.cs.checklist.checklist";
    protected boolean isInEditMode = false;
    private ChecklistArrayAdapter arrayAdapter;
    private ListView checklistListView;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * Returns a valid listview object as referenced in the layout file.
     *
     * @return
     */
    private ListView getChecklistListView() {
        if (checklistListView == null) {
            checklistListView = (ListView) findViewById(R.id.checklist_listview);
        }

        return checklistListView;
    }

    /**
     * Returns the ArrayAdapter in a valid state by creating an empty Arraylist and attaching it to
     * the ListView.
     */
    protected ChecklistArrayAdapter getArrayAdapter() {
        if (arrayAdapter == null) {
            arrayAdapter = new ChecklistArrayAdapter(this, R.layout.activity_checklist_row, new ArrayList<ChecklistRow>());
            getChecklistListView().setAdapter(arrayAdapter);
        }
        return arrayAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Before the activity is visible to the user the list is populated.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        // Populate the listView with the contents of the Checklist table.
        new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        }.run();
    }

    /**
     * When the activity has resumed from some inactive state. Clear and repopulate the list.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").

        getArrayAdapter().clear();
        // Populate the listView with the contents of the Checklist table.
        // Possible new checklists have been added.
        new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        }.run();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }

    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")

        // Save the state of the activity.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    /**
     * Populates the list view object with the contents of the Checklist table.
     */
    protected void populateListView() {

        // Set the adapter to default state.
        getArrayAdapter().clear();

        // Get the array adapter and add the feched results to the adapter.
        getArrayAdapter().addAll(ChecklistContractDBHelper.getDb_helper(this).returnChecklistRows(null));

        // Notify the listview that the data within the adapter has changed and the view needs to
        //  be redrawn to reflect this change.
        getArrayAdapter().notifyDataSetChanged();
    }

    /**
     * When called changes the editMode boolean instance variable.
     *
     * @param menuItem
     */
    public void menuEditButtonPressed(MenuItem menuItem) {
        if (isInEditMode) {
            // Change the visual state of the application to indicate edit mode.
            menuItem.setIcon(getResources().getDrawable(R.drawable.edit_add));
            isInEditMode = false;
        }
        else {
            menuItem.setIcon(getResources().getDrawable(R.drawable.edit_remove));
            isInEditMode = true;
        }
    }

}
