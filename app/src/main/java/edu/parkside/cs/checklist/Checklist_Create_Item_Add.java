package edu.parkside.cs.checklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import u.ready_wisc.R;

/**
 * @author David Krawchuk
 * @email krawchukdavid@gmail.com
 * @date 02/20/2014
 *
 * Description:
 *  The controller for the Checklist_Create_Item_Add.xml layout.
 *  Class is meant to be used as a helper class to the Checklist Create Activity.
 */
public class Checklist_Create_Item_Add extends Checklist_Item_Create {

    public static final String EXTRA_MESSAGE_ITEM = "edu.parkside.cs.checklist_create_item_add_item";
    public static final String EXTRA_MESSAGE_DESC = "edu.parkside.cs.checklist_create_item_add_desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist_item_create);
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/25/2014
     *
     * Description:
     *
     *
     * @param view
     */
    @Override
    public void saveButtonPressed(View view) {

        // Retrieve fields from layout.
        EditText name_editText = (EditText)findViewById(R.id.activity_checklist_item_create_name_edittext);
        EditText qty_editText = (EditText)findViewById(R.id.activity_checklist_item_create_qty_edittext);
        EditText description_editText = (EditText)findViewById(R.id.activity_checklist_item_create_description_edittext);

        // Create and populate item.
        Checklist_Item_Row item_row = new Checklist_Item_Row();
        item_row.setName(name_editText.getText().toString());
        item_row.setQty(new Integer(qty_editText.getText().toString()));

        // Create and return the populated item and description to the calling activity.
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE_ITEM, item_row);
        intent.putExtra(EXTRA_MESSAGE_DESC, description_editText.getText().toString());
        setResult(RESULT_OK, intent);

        finish();
    }

}
