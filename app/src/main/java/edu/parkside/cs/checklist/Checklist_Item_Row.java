package edu.parkside.cs.checklist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author David Krawchuk
 * @email krawchukdavid@gmail.com
 * @date 02/20/2014
 *
 * Description:
 *  Provides the physical support for the Checklist Items.
 *
 *  Note: This class implements Parcelable for performance considerations. Parceable allows the object
 *   to be passed between activities.
 */
public class Checklist_Item_Row implements Parcelable {

    /* INSTANCE VARIABLE BLOCK BEGIN */
    private int entryid;
    private String name;
    private int qty;
    private boolean isChecked;
    private int checklist_entryid;
    /* INSTANCE VARIABLE BLOCK END */

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description: Default constructor.
     */
    public Checklist_Item_Row(){
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Constructor.
     *
     * @param title
     * @param isChecked
     */
    public Checklist_Item_Row(String title, boolean isChecked){
        this.name = title;
        this.isChecked = isChecked;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Parcelable constructor.
     *
     * @param in
     */
    public Checklist_Item_Row(Parcel in){
        readFromParcel(in);
    };

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Getter.
     *
     * @return
     */
    public String getName() {
        if (name == null)
        {
            name = "Empty";
        }
        return name;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Setter.
     *
     *  @todo Verify input.
     *
     * @param name
     */
    public void setName(String name) {
        // 1. Check for valid input.
        // 2. Is the new setting reasonable?
        // 3. If so then set name.
        this.name = name;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Getter.
     *
     * @return
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Setter.
     *
     *  @todo Verify input.
     */
    public void setChecked() {
        // 1. Check for valid input.
        // 2. Is the new setting reasonable?
        // 3. If so then set checked.

        this.isChecked = (this.isChecked == true) ? false : true;
    }


    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Setter.
     *
     *  @todo Verify input.
     *
     * @param condition
     */
    public void setChecked(int condition)
    {
        // 1. Check for valid input.
        // 2. Is the new setting reasonable?
        // 3. If so then set checked.
        this.isChecked = (condition == 1) ? true : false;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Getter. Converts internal implementation and returns an int.
     *
     * @return
     */
    public int getChecked(){
        return (isChecked == true) ? 1 : 0;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Getter.
     *
     * @return
     */
    public int getEntryid() {
        return entryid;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Setter.
     *
     * @todo Verify input values.
     *
     * @param entryid
     */
    public void setEntryid(int entryid) {
        this.entryid = entryid;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Getter.
     *
     * @return
     */
    public int getQty() {
        return qty;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Setter.
     * @param qty
     */
    public void setQty(int qty) {
        this.qty = qty;
    }


    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Getter.
     *
     * @return
     */
    public int getChecklist_entryid() {
        return checklist_entryid;
    }


    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Setter.
     *
     *  @todo Verify input.
     *
     * @param checklist_entryid
     */
    public void setChecklist_entryid(int checklist_entryid) {
        this.checklist_entryid = checklist_entryid;
    }


    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Required method for the parcelable implementation.
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Translates the object attributes into parcel elements.
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
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Translates the parcel elements into object attributes.
     *
     * @param source
     */
    private void readFromParcel(Parcel source){
        setEntryid(source.readInt());
        setName(source.readString());
        setQty(source.readInt());
        setChecked(source.readInt());
        setChecklist_entryid(source.readInt());
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Required creator method for the parcelable implementation.
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
}
