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
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import u.ready_wisc.MenuActivity;
import u.ready_wisc.R;
import u.ready_wisc.RssActivity;


//Fragment is built to hold the RSS listview
public class RssFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ProgressBar progressBar;
    private ListView listView;
    private TextView textView;
    private String county;
    private Intent service;
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
            view = inflater.inflate(R.layout.rss_fragment, container, false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.listView);
            textView = (TextView) view.findViewById(R.id.awText);
            textView.setText("Alerts and Warnings for\n" + county + " County");
            final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)
                    getActivity().findViewById(R.id.swipe_container);
            listView.setOnItemClickListener(this);
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int topRowVerticalPosition =
                            (listView == null || listView.getChildCount() == 0) ?
                                    0 : listView.getChildAt(0).getTop();
                    swipeRefreshLayout.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                }
            });
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
        setPrevAndNext();
        return view;
    }

    //setup next and previous buttons
    private void setPrevAndNext() {
        ImageButton leftArr = (ImageButton) view.findViewById(R.id.previousIcon);
        ImageButton rightArr = (ImageButton) view.findViewById(R.id.nextIcon);
        final MenuActivity ma = (MenuActivity) getActivity();
        if (leftArr != null) {
            leftArr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ma.movePager("prev");
                }
            });
            if (ma.hasLeftFragment(county)) {
                leftArr.setVisibility(View.VISIBLE);
            } else {
                leftArr.setVisibility(View.GONE);
            }
        }
        if (rightArr != null) {
            rightArr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ma.movePager("next");
                }
            });
            if (ma.hasRightFragment(county)) {
                rightArr.setVisibility(View.VISIBLE);
            } else {
                rightArr.setVisibility(View.GONE);
            }
        }
    }

    // RSS service is started
    public void startService() {
        service = new Intent(getActivity(), RssService.class);
        service.putExtra(RssService.RECEIVER, resultReceiver);
        service.putExtra("county", county);
        getActivity().startService(service);
    }

    @Override
    // If a listitem is clicked, the details of the item are loaded into a separate intent and started
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);

        String link = item.getLink();
        String desc = item.getDesc();
        String title = item.getTitle();

        Intent intent = new Intent(getActivity(), RssActivity.class);
        intent.putExtra("county", county);
        intent.putExtra("link", link);
        intent.putExtra("desc", desc);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    // returns true or false based on if device has an internet connection.
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // Once the RssService & Pushbot notification finishes its task, the result is sent to this
    // ResultReceiver.
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.GONE);
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssService.ITEMS);
            //only use pushbots if primary county
            SharedPreferences sp = getActivity().getSharedPreferences("MyPrefsFile", 0);
            if (county.equals(sp.getString("county",""))) {
                if (sp.contains("messages")) {
                    Set<String> messages = sp.getStringSet("messages", null);
                    if (messages != null && !messages.isEmpty()) {
                        for (String message : messages) {
                            assert items != null;
                            items.add(new RssItem(message, "", ""));
                        }
                    }
                }
            }
            if (items != null) {
                RssAdapter adapter = new RssAdapter(getActivity(), items);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "An error occurred while downloading the feed.",
                        Toast.LENGTH_LONG).show();
            }
            listView.setVisibility(View.VISIBLE);
        }
    };

}
