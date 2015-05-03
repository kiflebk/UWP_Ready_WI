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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import u.ready_wisc.R;

/**
 * Class represents the Checklist creation activity.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015 *
 * @email krawchukdavid@gmail.com
 */
public class ChecklistCreate extends ActionBarActivity {
    /* INSTANCE VARIABLE BLOCK BEGIN */
    public static final String EXTRA_MESSAGE = "edu.parkside.cs.checklist_create";
    // The request code required by the returning activity callback.
    static final int NEW = 1;
    static final int UPDATE = 2;
    static final int SUCCESS = 0;
    static final int CHECKLIST_INSERT_ERROR = 1;
    static final int CHECKLIST_ROW_INSERT_ERROR = 2;
    static final int CHECKLIST_DESCRIPTION_ERROR = 3;

    ChecklistRow checklist_row;
    ArrayList<ChecklistItemRow> added_items;
    ArrayList<String> added_descriptions;
    ChecklistCreateItemAdapter checklist_create_item_adapter;
    ListView checklist_item_listView;
    boolean isInEditMode = false;
    boolean nameTextFieldHasBeenEdited;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * Getter. Creates new Arraylist if reference is null.
     *
     * @return
     */
    public ArrayList<ChecklistItemRow> getAddedItems() {
        if (added_items == null) {
            added_items = new ArrayList<>();
        }
        return added_items;
    }

    /**
     * Getter. Creates new Arraylist if reference is null.
     *
     * @return
     */
    public ArrayList<String> getAddedDescriptions() {
        if (added_descriptions == null) {
            added_descriptions = new ArrayList<>();
        }
        return added_descriptions;
    }

    /**
     * Getter. Creates an empty checklist row if reference is empty.
     *
     * @return
     */
    ChecklistRow getChecklistRow() {
        if (checklist_row == null) {
            checklist_row = new ChecklistRow(0, "Empty", 0);
        }
        return checklist_row;
    }

    /**
     * Getter. Retrieves resouce from xml resource file if reference is null.
     *
     * @return
     */
    private ListView getChecklistItemListView() {
        if (checklist_item_listView == null) {
            checklist_item_listView = (ListView) findViewById(R.id.activity_checklist_create_item_listView);
        }
        return checklist_item_listView;
    }


    /**
     * Creates a new adapter if one doesn't exist and attaches it to the listview within the activity.
     *
     * @return
     */
    private ChecklistItemArrayAdapter getChecklistCreateItemAdapter() {
        if (checklist_create_item_adapter == null) {
            checklist_create_item_adapter =
                    new ChecklistCreateItemAdapter(this, R.layout.activity_checklist_item_listview_row, getAddedItems(), getAddedDescriptions());

            getChecklistItemListView().setAdapter(checklist_create_item_adapter);
        }
        return checklist_create_item_adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_create);

        // Set button default status.
        ((Button) findViewById(R.id.activity_checklist_create_save_button)).setEnabled(false);

