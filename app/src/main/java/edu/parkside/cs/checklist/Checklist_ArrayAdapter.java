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

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import u.ready_wisc.R;

/**
 * Maps the activity_checklist.xml to the Checklist_Row objects.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class Checklist_ArrayAdapter extends ArrayAdapter {

    /* INSTANCE VARIABLE BLOCK START */
    Context context;
    ArrayList<Checklist_Row> list;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * Constructor
     *
     * @param context
     * @param resource
     */
    public Checklist_ArrayAdapter(Context context, int resource) {
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
    public Checklist_ArrayAdapter(Context context, int resource, ArrayList<Checklist_Row> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    /**
     * Resets the lists contents and notifies the listener that the adapter's contents have changed.
     *
     * @param list
     */
    public void setList(ArrayList<Checklist_Row> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * Returns the inflated row filled with the Checklist_Row attributes. Given the current editing
     * condition allows for selection or deletion of checklist rows.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     * @TODO Error condition needs to be implemented!
     * @TODO Replace string comparisons with index or Object type.
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

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((Checklist) context).isInEditMode) {
                    // If the item is not the Add Checklist row, allow removal.
                    if (position != list.size() - 1) {
                        int status = Checklist_Contract_Db_Helper.getDb_helper(context).deleteChecklist(list.get(position));
                        if (status == Checklist_Contract_Db_Helper.FAILURE) {
                            // Handle error. Possible display an alertView to notify user.
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
                    Intent intent = new Intent(context, Checklist_Create.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Checklist_Item_ListView.class);
                    intent.putExtra(Checklist.EXTRA_MESSAGE, list.get(position));
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }
}
