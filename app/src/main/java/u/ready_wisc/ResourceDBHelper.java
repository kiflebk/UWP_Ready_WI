package u.ready_wisc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by Jake on 4/27/2015 based on work by kiflebk.
 */
public class ResourceDBHelper extends SQLiteOpenHelper{// components of the table which can be changed later to join up with other team later on

    public static final String TABLE_RESOURCES = "resources";

    public static final String COL_COUNTY = "county";

    public static final String COL_NAME = "name";

    public static final String COL_ADDRESS = "address";

    public static final String COL_PHONE = "phone";

    public static final String COL_OTHER = "other";

    public static final String COL_TYPE = "type";

    private static final String DATABASE_NAME = "resources.db";

    private static final int DATABASE_VERSION = 2;

    public ResourceDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_RESOURCES + " ("

                + COL_COUNTY + "TEXT NOT NULL,"

                + COL_NAME + " TEXT PRIMARY KEY NOT NULL,"

                + COL_ADDRESS + " TEXT,"

                + COL_PHONE + " TEXT,"

                + COL_OTHER + " TEXT,"

                + COL_TYPE + " TEXT"

                + ");");

        ContentValues values = new ContentValues();
        values.put(COL_COUNTY, "Kenosha");
        values.put(COL_NAME, "Kenosha County Sheriff's Department");
        values.put(COL_ADDRESS, "1000 55th Street Kenosha, WI 53140");
        values.put(COL_PHONE, "(262)-605-5100");
        values.put(COL_TYPE, "Sheriff");
        values.put(COL_OTHER, "");
        db.insert(TABLE_RESOURCES, null, values);
    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESOURCES + ";");

        onCreate(db);

    }

    public long insert(String tableName, ContentValues values) throws NotValidException {

        validate(values); //checks values

        return getWritableDatabase().insert(tableName, null, values);

    }

    public int update(String tableName, String name, ContentValues values) throws NotValidException {

        validate(values);

        String selection = COL_NAME + " = ?";

        String[] selectionArgs = {name};

        return getWritableDatabase().update(tableName, values, selection, selectionArgs);

    }

    protected void validate(ContentValues values) throws NotValidException {

        if (!values.containsKey(COL_NAME) || values.getAsString(COL_NAME) == null || values.getAsString(COL_NAME).isEmpty()) {

            throw new NotValidException("Resource name must be set");

        }

    }

    public static class NotValidException extends Throwable {

        public NotValidException(String msg) {

            super(msg);

        }

    }

    public Cursor query(String tableName, String orderedBy) {

        String[] projection = {COL_COUNTY, COL_NAME, COL_ADDRESS, COL_PHONE, COL_OTHER, COL_TYPE};

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
            } while (result.moveToFirst());
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
            } while (result.moveToFirst());
        }
        result.close();
        resourceDB.close();
        return resourceList;
    }
}
