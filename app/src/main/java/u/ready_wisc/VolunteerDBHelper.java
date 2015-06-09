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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

public class VolunteerDBHelper extends SQLiteOpenHelper {
    // components of the table which can be changed later to join up with other team later on

    public static final String TABLE_VOLUNTEER = "volunteer";

    public static final String TABLE_SHELTER = "shelter";

    public static final String TABLE_MEDIA = "media";

    public static final String COL_ID = BaseColumns._ID;
    public static final String COL_NAME = "name_of_org";
    public static final String COL_EMAIL = "email";
    public static final String COL_PHONE = "vol_phone";
    public static final String COL_VOL_URL = "vol_url";
    public static final String COL_SHELTER_ADD = "shelter_add";
    public static final String COL_CITY = "city";
    public static final String COL_SHELTER_PHONE = "shelter_phone";
    public static final String COL_PERSON = "person";
    public static final String COL_ORG = "org_name";
    public static final String COL_FACEBOOK = "facebook";
    public static final String COL_TWITTER = "twitter";
    public static final String COL_EXTRA = "extra";
    private static final String DATABASE_NAME = "my_app.db";
    private static final int DATABASE_VERSION = 1;

    public VolunteerDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("DB Update", "Creating Tables");

        db.execSQL("CREATE TABLE " + TABLE_VOLUNTEER + " ("

                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                + COL_NAME + " TEXT NOT NULL,"

                + COL_EMAIL + " TEXT,"

                + COL_PHONE + " TEXT,"

                + COL_VOL_URL + " TEXT"

                + ");");

        db.execSQL("CREATE TABLE " + TABLE_SHELTER + " ("

                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                + COL_SHELTER_ADD + " TEXT,"

                + COL_CITY + " TEXT,"

                + COL_SHELTER_PHONE + " TEXT,"

                + COL_PERSON + " TEXT,"

                + COL_ORG + " TEXT"

                + ");");

        db.execSQL("CREATE TABLE " + TABLE_MEDIA + " ("

                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                + COL_FACEBOOK + " TEXT,"

                + COL_TWITTER + " TEXT,"

                + COL_EXTRA + " TEXT"

                + ");");

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOLUNTEER + ";");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHELTER + ";");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDIA + ";");

        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOLUNTEER + ";");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHELTER + ";");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDIA + ";");

        onCreate(db);
    }

    public long insert(String tableName, ContentValues values) throws NotValidException {

        Log.i("DB Update", "Inserting into tables");

        return getWritableDatabase().insert(tableName, null, values);

    }

    public int update(String tableName, long id, ContentValues values) throws NotValidException {

        String selection = COL_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        return getWritableDatabase().update(tableName, values, selection, selectionArgs);

    }

    public int delete(String tableName, long id) {

        String selection = COL_ID + " = ?";

        String[] selectionArgs = {String.valueOf(id)};

        return getWritableDatabase().delete(tableName, selection, selectionArgs);

    }

    public Cursor queryVolunteer() {

        String[] projection = {COL_ID, COL_NAME, COL_EMAIL, COL_PHONE, COL_VOL_URL};

        return getReadableDatabase().query(TABLE_VOLUNTEER, projection, null, null, null, null, null);

    }

    public Cursor queryShelter() {

        String[] projection = {COL_ID, COL_SHELTER_ADD, COL_CITY, COL_SHELTER_PHONE, COL_PERSON, COL_ORG};

        return getReadableDatabase().query(TABLE_SHELTER, projection, null, null, null, null, null);

    }

    public Cursor queryMedia() {

        String[] projection = {COL_ID, COL_FACEBOOK, COL_TWITTER, COL_EXTRA};

        return getReadableDatabase().query(TABLE_MEDIA, projection, null, null, null, null, null);

    }

    public ArrayList<VolunteerItem> getVolunteerData() {

        ArrayList<VolunteerItem> volunteerList = new ArrayList();
        SQLiteDatabase resourceDB = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_VOLUNTEER + ";";
        Cursor result = resourceDB.rawQuery(query, null);
        if (result.moveToFirst()) {
            do {
                VolunteerItem item = new VolunteerItem();
                item.setName(result.getString(1));
                item.setEmail(result.getString(2));
                item.setPhone(result.getString(3));
                item.setUrl(result.getString(4));
                volunteerList.add(item);
            } while (result.moveToNext());
        }
        result.close();
        resourceDB.close();
        return volunteerList;
    }

    public ArrayList<ShelterItem> getShelterData() {
        ArrayList<ShelterItem> shelterList = new ArrayList();
        SQLiteDatabase resourceDB = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SHELTER + ";";
        Cursor result = resourceDB.rawQuery(query, null);
        if (result.moveToFirst()) {
            do {
                ShelterItem item = new ShelterItem();
                item.setAddress(result.getString(1));
                item.setCity(result.getString(2));
                item.setPhone(result.getString(3));
                item.setContact(result.getString(4));
                item.setOrganization(result.getString(5));
                shelterList.add(item);
            } while (result.moveToNext());
        }
        result.close();
        resourceDB.close();
        return shelterList;
    }

    public ArrayList<MediaItem> getMediaData() {
        ArrayList<MediaItem> volunteerList = new ArrayList();
        SQLiteDatabase resourceDB = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MEDIA + ";";
        Cursor result = resourceDB.rawQuery(query, null);
        if (result.moveToFirst()) {
            do {
                MediaItem item = new MediaItem();
                item.setFacebook(result.getString(1));
                item.setTwitter(result.getString(2));
                item.setExtra(result.getString(3));
                volunteerList.add(item);
            } while (result.moveToNext());
        }
        result.close();
        resourceDB.close();
        return volunteerList;
    }

    public static class NotValidException extends Throwable {

        public NotValidException(String msg) {

            super(msg);

        }

    }
}
