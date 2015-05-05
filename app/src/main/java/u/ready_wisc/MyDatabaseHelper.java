package u.ready_wisc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.*;
import android.os.Process;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by kiflebk on 2/11/15.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{
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

    public static final String COL_TYPE = "type";


    public MyDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_USERS + " ("

                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"

                + COL_NAME + " TEXT NOT NULL,"

                + COL_EMAIL + " TEXT,"

                + COL_DOB + " INTEGER"

                + ");");

        db.execSQL("CREATE TABLE " + TABLE_RESOURCES + " ("

                + COL_RNAME + " TEXT PRIMARY KEY NOT NULL,"

                + COL_ADDRESS + " TEXT,"

                + COL_PHONE + " TEXT,"

                + COL_COUNTY + " TEXT NOT NULL,"

                + COL_TYPE + " TEXT"

                + ");");
        addResourceData();
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

    // Retrieves resource data for a specific county
    public ArrayList<ResourceItem> getDataFromCounty(String county){
        ArrayList<ResourceItem> resourceList = new ArrayList(); // Create array of items
        SQLiteDatabase resourceDB = this.getReadableDatabase(); // Get database

        // Query database for all data from a specific county
        String query = "SELECT * FROM " + TABLE_RESOURCES + " WHERE " + COL_COUNTY + "=\"" + county + "\"";
        Cursor result = resourceDB.rawQuery(query, null);

        // For every row returned, create an empty item, add info from each column, and put the item in the array
        if(result.moveToFirst()){
            do{
                ResourceItem item = new ResourceItem();
                item.setName(result.getString(0));
                item.setAddress(result.getString(1));
                item.setPhone(result.getString(2));
                item.setType(result.getString(4));
                resourceList.add(item);
            } while (result.moveToNext());
        }

        // No memory leaks
        result.close();
        resourceDB.close();
        return resourceList; // Return the array of resources
    }

    // Retrieves resource data for a specific county and type of resource
    public ArrayList<ResourceItem> getDataFromType(String county, String type){
        ArrayList<ResourceItem> resourceList = new ArrayList(); // Create array of items
        SQLiteDatabase resourceDB = this.getReadableDatabase(); // Get database

        // Query database for all data from a specific county and of a specific type
        String query = "SELECT * FROM resources WHERE COUNTY=\"" + county + "\" AND TYPE=\"" + type + "\"";
        Cursor result = resourceDB.rawQuery(query, null);

        // For every row returned, create an empty item, add info from each column, and put the item in the array
        if(result.moveToFirst()){
            do{
                ResourceItem item = new ResourceItem();
                item.setName(result.getString(0));
                item.setAddress(result.getString(1));
                item.setPhone(result.getString(2));
                item.setType(result.getString(4));
                resourceList.add(item);
            } while (result.moveToNext());
        }

        // No memory leaks
        result.close();
        resourceDB.close();
        return resourceList; // Return the array of resources
    }

    // Populates resource table with available data
    // TODO: Move data to web database and update local database on start, to provide easier updates and expansions to data
    public void addResourceData(){

        SQLiteDatabase db = getWritableDatabase();
        // Sheriff Departments
        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Kenosha County Sheriff's Department\", " +
                "\"1000 55th Street, Kenosha, WI 53140\", " +
                "\"(262)-605-5100\"," +
                "\"Kenosha\", " +
                "\"Sheriff\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Racine County Sheriff's Office\", " +
                "\"717 Wisconsin Avenue, Racine, WI 53403\", " +
                "\"(262)-636-3822\", " +
                "\"Racine\", " +
                "\"Sheriff\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Milwaukee County Sheriff's Office\", " +
                "\"821 W State St, Milwaukee, WI 53233\", " +
                "\"(414)-278-4766\", " +
                "\"Milwaukee\", " +
                "\"Sheriff\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Waukesha County Sheriff Department\", " +
                "\"515 W Moreland Blvd, Waukesha, WI\", " +
                "\"(262)-548-7122\", " +
                "\"Waukesha\", " +
                "\"Sheriff\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Walworth County Sheriff's Office\", " +
                "\"1770 County Road NN, Elkhorn, WI 53121\", " +
                "\"(262)-741-4400\", " +
                "\"Walworth\", " +
                "\"Sheriff\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Ozaukee County Sheriff's Office\", " +
                "\"1201 S. Spring St., Port Washington, WI 53074\", " +
                "\"(262)-284-7172\", " +
                "\"Ozaukee\", " +
                "\"Sheriff\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Jefferson County Sheriffs Department\", " +
                "\"411 S Center Ave, Jefferson, WI 53549\", " +
                "\"(920)-674-7310\", " +
                "\"Jefferson\", " +
                "\"Sheriff\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Washington County Sheriff's Office\", " +
                "\"500 Schmidt Rd, West Bend, WI 53090\", " +
                "\"(262)-335-4378\", " +
                "\"Washington\", " +
                "\"Sheriff\");");

        //Hospitals
        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora Medical Center-Kenosha\", " +
                "\"10400 75th Avenue, Kenosha 53142-8323\", " +
                "\"(262)-948-5600\", " +
                "\"Kenosha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"United Hospital\", " +
                "\"6308 Eighth Avenue Kenosha\", " +
                "\"(262)-656-2011\", " +
                "\"Kenosha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora Memorial Hospital of Burlington\", " +
                "\"252 McHenry Street Burlington\", " +
                "\"(262)-767-6000\", " +
                "\"Racine\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Wheaton Franciscan Healthcare - All Saints\", " +
                "\"3801 Spring Street, Racine 53405\", " +
                "\"(262)-687-4011\", " +
                "\"Racine\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Lakeview Specialty Hospital & Rehab.\", " +
                "\"1701 Sharp Road, Waterford 53185\", " +
                "\"(262)-534-7297\", " +
                "\"Racine\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora Psychiatric Hospital, Inc.\", " +
                "\"1220 Dewey Avenue, Wauwatosa 53213-2598\", " +
                "\"(414)-454-6600\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Wheaton Franciscan - Midwest Spine/Orthopedic Hospl/Wisconsin Heart Hospital\", " +
                "\"10000 W. Bluemound Road, Wauwatosa 53226\", " +
                "\"(414)-778-7800\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Wheaton Franciscan Healthcare-Franklin\", " +
                "\"10101 S. 27th Street, Franklin 53132\", " +
                "\"(414)-325-4700\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Orthopaedic Hospital of Wisconsin\", " +
                "\"475 West River Woods Parkway, Glendale 53212\", " +
                "\"(414)-961-6800\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Post Acute Specialty Hospital of Milwaukee, LLC\", " +
                "\"5017 South 110th Street, Greenfield 53228\", " +
                "\"(414)-427-8282\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Columbia Center Birth Hospital\", " +
                "\"2025 East Newport Avenue, Suite 3000, Mequon 53211\", " +
                "\"(414)-906-2229\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Columbia St. Mary's Hospital Milwaukee\", " +
                "\"2323 North Lake Drive, PO Box 503, Milwaukee 53201-0503\", " +
                "\"(414)-291-1000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Columbia St. Mary's - Sacred Heart Rehabilitation Institute\", " +
                "\"2323 N. Lake Drive, Milwaukee 53211\", " +
                "\"(414)-298-6700\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Froedtert & The Medical College of Wisconsin Froedtert Hospital campus\", " +
                "\"9200 W. Wisconsin Ave., PO Box 26099, Milwaukee 53226\", " +
                "\"(414)-805-3000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Select Specialty Hospital-Milwaukee-St. Luke's\", " +
                "\"2900 West Oklahoma Ave, 4th Floor N., Milwaukee 53215\", " +
                "\"(414)-649-6991\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Wheaton Franciscan Healthcare-St. Francis\", " +
                "\"3237 South 16th Street, Milwaukee 53215\", " +
                "\"(414)-647-5000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora Sinai Medical Center, Inc.\", " +
                "\"945 N. 12th Street, PO Box 342, Milwaukee 53233\", " +
                "\"(414)-219-2000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora St. Luke's Medical Center\", " +
                "\"2900 West Oklahoma Avenue, Milwaukee 53215\", " +
                "\"(414)-649-6000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Children's Hospital of Wisconsin\", " +
                "\"9000 W. Wisconsin Ave., PO Box 1997, Milwaukee 53201\", " +
                "\"(414)-266-2000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Clement J. Zablocki VA Medical Center\", " +
                "\"5000 West National Avenue, Milwaukee 53295\", " +
                "\"(414)-384-2000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Wheaton Franciscan - St. Joseph Campus\", " +
                "\"5000 West Chambers Street, Milwaukee 53210\", " +
                "\"(414)-447-2000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora West Allis Medical Center\", " +
                "\"8901 W. Lincoln Avenue, West Allis 53227\", " +
                "\"(414)-328-6000\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Select Specialty Hospital-Milwaukee\", " +
                "\"8901 W. Lincoln Ave., 6th Floor, West Allis 53227\", " +
                "\"(414)-328-7700\", " +
                "\"Milwaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Fort HealthCare\", " +
                "\"611 East Sherman Avenue, Fort Atkinson 53538\", " +
                "\"(920)-568-5000\", " +
                "\"Jefferson\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"UW Health Partners Watertown Reg. Med. Ctr.\", " +
                "\"125 Hospital Drive, Watertown 53098\", " +
                "\"(920)-261-4210\", " +
                "\"Jefferson\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora Lakeland Medical Center\", " +
                "\"W3985 Co. Road NN, Elkhorn 53121\", " +
                "\"(262)-741-2000\", " +
                "\"Walworth\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Mercy Walworth Hospital and Medical Center\", " +
                "\"N 2950 State Road 67, Lake Geneva 53147\", " +
                "\"262-245-0535\", " +
                "\"Walworth\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Wheaton Franciscan - Elmbrook Memorial Campus\", " +
                "\"19333 West North Avenue, Brookfield 53045\", " +
                "\"(262)-785-2000\", " +
                "\"Waukesha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Froedtert & The Medical College of Wisconsin Community Memorial Hospital campus\", " +
                "\"W180 N8085 Town Hall Road, Menomonee Falls 53051\", " +
                "\"(262)-251-1000\", " +
                "\"Waukesha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Oconomowoc Memorial Hospital\", " +
                "\"791 Summit Avenue, Oconomowoc 53066\", " +
                "\"(262)-569-9400\", " +
                "\"Waukesha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Rogers Memorial Hospital\", " +
                "\"34700 Valley Road, Oconomowoc 53066\", " +
                "\"(262)-646-4411\", " +
                "\"Waukesha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora Medical Center Summit\", " +
                "\"36500 Aurora Drive, Summit 53066\", " +
                "\"262-434-5000\", " +
                "\"Waukesha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Rehabilitation Hospital of Wisconsin\", " +
                "\"1625 Coldwater Creek Drive, Waukesha53188\", " +
                "\"(262)-521-8800\", " +
                "\"Waukesha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Waukesha Memorial Hospital\", " +
                "\"725 American Avenue, Waukesha 53188\", " +
                "\"(262)-928-1000\", " +
                "\"Waukesha\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Froedtert & The Medical College of Wisconsin St. Joseph's Hospital campus\", " +
                "\"3200 Pleasant Valley Road, West Bend 53095\", " +
                "\"(262)-334-5533\", " +
                "\"Washington\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora Medical Center in Hartford\", " +
                "\"1032 East Sumner Street, Hartford 53027\", " +
                "\"(262)-673-2300\", " +
                "\"Washington\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Aurora Medical Center in Grafton\", " +
                "\"975 Port Washington Road, Grafton 53024\", " +
                "\"(262)-329-1000\", " +
                "\"Ozaukee\", " +
                "\"Hospital\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Columbia St. Mary's Hospital Ozaukee\", " +
                "\"13111 N. Port Washington Road, Mequon 53097-2416\", " +
                "\"(262)-243-7300\", " +
                "\"Ozaukee\", " +
                "\"Hospital\");");

        // Fire Departments
        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City of Kenosha Fire Department\", " +
                "\"625 52 Street, Kenosha, WI 53140\", " +
                "\"(262) 653-4100\", " +
                "\"Kenosha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Town of Randall Fire Department\", " +
                "\"34524 Bassett Road, Bassett, WI 53101\", " +
                "\"(262) 877-2941\", " +
                "\"Kenosha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Bristol Fire Department\", " +
                "\"8312 198 Avenue, Bristol, WI 53104\", " +
                "\"(262) 857-2711\", " +
                "\"Kenosha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Pleasant Prairie Fire Department\", " +
                "\"8044 88th Avenue, Pleasant Prairie, WI 53158\", " +
                "\"(262) 694-8027\", " +
                "\"Kenosha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Silver Lake Volunteer Fire Department\", " +
                "\"113 S First Street, Silver Lake, WI 53170\", " +
                "\"(262) 889-4713\", " +
                "\"Kenosha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Somers Fire Department\", " +
                "\"7511 12 Street, Somers, WI 53171\", " +
                "\"(262) 859-2277\", " +
                "\"Kenosha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Trevor Volunteer Fire Department\", " +
                "\"25700 Wimot Road, Trevor, WI 53179\", " +
                "\"(262) 862-2431\", " +
                "\"Kenosha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Twin Lakes Volunteer Fire Department & Rescue Squad\", " +
                "\"236 E Main Street, Twin Lakes, WI 53181\", " +
                "\"(262) 877-2373\", " +
                "\"Kenosha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Allenton Fire Department\", " +
                "\"431 Railroad Street, Allenton, WI 53002\", " +
                "\"(262) 629-5413\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Germantown Fire Department\", " +
                "\"N115W18752 Edison Drive, Germantown, WI 53022\", " +
                "\"(262) 502-4701\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"St. Lawrence Fire and Rescue\", " +
                "\"4955 Highway 175, Hartford, WI 53027\", " +
                "\"(262) 644-8529\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Hartford Fire and Rescue\", " +
                "\"111 W Wisconsin Street, Hartford, WI 53027\", " +
                "\"(262) 673-8290\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Jackson Fire Department\", " +
                "\"W204 N 16722 Jackson Drive, Jackson, WI 53037\", " +
                "\"(262) 677-3811\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Boltonville Volunteer Fire Department\", " +
                "\"9336 Bolton Drive, Kewaskum, WI 53040\", " +
                "\"(262) 692-2231\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Kewaskum Fire Department\", " +
                "\"1106 Fond du lac Avenue, Kewaskum, WI 53040\", " +
                "\"(262) 626-2411\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Newburg Fire Department\", " +
                "\"508 Main Streetreet, Newburg, WI 53060\", " +
                "\"(262) 675-6262\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Richfield Volunteer Fire Company\", " +
                "\"2008 Highway 175, Richfield, WI 53076\", " +
                "\"(262) 628-1601\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Slinger Fire Department\", " +
                "\"201 Oak Street, Slinger, WI 53086\", " +
                "\"(262) 644-5331\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Fillmore Fire Department\", " +
                "\"8485 Trading Post Trail Road, West Bend, WI 53090\", " +
                "\"(262) 692-2361\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"West Bend Fire Department\", " +
                "\"325 N 8th Avenue, West Bend, WI 53095\", " +
                "\"(262) 335-5054\", " +
                "\"Washington\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Fort Atkinson Fire Department\", " +
                "\"128 Milwaukee Avenue W, Fort Atkinson, WI 53538\", " +
                "\"(920) 563-7795\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Helenville Fire Department\", " +
                "\"N4734 N Helenville Road, Helenville, WI 53137\", " +
                "\"(920) 674-5389\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Ixonia Fire and EMS\", " +
                "\"N8320 North Street, Ixonia, WI 53036\", " +
                "\"(920) 261-2440\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Jefferson Fire Department\", " +
                "\"351 E Racine Street, Jefferson, WI 53549\", " +
                "\"(920) 675-7723\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Johnson Creek Fire Department\", " +
                "\"120 S Watertown Street, Johnson Creek, WI 53038\", " +
                "\"(920) 699-3456\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City of Lake Mills Fire Department\", " +
                "\"200 D Water Street, Lake Mills, WI 53551\", " +
                "\"(920) 648-5117\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Palmyra Fire Department\", " +
                "\"126 N First Street, Palmyra, WI 53156\", " +
                "\"(262) 495-2380\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Sullivan Volunteer Fire Department\", " +
                "\"800 Pleasant Street, Sullivan, WI 53178\", " +
                "\"(262) 593-2200\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Waterloo Fire Department\", " +
                "\"900 Industrial LN, Waterloo, WI 53594\", " +
                "\"(920) 478-3025\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Watertown Fire Department\", " +
                "\"106 Jones Street, Watertown, WI 53094\", " +
                "\"(920) 261-3610\", " +
                "\"Jefferson\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Belgium Fire Department\", " +
                "\"195 Commerce Street, Belgium, WI 53004\", " +
                "\"(262) 285-3257\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Cedarburg Fire Department\", " +
                "\"W61N631 Mequon Avenue, Cedarburg, WI 53012\", " +
                "\"(262) 375-7630\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Fredonia Fire Department\", " +
                "\"201 S Milwaukee Street, Fredonia, WI 53021\", " +
                "\"(262) 692-9973\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Grafton Volunteer Fire Department\", " +
                "\"1431 13TH Avenue, Grafton, WI 53024\", " +
                "\"(262) 375-5314\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City Of Mequon Fire Department\", " +
                "\"11300 N Buntrock Avenue, Mequon, WI 53092\", " +
                "\"(262) 242-2530\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Port Washington Fire Department\", " +
                "\"104 W Washington Street, Port Washington, WI 53074\", " +
                "\"(262) 284-2891\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Saukville Fire Department\", " +
                "\"520 W Dekora Street, Saukville, WI 53080\", " +
                "\"(262) 284-5800\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Thiensville Volunteer Fire Department\", " +
                "\"250 Elm Street, Thiensville, WI 53092\", " +
                "\"(262) 242-3393\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Waubeka Volunteer Fire Department\", " +
                "\"W4114 River Road, Waubeka, WI 53021\", " +
                "\"(262) 692-2656\", " +
                "\"Ozaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Darien Volunteer Fire Department\", " +
                "\"311 W Madison Street, Darien, WI 53114\", " +
                "\"(262) 882-3678\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Delavan City Volunteer Fire Department\", " +
                "\"811 Ann Street, Delavan, WI 53115\", " +
                "\"(262) 728-5646\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"East Troy Volunteer Fire Department\", " +
                "\"N 8406 County Road E.s, East Troy, WI 53120\", " +
                "\"(262) 642-7439\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Troy Center Fire Department\", " +
                "\"N8870 Briggs Street, East Troy, WI 53120\", " +
                "\"(262) 642-5292\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Elkhorn Area Fire Department\", " +
                "\"13 S Broad Street, Elkhorn, WI 53121\", " +
                "\"(262) 723-2277\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Bloomfield Genoa City Fire and Rescue\", " +
                "\"715 Walworth Street, Genoa City, WI 53128\", " +
                "\"N/A\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Lake Geneva Fire Department\", " +
                "\"730 Marshall Street, Lake Geneva, WI 53147\", " +
                "\"(262) 248-6075\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Town of Linn Fire Department\", " +
                "\"N 1457 Hillside Road, Lake Geneva, WI 53147\", " +
                "\"(262) 249-8808\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Town of Lyons Fire Department\", " +
                "\"1572 Mill Street, Lyons, WI 53148\", " +
                "\"(262) 763-3322\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Sharon Fire and Rescue\", " +
                "\"182 Park Avenue, Sharon, WI 53585\", " +
                "\"(262) 736-4543\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Walworth Fire Department\", " +
                "\"247 N Main Street, Walworth, WI 53184\", " +
                "\"(262) 275-3838\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Lauderdale - LaGrange Fire Department\", " +
                "\"W6080 Highway 12, Whitewater, WI 53190\", " +
                "\"(262) 495-8400\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Whitewater Fire Department\", " +
                "\"312 W Whitewater Street, Whitewater, WI 53190\", " +
                "\"(262) 473-0510\", " +
                "\"Walworth\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City Of Burlington Fire Department\", " +
                "\"165 W Washington Street, Burlington, WI 53105\", " +
                "\"(262) 763-7842\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Burlington Township Fire Department\", " +
                "\"32288 Bushnell Road, Burlington, WI 53105\", " +
                "\"(262) 767-7429\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Caledonia Fire Department\", " +
                "\"6900 Nicholson Road, Caledonia, WI 53108\", " +
                "\"(262) 835-2050\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Town of Raymond Fire and Rescue\", " +
                "\"2255 S 76 Street, Franksville, WI 53126\", " +
                "\"(262) 835-1687\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Kansasville Fire and Rescue Department\", " +
                "\"23730 Durand Avenue, Kansasville, WI 53139\", " +
                "\"(262) 878-3811\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Racine Fire Department\", " +
                "\"810 8TH Street, Racine, WI 53403\", " +
                "\"(262) 635-7911\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"SC Johnson Fire Brigade\", " +
                "\"1525 Howe Street, Racine, WI 53404\", " +
                "\"(262) 260-3372\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Mount Pleasant Fire Department\", " +
                "\"6200 Durand Avenue, Racine, WI 53406\", " +
                "\"(262) 554-8812\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Rochester Volunteer Fire Company\", " +
                "\"31020 Academy Road, Rochester, WI 53167\", " +
                "\"(262) 534-3444\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Sturtevant Fire Department\", " +
                "\"2801 89TH Street, Sturtevant, WI 53177\", " +
                "\"(262) 886-7224\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Paris Fire Department\", " +
                "\"16607 Burlington Road, Union Grove, WI 53182\", " +
                "\"(262) 859-3009\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Union Grove - Yorkville Fire Department\", " +
                "\"700 Main Street, Union Grove, WI 53182\", " +
                "\"(262) 878-4181\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Tichigan Volunteer Fire Company\", " +
                "\"8205 Big Bend Road, Waterford, WI 53185\", " +
                "\"(262) 662-3570\", " +
                "\"Racine\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"North Shore Fire Department\", " +
                "\"4401 W River LN, Brown Deer, WI 53223\", " +
                "\"(414) 357-0113\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Cudahy Fire Department\", " +
                "\"4626 S Packard Avenue, Cudahy, WI 53110\", " +
                "\"(414) 769-2231\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City of Franklin Fire Department\", " +
                "\"8901 W Drexal Avenue, Franklin, WI 53132\", " +
                "\"(414) 425-1420\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Greendale Fire Department\", " +
                "\"6200 W Loomis Road, Greendale, WI 53129\", " +
                "\"(414) 423-2131\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Greenfield Fire Department\", " +
                "\"4333 S 92nd Street, Greenfield, WI 53228\", " +
                "\"(414) 545-7946\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Hales Corners Fire Department\", " +
                "\"10000 W Forest Home Avenue, Hales Corners, WI 53130\", " +
                "\"(414) 529-6168\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Milwaukee Fire Department\", " +
                "\"711 W Wells Street, Milwaukee, WI 53233\", " +
                "\"(414) 286-8948\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Mitchell International Airport Fire Department\", " +
                "\"5800 S Howell Avenue, Milwaukee, WI 53207\", " +
                "\"(414) 747-5348\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City of Oak Creek Fire Department\", " +
                "\"7000 S 6TH Street, Oak Creek, WI 53154\", " +
                "\"(414) 570-5630\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"South Milwaukee Fire Department\", " +
                "\"929 Marshall Court, South Milwaukee, WI 53172\", " +
                "\"(414) 768-8191\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City of Street. Francis Fire Department\", " +
                "\"4235 S Nicholson Avenue, Street Francis, WI 53235\", " +
                "\"(414) 483-4424\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Wauwatosa Fire Department\", " +
                "\"1463 Underwood Avenue, Wauwatosa, WI 53213\", " +
                "\"(414) 471-8490\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"West Allis Fire Department\", " +
                "\"7332 W National Avenue, West Allis, WI 53214\", " +
                "\"(414) 302-8900\", " +
                "\"Milwaukee\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City of Brookfield Fire Department\", " +
                "\"2100 N Calhoun Road, Brookfield, WI 53005\", " +
                "\"(262) 782-8932\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Town of Brookfield Fire Department\", " +
                "\"645 Janacek Road, Brookfield, WI 53045\", " +
                "\"(262) 796-3792\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Butler Volunteer Fire Department\", " +
                "\"12621 W Hampton Avenue, Butler, WI 53007\", " +
                "\"(262) 783-2537\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City of Delafield Fire Department\", " +
                "\"500 Genesee Street, Delafield, WI 53018\", " +
                "\"(262) 646-6235\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Dousman Fire District\", " +
                "\"107 S Main Street, Dousman, WI 53118\", " +
                "\"(262) 965-2262\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Eagle Fire Department\", " +
                "\"126 Main Street, Eagle, WI 53119\", " +
                "\"(262) 594-3302\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Elm Grove Volunteer Fire Department\", " +
                "\"13600 Juneau Boulevard, Elm Grove, WI 53122\", " +
                "\"(262) 782-6700\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Chenequa Fire Department\", " +
                "\"31275 Highway K, Hartland, WI 53029\", " +
                "\"(262) 367-2239\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Hartland Fire Department\", " +
                "\"150 Lawn Street, Hartland, WI 53029\", " +
                "\"(262) 367-6878\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Lannon Fire Department\", " +
                "\"20399 Main Street, Lannon, WI 53046\", " +
                "\"(262) 251-1130\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Menomonee Falls Fire Department\", " +
                "\"W140 N7501 Lilly Road, Menomonee Falls, WI 53051\", " +
                "\"(262) 532-8823\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Merton Fire Department\", " +
                "\"W28343 Sussex Road, Merton, WI 53056\", " +
                "\"(262) 538-1012\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Mukwonago Fire Department\", " +
                "\"1111 Fox Street, Mukwonago, WI 53149\", " +
                "\"(262) 363-6426\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Tess Corners Volunteer Fire Department\", " +
                "\"W144S6731 Tess Corners Drive, Muskego, WI 53150\", " +
                "\"(414) 422-9733\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Muskego Fire Department\", " +
                "\"S76 W 17858 Janesville Road, Muskego, WI 53150\", " +
                "\"(262) 679-4118\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Lake Country Fire Department\", " +
                "\"N44W32787 Rasmus Drive, Nashotah, WI 53058\", " +
                "\"(262) 367-9664\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"New Berlin Fire Department\", " +
                "\"16300 W National Avenue, New Berlin, WI 53151\", " +
                "\"(262) 785-6120\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"North Lake Fire Department\", " +
                "\"PO Box 81, North Lake, WI 53064\", " +
                "\"(262) 966-2091\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"North Prairie Fire Department\", " +
                "\"108 N Oakridge Drive, North Prairie, WI 53153\", " +
                "\"(262) 392-2700\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Stone Bank Volunteer Fire Department\", " +
                "\"W335N7107 Stone Bank Road, Oconomowoc, WI 53066\", " +
                "\"(262) 966-2414\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City Of Oconomowoc Fire Department\", " +
                "\"212 S Concord Road, Oconomowoc, WI 53066\", " +
                "\"(262) 569-3223\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Summit Volunteer Fire District\", " +
                "\"2911 N Dousman Road, Oconomowoc, WI 53066\", " +
                "\"(262) 567-8282\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Okauchee Fire Department\", " +
                "\"5060 Shady LN, Okauchee, WI 53069\", " +
                "\"(262) 567-3585\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Lisbon Fire Department\", " +
                "\"N54W26455 Highway K, Pewaukee, WI 53072\", " +
                "\"(262) 538-3902\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Town of Delafield Fire Department\", " +
                "\"W304 N2455 Maple Avenue, Pewaukee, WI 53072\", " +
                "\"(262) 367-6930\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Sussex Fire Department\", " +
                "\"N63 W24335 Main Street, Sussex, WI 53089\", " +
                "\"(262) 246-5197\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Wales Genesee Fire Department\", " +
                "\"600 S Wales Road, Wales, WI 53183\", " +
                "\"(262) 968-3301\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"City of Waukesha Fire Department\", " +
                "\"130 W Street. Paul Avenue, Waukesha, WI 53188\", " +
                "\"(262) 524-3651\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Town of Waukesha Fire Department\", " +
                "\"W250S3567 Center Road, Waukesha, WI 53189\", " +
                "\"(262) 542-3199\", " +
                "\"Waukesha\", " +
                "\"Fire\");");

        db.execSQL("INSERT INTO " + TABLE_RESOURCES + " VALUES (" + "\"Pewaukee Fire Department\", " +
                "\"W239 N2242 Pewaukee Road, Waukesha, WI 53188\", " +
                "\"(262) 691-5610\", " +
                "\"Waukesha\", " +
                "\"Fire\");");
    }
}

