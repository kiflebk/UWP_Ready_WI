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

package u.ready_wisc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by nathaneisner on 6/2/15.
 */
public class CountyDialog {
    private MaterialDialog chooser;
    public Context context;
    private Set<String> keys = Config.COUNTIES.keySet();
    private String[] countyNames;
    private String primaryCounty;
    private HashSet<String> selectedCounties;
    public final static int PRIMARY_COUNTY = 0;
    public final static int SECONDARY_COUNTIES = 1;
    private final String PRIMARY_TITLE = "Primary County";
    private final String SECONDARY_TITLE = "Additional Counties";
    private int prePrimaryIndex;
    private Integer[] addCountyIndices;
    private boolean selectionChanged = false;
    private int type;

    public CountyDialog(Context ct, int type) {
        this.context = ct;
        selectedCounties = new HashSet<>();
        countyNames = keys.toArray(new String[keys.size()]);
        //sort alphabetical for display purposes
        Arrays.sort(countyNames);
        getPreviousSelections();
        switch (type) {
            case PRIMARY_COUNTY:
                this.type = PRIMARY_COUNTY;
                chooser = primaryBuilder();
                break;
            case SECONDARY_COUNTIES:
                this.type = SECONDARY_COUNTIES;
                chooser = secondaryBuilder();
                break;
            default:
                break;
        }
        //if context is splashactvity then when dialog is dismissed to go to menuactivity
        if (context instanceof SplashActivity) {
            chooser.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(context, MenuActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        if (context instanceof MenuActivity) {
            chooser.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ((MenuActivity) context).setCounties();
                }
            });
        }
    }

    //show dialog and check county list if an additional counties dialog
    public void showDialog() {
        if (type == SECONDARY_COUNTIES) {
            removePrimary();
            getPreviousSelections();
            chooser.setItems(countyNames);
            if (addCountyIndices != null)
                chooser.setSelectedIndices(addCountyIndices);
        }
        chooser.show();
    }

    //look up previously selected options and use them when the dialog appears
    private void getPreviousSelections() {
        SharedPreferences settings = context.getSharedPreferences("MyPrefsFile", 0);
        String prevPrimary = settings.getString("county", "");
        prePrimaryIndex = getCountyIndex(prevPrimary);
        Set<String> addCounties = settings.getStringSet("counties", null);
        ArrayList<Integer> tempAL = new ArrayList<>();
        if (addCounties != null) {
            for (String county : addCounties) {
                int index = getCountyIndex(county);
                if (index != -1)
                    tempAL.add(new Integer(index));
            }
            addCountyIndices = tempAL.toArray(new Integer[tempAL.size()]);
        }
    }

    //builder for primary county ... only one allowed
    private MaterialDialog primaryBuilder() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(PRIMARY_TITLE)
                .items(countyNames)
                .alwaysCallSingleChoiceCallback()
                        //TODO: Preselect county that user is currently in?
                .itemsCallbackSingleChoice(prePrimaryIndex, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        //Toast.makeText(context, String.valueOf(which), Toast.LENGTH_SHORT).show();
                        //bug in dialog library sometimes doesn't clear old selection
                        dialog.setSelectedIndex(-1);
                        dialog.setSelectedIndex(which);
                        primaryCounty = String.valueOf(text);
                        selectionChanged = true;
                        return true;
                    }
                })
                .positiveText("Done")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //done selecting
                        if (!selectionChanged) {
                            primaryCounty = countyNames[prePrimaryIndex];
                        }
                        primarySelected();
                    }

                    @Override
                    public void onNeutral(MaterialDialog dialog) {
                        prePrimaryIndex = dialog.getSelectedIndex();
                        primarySelected();
                    }
                });

        return builder.build();
    }

    //builder for the for secondary counties
    private MaterialDialog secondaryBuilder() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(SECONDARY_TITLE)
                .items(countyNames)
                .alwaysCallMultiChoiceCallback()
                .itemsCallbackMultiChoice(addCountyIndices, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        selectedCounties.clear();
                        addCounties(which, text);
                        selectionChanged = true;
                        return true;
                    }
                })
                .positiveText("Done")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //done selecting
                        multiSelected();
                    }
                });

        return builder.build();
    }

    //add selected counties to to county set
    private void addCounties(Integer[] which, CharSequence[] text) {
        for (int i = 0; i < which.length; i++) {
            selectedCounties.add(String.valueOf(text[i]));
        }
    }

    //primary selected county
    private void primarySelected() {
        // saving the primary county in the preferences for all future uses
        if (selectionChanged) {
            SharedPreferences settings = context.getSharedPreferences("MyPrefsFile", 0);
            Set<String> addCounties = settings.getStringSet("counties", null);
            String[] additionCounties = addCounties.toArray(new String[addCounties.size()]);
            if (additionCounties != null || additionCounties.length != 0) {
                for (int i = 0; i < additionCounties.length; i ++){
                    String county = additionCounties[i];
                    if (county.equals(primaryCounty)) {
                        addCounties.remove(county);
                    }
                }
            }
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("county", primaryCounty);
            editor.putStringSet("counties", addCounties);
            editor.apply();
        }
    }

    //returns the county type
    public int getType() {
        return type;
    }

    //multiple secondary counties
    private void multiSelected() {
        if (selectionChanged) {
            // saving the counties in the preferences for all future uses
            SharedPreferences settings = context.getSharedPreferences("MyPrefsFile", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putStringSet("counties", selectedCounties);
            editor.apply();
        }
    }

    //set what neutral button does
    public void setNeutralDialog(String name, final CountyDialog nDialog) {
        chooser.setActionButton(DialogAction.NEUTRAL, name);
        View neutral = chooser.getActionButton(DialogAction.NEUTRAL);
        neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooser.hide();
                switch (type) {
                    case PRIMARY_COUNTY:
                        primaryCounty = countyNames[chooser.getSelectedIndex()];
                        primarySelected();
                        break;
                    case SECONDARY_COUNTIES:
                        ArrayList<Integer> tempAL = new ArrayList<>();
                        for (Integer i : chooser.getSelectedIndices()) {
                            tempAL.add(i);
                        }
                        Integer[] indices = tempAL.toArray(new Integer[tempAL.size()]);
                        selectedCounties = getCountyNames(indices);
                        multiSelected();
                        break;
                    default:
                        break;
                }
                nDialog.showDialog();
            }
        });
    }

    //get the names of the counties for the given indices
    private HashSet<String> getCountyNames(Integer[] index) {
        HashSet<String> tH = new HashSet<>();
        for (int i : index) {
            tH.add(countyNames[i]);
        }
        return tH;
    }

    //gets te index of the given county name
    private int getCountyIndex(String county) {
        int index = 0;
        for (String name : countyNames) {
            if (name.equals(county)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    //remove primary county from additional county list
    private void removePrimary() {
        SharedPreferences settings = context.getSharedPreferences("MyPrefsFile", 0);
        String primCounty = settings.getString("county", "");
        if (!primCounty.isEmpty()) {
            int i = 0;
            Iterator<String> counties = Config.COUNTIES.keySet().iterator();
            String[] tmpCounties = new String[Config.COUNTIES.size() - 1];
            while (counties.hasNext()) {
                String county = counties.next();
                if (!primCounty.equals(county)) {
                    tmpCounties[i] = county;
                    i++;
                }
            }
            //sort alphabetically before finishing
            Arrays.sort(tmpCounties);
            countyNames = tmpCounties;
        }
    }
}
