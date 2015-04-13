package edu.parkside.cs.checklist;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.ListView;

import u.ready_wisc.R;

/**
 * Created by krawchukd on 4/12/15.
 */
public class ChecklistFunctionalTest extends ActivityInstrumentationTestCase2<Checklist> {

    /* Instance Variable Block Begin */
    private static final int TIMEOUT_IN_MS =7000;
    private Checklist checklistActivity;
    /* Instance Variable Block End */

    /**
     *
     */
    public ChecklistFunctionalTest() {
        super(Checklist.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        checklistActivity = getActivity();
    }

    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testPreconditions() {
        assertNotNull("checklistActivity is null", checklistActivity);
    }

    @MediumTest
    public void testTouchAddChecklistRowLaunchesActivity(){
        //Because this functional test tests interaction across multiple components these views
        //are part of the actual test method and not of the test fixture
        final ListView listView = (ListView) checklistActivity.findViewById(R.id.checklist_listview);

        //Create and add an ActivityMonitor to monitor interaction between the system and the
        //ReceiverActivity
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation()
                .addMonitor(ChecklistCreate.class.getName(), null, false);

        //Request focus on the EditText field. This must be done on the UiThread because
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                listView.requestFocus();
            }
        });

        //Wait until all events from the MainHandler's queue are processed
        getInstrumentation().waitForIdleSync();

        int listViewRowCount = listView.getChildCount();
        View view = listView.getChildAt(listViewRowCount - 1);
        TouchUtils.clickView(this, listView.getChildAt(listViewRowCount - 1));

        //Wait until ChecklistCreate was launched and get a reference to it.
        ChecklistCreate receiverActivity = (ChecklistCreate) receiverActivityMonitor
                .waitForActivityWithTimeout(TIMEOUT_IN_MS);
        //Verify that ChecklistCreate Activity was started
        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1,
                receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", ChecklistCreate.class,
                receiverActivity.getClass());

        //Unregister monitor for ReceiverActivity
        getInstrumentation().removeMonitor(receiverActivityMonitor);
    }
}
