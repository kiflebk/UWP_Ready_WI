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


package u.readybadger.Emergency_Main;


//This class builds damageReports
//Then calls a post method that will send
//the data to the sever as a HTTP Post.


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;

import u.readybadger.Connectivity;
import u.readybadger.R;
import u.readybadger.ReportsDatabaseHelper;

public class DamageReports extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CAMERA = 2;
    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };
    private static final int CAM_REQUEST = 1313;
    private static final int GALLERY_REQUEST = 4117;
    //the limit for damage photos
    private final int IMG_LIMIT = 3;
    private final String[] PICSOURCES = new String[]{"Camera", "Gallery"};
    static ReportsDatabaseHelper mDatabaseHelper;
    //Variable declare.
    private static EditText dateEdit;
    Button btnSubmit;
    RadioButton fireButton;
    RadioButton floodBox;
    RadioButton severeBox;
    RadioButton sewerBox;
    RadioButton otherBox;
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
    String encodedString;
    LinearLayout imageGallery;
    ImagesCache imgCache;
    int viewClicked;
    Menu mActionBarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_reports);

        // Shows the Warning Dialog
        createWarning();

        mDatabaseHelper = new ReportsDatabaseHelper(this);

        //Initialize variables on create of activity.
        dateEdit = (EditText) findViewById(R.id.dateEdit);
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btnSubmit = (Button) findViewById(R.id.submitbutton);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReport();
            }
        });
        fireButton = (RadioButton) findViewById(R.id.fireButton);
        floodBox = (RadioButton) findViewById(R.id.floodBox);
        severeBox = (RadioButton) findViewById(R.id.severeBox);
        sewerBox = (RadioButton) findViewById(R.id.sewerBox);
        otherBox = (RadioButton) findViewById(R.id.otherBox);
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
        insurDeductAmt = (EditText) findViewById(R.id.insurDeductAmt);
        //image cache
        imgCache = ImagesCache.getInstance();
        imgCache.initializeCache();
        imageGallery = (LinearLayout) findViewById(R.id.linearImage);
        viewClicked = -1;

        //This line is used to hide keyboard on startup.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imgCache.clearCache();
    }

    //builds and shows a chooser for camera or gallery
    private void showPictureSources() {
        if (imgCache.getImageCount() < IMG_LIMIT) {
            new MaterialDialog.Builder(DamageReports.this)
                    .title("Picture Source")
                    .items(PICSOURCES)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            switch (which) {
                                case 0:
                                    //TODO ask for permission to use
                                    if (canAccessCamera()) {
                                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                            startActivityForResult(takePictureIntent, CAM_REQUEST);
                                        }
                                    } else {
                                        requestCamera();
                                    }
                                    break;
                                case 1:
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                                    galleryIntent.setType("image/*");
                                    startActivityForResult(galleryIntent, GALLERY_REQUEST);
                            }
                        }
                    })
                    .show();
        } else {
            Toast.makeText(this, "Maximum Pictures Added!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_damage_reports, menu);
        mActionBarMenu = menu;
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
        //camera icon to add pictures to report
        if (id == R.id.action_pictures) {
            showPictureSources();
        }
        //delete picture button to remove picture
        if (id == R.id.action_remove_pic) {
            item.setVisible(false);
            removePhoto();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm = null;
        //check for null ... back pressed
        if (data != null) {
            //camera used
            if (requestCode == CAM_REQUEST) {
                bm = (Bitmap) data.getExtras().get("data");
            }
            //gallery used
            if (requestCode == GALLERY_REQUEST) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    bm = BitmapFactory.decodeStream(imageStream);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (bm != null) {
            putPhoto(bm);
        }
    }

    //put photo in cache and in picture gallery
    private void putPhoto(Bitmap bm) {
        //put in gallery
        imageGallery.addView(myPhoto(bm));
        //cache image
        imgCache.addImage(bm);
    }

    //removes photo that is clicked...viewClicked int
    private void removePhoto() {
        Log.d("View", String.valueOf(viewClicked));
        View imageToDelete = findViewById(viewClicked);
        imageToDelete.setBackgroundResource(android.R.color.transparent);
        //break apart imgview id to get ll id
        //ex: 9901 = imgview id & llid = 01
        char[] charArray = String.valueOf(viewClicked).toCharArray();
        charArray = Arrays.copyOfRange(charArray, 2, charArray.length);
        String llId = String.valueOf(charArray);
        LinearLayout ll = (LinearLayout) findViewById(Integer.valueOf(llId));
        ll.removeView(imageToDelete);
        imageGallery.removeView(ll);
        imgCache.removeImage(llId);
        viewClicked = -1;
    }

    //a custom image view to put in the gallery
    private View myPhoto(Bitmap bm) {
        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setLayoutParams(new ViewGroup.LayoutParams(360, 360));
        layout.setGravity(Gravity.CENTER);
        layout.setId(imgCache.getImageCount());

        Bitmap resized = ThumbnailUtils.extractThumbnail(bm, 350, 350);
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(350, 350));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(resized);
        //sets imgview id to "99 + the size of the imgcache"
        //this allows callback to cache if the image is clicked
        String imageId = "99" + String.valueOf(imgCache.getImageCount());
        imageView.setId(Integer.valueOf(imageId));
        //what happens when someone clicks a thumbnail
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewClicked == v.getId()) {
                    v.setBackgroundResource(android.R.color.transparent);
                    viewClicked = -1;
                    mActionBarMenu.findItem(R.id.action_remove_pic).setVisible(false);
                    return;
                }
                viewClicked = v.getId();
                v.setBackgroundResource(R.drawable.clicked_damage_picture);
                mActionBarMenu.findItem(R.id.action_remove_pic).setVisible(true);
            }
        });

        layout.addView(imageView);
        return layout;
    }

    //Date picker method that will show the date picker.
    public void showDatePickerDialog() {
        InputMethodManager imm = (InputMethodManager) getSystemService( //hides keyboard since its not needed
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dateEdit.getWindowToken(), 0);
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    protected void addUser(String name) {

        Log.i("DB Error", "Starting addUser");

        ContentValues values = new ContentValues();

        values.put(ReportsDatabaseHelper.COL_JSON, name);

        try {
            Log.i("DB Error", "Insertion Start");

            mDatabaseHelper.insert(mDatabaseHelper.TABLE_USERS, values);

            Log.i("DB Error", "Insertion Successful");

        } catch (ReportsDatabaseHelper.NotValidException e) {

            Log.e("DB Error:", "Unable to insert into DB.");

        }

    }

    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                //TODO: get images from cache then encode images from cache to string for transmission
                //thumbnail.compress(Bitmap.CompressFormat.JPEG, 8, stream);  /// I changed this from PNG and 50
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                Log.d("Pic encode2", encodedString);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {

            }
        }.execute(null, null, null);
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
            DamageReports.dateEdit.setText(month + 1 + "/" + day + "/" + year);
        }
    }

    // Methods that handle submitting a damage report
    public void submitReport() {
        Log.e("Post Test", "Button Working");

        try {

            //Block of if statements to check each field.
            if ((dateEdit.getText().toString() == null || dateEdit.getText().toString().matches(""))) {
                Toast.makeText(getApplicationContext(), "Please select a date of occurrence.", Toast.LENGTH_LONG).show();
            } else if ((name.getText().toString() == null || name.getText().toString().matches(""))) {
                Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_LONG).show();
            } else if (address.getText().toString() == null || address.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "Please enter an address.", Toast.LENGTH_LONG).show();
            } else if (city.getText().toString() == null || city.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "Please enter a city.", Toast.LENGTH_LONG).show();
            } else if (zip.getText() == null ||
                    (zip.getText().length() > 5 || zip.getText().length() < 5)) {
                Toast.makeText(getApplicationContext(), "Please enter a zip code.", Toast.LENGTH_LONG).show();
            } else if (damageCost.getText().toString() == null || damageCost.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "Please estimate a damage cost.", Toast.LENGTH_LONG).show();
            } else if (loss_percent.getText().toString() == null || loss_percent.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "Please estimate a loss percentage.", Toast.LENGTH_LONG).show();
                //TODO uncomment/add photo check
