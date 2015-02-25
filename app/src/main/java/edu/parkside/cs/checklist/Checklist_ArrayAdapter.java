package edu.parkside.cs.checklist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import u.ready_wisc.R;

import java.util.ArrayList;

/**
 * @author David Krawchuk
 * @email krawchukdavid@gmail.com
 * @date 02/20/2014
 *
 * Description:
 *  Maps the activity_checklist.xml to the Checklist_Row objects.
 */
public class Checklist_ArrayAdapter extends ArrayAdapter {

    /* INSTANCE VARIABLE BLOCK START */
    Context context;
    ArrayList<Checklist_Row> list;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Alternative constructor.
     *
     * @param context
     * @param resource
     */
    public Checklist_ArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Default constructor.
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
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Resets the lists contents and notifies the listener that the adapter's contents have changed.
     *
     * @param list
     */
    public void setList(ArrayList<Checklist_Row> list){
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Returns the inflated row filled with the Checklist_Row attributes. Givin the current editing
     *   condition allows for selection or deletion of checklist rows.
     *
     *   @TODO Error condition needs to be implemented!
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
        TextView title = (TextView)rowView.findViewById(R.id.activity_checklist_row_textview);
        ProgressBar progressBar = (ProgressBar)rowView.findViewById(R.id.activity_checklist_row_progressbar);

        // Apply layout attributes.
        title.setText(this.list.get(position).getTitle());
        progressBar.setProgress(this.list.get(position).getProgress());

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((Checklist)context).isInEditMode)
                {
                    // If the item is not the Add Checklist row, allow removal.
                    if (position != list.size() - 1)
                    {
                        int status = Checklist_Contract_Db_Helper.getDb_helper(context).deleteChecklist(list.get(position));
                        if (status == Checklist_Contract_Db_Helper.FAILURE)
                        {
                            // Handle error. Possible display an alertView to notify user.
                        }
                        else {
                            // Remove row and notify listeners that the adapter contents have changed.
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }


                }
                else if (list.get(position).getTitle().contains("Add Checklist")){
                        Intent intent = new Intent(context, Checklist_Create.class);
                        context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, Checklist_Item_ListView.class);
                    intent.putExtra(Checklist.EXTRA_MESSAGE, list.get(position));
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }
}