        // Attach listeners.
        attachTextListenersToTextViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist__kit__create, menu);
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

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }

    /**
     * Called when the activity returns from some state other than active. Populates the list
     * with the contents of the added items.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").

        // Repopulate the listView with the contents of the
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
     * Populates the list view with the contents of the create_checklist_item_array_adapter.
     */
    public void populateListView() {
        // Notify clients that contents of the adapter have changed and they should update their state.
        getChecklistCreateItemAdapter().notifyDataSetChanged();
    }

    /**
     * Callback method called when the ChecklistCreateItemAdd activity returns. Called prior to
     * onResume().
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == NEW) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Fetch results from the returned Intent parameter and add the contents to the proper
                //  arraylist.
                ChecklistItemRow item = data.getParcelableExtra(ChecklistCreateItemAdd.EXTRA_MESSAGE_ITEM);
                String description = data.getStringExtra(ChecklistCreateItemAdd.EXTRA_MESSAGE_DESC);

                getAddedItems().add(item);
                getAddedDescriptions().add(description);
            }
        } else if (requestCode == UPDATE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Fetch results from the returned Intent parameter and add the contents to the proper
                //  arraylist.
                ChecklistItemRow item = data.getParcelableExtra(ChecklistCreateItemAdd.EXTRA_MESSAGE_ITEM);
                String description = data.getStringExtra(ChecklistCreateItemAdd.EXTRA_MESSAGE_DESC);

                // Retrieve index if the object exists in the array and replace the item and description.
                int item_index = getAddedItems().indexOf(item);
                if (item_index != -1) {
                    getAddedItems().set(item_index, item);
                    getAddedDescriptions().set(item_index, description);
                }
            }
        }
    }


    /**
     * When called changes the editMode boolean instance variable.
     *
     * @param menuItem
     * @TODO Change the visual state of the application to indicate edit mode.
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

    /**
     * Called when the add button has been pressed to create a new checklist.
     *
     * @param button
     */
    public void addButtonPressed(View button) {
        Intent intent = new Intent(this, ChecklistCreateItemAdd.class);
        startActivityForResult(intent, NEW);
    }

    /**
     * Called when the cancel button has been pressed by the user. Returns to the previous activity.
     *
     * @param button
     */
    public void cancelButtonPressed(View button) {
        finish();
    }


    /**
     * Called when the user presses the save button.
     *
     * @param button
     * @TODO Implement Error handling / AlertViews.
     */
    public void saveButtonPressed(View button) {

        if (createChecklist() == CHECKLIST_INSERT_ERROR) {
            // alert user of error and break on some condition.
        }
        if (createChecklistRows() == CHECKLIST_ROW_INSERT_ERROR) {
            // alert user of error and break on some condition.
        }

        // If control reaches this point all is ok. Return.
        finish();
    }

    /**
     * Inserts the checklist into the database. Must be called prior to createCheclistRows().
     *
     * @return
     */
    public int createChecklist() {
        EditText name_editText = (EditText) findViewById(R.id.activity_checklist_create_edittext);

        int status =
                ChecklistContractDBHelper.getDb_helper(this).addChecklist(new ChecklistRow(name_editText.getText().toString(), 0));

        return (status == ChecklistContractDBHelper.SUCCESS) ? SUCCESS : CHECKLIST_INSERT_ERROR;
    }

    /**
     * Inserts all of the items in the array of items and thier descriptions into the database.
     *
     * @return
     */
    public int createChecklistRows() {
        EditText name_editText = (EditText) findViewById(R.id.activity_checklist_create_edittext);

        // Retrieve Checklist from returned array.
        ArrayList<ChecklistRow> returnedArrayOfChecklistRows =
                ChecklistContractDBHelper.getDb_helper(this)
                        .returnChecklistRows(new String[]{Checklist_Contract.Checklist_Queries.fetchChecklist(new ChecklistRow(name_editText.getText().toString(), 0))});

        ChecklistRow firstReturnedChecklist = returnedArrayOfChecklistRows.get(0);


        // Iterate through the array of items added items and insert them into the database.
        //  If a failure occurs return an error code.
        for (int i = 0; i < getAddedItems().size(); i++) {
            // Get the checklist item and set its checklist_id property.
            ChecklistItemRow checklist_item_row = getAddedItems().get(i);
            checklist_item_row.setChecklist_entryid(firstReturnedChecklist.getEntryid());

            if (ChecklistContractDBHelper.getDb_helper(this).insertItem(checklist_item_row, getAddedDescriptions().get(i))
                    == ChecklistContractDBHelper.FAILURE) {
                return ChecklistContractDBHelper.FAILURE;
            }
        }
        return SUCCESS;
    }


    /**
     * Called when the edit text views focus has changed.
     *
     * @param view
     */
    private void editTextViewHasBeenSelected(View view) {


        switch (view.getId()) {
            case (R.id.activity_checklist_create_edittext):
                nameTextFieldHasBeenEdited = true;
                break;
        }

        if (nameTextFieldHasBeenEdited) {
            ((Button) findViewById(R.id.activity_checklist_create_save_button)).setEnabled(true);
        }
    }

    /**
     * Attaches text watchers to the editText views.
     */
    private void attachTextListenersToTextViews() {
        final TextView nameField = ((TextView) this.findViewById(R.id.activity_checklist_create_edittext));

        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextViewHasBeenSelected(nameField);
            }
        });
    }
}
