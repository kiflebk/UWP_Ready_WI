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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import u.ready_wisc.R;

/**
 * The controller for the Checklist_Create_Item_Add.xml layout.
 * Class is meant to be used as a helper class to the Checklist Create Activity.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class Checklist_Create_Item_Add extends Checklist_Item_Create {

    /* Instance Variable Block Begin */
    public static final String EXTRA_MESSAGE_ITEM = "edu.parkside.cs.checklist_create_item_add_item";
    public static final String EXTRA_MESSAGE_DESC = "edu.parkside.cs.checklist_create_item_add_desc";

    boolean nameTextFieldHasBeenEdited;
    boolean qtyTextFieldHasBeenEdited;
    boolean descriptionTextFieldHasBeenEdited;
    /* Instance Variable Block End */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_item_create);

        // Set button default status.
        ((Button) findViewById(R.id.activity_checklist_item_create_save_button)).setEnabled(false);

        // Populate text fields if data has been passed through the message mechanism.
        Checklist_Item_Row item = this.getIntent().getParcelableExtra(this.EXTRA_MESSAGE_ITEM);
        String desc = this.getIntent().getStringExtra(this.EXTRA_MESSAGE_DESC);

        if (item != null && desc != null) {
            // Retrieve fields from layout.
            EditText name_editText = (EditText) findViewById(R.id.activity_checklist_item_create_name_edittext);
            EditText qty_editText = (EditText) findViewById(R.id.activity_checklist_item_create_qty_edittext);
            EditText description_editText = (EditText) findViewById(R.id.activity_checklist_item_create_description_edittext);

            name_editText.setText(item.getName());
            qty_editText.setText("" + item.getQty());
            description_editText.setText(desc);

            // Set button default status.
            ((Button) findViewById(R.id.activity_checklist_item_create_save_button)).setEnabled(true);
        }

        // Attach listeners.
        attachTextListenersToTextViews();

    }

    /**
     * @param view
     */
    @Override
    public void saveButtonPressed(View view) {

        // Retrieve fields from layout.
        EditText name_editText = (EditText) findViewById(R.id.activity_checklist_item_create_name_edittext);
        EditText qty_editText = (EditText) findViewById(R.id.activity_checklist_item_create_qty_edittext);
        EditText description_editText = (EditText) findViewById(R.id.activity_checklist_item_create_description_edittext);

        // Create and populate item.
        Checklist_Item_Row item_row = new Checklist_Item_Row();
        item_row.setName(name_editText.getText().toString());
        item_row.setQty(new Integer(qty_editText.getText().toString()));

        // Create and return the populated item and description to the calling activity.
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE_ITEM, item_row);
        intent.putExtra(EXTRA_MESSAGE_DESC, description_editText.getText().toString());
        setResult(RESULT_OK, intent);

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
