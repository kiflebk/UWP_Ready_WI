package u.ready_wisc.Emergency_Main;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_reports);

        //Initialize variables on create of activity.
        btnTakePhoto = (Button) findViewById(R.id.camerabutton);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageview1);
        text9 = (EditText) findViewById(R.id.dateEdit);
        btnSubmit = (Button) findViewById(R.id.submitbutton);

        btnTakePhoto.setOnClickListener(new btnTakenPhotoClicker());
        btnSubmit.setOnClickListener(new btnSubmit());

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

//    //Method that would use the location every time it is changed.
//    public void makeUseOfNewLocation(Location location) {
//
//    }


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

        // added check so app does not crash if camera is exited before picture is taken
        if (requestCode == CAM_REQUEST) {
            if (RESULT_OK == resultCode) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imgTakenPhoto.setImageBitmap(thumbnail);
            }
        }
    }

    /*Class for button click of the "submit" button.*/
    class btnSubmit implements Button.OnClickListener {

        //Show the lat and long dialog fragment.
        @Override
        public void onClick(View v) {
            DialogFragment newFragment = new ShowLocationFragment();
            newFragment.show(getFragmentManager(), "location");
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

            if (loc != null) {
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
            } else {
                builder.setMessage("Location has not been set.");
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        locationManager.removeUpdates(locationListener);
                    }
                });
            }
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}

