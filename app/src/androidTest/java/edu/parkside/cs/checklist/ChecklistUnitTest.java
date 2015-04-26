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

package edu.parkside.cs.checklist;


import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import u.ready_wisc.R;

public class ChecklistUnitTest extends ActivityUnitTestCase<Checklist> {

    private Intent intent;

    public ChecklistUnitTest() {
        super(Checklist.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Create intent to launch activity.
        intent = new Intent(getInstrumentation().getContext(), Checklist.class);
    }

    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testPreconditions() {
        //Start the activity under test in isolation, without values for savedInstanceState and
        //lastNonConfigurationInstance
        startActivity(intent, null, null);
        final ListView checklistListView = (ListView) getActivity().findViewById(R.id.checklist_listview);

        assertNotNull("Checklist Activity is null", getActivity());
        assertNotNull("checklistListView is null", checklistListView);
    }


    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {

        startActivity(intent, null, null);

        // Retain reference to activity.
        Checklist checklistActivity = (Checklist)getActivity();
        checklistActivity.populateListView();

        // Get the index of the last object which is assumed always to be the creation view.
        final int addChecklistIndex = checklistActivity.getArrayAdapter().list.size() - 1;

        // Retrieve ListView
        ListView checklistListView = (ListView) getActivity().findViewById(R.id.checklist_listview);

        // Perform click on the add checklist.
        checklistListView.getChildAt(addChecklistIndex);

        // Get the intent for the next started activity
        final Intent launchIntent = getStartedActivityIntent();
        //Verify the intent was not null.
        assertNotNull("Intent was null", launchIntent);
        //Verify that LaunchActivity was finished after button click
        assertTrue(isFinishCalled());

        final String payload =
            launchIntent.getStringExtra(Checklist.EXTRA_MESSAGE);
        assertEquals("Payload is empty. As it should be.", null, null);
    }

    @MediumTest
    public void testAddChecklistTextIsPresentInListViewRow(){

        // Retrieve last row with index.
        final View row = fetchChecklistAddListViewRow();

        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView title = (TextView)row.findViewById(R.id.checklist_item_listview_row_textview);

                    assertEquals("Add Checklist", title.getText());
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    protected View fetchChecklistAddListViewRow(){

        startActivity(intent, null, null);

        // Retain reference to activity.
        Checklist checklistActivity = (Checklist)getActivity();
        checklistActivity.populateListView();

        // Get the index of the last object which is assumed always to be the creation view.
        final int addChecklistIndex = checklistActivity.getArrayAdapter().list.size() - 1;

        // Retrieve ListView
        final ListView checklistListView = (ListView) getActivity().findViewById(R.id.checklist_listview);

        // Retrieve last row with index.
        return checklistListView.getChildAt(addChecklistIndex);
    }
}
