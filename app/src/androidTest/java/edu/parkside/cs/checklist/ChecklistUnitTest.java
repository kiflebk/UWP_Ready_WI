package edu.parkside.cs.checklist;


import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.ListView;

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
    public void testChecklistListViewContents(){
        startActivity(intent, null, null);

        getInstrumentation().callActivityOnCreate(getActivity(), null);
        getInstrumentation().callActivityOnStart(getActivity());
        getActivity().onResume();

        ListView listView = (ListView) getActivity().findViewById(R.id.checklist_listview);
        listView.setVisibility(ListView.VISIBLE);
        int viewcount = listView.getChildCount();
        int isVisible = listView.getVisibility();
        int count = listView.getCount();


    }
}