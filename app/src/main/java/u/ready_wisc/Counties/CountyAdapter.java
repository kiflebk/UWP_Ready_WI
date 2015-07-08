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
*/

package u.ready_wisc.Counties;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Set;

import u.ready_wisc.R;

/**
 * Created by nathaneisner on 6/21/15.
 */
public class CountyAdapter extends RecyclerView.Adapter<CountyAdapter.ViewHolder> {
    private String[] mDataset;
    private CountyActivity countyActivity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CountyAdapter(String[] myDataset, CountyActivity ca) {
        //sort first
        Arrays.sort(myDataset);
        this.mDataset = myDataset;
        this.countyActivity = ca;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CountyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selectable_county, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String countyName = mDataset[position].trim();
        final Set<String> additionalCounties = countyActivity.getAdditionalCounties();
        TextView txtCounty = (TextView) holder.mView.findViewById(R.id.txtCounty);
        CheckBox primary = (CheckBox) holder.mView.findViewById(R.id.cbxPrim);
        CheckBox additional = (CheckBox) holder.mView.findViewById(R.id.cbxAdd);
        String thePrimary = countyActivity.getPrimaryCounty();
        if (countyActivity.hasPrimarySelected()) {
            if (thePrimary.equals(countyName)) {
                additional.setEnabled(false);
                additional.setChecked(false);
                primary.setChecked(true);
                primary.setEnabled(true);
            } else {
                primary.setEnabled(false);
            }
        }

        if (additionalCounties.contains(countyName)) {
            additional.setChecked(true);
        }

        primary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    countyActivity.setPrimaryCounty(countyName);
                } else {
                    countyActivity.setPrimaryCounty("");
                }
            }
        });
        additional.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    countyActivity.addAdditionalCounty(countyName);
                } else {
                    countyActivity.removeAdditionalCounty(countyName);
                }
            }
        });
        txtCounty.setText(countyName);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

