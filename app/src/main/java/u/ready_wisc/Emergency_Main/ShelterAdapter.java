package u.ready_wisc.Emergency_Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import u.ready_wisc.R;
import u.ready_wisc.ShelterItem;

/**
 * Created by Ben on 5/2/2015.
 */
public class ShelterAdapter extends ArrayAdapter<ShelterItem> {

    private final Context context;
    private final ArrayList<ShelterItem> shelterList;

    public ShelterAdapter(Context context, ArrayList<ShelterItem> shelterList){
        super(context, R.layout.shelter_list_adapter, shelterList);
        this.context = context;
        this.shelterList = shelterList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.resources_listadapt,parent,false);

        TextView nameText = (TextView) row.findViewById(R.id.shelterName);
        TextView addressText = (TextView) row.findViewById(R.id.shelterAddress);
        TextView phoneText = (TextView) row.findViewById(R.id.shelterPhone);
        TextView picText = (TextView) row.findViewById(R.id.shelterPerson);
        TextView cityText = (TextView) row.findViewById(R.id.shelterCity);
        ImageView shelterIcon = (ImageView) row.findViewById(R.id.shelterIcon);

        nameText.setText(shelterList.get(position).getOrganization());
        addressText.setText(shelterList.get(position).getAddress());
        phoneText.setText(shelterList.get(position).getPhone());
        picText.setText(shelterList.get(position).getContact());
        cityText.setText(shelterList.get(position).getCity());

        shelterIcon.setImageResource(R.drawable.shelter_icon);

        return row;
    }
}
