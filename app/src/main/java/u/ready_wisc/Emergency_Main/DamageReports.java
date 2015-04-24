package u.ready_wisc.Emergency_Main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

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
    //EditText date;
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
        //date = (EditText) findViewById(R.id.dateEdit);
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
                Log.d("String URL:   ", Config.URL_REPORT);
                Log.d("JSON OBJ:   ", jObject.toString());
                // Something is wrong with putDataToServer method... this is why teh toast will not work.
                // URL located in config file
                String rep = (putDataToServer(Config.URL_REPORT, jObject));
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
                obj.put("type_of_occurrence", (disasterType+"").replace(" ","%20"));
                obj.put("date", String.valueOf(text9.getText()).replace(" ","%20"));
                obj.put("name", name.getText().toString().replace(" ","%20"));
                obj.put("address", address.getText().toString().replace(" ","%20"));
                obj.put("city", city.getText().toString().replace(" ","%20"));
                obj.put("add_state", state.getText().toString().replace(" ","%20"));
                obj.put("zip", zip.getText().toString().replace(" ","%20"));
                obj.put("own_or_rent", (rentOrOwned+"").replace(" ","%20"));
                obj.put("insurance_deductible", insurDeductAmt.getText().toString().replace(" ","%20"));
                obj.put("damage_cost", damageCost.getText().toString().replace(" ","%20"));
                obj.put("loss_percent", loss_percent.getText().toString().replace(" ","%20"));
                obj.put("habitable", (checked(habitable)+"").replace(" ","%20"));
                obj.put("basement_water", (checked(basement_water)+"").replace(" ","%20"));
                obj.put("water_depth", water_depth.getText().toString().replace(" ","%20"));
                obj.put("basement_resident", (checked(basement_resident)+"").replace(" ","%20"));
                obj.put("damage_desc", damage_desc.getText().toString().replace(" ","%20"));
                obj.put("longitude", (loc.getLongitude()+"").replace(" ","%20"));
                obj.put("latitude", (loc.getLatitude()+"").replace(" ","%20"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return obj;
        }

        //Method to send data to server via HTTP Post
        public String putDataToServer(String url, JSONObject json) throws Throwable{

            PutData test = new PutData(url, json);
            Thread t = new Thread(test);
            t.start();

//            Log.d("Data to server", "started");
//            int TIMEOUT_MILLISEC = 10000;  // = 10 seconds
//            DefaultHttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppostreq = new HttpPost("http://joshuaolufs.com/php/TESTquery_zipcodes.php");
//            StringEntity se = new StringEntity(json.toString());
//            //httppostreq.setEntity(new ByteArrayEntity(json.toString().getBytes("UTF8")));
//            httppostreq.setEntity(se);
//            HttpResponse httpresponse = httpclient.execute(httppostreq);
//            String responseText = null;
//
//
//            try {
//                responseText = EntityUtils.toString(httpresponse.getEntity());
//            }catch (ParseException e) {
//                e.printStackTrace();
//                Log.i("Parse Exception", e + "");
//            }
//
//            Log.d("Response", responseText);
//change
/*            DefaultHttpClient httpclient = new DefaultHttpClient();
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

            return sb.toString();*/
            return "hi";
        }
//this is a test
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
        InputMethodManager imm = (InputMethodManager) getSystemService( //hides keyboard since its not needed
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(text9.getWindowToken(), 0);
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

}
