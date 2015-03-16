package u.ready_wisc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by OZAN on 3/13/2015.
 * this is my custom adapter to allow a list to be displayed with icons aka
 * imageViews next to them.
 */
public class myAdapter extends ArrayAdapter<String> {
    public myAdapter(Context context, String[] values) {
        super(context, R.layout.row_layout_2, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflater puts the layout into the correct view
        LayoutInflater theInflater = LayoutInflater.from(getContext());

        //inflate takes the resource to load the parent that the resource may be loaded into
        //and true or false if we are loading into a parent view
        View theView = theInflater.inflate(R.layout.row_layout_2, parent, false);

        //Get the Text from the array which is for our disasters
        String disaster = getItem(position);

        //get the textview we want to edit
        TextView theTextView = (TextView) theView.findViewById(R.id.textView1);

        //put the next disaster into the textview
        theTextView.setText(disaster);

        //get the imageView in the layout
        ImageView theImageView = (ImageView) theView.findViewById(R.id.imageview1);

        if (disaster == "Tornado") {
            theImageView.setImageResource(R.drawable.ic_launcher);
        } else if (disaster == "Winter Storm") {
            theImageView.setImageResource(R.drawable.ic_storm);
        } else if (disaster == "Fire/Wildfire") {
            theImageView.setImageResource(R.drawable.ic_fire);
        } else if (disaster == "Flood") {
            theImageView.setImageResource(R.drawable.ic_flood);
        } else if (disaster == "Power Outage") {
            theImageView.setImageResource(R.drawable.ic_power_loss);
        } else if (disaster == "Public Health Emergency") {
            theImageView.setImageResource(R.drawable.phe_ic);
        } else if (disaster == "Terrorism") {
            theImageView.setImageResource(R.drawable.ic_terrorism);
        } else if (disaster == "Bomb Threats") {
            theImageView.setImageResource(R.drawable.ic_bomb);
        } else if (disaster == "Thunderstorms") {
            theImageView.setImageResource(R.drawable.ic_thunderstorm);
        } else if (disaster == "Extreme Heat") {
            theImageView.setImageResource(R.drawable.heat_wave);
        } else if (disaster == "Build A Kit") {
            theImageView.setImageResource(R.drawable.build_icon);
        } else if (disaster == "Make A Plan") {
            theImageView.setImageResource(R.drawable.list_icon);
        } else if (disaster == "Volunteer") {
            theImageView.setImageResource(R.drawable.volunteer_icon);
        } else if (disaster == "Custom List") {
            theImageView.setImageResource(R.drawable.custom_icon);
        } else if (disaster == "Emergency Map") {
            theImageView.setImageResource(R.drawable.map_icon);
        } else if (disaster == "Shelters") {
            theImageView.setImageResource(R.drawable.shelter_icon);
        } else if (disaster == "Disaster Info") {
            theImageView.setImageResource(R.drawable.disaster_icon);
        } else if (disaster == "Report Damage") {
            theImageView.setImageResource(R.drawable.list_icon);
        } else if (disaster == "Social Media") {
            theImageView.setImageResource(R.drawable.social_icon);
        } else {
            //This is where you can set the imageview.
            theImageView.setImageResource(R.drawable.rw);
        }
        return theView;
    }
}
