package edu.parkside.cs.checklist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krawchukd on 2/15/15.
 */
public class Checklist_Row implements Parcelable {

    // Instance variables.
    private int entryid;
    private String title;
    private int progress;

    public Checklist_Row(Parcel in){
        readFromParcel(in);
    }

    public Checklist_Row(){
        title = "Empty";
        progress = 0;
    }

    public Checklist_Row(String title, int progress)
    {
        this.title = title;
        this.progress = progress;
    }

    public Checklist_Row(int entryid, String title, int progress) {
        this.entryid = entryid;
        this.title = title;
        this.progress = progress;
    }

    public void setTitle(String title){
        // 1. Check string length.
        // 2. Filter valid inputs.
        // 3. If 1 + 2 succeed then set string.

        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setProgress(int progress)
    {
        // 1. Check for valid input.
        // 2. Is the new setting reasonable?
        // 3. If so then set progress.

        this.progress = progress;
    }

    public int getProgress(){
        return this.progress;
    }

    public int getEntryid() {
        return entryid;
    }

    public void setEntryid(int entryid) {
        this.entryid = entryid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(entryid);
        dest.writeString(title);
        dest.writeInt(progress);
    }

    private void readFromParcel(Parcel source){
        this.entryid = source.readInt();
        this.title = source.readString();
        this.progress = source.readInt();
    }

    public static final Parcelable.Creator<Checklist_Row> CREATOR
            = new Parcelable.Creator<Checklist_Row>() {
        public Checklist_Row createFromParcel(Parcel in) {
            return new Checklist_Row(in);
        }

        public Checklist_Row[] newArray(int size) {
            return new Checklist_Row[size];
        }
    };

}
