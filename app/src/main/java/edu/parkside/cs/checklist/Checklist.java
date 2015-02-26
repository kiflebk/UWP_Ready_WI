package edu.parkside.cs.checklist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import u.ready_wisc.R;

import java.util.ArrayList;

public class Checklist extends ActionBarActivity {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    public final static String EXTRA_MESSAGE = "edu.parkside.cs.checklist.checklist";
    private Checklist_ArrayAdapter arrayAdapter;
    private ListView checklistListView;
    protected boolean isInEditMode = false;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Returns a valid listview object as referenced in the layout file.
     *
     * @return
     */
    private ListView getChecklistListView(){
        if (checklistListView == null){
            checklistListView = (ListView)findViewById(R.id.checklist_listview);
        }

        return checklistListView;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Returns the ArrayAdapter in a valid state by creating an empty Arraylist and attaching it to
     *   the ListView.
     *
     * @return
     */
    private Checklist_ArrayAdapter getArrayAdapter()
    {
        if (arrayAdapter == null)
        {
            arrayAdapter = new Checklist_ArrayAdapter(this, R.layout.activity_checklist_row, new ArrayList<Checklist_Row>());
            getChecklistListView().setAdapter(arrayAdapter);
        }
        return arrayAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Checklist_Contract_Db_Helper.getDb_helper(this).addChecklist(new Checklist_Row("Summer", 34));
        //Checklist_Contract_Db_Helper.getDb_helper(this).addChecklist(new Checklist_Row("Winter", 75));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist, menu);
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
     *  Before the activity is visible to the user the list is populated.
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

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  When the activity has resumed from some inactive state. Clear and repopulate the list.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").

        getArrayAdapter().clear();
        // Populate the listView with the contents of the Checklist table.
        // Possible new checklists have been added.
        new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        }.run();
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     */
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
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
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")

        // Save the state of the activity.
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
     *  Populates the list view object with the contents of the Checklist table.
     */
    private void populateListView(){

        // Set the adapter to default state.
        getArrayAdapter().clear();

        // Get the array adapter and add the feched results to the adapter.
        getArrayAdapter().addAll(Checklist_Contract_Db_Helper.getDb_helper(this).returnChecklistRows(null));

        // Notify the listview that the data within the adapter has changed and the view needs to
        //  be redrawn to reflect this change.
        getArrayAdapter().notifyDataSetChanged();
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
