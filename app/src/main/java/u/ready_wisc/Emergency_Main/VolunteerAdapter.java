package u.ready_wisc.Emergency_Main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import u.ready_wisc.R;
import u.ready_wisc.VolunteerItem;

/**
 * Adapter for volunteer item listview
 */
public class VolunteerAdapter extends ArrayAdapter<VolunteerItem> {

    private final Context context;
    private final ArrayList<VolunteerItem> volunteerList;

    public VolunteerAdapter(Context context, ArrayList<VolunteerItem> volunteerList) {
        super(context, R.layout.volunteer_list_adapter, volunteerList);
        this.context = context;
        this.volunteerList = volunteerList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.volunteer_list_adapter, parent, false);

        TextView nameText = (TextView) row.findViewById(R.id.volunteerName);
        TextView addressText = (TextView) row.findViewById(R.id.volunteerPhone);
        TextView phoneText = (TextView) row.findViewById(R.id.volunteerEmail);
        TextView picText = (TextView) row.findViewById(R.id.volunteerSite);

        nameText.setText(volunteerList.get(position).getName());
        addressText.setText(volunteerList.get(position).getPhone());
        phoneText.setText(volunteerList.get(position).getEmail());
        picText.setText(volunteerList.get(position).getUrl());

        return row;
    }
}
