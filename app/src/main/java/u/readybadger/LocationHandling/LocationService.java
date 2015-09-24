package u.readybadger.LocationHandling;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;

public class LocationService extends IntentService {

    private static final String TAG = LocationService.class.getSimpleName();

    private static final String ACTION_LOCATION_UPDATED = "location updated";
    private static final String ACTION_REQUEST_LOCATION = "request location";

    private SharedPreferences settings;

    public static void requestLocation(Context context) {
        Intent intent = new Intent(context, LocationService.class);
        intent.setAction(LocationService.ACTION_REQUEST_LOCATION);
        context.startService(intent);
    }

    public LocationService() {
        super(TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent != null ? intent.getAction() : null;
        if (ACTION_REQUEST_LOCATION.equals(action)) {
            requestLocationInternal();
        } else if (ACTION_LOCATION_UPDATED.equals(action)) {
            locationUpdated(intent);
        }
    }

    private void requestLocationInternal() {
        Log.v(TAG, ACTION_REQUEST_LOCATION);
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        ConnectionResult connectionResult = googleApiClient.blockingConnect(
                10, TimeUnit.SECONDS);

        if (connectionResult.isSuccess() && googleApiClient.isConnected()) {

            Intent updatedIntent = new Intent(this, LocationService.class);
            updatedIntent.setAction(ACTION_LOCATION_UPDATED);

            // send last known location out if available
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                Intent lastLocIntent = new Intent(updatedIntent);
                lastLocIntent.putExtra(FusedLocationProviderApi.KEY_LOCATION_CHANGED, location);
                startService(lastLocIntent);
            }

            // request new location
            LocationRequest request = new LocationRequest().
                    setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, request, PendingIntent.getService(this, 0, updatedIntent, 0));

            // disconnect
            googleApiClient.disconnect();
        } else {
            Log.e(TAG, String.format("Could not connect ",
                    connectionResult.getErrorCode()));
        }
    }

    private void locationUpdated(Intent intent) {
        Log.v(TAG, ACTION_LOCATION_UPDATED);

        Location location = intent.getParcelableExtra(FusedLocationProviderApi.KEY_LOCATION_CHANGED);

        if (location != null) {
            ReverseGeocoder rgc = new ReverseGeocoder();
            rgc.setAddress(location);
            String currentCounty = rgc.getCounty();
            String currentCity = rgc.getCity();
            String currentState = rgc.getState();

            settings = this.getSharedPreferences("MyPrefsFile", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("county", currentCounty);
            editor.putString("city", currentCity);
            editor.putString("state", currentState);
            editor.apply();

            Log.d(TAG, location.toString() + "\n address1: " + rgc.getAddress1() +
                    "\n city: " + rgc.getCity()+
                    "\n state: " + rgc.getState() +
                    "\n zip " + rgc.getZIP() +
                    "\n county: " + currentCounty);
        }
    }

}

