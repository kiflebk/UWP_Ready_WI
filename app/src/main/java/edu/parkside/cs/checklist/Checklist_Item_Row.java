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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Provides the physical support for the Checklist Items.
 * <p/>
 * Note: This class implements Parcelable for performance considerations. Parcelable allows the object
 * to be passed between activities.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class Checklist_Item_Row implements Parcelable {
    /**
     * Required creator method for the parcelable implementation.
     */
    public static final Parcelable.Creator<Checklist_Item_Row> CREATOR
            = new Parcelable.Creator<Checklist_Item_Row>() {
        public Checklist_Item_Row createFromParcel(Parcel in) {
            return new Checklist_Item_Row(in);
        }

        public Checklist_Item_Row[] newArray(int size) {
            return new Checklist_Item_Row[size];
        }
    };
    /* INSTANCE VARIABLE BLOCK BEGIN */
    private int entryid;
    private String name;
    private int qty;
    private boolean isChecked;
    /* INSTANCE VARIABLE BLOCK END */
    private int checklist_entryid;

    /**
     * Constructor.
     */
    public Checklist_Item_Row() {
    }

    /**
     * Constructor.
     *
     * @param title
     * @param isChecked
     */
    public Checklist_Item_Row(String title, boolean isChecked) {
        this.name = title;
        this.isChecked = isChecked;
    }

    ;

    /**
     * Parcel constructor.
     *
     * @param in
     */
    public Checklist_Item_Row(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Getter. If the reference is null, the a string is returned with the contents of "Empty".
     *
     * @return
     */
    public String getName() {
        if (name == null) {
            name = "Empty";
        }
        return name;
    }

    /**
     * Setter.
     *
     * @param name
     * @todo Verify input.
     */
    public void setName(String name) {
        // 1. Check for valid input.
        // 2. Is the new setting reasonable?
        // 3. If so then set name.
        this.name = name;
    }

    /**
     * Getter.
     *
     * @return
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Setter.
     *
     * @todo Verify input.
     */
    public void setChecked() {
        // 1. Check for valid input.
        // 2. Is the new setting reasonable?
        // 3. If so then set checked.

        this.isChecked = (this.isChecked == true) ? false : true;
    }

    /**
     * Getter. Converts internal implementation and returns an int.
     */
    public int getChecked() {
        return (isChecked == true) ? 1 : 0;
    }

    /**
     * Setter.
     *
     * @todo Verify input.
     */
    public void setChecked(int condition) {
        // 1. Check for valid input.
        // 2. Is the new setting reasonable?
        // 3. If so then set checked.
        this.isChecked = (condition == 1) ? true : false;
    }

    /**
     * Getter.
     */
    public int getEntryid() {
        return entryid;
    }

    /**
     * Setter.
     *
     * @param entryid
     * @todo Verify input values.
     */
    public void setEntryid(int entryid) {
        this.entryid = entryid;
    }

    /**
     * Getter.
     *
     * @return
     */
    public int getQty() {
        return qty;
    }

    /**
     * Setter.
     *
     * @param qty
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * Getter.
     *
     * @return
     */
    public int getChecklist_entryid() {
        return checklist_entryid;
    }

    /**
     * Setter.
     *
     * @param checklist_entryid
     * @todo Verify input.
     */
    public void setChecklist_entryid(int checklist_entryid) {
        this.checklist_entryid = checklist_entryid;
    }

    /**
     * Required method for the parcelable implementation.
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Translates the object attributes into parcel elements.
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getEntryid());
        dest.writeString(getName());
        dest.writeInt(getQty());
        dest.writeInt(getChecked());
        dest.writeInt(getChecklist_entryid());
    }

    /**
     * Translates the parcel elements into object attributes.
     *
     * @param source
     */
    private void readFromParcel(Parcel source) {
        setEntryid(source.readInt());
        setName(source.readString());
        setQty(source.readInt());
        setChecked(source.readInt());
        setChecklist_entryid(source.readInt());
    }
}
