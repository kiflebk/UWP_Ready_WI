package u.ready_wisc;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

//import com.pushbots.push.Pushbots;

public class SplashActivity extends ActionBarActivity {

    private final int time = 2000;
    static MyDatabaseHelper mDatabaseHelper;
    boolean splashClose = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabaseHelper = new MyDatabaseHelper(this);
        mDatabaseHelper.onUpgrade(mDatabaseHelper.getReadableDatabase(),0,1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
       // Pushbots.sharedInstance().init(this);

        if (isOnline()) {
            DBUpdateFromWeb foo = new DBUpdateFromWeb();
            Thread t = new Thread(foo);

            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,CountyPicker.class);
                SplashActivity.this.startActivity(intent);
                splashClose = true;
//                Intent mainIntent = new Intent(SplashActivity.this, MenuActivity.class);
//                SplashActivity.this.startActivity(mainIntent);
//                SplashActivity.this.finish();
            }
        }, time);

    }

    protected void onResume(){
        super.onResume();
        if (splashClose)
        SplashActivity.this.finish();
    }

    // returns true or false based on if device has an internet connection.
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_activity, menu);
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

    protected static void addUser(String name, String email, long dateOfBirthMillis) {

        //mDatabaseHelper.onUpgrade(mDatabaseHelper.getReadableDatabase(), 0, 1);

        ContentValues values = new ContentValues();

        values.put(MyDatabaseHelper.COL_NAME, name);

        if (email != null) {

            values.put(MyDatabaseHelper.COL_EMAIL, email);

        }

        if (dateOfBirthMillis != 0) {

            values.put(MyDatabaseHelper.COL_DOB, dateOfBirthMillis);

        }

        try {

            mDatabaseHelper.insert(mDatabaseHelper.TABLE_USERS, values);

        } catch (MyDatabaseHelper.NotValidException e) {

            Log.e("DB Error:", "Unable to insert into DB.");

        }

    }
}
