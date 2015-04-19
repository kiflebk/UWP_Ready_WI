package u.ready_wisc.Emergency_Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import u.ready_wisc.Config;
import u.ready_wisc.R;

public class DamageReports extends ActionBarActivity {

    //Variable declare.
    private static EditText text9;
    Button btnTakePhoto;
    ImageView imgTakenPhoto;
    Button btnSubmit;
    private static final int CAM_REQUEST = 1313;
    static LocationManager locationManager;
    static Location loc;
    static LocationListener locationListener;
    private AlertDialog.Builder alert;
    RadioButton fireButton;
    RadioButton floodBox;
    RadioButton severeBox;
    RadioButton sewerBox;
    RadioButton otherBox;
    EditText date;
    EditText name;
    EditText address;
    EditText city;
    EditText state;
    EditText zip;
    RadioButton own;
    RadioButton rent;
    EditText damageCost;
    EditText loss_percent;
    RadioButton habitable;
    RadioButton basement_water;
    EditText water_depth;
    RadioButton basement_resident;
    EditText damage_desc;
    EditText insurDeductAmt;
    int disasterType;
    int rentOrOwned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_reports);

        //Initialize variables on create of activity.
        btnTakePhoto = (Button) findViewById(R.id.camerabutton);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageview1);
        text9 = (EditText) findViewById(R.id.dateEdit);
        btnSubmit = (Button) findViewById(R.id.submitbutton);
        fireButton = (RadioButton) findViewById(R.id.fireButton);
        floodBox = (RadioButton) findViewById(R.id.floodBox);
        severeBox = (RadioButton) findViewById(R.id.severeBox);
        sewerBox = (RadioButton) findViewById(R.id.sewerBox);
        otherBox = (RadioButton) findViewById(R.id.otherBox);
        date = (EditText) findViewById(R.id.dateEdit);
        name = (EditText) findViewById(R.id.nameEdit);
        address = (EditText) findViewById(R.id.addressEdit);
        city = (EditText) findViewById(R.id.cityEdit);
        state = (EditText) findViewById(R.id.stateEdit);
        zip = (EditText) findViewById(R.id.zipEdit);
        own = (RadioButton) findViewById(R.id.ownBox);
        rent = (RadioButton) findViewById(R.id.rentBox);
        damageCost = (EditText) findViewById(R.id.lossEdit);
        loss_percent = (EditText) findViewById(R.id.percentEdit);
        habitable = (RadioButton) findViewById(R.id.habitBoxYes);
        basement_water = (RadioButton) findViewById(R.id.waterBoxYes);
        water_depth = (EditText) findViewById(R.id.yesWaterEdit);
        basement_resident = (RadioButton) findViewById(R.id.familyBoxYes);
        damage_desc = (EditText) findViewById(R.id.damageEdit);
        btnTakePhoto.setOnClickListener(new btnTakenPhotoClicker());
        btnSubmit.setOnClickListener(new btnSubmit());
        insurDeductAmt = (EditText) findViewById(R.id.insurDeductAmt);


        //This line is used to hide keyboard on startup.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //locationManager initialize
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //Location settings with default methods.
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                loc = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //Request location updates from the network (wifi or cell tower).
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    //Method that would use the location every time it is changed.
    public void makeUseOfNewLocation(Location location) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_damage_reports, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgTakenPhoto.setImageBitmap(thumbnail);
        }
    }

    /*Class for button click of the "submit" button.*/
    class btnSubmit implements Button.OnClickListener {

        public void onClick(View v) {

            try {

                JSONObject jObject = createJObject();

                // URL located in config file
                String rep = putDataToServer(Config.URL_REPORT, jObject);

                Toast.makeText(getApplicationContext(), rep, Toast.LENGTH_LONG).show();
            } catch (Throwable e) {

            }

        }

        //method to check if radio boxes are checked.
        public int checked(RadioButton r) {
            //return 1 if not checked.
            int checked = 1;
            //return 0 if checked.
            if (r.isChecked())
                checked = 0;

            return checked;
        }

        //Method to create a JSONObject that holds the contents of the form
        public JSONObject createJObject() {
            JSONObject obj = new JSONObject();

            //Insert damage report fields into JSONObject.
            //(Key,value)

            if (checked(floodBox) == 0)
                disasterType = 0;
            else if (checked(severeBox) == 0)
                disasterType = 1;
            else if (checked(sewerBox) == 0)
                disasterType = 2;
            else if (checked(fireButton) == 0)
                disasterType = 3;
            else
                disasterType = 4;

            if (checked(own) == 0)
                rentOrOwned = 0;
            else if (checked(rent) == 0)
                rentOrOwned = 1;

            //If amount is left blank assume there is no insurance
            if (insurDeductAmt.getText() == null) {
                insurDeductAmt.setText("0.00");
            }


            try {

                obj.put("deviceid", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                obj.put("type_of_occurrence", disasterType);
                obj.put("date", date.getText().toString());
                obj.put("name", name.getText().toString());
                obj.put("address", address.getText().toString());
                obj.put("city", city.getText().toString());
                obj.put("add_state", state.getText().toString());
                obj.put("zip", zip.getText().toString());
                obj.put("own_or_rent", rentOrOwned);
                obj.put("Insurance Deductible", insurDeductAmt);
                obj.put("damage_cost", damageCost.getText().toString());
                obj.put("loss_percent", loss_percent.getText().toString());
                obj.put("habitable", checked(habitable));
                obj.put("basement_water", checked(basement_water));
                obj.put("water_depth", water_depth.getText().toString());
                obj.put("basement_resident", checked(basement_resident));
                obj.put("damage_desc", damage_desc.getText().toString());
                obj.put("longitude", loc.getLongitude());
                obj.put("latitude", loc.getLatitude());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return obj;
        }

        //Method to send data to server via HTTP Post
        public String putDataToServer(String url, JSONObject json) throws Throwable {


            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);

            StringBuilder sb = new StringBuilder();


            StringEntity entity = new StringEntity(json.toString());

            entity.setContentType("application/json;charset=UTF-8");
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));


            request.setEntity(entity);

            HttpResponse response = null;


            HttpConnectionParams.setSoTimeout(httpclient.getParams(), 10000);
            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 10000);
            try {
                Log.d("Sending report", url);
                response = httpclient.execute(request);
            } catch (SocketException se) {
                Log.e("SocketException", se + "");
                throw se;
            }


            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }

            return sb.toString();
        }

    }

    /*Class for button click of the "take photo" button.*/
    class btnTakenPhotoClicker implements Button.OnClickListener {

        //What to do on click.
        @Override
        public void onClick(View v) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAM_REQUEST);
        }
    }

    //Date picker method that will show the date picker.
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datepicker");
    }

    /*Class to create a date picker fragment.*/
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        //Method that sets default date on creation of the date picker.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        //Method to set the text field for date to the user picked date.
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            DamageReports.text9.setText(month + 1 + "/" + day + "/" + year);
        }
    }

    /*Class to show the dialog on press of the submit button.*/
    public static class ShowLocationFragment extends DialogFragment {

        //Method that will make everything upon creation of the fragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            //Variables that will get lat and long from the Location object.
            double lat = loc.getLatitude();
            double longit = loc.getLongitude();

            //Setting the builder to have a message and options for "okay" and "cancel".
            builder.setMessage("Latitude: " + Double.toString(lat) + " \nLongitude: " + Double.toString(longit))
                    .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            locationManager.removeUpdates(locationListener);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            locationManager.removeUpdates(locationListener);
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
