package edu.parkside.cs.checklist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import u.ready_wisc.R;

/**
 * @author David Krawchuk
 * @email krawchukdavid@gmail.com
 * @date 02/25/2014
 *
 * Description:
 *
 */
public class Checklist_Create_Item_Adapter extends Checklist_Item_ArrayAdapter {

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/25/2014
     *
     * Description:
     *
     *
     * @param context
     * @param resource
     */
    public Checklist_Create_Item_Adapter(Context context, int resource) {
        super(context, resource);
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/25/2014
     *
     * Description:
     *
     * @param context
     * @param resource
     * @param objects
     */
    public Checklist_Create_Item_Adapter(Context context, int resource, ArrayList<Checklist_Item_Row> objects) {
        super(context, resource, objects);
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * @TODO Implement delete mechanism when checkbox is selected.
     * @TODO Send description to detail activity.
     *
     * Description:
     *
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

        TextView itemName = (TextView)rowView.findViewById(R.id.checklist_item_listview_row_textview);
        final CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.checklist_item_listview_row_checkbox);

        itemName.setText(this.checklist_item_rowArrayList.get(position).getName());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Maybe delete the row when the user clicks?
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((Checklist_Item_ListView)context).isInEditMode)
                {
                    // If the item is not the Add Checklist row, allow removal.
                    if (position != checklist_item_rowArrayList.size() - 1)
                    {

                        int status = Checklist_Contract_Db_Helper.getDb_helper(context)
                                .deleteItems(new Checklist_Item_Row[]{checklist_item_rowArrayList.get(position)});
                        if (status == Checklist_Contract_Db_Helper.FAILURE)
                        {
                            // Handle error. Possible display an alertView to notify user.
                        }
                        else {
                            // Remove row and notify listeners that the adapter contents have changed.
                            checklist_item_rowArrayList.remove(position);
                            notifyDataSetChanged();
                        }
                    }


                }
                else if (checklist_item_rowArrayList.get(position).getName().contains("Add Item")){
                    Intent intent = new Intent(context, Checklist_Create_Item_Add.class);
                    ((Checklist_Create)context).startActivityForResult(intent, Checklist_Create.REQUEST);
                }
                else {
                    Intent intent = new Intent(context, Checklist_Item_Detail.class);
                    intent.putExtra(Checklist_Create.EXTRA_MESSAGE, checklist_item_rowArrayList.get(position));
                    // Send description.
                    ((Checklist_Create)context).startActivityForResult(intent, Checklist_Create.REQUEST);
                }

            }
        });

        return rowView;
    }

}