//                }
//                else if (encodedString == null || encodedString.equals("")) {
//                    Toast.makeText(getApplicationContext(), "Please take a picture.", Toast.LENGTH_LONG).show();

            }

            // JSON object is created based off of user input
            else {
                JSONObject jObject = createJObject();
                Log.e("Post Test", jObject.toString());

                // Convert JSON object to url string
                //String url = Config.DAMAGE_REPORT_URL + jObject.toString().replace('{', ' ').replace('}', ' ').replace(backspace, ' ').trim().replace('"', ' ').replace(" ", "").replace(':', '=').replace(',', '&');

                // The JSON object is passed over to be sent
                if (Connectivity.isOnline(this))
                    putDataToServer(jObject.toString());
                else {
                    addUser(jObject.toString());
                    String place = null;
                    Cursor cur = mDatabaseHelper.query(ReportsDatabaseHelper.TABLE_USERS, null);

                    if (cur.moveToFirst()) {
                        int placeColumn = cur.getColumnIndex(ReportsDatabaseHelper.COL_JSON);
                        place = cur.getString(placeColumn);
                    }

                    Log.i("Post Test", place);
                    Toast.makeText(getApplicationContext(), "No Network Connection.  Report will be submitted when network connection is established.", Toast.LENGTH_LONG).show();
                    DamageReports.this.finish();
                }

            }
        } catch (Throwable ignored) {

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

            // All data is convert to a string and put into the JSON object

            obj.put("deviceid", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            obj.put("type_of_occurrence", disasterType);
            obj.put("date", String.valueOf(dateEdit.getText()));
            obj.put("name", name.getText().toString());
            obj.put("address", address.getText().toString());
            obj.put("city", city.getText().toString());
            obj.put("add_state", state.getText().toString());
            obj.put("zip", zip.getText().toString());
            obj.put("own_or_rent", (rentOrOwned + ""));
            obj.put("insurance_deductible", insurDeductAmt.getText());
            obj.put("damage_cost", damageCost.getText().toString());
            obj.put("loss_percent", loss_percent.getText().toString());
            obj.put("habitable", (checked(habitable) + ""));
            obj.put("basement_water", (checked(basement_water) + ""));
            obj.put("water_depth", water_depth.getText().toString());
            obj.put("basement_resident", checked(basement_resident) + "");
            obj.put("damage_desc", damage_desc.getText().toString());

            //TODO instead of encoding images, add them to the multipart entity
            //obj.put("encoded_image", encodedString.replace("/", "%2F").replace("+", "%2B").replace("\n", ""));

            if (Connectivity.isOnline(this)) {
                SharedPreferences settings = this.getSharedPreferences("MyPrefsFile", 0);
                long lon = settings.getLong("lon", 0);
                long lat = settings.getLong("lat", 0);
                obj.put("longitude", (lon + ""));
                obj.put("latitude", (lat + ""));
            }
            //TODO add else if for when network isn't reached

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    //Method to send data to server via HTTP Post
    public void putDataToServer(String json) throws Throwable {

        String reportAccepted;
        Log.e("Post Test", json);
        PutData httpPost = new PutData(json);
        Thread t = new Thread(httpPost);
        t.start();
        t.join();
        Log.e("Post Test", json);

        //TODO get validation for successful post requests
//            reportAccepted = httpPost.getDataAccepted();
//            Log.d("Send pic", reportAccepted);
//
//            if (reportAccepted.equals("1")) {
//                Toast.makeText(getApplicationContext(), "Report Submitted Successfully", Toast.LENGTH_LONG).show();
//                DamageReports.this.finish();
//
//            } else
//                Toast.makeText(getApplicationContext(), "Report Not Sent", Toast.LENGTH_LONG).show();

    }

    // method to create the AlertDialog
    public void createWarning() {
        DialogFragment mFragment = new WarningDialog();
        mFragment.show(getFragmentManager(), "warning");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, CAM_REQUEST);
            }
        }
    }

    private boolean canAccessCamera() {
        return (hasPermission(CAMERA_PERMS[0]));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, perm));
    }

    private void requestCamera() {
        ActivityCompat.requestPermissions(this,
                CAMERA_PERMS,
                PERMISSION_REQUEST_CAMERA);
    }
}
