package edu.parkside.cs.checklist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import u.ready_wisc.R;

/**
 * @author David Krawchuk
 * @email krawchukdavid@gmail.com
 * @date 02/20/2015
 * <p/>
 * Description:
 * Provides the mapping between the checklist_item_row.xml and checklist_item_row objects.
 */
public class Checklist_Item_ArrayAdapter extends ArrayAdapter<Checklist_Item_Row> {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    ArrayList<Checklist_Item_Row> checklist_item_rowArrayList;
    Context context;
    int resourceID;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * @param context
     * @param resource
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2015
     * <p/>
     * Description:
     */
    public Checklist_Item_ArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resourceID = resource;
    }

    /**
     * @param context
     * @param resource
     * @param objects
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2015
     * <p/>
     * Description:
     * Default constructor.
     */
    public Checklist_Item_ArrayAdapter(Context context, int resource, ArrayList<Checklist_Item_Row> objects) {
        super(context, resource, objects);
        this.checklist_item_rowArrayList = objects;
        this.context = context;
        this.resourceID = resource;
    }

    /**
     * @param arrayList
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2015
     * <p/>
     * Description:
     * Setter.
     */
    public void setList(ArrayList<Checklist_Item_Row> arrayList) {
        this.checklist_item_rowArrayList = arrayList;
    }


    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2015
     * <p/>
     * Description:
     * Returns the inflated row filled with the Checklist_Item_Row attributes. Givin the current editing
     * condition allows for selection or deletion of checklist item rows.
     * @TODO Error condition needs to be implemented!
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
                Checklist_Contract_Db_Helper.getDb_helper(context).updateItem(checklist_item_rowArrayList.get(position), null);

                // Update checklist complete percentage.
                updateChecklistCompletePercentage(context);
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Checklist_Item_ListView) context).isInEditMode) {
                    // If the item is not the Add Checklist row, allow removal.
                    if (position != checklist_item_rowArrayList.size() - 1) {
                        // Create list of items to delete.
                        ArrayList<Checklist_Item_Row> itemToBeInserted = new ArrayList<Checklist_Item_Row>(1);
                        itemToBeInserted.add(checklist_item_rowArrayList.get(position));

                        int status = Checklist_Contract_Db_Helper.getDb_helper(context)
                                .deleteItems(itemToBeInserted);
                        if (status == Checklist_Contract_Db_Helper.FAILURE) {
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
                    Intent intent = new Intent(context, Checklist_Item_Create.class);
                    intent.putExtra(Checklist_Item_ListView.EXTRA_MESSAGE,
                            ((Checklist_Item_ListView) context).passedChecklist);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Checklist_Item_Detail.class);
                    intent.putExtra(Checklist_Item_ListView.EXTRA_MESSAGE, checklist_item_rowArrayList.get(position));
                    context.startActivity(intent);
                }

            }
        });

        return rowView;
    }

    /**
     * @param context
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 03/15/2015
     * <p/>
     * Description:
     * Updates the checklist percentage attribute.
     * @todo Add alert dialog to notify user of error.
     */
    public void updateChecklistCompletePercentage(Context context) {
        // Retrieve Checklist from context.
        Checklist_Row checklist_row = ((Checklist_Item_ListView) context).passedChecklist;

        double numberOfItemsChecked = 0;

        // Count completed items.
        for (Checklist_Item_Row row : checklist_item_rowArrayList) {
            if (row.isChecked())
                numberOfItemsChecked++;
        }

        // Calculate progress.
        final int CHECKLIST_TOTAL_COUNT = checklist_item_rowArrayList.size() - 1;
        int percentComplete = (int) ((numberOfItemsChecked / CHECKLIST_TOTAL_COUNT) * 100);

        // Assign percentage.
        checklist_row.setProgress(percentComplete);

        //Update database
        int returnStatus = Checklist_Contract_Db_Helper.getDb_helper(context).updateChecklist(checklist_row);

        // If an error occurs in the transaction notify the user.
        if (returnStatus == Checklist_Contract_Db_Helper.FAILURE) {
            //Insert Alert Dialog
        }
    }
}
