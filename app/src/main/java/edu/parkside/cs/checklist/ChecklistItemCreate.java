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

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.TextView;

import u.ready_wisc.R;

/**
 * Class represents the controller for the Checklist Item Creation Activity.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class ChecklistItemCreate extends Activity {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    public static final String RETURN_MESSAGE = "edu.parkside.cs.checklist_item_create";
    ChecklistRow checklist_row;
    boolean nameTextFieldHasBeenEdited;
    boolean qtyTextFieldHasBeenEdited;
    boolean descriptionTextFieldHasBeenEdited;
    /* INSTANCE VARIABLE BLOCK END */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_item_create);

        // Retrieved the passed checklist item
        checklist_row = getIntent().getParcelableExtra(ChecklistItemListView.EXTRA_MESSAGE);

        // Set save button to default state.
        ((Button) findViewById(R.id.activity_checklist_item_create_save_button)).setEnabled(false);

        attachTextListenersToTextViews();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist__item__create, menu);

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

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed")
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
     * Called by the activity when the user presses the save button.
     *
     * @param view
     */
    public void saveButtonPressed(View view) {
        // Retrieve input values from editText fields.
        EditText name = (EditText) findViewById(R.id.activity_checklist_item_create_name_edittext);
        EditText qty = (EditText) findViewById(R.id.activity_checklist_item_create_qty_edittext);
        EditText description = (EditText) findViewById(R.id.activity_checklist_item_create_description_edittext);

        // Create item from editText fields.
        ChecklistItemRow item = new ChecklistItemRow();
        item.setName(name.getText().toString());
        item.setQty(new Integer(qty.getText().toString()).intValue());
        item.setChecklist_entryid(checklist_row.getEntryid());


        int status = ChecklistContractDBHelper.getDb_helper(this).insertItem(item, description.getText().toString());

        // Check status and update user.
        // If successful return to previous activity.
        if (status == ChecklistContractDBHelper.FAILURE) {
            /* Notify user of error. */
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setTitle("Error");
            alertDialog.setIcon(-1).setIcon(this.getResources().getDrawable(R.mipmap.warning_image));
            alertDialog.setMessage("The checklist item was unable to be created. Please try again.");
            alertDialog.setNeutralButton("OK", null);
            alertDialog.setCancelable(true);
            alertDialog.create();
            alertDialog.show();

        } else {
            // Create and return the populated item and description to the calling activity.
            Intent intent = new Intent();
            //intent.putExtra(RETURN_MESSAGE, checklist_row.);
            setResult(RESULT_OK, intent);

            finish();
        }
    }

    /**
     * Finish activity and navigate to source controller.
     *
     * @param view
     */
    public void cancelButtonPressed(View view) {
        finish();
    }

    /**
     * Called when the edit text views focus has changed.
     *
     * @param view
     */
    private void editTextViewHasBeenSelected(View view) {


        switch (view.getId()) {
            case (R.id.activity_checklist_item_create_name_edittext):
                nameTextFieldHasBeenEdited = true;
                break;

            case (R.id.activity_checklist_item_create_qty_edittext):
                qtyTextFieldHasBeenEdited = true;
                break;

            case (R.id.activity_checklist_item_create_description_edittext):
                descriptionTextFieldHasBeenEdited = true;
        }

        if (nameTextFieldHasBeenEdited && qtyTextFieldHasBeenEdited && descriptionTextFieldHasBeenEdited) {
            ((Button) findViewById(R.id.activity_checklist_item_create_save_button)).setEnabled(true);
        }
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     * <p/>
     * Description:
     * Attaches text watchers to the editText views.
     */
    private void attachTextListenersToTextViews() {
        final TextView nameField = ((TextView) this.findViewById(R.id.activity_checklist_item_create_name_edittext));
        final TextView qtyField = ((TextView) this.findViewById(R.id.activity_checklist_item_create_qty_edittext));
        final TextView descriptionField = ((TextView) this.findViewById(R.id.activity_checklist_item_create_description_edittext));

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

        qtyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextViewHasBeenSelected(qtyField);
            }
        });

        descriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextViewHasBeenSelected(descriptionField);
            }
        });
    }
}
