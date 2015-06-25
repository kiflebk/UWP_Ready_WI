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

package u.ready_wisc.Counties;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import u.ready_wisc.MenuActivity;
import u.ready_wisc.R;

/**
 * Created by nathaneisner on 6/18/15.
 */
public class CountyActivity extends AppCompatActivity {
    private View mapLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Menu actionBarMenu;
    private ActionBar actionBar;
    private Animator mCurrentAnimator;
    private Button btnDone;
    private int mAnimationDuration;
    private Rect startBounds;
    private Rect finalBounds;
    private Point globalOffset;
    private float startScale;
    private MenuItem searchItem;
    private ArrayAdapterSearchView searchText;
    private ArrayAdapter<String> searchAdapter;
    private ArrayList<String> searchList;
    private String primaryCounty;
    private Set<String> additionalCounties;
    private Boolean primarySelected = false;
    private SharedPreferences settings;
    private TextView primaryText;
    public static final int COUNTY_ACTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        settings = this.getSharedPreferences("MyPrefsFile", 0);
        additionalCounties = settings.getStringSet("counties", new HashSet<String>());
        primaryCounty = settings.getString("county", "");
        primaryText = (TextView) findViewById(R.id.txtPrimCountyName);
        if (!primaryCounty.isEmpty()) {
            primarySelected = true;
            primaryText.setText(primaryCounty);
        } else
            primaryText.setText("NONE");
        searchList = new ArrayList<>();
        searchList.addAll(Counties.ALL.keySet());
        makeSearchAdapter(searchList);
        mapLayout = findViewById(R.id.map_layout);
        mapLayout.setOnTouchListener(makeMapTouchListener());
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        btnDone = (Button) findViewById(R.id.btnDone);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Retrieve and cache the system's default animation time.
        mAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
        setupActions();
    }

    @Override
    public void onBackPressed() {
        if (mRecyclerView.getVisibility() == View.VISIBLE) {
            backToMap();
        } else if (searchItem.isActionViewExpanded()) {
            searchItem.collapseActionView();
            searchText.onActionViewCollapsed();
        } else {
            goBack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_county, menu);
        actionBarMenu = menu;
        searchItem = actionBarMenu.findItem(R.id.action_county_search);
        searchText = (ArrayAdapterSearchView) MenuItemCompat.getActionView(searchItem);
        searchText.setAdapter(searchAdapter);
        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do something when the user clicks on a county
                searchText.setText((String) parent.getItemAtPosition(position));
                searchText.clearFocus();
            }
        });
        searchText.setIconifiedByDefault(true);
        // Get the search close button image view
        ImageView closeButton = (ImageView) searchText.findViewById(R.id.search_close_btn);

        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //LoggerUtils.d(LOG, "Search close button clicked");
                //Find EditText view
                EditText et = (EditText) findViewById(R.id.search_src_text);

                //Clear the text from EditText view
                et.setText("");

                //Clear query
                searchText.setQuery("", false);
                //Collapse the action view
                searchText.onActionViewCollapsed();
                //Collapse the search widget
                searchItem.collapseActionView();
            }
        });
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //when the search query changes
                if (searchList.contains(newText)) {
                    //if the text is an actual county
                    searchText.clearFocus();
                    searchText.setText("");
                    searchItem.collapseActionView();
                    String[] tmp = {newText};
                    mAdapter = new CountyAdapter(tmp, CountyActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                    zoomToRegion(0, 0);
                    actionBar.setSubtitle("Search Result");
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (mRecyclerView.getVisibility() == View.VISIBLE) {
                backToMap();
                return true;
            } else {
                goBack();
            }
        }
        if (id == R.id.action_county_search) {
            //actionBar.setDisplayHomeAsUpEnabled(false);
        }
        if (id == R.id.action_clear) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Clear Primary County?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setPrimaryCounty("");
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    //An ontouchlistener for the state map
    private View.OnTouchListener makeMapTouchListener() {
        View.OnTouchListener mapClickListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getX();
                final int y = (int) event.getY();

                //gets what color was touched
                int touchColor = getColor(x, y);

                //finds if the color is close to what is touched
                if (closeMatch(Color.parseColor("#00ff66"), touchColor)) {
                    // Northern region
                    startRegion("N", x, y);
                }
                if (closeMatch(Color.parseColor("#00ffff"), touchColor)) {
                    // West Central
                    startRegion("WC", x, y);
                }
                if (closeMatch(Color.parseColor("#cc0033"), touchColor)) {
                    // South Central
                    startRegion("SC", x, y);
                }
                if (closeMatch(Color.parseColor("#cc6600"), touchColor)) {
                    // Northeast
                    startRegion("NE", x, y);
                }
                if (closeMatch(Color.parseColor("#ff00cc"), touchColor)) {
                    // Southeast
                    startRegion("SE", x, y);
                }
                return true;
            }
        };
        return mapClickListener;
    }

    //start a region with a given region and a x & y for annimation
    private void startRegion(String region, int x, int y) {
        ArrayList<String> counties = new ArrayList<>();
        for (County c : Counties.ALL.values()) {
            if (c != null) {
                if (c.getRegion().equals(region)) {
                    counties.add(c.getName());
                }
            }
        }
        mAdapter = new CountyAdapter(counties.toArray(new String[counties.size()]), this);
        mRecyclerView.setAdapter(mAdapter);
        zoomToRegion(x, y);
        actionBar.setSubtitle(Counties.REGIONS.get(region));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //gets the color of the special color drawable map of where a touch occured
    private int getColor(int x, int y) {
        ImageView img = (ImageView) findViewById(R.id.colortouch);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    //determines if the two colors are close enough
    public boolean closeMatch(int color1, int color2) {
        int tolerance = 25;
        if ((int) Math.abs(Color.red(color1) - Color.red(color2)) > tolerance)
            return false;
        if ((int) Math.abs(Color.green(color1) - Color.green(color2)) > tolerance)
            return false;
        if ((int) Math.abs(Color.blue(color1) - Color.blue(color2)) > tolerance)
            return false;
        return true;
    }

    //animation for changing view to show a region
    private void zoomToRegion(int x, int y) {
        startBounds = new Rect();
        finalBounds = new Rect();
        globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        startBounds.set(x - 10, y + 10, x + 10, y - 10);
        mRecyclerView
                .getGlobalVisibleRect(finalBounds, globalOffset);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
        startBounds.offset(-x, -y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        startScale = 0;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        mapLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        searchItem.collapseActionView();
        searchItem.setVisible(false);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        mRecyclerView.setPivotX(0f);
        mRecyclerView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(mRecyclerView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(mRecyclerView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(mRecyclerView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(mRecyclerView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;
    }

    //changes views back to map
    private void backToMap() {
        mapLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        actionBar.setSubtitle("");
        startBounds = null;
        finalBounds = null;
        startScale = 0;
        searchItem.setVisible(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    //set up anything with the action bar or buttons
    private void setupActions() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        //done onclick
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    //changes the searchtext adapter with the given list
    private void makeSearchAdapter(ArrayList<String> list) {
        // adapter for the search dropdown auto suggest
        searchAdapter = new ArrayAdapter<>(this, R.layout.item, list);
        if (searchText != null) {
            searchText.setAdapter(searchAdapter);
        }
    }

    //returns if there is a primarycounty selected
    public boolean hasPrimarySelected() {
        return primarySelected;
    }

    //returns the primary county
    public String getPrimaryCounty() {
        return primaryCounty;
    }

    //returns all additional counties
    public Set<String> getAdditionalCounties() {
        return additionalCounties;
    }

    //sets primary county to given name
    public void setPrimaryCounty(String name) {
        if (!name.isEmpty()) {
            primaryCounty = name;
            primarySelected = true;
            changePrimaryCbx(name);
            primaryText.setText(name);
            //Snackbar.make(getCurrentFocus(), "New Primary: " + name, Snackbar.LENGTH_SHORT).show();
        } else {
            primaryCounty = "";
            primarySelected = false;
            primaryText.setText("NONE");
            changePrimaryCbx("");
        }
    }

    //add additional county
    public void addAdditionalCounty(String name) {
        additionalCounties.add(name);
        Snackbar.make(getCurrentFocus(), "Added Additional: " + name, Snackbar.LENGTH_SHORT).show();
    }

    //remove additional county
    public void removeAdditionalCounty(String name) {
        additionalCounties.remove(name);
        Snackbar.make(getCurrentFocus(), "Removed Additional: " + name, Snackbar.LENGTH_SHORT).show();
    }

    //changes the primary checkboxes when a new primary is selected
    private void changePrimaryCbx(String newPrim) {
        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            View v = mRecyclerView.getChildAt(i);
            TextView countyName = (TextView) v.findViewById(R.id.txtCounty);
            CheckBox primCbx = (CheckBox) v.findViewById(R.id.cbxPrim);
            CheckBox addCbx = (CheckBox) v.findViewById(R.id.cbxAdd);
            if (!countyName.getText().equals(newPrim)) {
                primCbx.setChecked(false);
                primCbx.setEnabled(false);
            } else {
                addCbx.setEnabled(false);
                addCbx.setChecked(false);
            }
            if (newPrim.isEmpty()) {
                primCbx.setChecked(false);
                primCbx.setEnabled(true);
                addCbx.setEnabled(true);
            }
        }
    }

    //decides to go back to menu activity
    private void goBack() {
        if (primarySelected) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("county", primaryCounty);
            editor.putStringSet("counties", additionalCounties);
            editor.apply();
            setResult(COUNTY_ACTION);
            Intent intent = new Intent(CountyActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Primary County needs to be selected!")
                    .setCancelable(true)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

}

