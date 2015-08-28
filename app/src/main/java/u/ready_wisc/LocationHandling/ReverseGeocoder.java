package u.ready_wisc.LocationHandling;

import android.location.Location;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReverseGeocoder {
    private String Address1;
    private String Address2;
    private String City;
    private String State;
    private String ZIP;
    private String County;
    private String Country;

    public void setAddress(Location location) {
        Address1 = "";
        Address2 = "";
        City = "";
        State = "";
        ZIP = "";
        County = "";
        Country = "";

        LocationJsonParser jparser = new LocationJsonParser();
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
                + latitude + "," + longitude + "&sensor=true";

        try {
            JSONObject jsonObject = jparser.getJsonFromURL(url);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("OK")) {
                JSONArray results = jsonObject.getJSONArray("results");
                JSONObject first = results.getJSONObject(0);
                JSONArray address_components = first.getJSONArray("address_components");

                for (int i = 0; i < address_components.length(); i++) {
                    JSONObject zero2 = address_components.getJSONObject(i);
                    String long_name = zero2.getString("long_name");
                    JSONArray mtypes = zero2.getJSONArray("types");
                    String type = mtypes.getString(0);

                    if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null)
                            || long_name.length() > 0 || long_name != "") {
                        if (type.equalsIgnoreCase("street_number")) {
                            Address1 = long_name + " ";
                        } else if (type.equalsIgnoreCase("route")) {
                            Address1 = Address1 + long_name;
                        } else if (type.equalsIgnoreCase("sublocality")) {
                            Address2 = long_name;
                        } else if (type.equalsIgnoreCase("locality")) {
                            City = long_name;
                        } else if (type.equalsIgnoreCase("administrative_area_level_2")) {
                            // The JSON says "Racine County" but we just want "Racine"
                            County = long_name.replace(" County", "");
                        } else if (type.equalsIgnoreCase("administrative_area_level_1")) {
                            State = long_name;
                        } else if (type.equalsIgnoreCase("country")) {
                            Country = long_name;
                        } else if (type.equalsIgnoreCase("postal_code")) {
                            ZIP = long_name;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAddress1() {
        return Address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public String getCity() {
        return City;
    }

    public String getState() {
        return State;
    }

    public String getZIP() {
        return ZIP;
    }

    public String getCounty() {
        return County;
    }

    public String getCountry() {
        return Country;
    }
}