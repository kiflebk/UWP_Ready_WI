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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import u.ready_wisc.R;

/**
 * The controller for displaying the contents of a checklist.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class ChecklistItemListView extends AppCompatActivity {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    public static final String EXTRA_MESSAGE = "edu.parkside.cs.checklist_item_listview";
    // The request code required by the returning activity callback.
    static final int NEW = 1;

    ChecklistItemArrayAdapter checklist_item_arrayAdapter;
    ListView checklist_item_listView;
    ChecklistRow passedChecklist;
    boolean isInEditMode = false;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * Getter. Obtains a reference to the item listview from the xml resource file.
     *
     * @return
     */
    private ListView getChecklist_item_listView() {
        if (checklist_item_listView == null) {
            checklist_item_listView = (ListView) findViewById(R.id.activity_checklist_item_listview);
        }
        return checklist_item_listView;
    }

    /**
     * Creates a new adapter if one doesn't exist and attaches it to the listview within the activity.
     *
     * @return
     */
    private ChecklistItemArrayAdapter getChecklist_item_arrayAdapter() {
        if (checklist_item_arrayAdapter == null) {
            checklist_item_arrayAdapter =
                    new ChecklistItemArrayAdapter(this, R.layout.activity_checklist_item_listview_row, new ArrayList<ChecklistItemRow>());

            getChecklist_item_listView().setAdapter(checklist_item_arrayAdapter);
        }
        return checklist_item_arrayAdapter;
    }

    /**
     * Called when the activity view is created. After calling the super class retrieves the
     * passed checklist object from the passed message.
     *
     * @param savedInstanceState
     * @XXX Known error occurs when user presses back button in the navigation bar instead of cancel.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_item_listview);

        // Retrieve the passed checklist.
        passedChecklist = getIntent().getParcelableExtra(Checklist.EXTRA_MESSAGE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist__item_list_view, menu);
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
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }

    /**
     * On activity resume, reloads the listview with the contents of the appropriate database.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").

        // Repopulate the listView with the contents of the Checklist table.
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }


    /**
     * Populates the array adapter with the contents of the appropriate database table.
     * Notifies the listview that the contents have changed so the listview can update the appropriate
     * views.
     */
    public void populateListView() {
        // Clear the adapter contents.
        getChecklist_item_arrayAdapter().clear();

        // Repopulate the adapter contents.
        String[] query_of_items = {Checklist_Contract.Checklist_Item_Queries.fetchItems(passedChecklist)};
        getChecklist_item_arrayAdapter().addAll(ChecklistContractDBHelper.getDb_helper(this).returnChecklistItemRows(query_of_items));

        // Notify clients that contents of the adapter have changed and they should update their state.
        getChecklist_item_arrayAdapter().notifyDataSetChanged();
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
        } else {
            menuItem.setIcon(getResources().getDrawable(R.drawable.edit_remove));
            isInEditMode = true;
        }
    }
}
