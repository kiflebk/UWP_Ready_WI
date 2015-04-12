package edu.parkside.cs.checklist;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import u.ready_wisc.R;

public class ChecklistItemDetailUnitTest extends ActivityUnitTestCase<ChecklistItemDetail> {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    private Intent intent;
    /* INSTANCE VARIABLE BLOCK END */

    public ChecklistItemDetailUnitTest() {
        super(ChecklistItemDetail.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        intent = new Intent(getInstrumentation().getContext(), ChecklistItemDetail.class);
    }

    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testPreconditions() {

        //Start the activity under test in isolation, without values for savedInstanceState and
        //lastNonConfigurationInstance
        startActivity(intent, null, null);

        final TextView nameTextField = (TextView) getActivity().findViewById(R.id.activity_checklist_item_detail_name_textview);
        final EditText nameEditTextField = (EditText) getActivity().findViewById(R.id.activity_checklist_item_detail_name_edittext);
        final TextView qtyTextField = (TextView) getActivity().findViewById(R.id.activity_checklist_item_detail_qty_textview);
        final EditText qtyEditTextField = (EditText) getActivity().findViewById(R.id.activity_checklist_item_detail_qty_edittext);
        final TextView descriptionTextField = (TextView) getActivity().findViewById(R.id.activity_checklist_item_detail_description_textview);
        final EditText descriptionField = (EditText) getActivity().findViewById(R.id.activity_checklist_item_detail_decription_edittext);
        final Button cancelButton = (Button) getActivity().findViewById(R.id.activity_checklist_item_detail_cancel_button);
        final Button updateButton = (Button) getActivity().findViewById(R.id.activity_checklist_item_detail_update_button);


        // Verify UI elements instantiate
        assertNotNull("Launched Activity is null", getActivity());
        assertNotNull("nameTextField is null", nameTextField);
        assertNotNull("nameEditTextField is null", nameEditTextField);
        assertNotNull("qtyTextField is null", qtyTextField);
        assertNotNull("qtyEditTextField is null", qtyEditTextField);
        assertNotNull("descriptionTextField is null", descriptionTextField);
        assertNotNull("descriptionField is null", descriptionField);
        assertNotNull("cancelButton is null", cancelButton);
        assertNotNull("updateButton is null", updateButton);


        Resources resources = getActivity().getResources();

        assertEquals("Label contents do not match expected.", nameTextField.getText(),
                resources.getString(R.string.activity_checklist_item_detail_name_textview));
        assertEquals("Label contents do not match expected.", qtyTextField.getText(),
                resources.getString(R.string.activity_checklist_item_detail_qty_textview));
        assertEquals("Label contents do not match expected.", descriptionTextField.getText(),
                resources.getString(R.string.activity_checklist_item_detail_description_textview));
        assertEquals("Button contents do not match expected.", cancelButton.getText(),
                resources.getString(R.string.activity_checklist_item_detail_cancel_button));
        assertEquals("Button contents do not match expected.", updateButton.getText(),
                resources.getString(R.string.activity_checklist_item_detail_update_button));

    }

    @MediumTest
    public void testActivityFinishedWhenCancelButtonPressed(){

        startActivity(intent, null, null);

        final Button cancelButton = (Button) getActivity().findViewById(R.id.activity_checklist_item_detail_cancel_button);

        cancelButton.performClick();
        assertTrue("Activity did not call finish()", isFinishCalled());
    }

    @MediumTest
    public void testUpdateButtonIsDisabled(){
        startActivity(intent, null, null);

        final Button updateButton = (Button) getActivity().findViewById(R.id.activity_checklist_item_detail_update_button);

        assertFalse("Update button is enabled.", updateButton.isEnabled());
    }

}