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

        switch (disaster) {
            case "Tornado":
                theImageView.setImageResource(R.drawable.ic_launcher);
                break;
            case "Winter Storm":
                theImageView.setImageResource(R.drawable.ic_storm);
                break;
            case "Fire/Wildfire":
                theImageView.setImageResource(R.drawable.ic_fire);
                break;
            case "Flood":
                theImageView.setImageResource(R.drawable.ic_flood);
                break;
            case "Power Outage":
                theImageView.setImageResource(R.drawable.ic_power_loss);
                break;
            case "Public Health Emergency":
                theImageView.setImageResource(R.drawable.phe_ic);
                break;
            case "Terrorism":
                theImageView.setImageResource(R.drawable.ic_terrorism);
                break;
            case "Bomb Threats":
                theImageView.setImageResource(R.drawable.ic_bomb);
                break;
            case "Thunderstorms":
                theImageView.setImageResource(R.drawable.ic_thunderstorm);
                break;
            case "Extreme Heat":
                theImageView.setImageResource(R.drawable.heat_wave);
                break;
            case "Build A Kit":
                theImageView.setImageResource(R.drawable.build_icon);
                break;
            case "Make A Plan":
                theImageView.setImageResource(R.drawable.list_icon);
                break;
            case "Volunteer":
                theImageView.setImageResource(R.drawable.volunteer_icon);
                break;
            case "Custom List":
                theImageView.setImageResource(R.drawable.custom_icon);
                break;
            case "Emergency Map":
                theImageView.setImageResource(R.drawable.map_icon);
                break;
            case "Shelters":
                theImageView.setImageResource(R.drawable.shelter_icon);
                break;
            case "Disaster Info":
                theImageView.setImageResource(R.drawable.disaster_icon);
                break;
            case "Report Damage":
                theImageView.setImageResource(R.drawable.list_icon);
                break;
            case "Social Media":
                theImageView.setImageResource(R.drawable.social_icon);
                break;
            case "Flashlight":
                theImageView.setImageResource(R.drawable.flashlight_ic);
                break;
            case "SOS Tone":
                theImageView.setImageResource(R.drawable.sos_icon);
                break;
            default:
                //This is where you can set the imageview.
                theImageView.setImageResource(R.drawable.rw);
                break;
        }
        return theView;
    }
}
