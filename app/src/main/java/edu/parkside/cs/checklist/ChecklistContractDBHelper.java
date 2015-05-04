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

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Helper class maps operations between the application and database using the checklist_contract
 * as a template.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public class ChecklistContractDBHelper extends SQLiteOpenHelper {
    /* Instance variable block begin */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ChecklistContract.db";
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    private static ChecklistContractDBHelper db_helper;
    /* Instance variable block end */

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public ChecklistContractDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Create a helper object to create, open, and/or manage a database.
     * The database is not actually created or opened until one of
     * {@link #getWritableDatabase} or {@link #getReadableDatabase} is called.
     * <p/>
     * <p>Accepts input param: a concrete instance of {@link android.database.DatabaseErrorHandler} to be
     * used to handle corruption when sqlite reports database corruption.</p>
     *
     * @param context      to use to open or create the database
     * @param name         of the database file, or null for an in-memory database
     * @param factory      to use for creating cursor objects, or null for the default
     * @param version      number of the database (starting at 1); if the database is older,
     *                     {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                     newer, {@link #onDowngrade} will be used to downgrade the database
     * @param errorHandler the {@link android.database.DatabaseErrorHandler} to be used when sqlite reports database
     */
    public ChecklistContractDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * Creates and returns a new instance of the databaseHelper even if one exists.
     *
     * @param context
     * @return
     */
    public static ChecklistContractDBHelper getDb_helper(Context context) {
        if (db_helper == null) {
            db_helper = new ChecklistContractDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return db_helper;
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        createChecklistTable(db);
        createItemTable(db);
        createDescriptionTable(db);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Called when the database needs to be downgraded. This is strictly similar to
     * {@link #onUpgrade} method, but is called whenever current version is newer than requested one.
     * However, this method is not abstract, so it is not mandatory for a customer to
     * implement it. If not overridden, default implementation will reject downgrade and
     * throws SQLiteException
     * <p/>
     * <p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    /**
     * Creates the initial Checklist table.
     *
     * @param database
     */
    private void createChecklistTable(SQLiteDatabase database) {

        database.execSQL(Checklist_Contract.Checklist_Queries.CREATE_CHECKLIST_TABLE);
    }

    /**
     * Called to create the initial Item Table.
     *
     * @param database
     */
    private void createItemTable(SQLiteDatabase database) {

        database.execSQL(Checklist_Contract.Checklist_Item_Queries.CREATE_ITEM_TABLE);
    }

    /**
     * Called to create the initial Checklist Table.
     *
     * @param database
     */
    private void createDescriptionTable(SQLiteDatabase database) {

        database.execSQL(Checklist_Contract.Checklist_Description_Qureries.CREATE_DESCRIPTION_TABLE);
    }

    /* Checklist Block Begin */

    /**
     * Populates the passed ArrayList with the contents of the cursor.
     * Note: Any changes to the Checklist Object and table must be reflected in this
     * method as well.
     *
     * @param cursor
     * @param list
     */
    private void populateListWithChecklist(Cursor cursor, ArrayList<ChecklistRow> list) {

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                ChecklistRow checklist_row = new ChecklistRow();
                int entryid_index = cursor.getColumnIndex((Checklist_Contract.Checklist._ID));
                int titleIndex = cursor.getColumnIndex(Checklist_Contract.Checklist.COLUMN_NAME_TITLE);
                int progressIndex = cursor.getColumnIndex(Checklist_Contract.Checklist.COLUMN_NAME_PROGRESS);

                checklist_row.setEntryid(cursor.getInt(entryid_index));
                checklist_row.setTitle(cursor.getString(titleIndex));
                checklist_row.setProgress(cursor.getInt(progressIndex));

                list.add(checklist_row);
            } while (cursor.moveToNext());
        }

    }

    /**
     * Takes an array of queries. If the input parameter is null, all results are returned.
     * Upon an sql exception, the method returns a null value.
     *
     * @param args
     * @return
     */
    public ArrayList<ChecklistRow> returnChecklistRows(String[] args) {

        ArrayList<ChecklistRow> rowList = new ArrayList();
        Cursor cursor;
        SQLiteDatabase database = this.getReadableDatabase();

        try {
            // Begin database interaction.
            database.beginTransaction();

            if (args == null) {
                cursor = database.rawQuery(Checklist_Contract.Checklist_Queries.ALL_ITEMS, null);
                populateListWithChecklist(cursor, rowList);
            } else {
                for (String query : args) {
                    cursor = database.rawQuery(query, null);
                    populateListWithChecklist(cursor, rowList);
                }
            }
            rowList.add(new ChecklistRow("Add Checklist", 0));

            // End database interaction.
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (SQLiteException e) {
            rowList = null;
        } finally {
            return rowList;
        }
    }

    /**
     * When passed a Checklist Row object inserts that object into the Checklist table.
     *
     * @param checklist_row
     * @return
     */
    public int addChecklist(ChecklistRow checklist_row) {

        boolean transaction_success = true;
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            database.beginTransaction();
            database.execSQL(Checklist_Contract.Checklist_Queries.insertChecklist(checklist_row));
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (SQLiteException e) {
            transaction_success = false;
        } finally {
            return (transaction_success == true) ? SUCCESS : FAILURE;

        }
    }

    /**
     * Updates the Checklist table with the values of the Checklist Row object. Upon completion
     * the method returns the status of the transaction.
     *
     * @param checklist_row
     * @return
     */
    public int updateChecklist(ChecklistRow checklist_row) {

        boolean transaction_success = true;
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            database.beginTransaction();
            database.execSQL(Checklist_Contract.Checklist_Queries.updateChecklist(checklist_row));
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (SQLiteException e) {
            transaction_success = false;
        } finally {
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }
    }

    /**
     * Removes the provided checklist row from the Checklist table. Upon completion the method
     * returns the status of the transaction.
     *
     * @param checklist_row
     * @return
     */
    public int deleteChecklist(ChecklistRow checklist_row) {

        boolean transaction_success = false;
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            database.beginTransaction();
            database.execSQL(Checklist_Contract.Checklist_Queries.deleteChecklist(checklist_row));
            database.setTransactionSuccessful();
            database.endTransaction();

            ArrayList<ChecklistItemRow> rowsToBeDeleted = new ArrayList();
            populateListWithChecklistItemRow(database.rawQuery(Checklist_Contract.Checklist_Item_Queries.fetchItems(checklist_row), null), rowsToBeDeleted);

            database.beginTransaction();
            deleteItems(rowsToBeDeleted);
            transaction_success = true;

            database.setTransactionSuccessful();
            database.endTransaction();


        } catch (Exception e) {
            System.out.println(e.toString());
            transaction_success = false;
        } finally {
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }
    }

    /* Checklist Block End */

    /* Checklist Item Block Begin */

    /**
     * Returns the description referenced in the item parameter. If an exception occurs a null value
     * is returned.
     *
     * @param item
     * @return
     *
     */
    public String returnDescriptionFromItem(ChecklistItemRow item) {

        String descriptionText = null;
        Cursor cursor;
        SQLiteDatabase database = this.getReadableDatabase();

        try {
            // Begin interaction with database.
            database.beginTransaction();

            cursor = database.rawQuery(Checklist_Contract.Checklist_Description_Qureries.getDescriptionWithItemEntryID(item.getEntryid()), null);

            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                do {
                    int descriptionIndex = cursor.getColumnIndex(Checklist_Contract.Description.COLUMN_NAME_DESCRIPTION);

                    descriptionText = cursor.getString(descriptionIndex);
                } while (cursor.moveToNext());
            } else
                descriptionText = "Empty";

            // End interaction with the database.
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (SQLiteException e) {
            System.out.println(e.toString());
        } finally {
            return descriptionText;
        }

    }

    /**
     * Populates the passed ArrayList with the contents of the cursor.
     * Note: Any changes to the Checklist Item Object and table must be reflected in this
     * method as well.
     *
     * @param cursor
     * @param rowList
     */
    public void populateListWithChecklistItemRow(Cursor cursor, ArrayList<ChecklistItemRow> rowList) {
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                ChecklistItemRow checklist_item_row = new ChecklistItemRow();
                /* Get the indexes of the columns within the cursor to reference, matching
                 *  the Item table. Note if table column order changes this will not effect
                 *  the method.
                 */
                int entryid_index = cursor.getColumnIndex(Checklist_Contract.Item._ID);
                int nameIndex = cursor.getColumnIndex(Checklist_Contract.Item.COLUMN_NAME_NAME);
                int qtyIndex = cursor.getColumnIndex(Checklist_Contract.Item.COLUMN_NAME_QTY);
                int isCheckedIndex = cursor.getColumnIndex(Checklist_Contract.Item.COLUMN_NAME_COMPLETE);
                int checklist_entry_index = cursor.getColumnIndex(Checklist_Contract.Item.COLUMN_NAME_CHECKLIST_ID);

                checklist_item_row.setEntryid(cursor.getInt(entryid_index));
                checklist_item_row.setName(cursor.getString(nameIndex));
                checklist_item_row.setQty(cursor.getInt(qtyIndex));
                checklist_item_row.setChecked(cursor.getInt(isCheckedIndex));
                checklist_item_row.setChecklist_entryid(cursor.getInt(checklist_entry_index));

                rowList.add(checklist_item_row);
            } while (cursor.moveToNext());
        }
    }

    /**
     * Returns an array list of Checklist_Item_Rows. If the args parameter is null, all items of
     * a Checklist are return. Given an array of query strings the method returns an ArrayList of
     * the combined queries.
     *
     * @param args
     * @return
     */
    public ArrayList<ChecklistItemRow> returnChecklistItemRows(String[] args) {

        ArrayList<ChecklistItemRow> rowList = new ArrayList<ChecklistItemRow>();
        Cursor cursor;

        SQLiteDatabase database = this.getReadableDatabase();

        // Begin interaction with database.
        database.beginTransaction();

        if (args == null) {
            cursor = database.rawQuery(Checklist_Contract.Checklist_Item_Queries.ALL_ITEMS, null);
            populateListWithChecklistItemRow(cursor, rowList);
        } else {
            for (String query : args) {
                cursor = database.rawQuery(query, null);
                populateListWithChecklistItemRow(cursor, rowList);
            }
        }
        rowList.add(new ChecklistItemRow("Add Item", false));

        // End interaction with database.
        database.setTransactionSuccessful();
        database.endTransaction();

        return rowList;
    }

    /**
     * Inserts an item from a checklist into the Item table. Retrieves that newly inserted item
     * for use as a reference to insert the description into the description table. Upon completion
     * of the transaction the method returns the condition code.
     *
     * @param item
     * @param description
     * @return
     */
    public int insertItem(ChecklistItemRow item, String description) {

        boolean transaction_success = true;

        try {
            SQLiteDatabase database = getWritableDatabase();
            String query;

            // Begin interaction with database.
            database.beginTransaction();

            // Insert item into Item table.
            query = Checklist_Contract.Checklist_Item_Queries.insertItem(item);
            database.execSQL(query);

            // Fetch updated item with valid attributes.
            query = Checklist_Contract.Checklist_Item_Queries.fetchItem(item);
            ArrayList<ChecklistItemRow> updated_item = returnChecklistItemRows(new String[]{query});

            // Insert description into description table.
            query = Checklist_Contract.Checklist_Description_Qureries.insertDescription(updated_item.remove(0), description);
            database.execSQL(query);

            // End database interaction.
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (SQLiteException e) {
            transaction_success = false;
        } finally {
            // Return condition constant...
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }

    }

    /**
     * Updates the item and its attributes in the Item table, as well as, the description table
     * and it's attributes. Upon completion of the transaction the method will return the
     * condition code.
     *
     * @param item
     * @param description
     * @return
     */
    public int updateItem(ChecklistItemRow item, String description) {

        boolean transaction_success = true;

        SQLiteDatabase database = getWritableDatabase();
        String query;

        try {

            // Begin interaction with the database.
            database.beginTransaction();

            // Update Item.
            query = Checklist_Contract.Checklist_Item_Queries.updateItem(item);
            database.execSQL(query);

            if (!(description == null)) {
                // Update description item.
                ArrayList<ChecklistItemRow> updatedItem =
                        returnChecklistItemRows(new String[]{Checklist_Contract.Checklist_Item_Queries.fetchItem(item)});
                query = Checklist_Contract.Checklist_Description_Qureries.updateDescription(updatedItem.remove(0), description);
                database.execSQL(query);
            }


            // End interaction with the database.
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (SQLiteException e) {
            transaction_success = false;
        } finally {
            // Return condition constant...
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }
    }

    /**
     * Deletes the items and their attributes in the Item table, as well as, the description table
     * and it's attributes. Upon completion of the transaction the method will return the
     * condition code.
     *
     * @param items
     * @return
     */
    public int deleteItems(ArrayList<ChecklistItemRow> items) {
        boolean transaction_success = false;

        SQLiteDatabase database = this.getWritableDatabase();

        try {
            for (ChecklistItemRow item : items) {
                // Begin database interaction.
                database.beginTransaction();

                // Deletes the item and description.
                String[] queries = Checklist_Contract.Checklist_Item_Queries.deleteItem(item);

                // Loop through the array of queries and execute them.
                for (String query : queries) {
                    database.beginTransaction();
                    database.execSQL(query);
                    database.setTransactionSuccessful();
                    database.endTransaction();
                }

                // End database interaction.
                transaction_success = true;
                database.setTransactionSuccessful();
                database.endTransaction();


            }

        } catch (Exception e) {
            System.out.println(e.toString());
            transaction_success = false;
        } finally {
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }
    }

    /* Checklist Item Block End */

}
