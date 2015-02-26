package edu.parkside.cs.checklist;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by krawchukd on 2/14/15.
 */
public class Checklist_Contract_Db_Helper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ChecklistContract.db";
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;
    private static Checklist_Contract_Db_Helper db_helper;

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
    public Checklist_Contract_Db_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
    public Checklist_Contract_Db_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * Descrtiption :
     *  Creates and returns a new instance of the databaseHelper even if one exists.
     *
     * @param context
     * @return
     */
    public static Checklist_Contract_Db_Helper getDb_helper(Context context)
    {
        if (db_helper == null)
            db_helper = new Checklist_Contract_Db_Helper(context, DATABASE_NAME, null, DATABASE_VERSION);

        return db_helper;
    }


    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Called when the database is created for the first time. This is where the
     *   creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
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
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Creates the initial Checklist table.
     *
     * @param database
     */
    private void createChecklistTable(SQLiteDatabase database){

        database.execSQL(Checklist_Contract.Checklist_Queries.CREATE_CHECKLIST_TABLE);

    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Called to create the initial Item Table.
     *
     * @param database
     */
    private void createItemTable(SQLiteDatabase database){

        database.execSQL(Checklist_Contract.Checklist_Item_Queries.CREATE_ITEM_TABLE);

    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Called to create the initial Checklist Table.
     *
     * @param database
     */
    private void createDescriptionTable(SQLiteDatabase database){

        database.execSQL(Checklist_Contract.Checklist_Description_Qureries.CREATE_DESCRIPTION_TABLE);

    }

    /* Checklist Block Begin */

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Populates the passed ArrayList with the contents of the cursor.
     *   Note: Any changes to the Checklist Object and table must be reflected in this
     *   method as well.
     *
     * @param cursor
     * @param list
     */
    private void populateListWithChecklist(Cursor cursor, ArrayList<Checklist_Row> list){

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Checklist_Row checklist_row = new Checklist_Row();
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
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Takes an array of queries. If the input parameter is null, all results are returned.
     *  Upon an sql exception, the method returns a null value.
     *
     * @param args
     * @return
     */
    public ArrayList<Checklist_Row> returnChecklistRows(String[] args){

        ArrayList<Checklist_Row> rowList = new ArrayList<Checklist_Row>();
        Cursor cursor;
        SQLiteDatabase database = this.getReadableDatabase();

        try {
            // Begin database interaction.
            database.beginTransaction();

            if(args == null)
            {
                cursor = database.rawQuery(Checklist_Contract.Checklist_Queries.ALL_ITEMS, null);
                populateListWithChecklist(cursor, rowList);
            }
            else
            {
                for(String query : args)
                {
                    cursor = database.rawQuery(query, null);
                    populateListWithChecklist(cursor, rowList);
                }
            }
            rowList.add(new Checklist_Row("Add Checklist", 0));

            // End database interaction.
            database.setTransactionSuccessful();
            database.endTransaction();
        }
        catch (SQLiteException e)
        {
            rowList = null;
        }
        finally {
            return rowList;
        }
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  When passed a Checklist Row object inserts that object into the Checklist table.
     *
     * @param checklist_row
     */
    public int addChecklist(Checklist_Row checklist_row){

        boolean transaction_success = true;
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            database.beginTransaction();
            database.execSQL(Checklist_Contract.Checklist_Queries.insertChecklist(checklist_row));
            database.setTransactionSuccessful();
            database.endTransaction();
        }
        catch (SQLiteException e)
        {
            transaction_success = false;
        }
        finally {
            return (transaction_success == true) ? SUCCESS : FAILURE;

        }
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Updates the Checklist table with the values of the Checklist Row object. Upon completion
     *  the method returns the status of the transaction.
     *
     * @param checklist_row
     * @return
     */
    public int updateChecklist(Checklist_Row checklist_row){

        boolean transaction_success = true;
        SQLiteDatabase database = this.getWritableDatabase();

        try{
            database.beginTransaction();
            database.execSQL(Checklist_Contract.Checklist_Queries.updateChecklist(checklist_row));
            database.setTransactionSuccessful();
            database.endTransaction();
        }
        catch (SQLiteException e){
            transaction_success = false;
        }
        finally {
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Removes the provided checklist row from the Checklist table. Upon completion the method
     *   returns the status of the transaction.
     *
     * @param checklist_row
     * @return
     */
    public int deleteChecklist(Checklist_Row checklist_row){

        boolean transaction_success = true;
        SQLiteDatabase database = this.getWritableDatabase();

        try {
            database.beginTransaction();
            database.execSQL(Checklist_Contract.Checklist_Queries.deleteChecklist(checklist_row));

            ArrayList<Checklist_Item_Row> rowsToBeDeleted = new ArrayList<Checklist_Item_Row>();
            populateListWithChecklistItemRow(database.rawQuery(Checklist_Contract.Checklist_Item_Queries.fetchItems(checklist_row), null), rowsToBeDeleted);
            deleteItems((Checklist_Item_Row [])rowsToBeDeleted.toArray());

            database.setTransactionSuccessful();
            database.endTransaction();
        }
        catch (SQLiteException e)
        {
            transaction_success = false;
        }
        finally {
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }
    }

    /* Checklist Block End */

    /* Checklist Item Block Begin */

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Returns the description referenced in the item parameter. If an exception occurs a null value
     *   is returned.
     *
     *   @todo Finish exception handling. Should return null if error occurs.
     *
     * @param item
     * @return
     */
    public String returnDescriptionFromItem(Checklist_Item_Row item){

        String descriptionText = null;
        Cursor cursor;
        SQLiteDatabase database = this.getReadableDatabase();

        try {
            // Begin interaction with database.
            database.beginTransaction();

            cursor = database.rawQuery(Checklist_Contract.Checklist_Description_Qureries.getDescriptionWithItemEntryID(item.getEntryid()), null);

            if (cursor.moveToFirst()){
                cursor.moveToFirst();
                do{
                    int descriptionIndex = cursor.getColumnIndex(Checklist_Contract.Description.COLUMN_NAME_DESCRIPTION);

                    descriptionText = cursor.getString(descriptionIndex);
                }while (cursor.moveToNext());
            }
            else
                descriptionText = "Empty";

            // End interaction with the database.
            database.setTransactionSuccessful();
            database.endTransaction();
        }
        catch (SQLiteException e)
        {

        }
        finally {
            return  descriptionText;
        }

    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Populates the passed ArrayList with the contents of the cursor.
     *   Note: Any changes to the Checklist Item Object and table must be reflected in this
     *   method as well.
     *
     * @param cursor
     * @param rowList
     */
    public void populateListWithChecklistItemRow(Cursor cursor, ArrayList<Checklist_Item_Row> rowList)
    {
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                Checklist_Item_Row checklist_item_row = new Checklist_Item_Row();
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
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Returns an array list of Checklist_Item_Rows. If the args parameter is null, all items of
     *   a Checklist are return. Given an array of query strings the method returns an ArrayList of
     *   the combined queries.
     *
     * @param args
     * @return
     */
    public ArrayList<Checklist_Item_Row> returnChecklistItemRows(String[] args){

        ArrayList<Checklist_Item_Row> rowList = new ArrayList<Checklist_Item_Row>();
        Cursor cursor;

        SQLiteDatabase database = this.getReadableDatabase();

        // Begin interaction with database.
        database.beginTransaction();

        if(args == null)
        {
            cursor = database.rawQuery(Checklist_Contract.Checklist_Item_Queries.ALL_ITEMS, null);
            populateListWithChecklistItemRow(cursor, rowList);
        }
        else
        {
            for(String query : args)
            {
                cursor = database.rawQuery(query, null);
                populateListWithChecklistItemRow(cursor, rowList);
            }
        }
        rowList.add(new Checklist_Item_Row("Add Item", false));

        // End interaction with database.
        database.setTransactionSuccessful();
        database.endTransaction();

        return rowList;
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Inserts an item from a checklist into the Item table. Retrieves that newly inserted item
     *   for use as a reference to insert the description into the description table. Upon completion
     *   of the transaction the method returns the condition code.
     *
     * @param item
     * @param description
     * @return
     */
    public int insertItem(Checklist_Item_Row item, String description){

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
            ArrayList<Checklist_Item_Row> updated_item =  returnChecklistItemRows(new String [] {query});

            // Insert description into description table.
            query = Checklist_Contract.Checklist_Description_Qureries.insertDescription(updated_item.remove(0), description);
            database.execSQL(query);

            // End database interaction.
            database.setTransactionSuccessful();
            database.endTransaction();
        }
        catch (SQLiteException e){
            transaction_success = false;
        }
        finally {
            // Return condition constant...
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }

    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Updates the item and its attributes in the Item table, as well as, the description table
     *  and it's attributes. Upon completion of the transaction the method will return the
     *  condition code.
     *
     * @param item
     * @param description
     * @return
     */
    public int updateItem(Checklist_Item_Row item, String description){

        boolean transaction_success = true;

        SQLiteDatabase database = getWritableDatabase();
        String query;

        try {

            // Begin interaction with the database.
            database.beginTransaction();

            // Update Item.
            query = Checklist_Contract.Checklist_Item_Queries.updateItem(item);
            database.execSQL(query);

            // Update description item.
            ArrayList<Checklist_Item_Row> updatedItem =
                    returnChecklistItemRows(new String[]{Checklist_Contract.Checklist_Item_Queries.fetchItem(item)});
            query = Checklist_Contract.Checklist_Description_Qureries.updateDescription(updatedItem.remove(0), description);
            database.execSQL(query);

            // End interaction with the database.
            database.setTransactionSuccessful();
            database.endTransaction();
        }
        catch (SQLiteException e){
            transaction_success = false;
        }
        finally {
            // Return condition constant...
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }
    }

    /**
     * @author David Krawchuk
     * @email krawchukdavid@gmail.com
     * @date 02/20/2014
     *
     * Description:
     *  Deletes the items and their attributes in the Item table, as well as, the description table
     *  and it's attributes. Upon completion of the transaction the method will return the
     *  condition code.
     *
     * @param items
     * @return
     */
    public int deleteItems(Checklist_Item_Row [] items){
        boolean transaction_success = true;

        SQLiteDatabase database = this.getWritableDatabase();

        try{
            for(Checklist_Item_Row item : items){
                // Begin database interaction.
                database.beginTransaction();

                // Deletes the item and description.
                String [] queries = Checklist_Contract.Checklist_Item_Queries.deleteItem(item);

                // Loop through the array of queries and execute them.
                for(String query : queries) {
                    database.beginTransaction();
                    database.execSQL(query);
                    database.endTransaction();
                }

                // End database interaction.
                database.setTransactionSuccessful();
                database.endTransaction();
            }

        }
        catch (SQLiteException e){
            transaction_success = false;
        }
        finally {
            return (transaction_success == true) ? SUCCESS : FAILURE;
        }
    }

    /* Checklist Item Block End */

}
