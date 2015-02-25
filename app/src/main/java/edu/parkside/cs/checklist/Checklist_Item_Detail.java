package edu.parkside.cs.checklist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import u.ready_wisc.R;

/**
 * @author David Krawchuk
 * @email krawchukdavid@gmail.com
 * @date 02/20/2014
 *
 * Description:
 *
 */
public class Checklist_Item_Detail extends ActionBarActivity {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    Checklist_Item_Row passedItem;
    boolean editTextHasBeenEdited;
    /* INSTANCE VARIABLE BLOCK END */


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
        setContentView(R.layout.activity_checklist_item_detail);

        // Set button default status.
        ((Button)findViewById(R.id.activity_checklist_item_detail_update_button)).setEnabled(false);

        // Receive selected item.
        passedItem = this.getIntent().getParcelableExtra(Checklist_Item_ListView.EXTRA_MESSAGE);

        // Populate the views.
        new Runnable() {
            @Override
            public void run() {
                populateWidgets();
            }
        }.run();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checklist__item__detail, menu);
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
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
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
     *  Populate the text fields with data supplied by the selected item from the source activity.
     *
     */
    private void populateWidgets(){

        // Retrieve the text and fill the description text field.
        new Runnable() {
            @Override
            public void run() {
                populateDescriptionTextField();
            }
        }.run();

        EditText nameTextField = (EditText)findViewById(R.id.activity_checklist_item_detail_name_edittext);
        EditText qtyTextField = (EditText)findViewById(R.id.activity_checklist_item_detail_qty_edittext);

        nameTextField.setText(passedItem.getName());
        qtyTextField.setText("" + passedItem.getQty());
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Retrieve the description text from the database and populate the description text view.
     *
     *  @todo Alert user if error occurs.
     *
     */
    private void populateDescriptionTextField(){
        EditText descriptionTextField = (EditText)findViewById(R.id.activity_checklist_item_detail_decription_edittext);
        String description = Checklist_Contract_Db_Helper.getDb_helper(this).returnDescriptionFromItem(passedItem);

        if (description != null)
        {
            descriptionTextField.setText(description);

        }
        else
        {

        }
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *
     * @todo Alert user if error occurs.
     *
     * @param view
     */
    public void updateButtonPressed(View view){
        int status = Checklist_Contract_Db_Helper.getDb_helper(this).updateItem(passedItem,
                ((EditText)findViewById(R.id.activity_checklist_item_detail_decription_edittext)).getText().toString());

        if (status == Checklist_Contract_Db_Helper.FAILURE)
        {

        }
        else
            finish();
    }

    public void cancelButtonPressed(View view){
        finish();
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Called when the edit text views focus has changed.
     *
     * @param view
     */
    public void editTextViewHasBeenSelected(View view){
        if (editTextHasBeenEdited == false)
        {
            ((Button)findViewById(R.id.activity_checklist_item_detail_update_button)).setEnabled(true);
            editTextHasBeenEdited = true;
        }
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Attaches text watchers to the editText views.
     */
    private void attachTextListenersToTextViews()
    {
        final TextView nameField = ((TextView)this.findViewById(R.id.activity_checklist_item_detail_name_edittext));
        final TextView qtyField = ((TextView)this.findViewById(R.id.activity_checklist_item_detail_qty_edittext));
        final TextView descriptionField = ((TextView)this.findViewById(R.id.activity_checklist_item_detail_decription_edittext));

        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextViewHasBeenSelected(nameField);
            }
        });

        qtyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextViewHasBeenSelected(qtyField);
            }
        });

        descriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextViewHasBeenSelected(descriptionField);
            }
        });
    }
}
