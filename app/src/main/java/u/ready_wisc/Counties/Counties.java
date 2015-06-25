package u.ready_wisc.Counties;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * singleton class for all county information
 * parses csv file counties.csv
 * Created by nathaneisner on 6/19/15.
 */
public class Counties {
    // Additional codes can be found at https://alerts.weather.gov/cap/wi.php?x=3
    //All County information
    public static final HashMap<String, County> ALL = new HashMap<>();
    public static final HashMap<String, String> REGIONS = new HashMap<>();
    private static Counties instance;
    private static Context context;
    private static Map<String, String> regions = new HashMap<>();

    static {
        regions.put("N", "Northern");
        regions.put("NE", "North East");
        regions.put("NW", "North West");
        regions.put("SC", "South Central");
        regions.put("WC", "West Central");
        regions.put("SE", "South East");
    }

    //constructor that runs all of the parsing
    private Counties(Context ct) {
        this.context = ct;
        int id = context.getResources().getIdentifier("counties", "raw", context.getPackageName());
        InputStream is = context.getResources().openRawResource(id);
        parseFile(is);
    }

    //makes this a singleton class
    public static Counties getInstance(Context ct) {
        if (instance == null) {
            instance = new Counties(ct);
        }
        context = ct;
        return instance;
    }


    private static void parseFile(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        try {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseLine(String line) {
        String[] cells = line.split(",");
        if (cells.length > 0) {
            //name of county
            String name = cells[0];
            //rss code
            String code = cells[1];
            //pushbots appid
            String appID = cells[2];
            //region county belongs
            String region = cells[3];
            ALL.put(name, new County(name, code, appID, region));
            //add a new region to the region map
            REGIONS.put(region,
                    (isNull(regions.get(region)) ? "" : regions.get(region)));
        }
    }

    private static boolean isNull(Object o) {
        return o == null;
    }
}
