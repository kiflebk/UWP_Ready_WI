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
            theImageView.setImageResource(R.mipmap.ic_launcher);
        } else if (disaster == "Winter Storm") {
            theImageView.setImageResource(R.mipmap.ic_storm);
        } else if (disaster == "Fire/Wildfire") {
            theImageView.setImageResource(R.mipmap.ic_fire);
        } else if (disaster == "Flood") {
            theImageView.setImageResource(R.mipmap.flood_ic);
        } else if (disaster == "Power Outage") {
            theImageView.setImageResource(R.mipmap.ic_power_loss);
        } else if (disaster == "Public Health Emergency") {
            theImageView.setImageResource(R.mipmap.phe_ic);
        } else if (disaster == "Terrorism") {
            theImageView.setImageResource(R.mipmap.ic_terrorism);
        } else if (disaster == "Bomb Threats") {
            theImageView.setImageResource(R.mipmap.ic_bomb);
        } else {
            //This is where you can set the imageview.
            theImageView.setImageResource(R.drawable.rw);
        }
        return theView;
    }
}
