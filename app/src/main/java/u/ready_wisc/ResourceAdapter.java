package u.ready_wisc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jake on 3/11/2015.
 */

// Adapts a list of resources to the Disaster Resource ListView
public class ResourceAdapter extends ArrayAdapter<ResourceItem>{

    private final Context context;
    private final ArrayList<ResourceItem> resourceList;

    public ResourceAdapter(Context context, ArrayList<ResourceItem> resourceList){
        super(context, R.layout.resources_listadapt, resourceList);
        this.context = context;
        this.resourceList = resourceList;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        // Set up
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.resources_listadapt,parent,false);

        // Identify widgets
        TextView nameText = (TextView) row.findViewById(R.id.resourceName);
        TextView addressText = (TextView) row.findViewById(R.id.resourceAddress);
        TextView phoneText = (TextView) row.findViewById(R.id.resourcePhone);
        ImageView resourceIcon = (ImageView) row.findViewById(R.id.resourceIcon);

        // Populate widgets
        nameText.setText(resourceList.get(position).getName());
        addressText.setText(resourceList.get(position).getAddress());
        phoneText.setText(resourceList.get(position).getPhone());

        // Icon decider
        // Adds an image to the row based on resource type
        String iconType = resourceList.get(position).getType();
        switch(iconType.toLowerCase()){
            case "hospital":
                resourceIcon.setImageResource(R.drawable.reshosp75);
                break;
            case "sheriff":
                resourceIcon.setImageResource(R.drawable.resheriff75);
                break;
            case "fire":
                resourceIcon.setImageResource(R.drawable.resfire75);
                break;
        }

        return row;
    }
}
