package edu.parkside.cs.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import u.ready_wisc.R;

/**
 * @author David Krawchuk
 * @email krawchukdavid@gmail.com
 * @date 02/20/2014
 *
 * Description:
 *  Class represents the Checklist creation activity.
 */
public class Checklist_Create extends ActionBarActivity {

    // The request code required by the returning activity callback.
    static final int REQUEST = 1;
    public static final String EXTRA_MESSAGE = "edu.parkside.cs.checklist_create";

    static final int SUCCESS = 0;
    static final int CHECKLIST_INSERT_ERROR = 1;
    static final int CHECKLIST_ROW_INSERT_ERROR = 2;
    static final int CHECKLIST_DESCRIPTION_ERROR = 3;

    /* INSTANCE VARIABLE BLOCK BEGIN */
    Checklist_Row checklist_row;
    ArrayList<Checklist_Item_Row> added_items;
    ArrayList<String> added_descriptions;
    Checklist_Create_Item_Adapter checklist_create_item_adapter;
    ListView checklist_item_listView;
    boolean isInEditMode = false;
    /* INSTANCE VARIABLE BLOCK END */

    Checklist_Row getChecklist_row()
    {
        if (checklist_row == null){
            checklist_row = new Checklist_Row(0, "Empty", 0);
        }
        return checklist_row;
    }

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
            checklist_item_listView = (ListView)findViewById(R.id.activity_checklist_create_item_listView);
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
    private Checklist_Item_ArrayAdapter getChecklist_create_item_adapter(){
        if (checklist_create_item_adapter == null){
            checklist_create_item_adapter =
                    new Checklist_Create_Item_Adapter(this, R.layout.activity_checklist_item_listview_row, new ArrayList<Checklist_Item_Row>());

            getChecklist_item_listView().setAdapter(checklist_create_item_adapter);
        }
        return checklist_create_item_adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_create);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist__kit__create, menu);
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

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Called when the activity returns from some state other than active. Populates the list
     *  with the contents of the added items.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").

        // Repopulate the listView with the contents of the
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
       // Notify clients that contents of the adapter have changed and they should update their state.
        getChecklist_create_item_adapter().notifyDataSetChanged();
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * @TODO Add case to handle return from detail view.
     *
     * Description:
     *  Callback method called when the Checklist_Create_Item_Add activity returns. Called prior to
     *  onResume().
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Fetch results from the returned Intent parameter and add the contents to the proper
                //  arraylist.
                Checklist_Item_Row item = data.getParcelableExtra(Checklist_Create_Item_Add.EXTRA_MESSAGE_ITEM);
                String description = data.getStringExtra(Checklist_Create_Item_Add.EXTRA_MESSAGE_DESC);

                added_items.add(item);
                added_descriptions.add(description);
            }
        }
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * @TODO Change the visual state of the application to indicate edit mode.
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

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *
     *
     * @param button
     */
    public void addButtonPressed(View button){
        Intent intent = new Intent(this, Checklist_Create_Item_Add.class);
        startActivityForResult(intent, REQUEST);
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/25/2014
     *
     * Description:
     *
     *
     * @param button
     */
    public void cancelButtonPressed(View button){
        finish();
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/25/2014
     *
     * @TODO Implement Error handling / AlertViews.
     *
     * Description:
     *
     *
     * @param button
     */
    public void saveButtonPressed(View button){

        if (createChecklist() == CHECKLIST_INSERT_ERROR){
            // alert user of error and break on some condition.
        }
        if (createChecklistRows() == CHECKLIST_ROW_INSERT_ERROR){
            // alert user of error and break on some condition.
        }
        if (createChecklistRowDescriptions() == CHECKLIST_DESCRIPTION_ERROR){
            // alert user of error and break on some condition.
        }

        // If control reaches this point all is ok. Return.
        finish();
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/25/2014
     *
     * Description:
     *
     *
     * @return
     */
    public int createChecklist(){
        EditText name_editText = (EditText)findViewById(R.id.activity_checklist_create_edittext);

        int status =
                Checklist_Contract_Db_Helper.getDb_helper(this).addChecklist(new Checklist_Row(name_editText.getText().toString(), 0));

        return (status == Checklist_Contract_Db_Helper.SUCCESS) ? SUCCESS : CHECKLIST_INSERT_ERROR;
    }

    public int createChecklistRows(){
        return SUCCESS;
    }

    public int createChecklistRowDescriptions(){
        return SUCCESS;
    }


}
