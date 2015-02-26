package edu.parkside.cs.checklist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import u.ready_wisc.R;

import java.util.ArrayList;

/**
 /**
 * @author David Krawchuk
 * @email krawchukdavid@gmail.com
 * @date 02/25/2014
 *
 * Description:
 *  The controller for displaying the contents of a checklist.
 */
public class Checklist_Item_ListView extends ActionBarActivity {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    public static final String EXTRA_MESSAGE = "edu.parkside.cs.checklist_item_listview";

    Checklist_Item_ArrayAdapter checklist_item_arrayAdapter;
    ListView checklist_item_listView;
    Checklist_Row passedChecklist;
    boolean isInEditMode = false;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Getter.
     *
     * @return
     */
    private ListView getChecklist_item_listView(){
        if (checklist_item_listView == null)
        {
            checklist_item_listView = (ListView)findViewById(R.id.activity_checklist_item_listview);
        }
        return checklist_item_listView;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Creates a new adapter if one doesn't exist and attaches it to the listview within the activity.
     *
     * @return
     */
    private Checklist_Item_ArrayAdapter getChecklist_item_arrayAdapter(){
        if (checklist_item_arrayAdapter == null){
            checklist_item_arrayAdapter =
                    new Checklist_Item_ArrayAdapter(this, R.layout.activity_checklist_item_listview_row, new ArrayList<Checklist_Item_Row>());

            getChecklist_item_listView().setAdapter(checklist_item_arrayAdapter);
        }
        return checklist_item_arrayAdapter;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_item_listview);

        // Retrieve the passed checklist.
        passedChecklist = getIntent().getParcelableExtra(Checklist.EXTRA_MESSAGE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist__item_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *
     */
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.

        // Populate the listView with the contents of the Checklist table.
        new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        }.run();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").

        // Repopulate the listView with the contents of the Checklist table.
        new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        }.run();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *
     */
    public void populateListView()
    {
        // Clear the adapter contents.
        getChecklist_item_arrayAdapter().clear();

        // Repopulate the adapter contents.
        String [] query_of_items = {Checklist_Contract.Checklist_Item_Queries.fetchItems(passedChecklist)};
        getChecklist_item_arrayAdapter().addAll(Checklist_Contract_Db_Helper.getDb_helper(this).returnChecklistItemRows(query_of_items));

        // Notify clients that contents of the adapter have changed and they should update their state.
        getChecklist_item_arrayAdapter().notifyDataSetChanged();
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  When called changes the editMode boolean instance variable.
     *
     * @param menuItem
     */
    public void menuEditButtonPressed(MenuItem menuItem){
        isInEditMode = (isInEditMode == true) ? false : true;

        // Change the visual state of the application to indicate edit mode.

    }
}
