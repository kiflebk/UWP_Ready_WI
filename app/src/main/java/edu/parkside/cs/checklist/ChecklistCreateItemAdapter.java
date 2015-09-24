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
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import u.readybadger.R;

/**
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015 *
 *          <p/>
 *          Description:
 *          The data source for the checklist create item listview.
 *          Provides the listeners for displaying item details and deletion upon clicking
 *          the checkbox.
 * @email krawchukdavid@gmail.com
 */
public class ChecklistCreateItemAdapter extends ChecklistItemArrayAdapter {
    /* Instance variable block. */
    ArrayList<String> arrayOfDescriptions = null;
    /* Instance variable block. */

    /**
     * Constructor.
     *
     * @param context
     * @param resource
     * @see ChecklistItemArrayAdapter
     */
    public ChecklistCreateItemAdapter(Context context, int resource) {
        super(context, resource);
    }

    /**
     * Constructor.
     *
     * @param context
     * @param resource
     * @param objects
     * @param arrayOfDescriptions
     * @see ChecklistItemArrayAdapter
     */
    public ChecklistCreateItemAdapter(Context context, int resource, ArrayList<ChecklistItemRow> objects, ArrayList<String> arrayOfDescriptions) {
        super(context, resource, objects);
        this.arrayOfDescriptions = arrayOfDescriptions;
    }

    /**
     * Provides the data to the source listview that requests the data.
     * Attaches onclick listeners to the items in order to view item details or delete items when
     * item checkbox is clicked.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_checklist_item_listview_row, parent, false);

        TextView itemName = (TextView) rowView.findViewById(R.id.checklist_item_listview_row_textview);
        final CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checklist_item_listview_row_checkbox);

        itemName.setText(this.checklist_item_rowArrayList.get(position).getName());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Remove checklist item and description.
                // 2. Update listview.
                checklist_item_rowArrayList.remove(position);
                arrayOfDescriptions.remove(position);
                notifyDataSetChanged();
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChecklistCreateItemAdd.class);
                intent.putExtra(ChecklistCreateItemAdd.EXTRA_MESSAGE_ITEM, checklist_item_rowArrayList.get(position));
                intent.putExtra(ChecklistCreateItemAdd.EXTRA_MESSAGE_DESC, arrayOfDescriptions.get(position));
                // Send description.
                ((ChecklistCreate) context).startActivityForResult(intent, ChecklistCreate.UPDATE);
            }
        });

        return rowView;
    }

}
