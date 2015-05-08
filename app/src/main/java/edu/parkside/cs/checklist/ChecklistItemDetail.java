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

import android.app.AlertDialog;
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
 * Provides the detailed view of the selected item within the item table.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 * @date 02/20/2014
 */
public class ChecklistItemDetail extends ActionBarActivity {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    ChecklistItemRow passedItem;
    boolean nameTextFieldHasBeenEdited;
    boolean qtyTextFieldHasBeenEdited;
    boolean descriptionTextFieldHasBeenEdited;
    /* INSTANCE VARIABLE BLOCK END */


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_item_detail);

        // Set button default status.
        ((Button) findViewById(R.id.activity_checklist_item_detail_update_button)).setEnabled(false);

        // Receive selected item.
        passedItem = this.getIntent().getParcelableExtra(ChecklistItemListView.EXTRA_MESSAGE);

        // Attach listeners.
        attachTextListenersToTextViews();

        // Populate the views.
        if (passedItem != null) {
            new Runnable() {
                @Override
                public void run() {
                    populateWidgets();
                }
            }.run();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist__item__detail, menu);
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
        // The activity has become visible (it is now "resumed").
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
     * Populate the text fields with data supplied by the selected item from the source activity.
     */
    private void populateWidgets() {

        // Retrieve the text and fill the description text field.
        new Runnable() {
            @Override
            public void run() {
                populateDescriptionTextField();
            }
        }.run();

        EditText nameTextField = (EditText) findViewById(R.id.activity_checklist_item_detail_name_edittext);
        EditText qtyTextField = (EditText) findViewById(R.id.activity_checklist_item_detail_qty_edittext);

        nameTextField.setText(passedItem.getName());
        qtyTextField.setText("" + passedItem.getQty());
    }

    /**
     * Retrieve the description text from the database and populate the description text view.
     *
     */
    private void populateDescriptionTextField() {
        EditText descriptionTextField = (EditText) findViewById(R.id.activity_checklist_item_detail_decription_edittext);
        String description = ChecklistContractDBHelper.getDb_helper(this).returnDescriptionFromItem(passedItem);

        if (description != null) {
            descriptionTextField.setText(description);

        }
        else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setTitle("Error");
            alertDialog.setIcon(-1).setIcon(this.getResources().getDrawable(R.mipmap.warning_image));
            alertDialog.setMessage("An error occured while trying to retrieve the description.\n Please try again.");
            alertDialog.setNeutralButton("OK", null);
            alertDialog.setCancelable(true);
            alertDialog.create();
            alertDialog.show();
        }

    }

    /**
     * Called by the activity when the user presses the update button.
     * Saves the item and detail information to the application local database.
     *
     * @param view
     */
    public void updateButtonPressed(View view) {
        EditText nameTextField = (EditText) findViewById(R.id.activity_checklist_item_detail_name_edittext);
        EditText qtyTextField = (EditText) findViewById(R.id.activity_checklist_item_detail_qty_edittext);

        passedItem.setName(nameTextField.getText().toString());
        passedItem.setQty(new Integer(qtyTextField.getText().toString()).intValue());

        int status = ChecklistContractDBHelper.getDb_helper(this).updateItem(passedItem,
                ((EditText) findViewById(R.id.activity_checklist_item_detail_decription_edittext)).getText().toString());

        if (status == ChecklistContractDBHelper.FAILURE) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            alertDialog.setTitle("Error");
            alertDialog.setIcon(-1).setIcon(this.getResources().getDrawable(R.mipmap.warning_image));
            alertDialog.setMessage("An error occured while trying to retrieve the item details.\n" +
                    "Please navigate to the previous screen and try again.");
            alertDialog.setNeutralButton("OK", null);
            alertDialog.setCancelable(true);
            alertDialog.create();
            alertDialog.show();
        } else
            finish();
    }

    /**
     * Called by the activity when the user presses the cancel button.
     * The user is returned to the previous activity on the activity stack.
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
            case (R.id.activity_checklist_item_detail_name_edittext):
                nameTextFieldHasBeenEdited = true;
                break;

            case (R.id.activity_checklist_item_detail_qty_edittext):
                qtyTextFieldHasBeenEdited = true;
                break;

            case (R.id.activity_checklist_item_detail_decription_edittext):
                descriptionTextFieldHasBeenEdited = true;
        }

        if (nameTextFieldHasBeenEdited && qtyTextFieldHasBeenEdited && descriptionTextFieldHasBeenEdited) {
            ((Button) findViewById(R.id.activity_checklist_item_detail_update_button)).setEnabled(true);
        }
    }

    /**
     * Attaches text watchers to the editText views. When all three fields have been edited. The
     * Save button is enabled.
     */
    private void attachTextListenersToTextViews() {
        final TextView nameField = ((TextView) this.findViewById(R.id.activity_checklist_item_detail_name_edittext));
        final TextView qtyField = ((TextView) this.findViewById(R.id.activity_checklist_item_detail_qty_edittext));
        final TextView descriptionField = ((TextView) this.findViewById(R.id.activity_checklist_item_detail_decription_edittext));

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
