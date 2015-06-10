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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import u.ready_wisc.Config;
import u.ready_wisc.R;
import u.ready_wisc.RssActivity;


//Fragment is built to hold the RSS listview
public class RssFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ProgressBar progressBar;
    private ListView listView;
    private TextView textView;
    private String county;
    private Intent service;
    private boolean notificationReceived = false;

    // Once the RssService & Pushbot notification finishes its task, the result is sent to this
    // ResultReceiver.
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.GONE);
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
            //only use pushbots if primary county
            if (county.equals(Config.countyPrim.getName())) {
                SharedPreferences sp = getActivity().getSharedPreferences("MyPrefsFile", 0);
                notificationReceived = sp.contains("message");
                if (notificationReceived) {
                    String message = sp.getString("message","");
                    //VV to clear message VV
                    //sp.edit().remove("message").apply();
                    if (!message.isEmpty()) {
                        items.add(new RssItem(message, "", ""));
                    }
                    notificationReceived = false;
                }
            }
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
    public void onDestroy() {
        super.onDestroy();
        //stop service for this county when fragment destroys
        if (service != null) {
            getActivity().stopService(service);
        }
    }

    public void setCounty(String county) {
        this.county = county;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If a current RSS fragment isn't loaded, the RSS feed is read in and
        // a new fragment is created and placed in the container.
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.listView);
            textView = (TextView) view.findViewById(R.id.awText);
            textView.setText("Alerts and Warnings for\n" + county + " County");
            listView.setOnItemClickListener(this);
            if (isOnline()) {
                startService();
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
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
    public void startService() {
        service = new Intent(getActivity(), RssService.class);
        service.putExtra(RssService.RECEIVER, resultReceiver);
        service.putExtra("county", county);
        getActivity().startService(service);
    }

    @Override
    // If a listitem is clicked, the details of the item are loaded into a seperate intent and started
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);

        String link = item.getLink();
        String desc = item.getDesc();
        String title = item.getTitle();

        Intent intent = new Intent(getActivity(), RssActivity.class);
        intent.putExtra("county",county);
        intent.putExtra("link",link);
        intent.putExtra("desc",desc);
        intent.putExtra("title",title);
        startActivity(intent);
    }

    // returns true or false based on if device has an internet connection.
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
