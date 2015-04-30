package u.ready_wisc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

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

public class MyDatabaseHelper extends SQLiteOpenHelper {
    // components of the table which can be changed later to join up with other team later on

    public static final String TABLE_USERS = "users";

    public static final String COL_ID = BaseColumns._ID;

    public static final String COL_NAME = "name";

    public static final String COL_EMAIL = "email";

    public static final String COL_DOB = "date_of_birth";

    private static final String DATABASE_NAME = "my_app.db";

    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_RESOURCES = "resources";

    public static final String COL_COUNTY = "county";

    public static final String COL_RNAME = "name";

    public static final String COL_ADDRESS = "address";

    public static final String COL_PHONE = "phone";

    public static final String COL_OTHER = "other";

    public static final String COL_TYPE = "type";


    public MyDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_USERS + " ("

                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                + COL_RNAME + " TEXT NOT NULL,"

                + COL_EMAIL + " TEXT,"

                + COL_DOB + " INTEGER"

                + ");");

        db.execSQL("CREATE TABLE " + TABLE_RESOURCES + " ("

                + COL_COUNTY + " TEXT NOT NULL,"

                + COL_RNAME + " TEXT PRIMARY KEY NOT NULL,"

                + COL_ADDRESS + " TEXT,"

                + COL_PHONE + " TEXT,"

                + COL_OTHER + " TEXT,"

                + COL_TYPE + " TEXT"

                + ");");

        ContentValues values = new ContentValues();
        values.put(COL_COUNTY, "Kenosha");
        values.put(COL_RNAME, "Kenosha County Sheriff's Department");
        values.put(COL_ADDRESS, "1000 55th Street Kenosha, WI 53140");
        values.put(COL_PHONE, "(262)-605-5100");
        values.put(COL_TYPE, "Sheriff");
        values.put(COL_OTHER, "");
        db.insert(TABLE_RESOURCES, null, values);


    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS + ";");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESOURCES + ";");

        onCreate(db);

    }

    public long insert(String tableName, ContentValues values) throws NotValidException {

        validate(values); //checks values

        return getWritableDatabase().insert(tableName, null, values);

    }

    public int update(String tableName, long id, ContentValues values) throws NotValidException {

        validate(values);

        String selection = COL_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        return getWritableDatabase().update(tableName, values, selection, selectionArgs);

    }

    public int delete(String tableName, long id) {

        String selection = COL_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        return getWritableDatabase().delete(tableName, selection, selectionArgs);

    }

    protected void validate(ContentValues values) throws NotValidException {

        if (!values.containsKey(COL_NAME) || values.getAsString(COL_NAME) == null || values.getAsString(COL_NAME).isEmpty()) {

            throw new NotValidException("User name must be set");

        }

    }

    public static class NotValidException extends Throwable {

        public NotValidException(String msg) {

            super(msg);

        }

    }

    public Cursor query(String tableName, String orderedBy) {

        String[] projection = {COL_ID, COL_NAME, COL_EMAIL, COL_DOB};

        return getReadableDatabase().query(tableName, projection, null, null, null, null, orderedBy);

    }

    public ArrayList<ResourceItem> getDataFromCounty(String county){
        ArrayList<ResourceItem> resourceList = new ArrayList();
        SQLiteDatabase resourceDB = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_RESOURCES + " WHERE " + COL_COUNTY + "=\"" + county + "\"";
        Cursor result = resourceDB.rawQuery(query, null);
        if(result.moveToFirst()){
            do{
                ResourceItem item = new ResourceItem();
                item.setName(result.getString(1));
                item.setAddress(result.getString(2));
                item.setPhone(result.getString(3));
                item.setOther(result.getString(4));
                item.setType(result.getString(5));
                resourceList.add(item);
            } while (result.moveToNext());
        }
        result.close();
        resourceDB.close();
        return resourceList;
    }

    public ArrayList<ResourceItem> getDataFromType(String county, String type){
        ArrayList<ResourceItem> resourceList = new ArrayList();
        SQLiteDatabase resourceDB = this.getReadableDatabase();
        String query = "SELECT * FROM resources WHERE COUNTY=\"" + county + "\" AND TYPE=\"" + type + "\"";
        Cursor result = resourceDB.rawQuery(query, null);
        if(result.moveToFirst()){
            do{
                ResourceItem item = new ResourceItem();
                item.setName(result.getString(1));
                item.setAddress(result.getString(2));
                item.setPhone(result.getString(3));
                item.setOther(result.getString(4));
                item.setType(result.getString(5));
                resourceList.add(item);
            } while (result.moveToNext());
        }
        result.close();
        resourceDB.close();
        return resourceList;
    }
}

