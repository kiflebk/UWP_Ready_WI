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
 * Checklist_Row class represents the checklist and its storable contents.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class Checklist_Row implements Parcelable {
    /**
     * Parcelable creator method.
     */
    public static final Parcelable.Creator<Checklist_Row> CREATOR
            = new Parcelable.Creator<Checklist_Row>() {
        public Checklist_Row createFromParcel(Parcel in) {
            return new Checklist_Row(in);
        }

        public Checklist_Row[] newArray(int size) {
            return new Checklist_Row[size];
        }
    };
    /* Instance Variables Begin */
    private int entryid;
    private String title;
    /* Instance Variables End */
    private int progress;

    /**
     * Constructs a Checklist_Row object from a Parcel object.
     *
     * @param in
     */
    public Checklist_Row(Parcel in) {
        readFromParcel(in);
    }

    /**
     * Constructor.
     */
    public Checklist_Row() {
        title = "Empty";
        progress = 0;
    }

    /**
     * Constructor.
     *
     * @param title
     * @param progress
     */
    public Checklist_Row(String title, int progress) {
        this.title = title;
        this.progress = progress;
    }

    /**
     * Constructor.
     *
     * @param entryid
     * @param title
     * @param progress
     */
    public Checklist_Row(int entryid, String title, int progress) {
        this.entryid = entryid;
        this.title = title;
        this.progress = progress;
    }

    /**
     * Title getter
     *
     * @return
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Title setter.
     *
     * @param title
     */
    public void setTitle(String title) {
        // 1. Check string length.
        // 2. Filter valid inputs.
        // 3. If 1 + 2 succeed then set string.

        this.title = title;
    }

    /**
     * Progress getter.
     *
     * @return
     */
    public int getProgress() {
        return this.progress;
    }

    /**
     * Progress setter.
     */
    public void setProgress(int progress) {
        // 1. Check for valid input.
        // 2. Is the new setting reasonable?
        // 3. If so then set progress.

        this.progress = progress;
    }

    /**
     * Entry getter.
     *
     * @return
     */
    public int getEntryid() {
        return entryid;
    }

    /**
     * Entry setter.
     *
     * @param entryid
     */
    public void setEntryid(int entryid) {
        this.entryid = entryid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Translates Checklist_Row object into parcel.
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(entryid);
        dest.writeString(title);
        dest.writeInt(progress);
    }

    /**
     * Translates parcel to Checklist_Row object.
     *
     * @param source
     */
    private void readFromParcel(Parcel source) {
        this.entryid = source.readInt();
        this.title = source.readString();
        this.progress = source.readInt();
    }

}
