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


package rss;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import u.ready_wisc.R;
import u.ready_wisc.RssActivity;


//Fragment is built to hold the RSS listview


public class RssFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static String weatherDesc;
    public static String weatherLink;
    private ProgressBar progressBar;
    private ListView listView;
    // Once the {@link RssService} finishes its task, the result is sent to this
    // ResultReceiver.
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.GONE);
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
            if (items != null) {
                RssAdapter adapter = new RssAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "An error occurred while downloading the rss feed.",
                        Toast.LENGTH_LONG).show();
            }
            listView.setVisibility(View.VISIBLE);
        }
    };
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If a current RSS fragment isn't loaded, the RSS feed is read in and
        // a new fragment is created and placed in the container.
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.listView);
            listView.setOnItemClickListener(this);
            startService();
        }

        // If we are returning from a configuration change:
        // "view" is still attached to the previous view hierarchy
        // so we need to remove it and re-attach it to the current one
        else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    // RSS service is started
    private void startService() {
        Intent intent = new Intent(getActivity(), RssService.class);
        intent.putExtra(RssService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    @Override
    // If a listitem is clicked, the details of the item are loaded into a seperate intent and started
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);

        weatherLink = item.getLink();
        weatherDesc = item.getDesc();

        Intent intent = new Intent(getActivity(), RssActivity.class);
        startActivity(intent);
    }
}
