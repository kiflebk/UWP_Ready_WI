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
public class ResourceAdapter extends ArrayAdapter<ResourceItem>{

    private final Context context;
    private final ArrayList<ResourceItem> resourceList;

    public ResourceAdapter(Context context, ArrayList<ResourceItem> resourceList){
        super(context, R.layout.resources_listadapt, resourceList);
        this.context = context;
        this.resourceList = resourceList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.resources_listadapt,parent,false);

        TextView nameText = (TextView) row.findViewById(R.id.resourceName);
        TextView addressText = (TextView) row.findViewById(R.id.resourceAddress);
        TextView phoneText = (TextView) row.findViewById(R.id.resourcePhone);
        TextView otherText = (TextView) row.findViewById(R.id.resourceOther);
        ImageView resourceIcon = (ImageView) row.findViewById(R.id.resourceIcon);

        nameText.setText(resourceList.get(position).getName());
        addressText.setText(resourceList.get(position).getAddress());
        phoneText.setText(resourceList.get(position).getPhone());
        otherText.setText(resourceList.get(position).getOther());

//        Icon decider to be implemented, basic idea here
//        String iconType = resourceList.get(position).getType();
//        switch(iconType.toLowerCase()){
//            case "hospital":
//                resourceIcon.setImageResource(R.id.icon_hospital);
//                break;
//            case "sheriff":
//                resourceIcon.setImageResource(R.id.icon_sheriff);
//                break;
//            case "shelter":
//                resourceIcon.setImageResource(R.id.shelter_icon);
//                break;
//            case "fire":
//                resourceIcon.setImageResource(R.id.fire_icon);
//                break;
//        }

        return row;
    }
}
