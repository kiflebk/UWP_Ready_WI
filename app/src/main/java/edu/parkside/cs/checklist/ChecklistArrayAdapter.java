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
import android.widget.ProgressBar;
import android.widget.TextView;

import u.readybadger.R;

import java.util.ArrayList;

/**
 * Maps the activity_checklist.xml to the ChecklistRow objects.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class ChecklistArrayAdapter extends ArrayAdapter {

    /* INSTANCE VARIABLE BLOCK START */
    Context context;
    ArrayList<ChecklistRow> list;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * Constructor
     *
     * @param context
     * @param resource
     */
    public ChecklistArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    /**
     * Constructor.
     *
     * @param context
     * @param resource
     * @param objects
     */
    public ChecklistArrayAdapter(Context context, int resource, ArrayList<ChecklistRow> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    /**
     * Resets the lists contents and notifies the listener that the adapter's contents have changed.
     *
     * @param list
     */
    public void setList(ArrayList<ChecklistRow> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * Returns the inflated row filled with the ChecklistRow attributes. Given the current editing
     * condition allows for selection or deletion of checklist rows.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Layout references.
        final View rowView = inflater.inflate(R.layout.activity_checklist_row, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.activity_checklist_row_textview);
        ProgressBar progressBar = (ProgressBar) rowView.findViewById(R.id.activity_checklist_row_progressbar);

        // Apply layout attributes.
        title.setText(this.list.get(position).getTitle());
        progressBar.setProgress(this.list.get(position).getProgress());

        // If the item is not the Add Checklist row, allow removal.
        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((Checklist) context).isInEditMode) {
                    if (position != list.size() - 1) {
                        int status = ChecklistContractDBHelper.getDb_helper(context).deleteChecklist(list.get(position));
                        if (status == ChecklistContractDBHelper.FAILURE) {
                            // Alert user to error.
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                            alertDialog.setTitle("Error");
                            alertDialog.setIcon(-1).setIcon(context.getResources().getDrawable(R.mipmap.warning_image));
                            alertDialog.setMessage("The " + list.get(position).getTitle() +
                                    " checklist could not be deleted.");

                            alertDialog.setNeutralButton("OK", null);
                            alertDialog.setCancelable(true);
                            alertDialog.create();
                            alertDialog.show();
                        } else {
                            // Remove row and notify listeners that the adapter contents have changed.
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }


                }
                // Should use last list index, or some object type instead of string,
                // bad idea if we end up using localized strings.
                else if (list.get(position).getTitle().contains("Add Checklist")) {
                    Intent intent = new Intent(context, ChecklistCreate.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ChecklistItemListView.class);
                    intent.putExtra(Checklist.EXTRA_MESSAGE, list.get(position));
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }
}
