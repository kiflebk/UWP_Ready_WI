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
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import u.readybadger.R;


/**
 * Provides the mapping between the checklist_item_row.xml and checklist_item_row objects.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class ChecklistItemArrayAdapter extends ArrayAdapter<ChecklistItemRow> {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    ArrayList<ChecklistItemRow> checklist_item_rowArrayList;
    Context context;
    int resourceID;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * Constructor.
     *
     * @param context
     * @param resource
     */
    public ChecklistItemArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resourceID = resource;
    }

    /**
     * Constructor.
     *
     * @param context
     * @param resource
     * @param objects
     */
    public ChecklistItemArrayAdapter(Context context, int resource, ArrayList<ChecklistItemRow> objects) {
        super(context, resource, objects);
        this.checklist_item_rowArrayList = objects;
        this.context = context;
        this.resourceID = resource;
    }

    /**
     * Setter.
     *
     * @param arrayList
     */
    public void setList(ArrayList<ChecklistItemRow> arrayList) {
        this.checklist_item_rowArrayList = arrayList;
    }


    /**
     * Returns the inflated row filled with the ChecklistItemRow attributes. Givin the current editing
     * condition allows for selection or deletion of checklist item rows.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_checklist_item_listview_row, parent, false);

        TextView itemName = (TextView) rowView.findViewById(R.id.checklist_item_listview_row_textview);
        final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checklist_item_listview_row_checkbox);

        itemName.setText(this.checklist_item_rowArrayList.get(position).getName());
        checkBox.setChecked(this.checklist_item_rowArrayList.get(position).isChecked());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set visual checkbox.
                checklist_item_rowArrayList.get(position).setChecked();

                // Set checked attribute of the item in the item table.
                ChecklistContractDBHelper.getDb_helper(context).updateItem(checklist_item_rowArrayList.get(position), null);

                // Update checklist complete percentage.
                updateChecklistCompletePercentage(context);
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ChecklistItemListView) context).isInEditMode) {
                    // If the item is not the Add Checklist row, allow removal.
                    if (position != checklist_item_rowArrayList.size() - 1) {
                        // Create list of items to delete.
                        ArrayList<ChecklistItemRow> itemToBeInserted = new ArrayList<ChecklistItemRow>(1);
                        itemToBeInserted.add(checklist_item_rowArrayList.get(position));

                        int status = ChecklistContractDBHelper.getDb_helper(context)
                                .deleteItems(itemToBeInserted);
                        if (status == ChecklistContractDBHelper.FAILURE) {
                            // Handle error. Possible display an alertView to notify user.
                        } else {

                            // Remove row and notify listeners that the adapter contents have changed.
                            checklist_item_rowArrayList.remove(position);

                            // Update checklist complete percentage.
                            updateChecklistCompletePercentage(context);

                            notifyDataSetChanged();
                        }
                    }


                } else if (checklist_item_rowArrayList.get(position).getName().contains("Add Item")) {
                    Intent intent = new Intent(context, ChecklistItemCreate.class);
                    intent.putExtra(ChecklistItemListView.EXTRA_MESSAGE,
                            ((ChecklistItemListView) context).passedChecklist);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ChecklistItemDetail.class);
                    intent.putExtra(ChecklistItemListView.EXTRA_MESSAGE, checklist_item_rowArrayList.get(position));
                    context.startActivity(intent);
                }

            }
        });

        return rowView;
    }

    /**
     * Updates the checklist percentage attribute.
     *
     * @param context
     */
    public void updateChecklistCompletePercentage(Context context) {
        // Retrieve Checklist from context.
        ChecklistRow checklist_row = ((ChecklistItemListView) context).passedChecklist;

        double numberOfItemsChecked = 0;

        // Count completed items.
        for (ChecklistItemRow row : checklist_item_rowArrayList) {
            if (row.isChecked())
                numberOfItemsChecked++;
        }

        // Calculate progress.
        final int CHECKLIST_TOTAL_COUNT = checklist_item_rowArrayList.size() - 1;
        int percentComplete = (int) ((numberOfItemsChecked / CHECKLIST_TOTAL_COUNT) * 100);

        // Assign percentage.
        checklist_row.setProgress(percentComplete);

        //Update database
        int returnStatus = ChecklistContractDBHelper.getDb_helper(context).updateChecklist(checklist_row);

        // If an error occurs in the transaction notify the user.
        if (returnStatus == ChecklistContractDBHelper.FAILURE) {
            //Insert Alert Dialog
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

            alertDialog.setTitle("Error");
            alertDialog.setIcon(-1).setIcon(context.getResources().getDrawable(R.mipmap.warning_image));
            alertDialog.setMessage("The checklist was unable to be updated. Please try again.");
            alertDialog.setNeutralButton("OK", null);
            alertDialog.setCancelable(true);
            alertDialog.create();
            alertDialog.show();
        }
    }
}
