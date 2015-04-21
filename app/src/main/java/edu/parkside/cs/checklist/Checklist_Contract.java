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

import android.provider.BaseColumns;


/**
 * The Checklist_Contract Class provides an interface for the sqlHelper class. All sql statements
 * that the Checklist subsection of the application will need are centralized here.
 *
 * @author David Krawchuk
 * @version 1.0v Build * March 18 2015
 * @email krawchukdavid@gmail.com
 */
public final class Checklist_Contract {

    // Stubbed to prevent instantiation.
    public Checklist_Contract() {
    }

    ;

    /* Inner class that defines the Checklist table contents. */
    public static abstract class Checklist implements BaseColumns {
        public static final String TABLE_NAME = "checklist";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PROGRESS = "progress";
    }

    /* Inner class that defines the Checklist Item table contents. */
    public static abstract class Item implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_QTY = "qty";
        public static final String COLUMN_NAME_COMPLETE = "complete";
        public static final String COLUMN_NAME_CHECKLIST_ID = "checklist_entryid";
    }

    /* Inner class that defines the Description table contents */
    public static abstract class Description implements BaseColumns {
        public static final String TABLE_NAME = "description";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_ITEM_ID = "item_entryid";
    }

    /* Inner class that contains the Checklist SQL queries. */
    public static abstract class Checklist_Queries {

        public static final String insertChecklist(Checklist_Row checklist_row) {
            String query = "INSERT INTO " + Checklist.TABLE_NAME +
                    " VALUES (NULL, " + "\'" + checklist_row.getTitle() + "\'" +
                    ", " + "\'" + checklist_row.getProgress() + "\'" + ")";

            return query;
        }

        public static final String fetchChecklist(Checklist_Row checklist_row) {
            String query = "SELECT * FROM " + Checklist.TABLE_NAME +
                    " WHERE " + Checklist.COLUMN_NAME_TITLE + " = " + "\'" + checklist_row.getTitle() + "\'";
            return query;
        }

        public static final String updateChecklist(Checklist_Row checklist_row) {

            String query = "UPDATE " + Checklist.TABLE_NAME + " SET " +
                    Checklist.COLUMN_NAME_TITLE + " = " + "\'" + checklist_row.getTitle() + "\'" +
                    ", " + Checklist.COLUMN_NAME_PROGRESS + " = " + "\'" + checklist_row.getProgress() + "\'" +
                    " WHERE " + Checklist._ID + " = " + "\'" + checklist_row.getEntryid() + "\'";

            return query;
        }

        public static final String deleteChecklist(Checklist_Row checklist_row) {
            String query = "DELETE FROM " + Checklist.TABLE_NAME +
                    " WHERE " + Checklist._ID + " = " + "\'" + checklist_row.getEntryid() + "\'";
            return query;
        }

        public static final String CREATE_CHECKLIST_TABLE = "CREATE TABLE " + Checklist_Contract.Checklist.TABLE_NAME +
                "(" + Checklist_Contract.Checklist._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Checklist_Contract.Checklist.COLUMN_NAME_TITLE + " TEXT, " +
                Checklist_Contract.Checklist.COLUMN_NAME_PROGRESS + " INTEGER)";


        public static final String ALL_ITEMS = "SELECT * FROM " +
                Checklist.TABLE_NAME;


    }

    /* Inner class that contains the Checklist Item SQL queries. */
    public static abstract class Checklist_Item_Queries {

        public static final String CREATE_ITEM_TABLE = "CREATE TABLE " + Checklist_Contract.Item.TABLE_NAME +
                "(" + Checklist_Contract.Item._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Checklist_Contract.Item.COLUMN_NAME_NAME + " TEXT, " +
                Checklist_Contract.Item.COLUMN_NAME_QTY + " INTEGER, " +
                Checklist_Contract.Item.COLUMN_NAME_COMPLETE + " INTEGER, " +
                Checklist_Contract.Item.COLUMN_NAME_CHECKLIST_ID + " INTEGER )";

        public static final String ALL_ITEMS = "SELECT * FROM " +
                Item.TABLE_NAME;

        public static final String fetchItem(Checklist_Item_Row item) {
            String query = "SELECT * FROM " + Item.TABLE_NAME +
                    " WHERE " + Item.COLUMN_NAME_NAME + " = " + "\'" + item.getName() + "\'";
            return query;
        }

        public static final String fetchItems(Checklist_Row checklist) {
            String query = "SELECT * FROM " + Item.TABLE_NAME +
                    " WHERE " + Item.COLUMN_NAME_CHECKLIST_ID + " = " + "\'" + checklist.getEntryid() + "\'";
            return query;
        }

        public static final String insertItem(Checklist_Item_Row item) {
            String query = "INSERT INTO " + Item.TABLE_NAME + " (" +
                    Item._ID + ", " + Item.COLUMN_NAME_NAME + ", " +
                    Item.COLUMN_NAME_QTY + ", " + Item.COLUMN_NAME_COMPLETE + ", " +
                    Item.COLUMN_NAME_CHECKLIST_ID + ") VALUES (NULL, " +
                    "\'" + item.getName() + "\'" +
                    ", " + "\'" + item.getQty() + "\'" + ", " +
                    "\'" + item.getChecked() + "\'" + ", " +
                    "\'" + item.getChecklist_entryid() + "\'" + ")";

            return query;
        }


        public static final String updateItem(Checklist_Item_Row item) {
            String query = "UPDATE " + Item.TABLE_NAME +
                    " SET " + Item.COLUMN_NAME_NAME + " = " + "\'" + item.getName() + "\'" + ", " +
                    Item.COLUMN_NAME_QTY + " = " + "\'" + item.getQty() + "\', " +
                    Item.COLUMN_NAME_COMPLETE + " = " + "\'" + item.getChecked() + "\'" +
                    " WHERE " + Item._ID + " = " + "\'" + item.getEntryid() + "\'";

            return query;
        }

        public static final String[] deleteItem(Checklist_Item_Row item) {

            String query = Checklist_Description_Qureries.deleteDescription(item);

            String itemPropertiesQuery = "DELETE FROM " + Item.TABLE_NAME +
                    " WHERE " + Item._ID + " = " + "\'" + item.getEntryid() + "\'";

            return new String[]{query, itemPropertiesQuery};
        }
    }

    /* Inner class that contains the Checklist Description SQL queries. */
    public static abstract class Checklist_Description_Qureries {

        public static final String CREATE_DESCRIPTION_TABLE = "CREATE TABLE " + Checklist_Contract.Description.TABLE_NAME +
                "(" + Checklist_Contract.Description._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Checklist_Contract.Description.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                Checklist_Contract.Description.COLUMN_NAME_ITEM_ID + " NUMBER)";

        public static final String getDescriptionWithItemEntryID(int entryID) {
            String queryString = "SELECT description FROM " + Description.TABLE_NAME +
                    " WHERE item_entryid = " + "\'" + entryID + "\'";
            return queryString;
        }

        public static final String insertDescription(Checklist_Item_Row item, String description) {
            String query = "INSERT INTO " + Description.TABLE_NAME + " (" + Description._ID + ", " +
                    Description.COLUMN_NAME_DESCRIPTION + ", " + Description.COLUMN_NAME_ITEM_ID +
                    ") VALUES (NULL, " +
                    "\'" + description + "\'" + ", " +
                    "\'" + item.getEntryid() + "\'" + ")";

            return query;
        }

        public static final String updateDescription(Checklist_Item_Row item, String description) {
            String query = "UPDATE " + Description.TABLE_NAME +
                    " SET " + Description.COLUMN_NAME_DESCRIPTION + " = " + "\'" + description + "\'" +
                    " WHERE " + Description.COLUMN_NAME_ITEM_ID + " = " + "\'" + item.getEntryid() + "\'";
            return query;
        }

        public static final String deleteDescription(Checklist_Item_Row item) {
            String query = "DELETE FROM " + Description.TABLE_NAME
                    + " WHERE " + Description.COLUMN_NAME_ITEM_ID + " = " + "\'" + item.getEntryid() + "\'";
            return query;
        }
    }
}
