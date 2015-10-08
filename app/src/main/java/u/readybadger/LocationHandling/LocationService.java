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

package u.readybadger.LocationHandling;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.concurrent.TimeUnit;

public class LocationService extends IntentService {

    private static final String TAG = LocationService.class.getSimpleName();

    public static final String ACTION_LOCATION_UPDATED = "location updated";
    public static final String ACTION_REQUEST_LOCATION = "request location";

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
            try {
                LocationRequest request = new LocationRequest().
                        setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        googleApiClient, request, PendingIntent.getService(this, 0, updatedIntent, 0));
            } catch (Exception e) {
                Log.e("Location", e.getMessage());
            }
            final Intent sendIntent = new Intent(ACTION_REQUEST_LOCATION);
            sendIntent.putExtra("status", true);
            LocalBroadcastManager.getInstance(this).sendBroadcast(sendIntent);
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
            if (!currentCounty.isEmpty())
                editor.putString("county", currentCounty);
            if (!currentCity.isEmpty())
                editor.putString("city", currentCity);
            if (!currentState.isEmpty())
                editor.putString("state", currentState);
            editor.putLong("lat", (long) location.getLatitude());
            editor.putLong("lon", (long) location.getLongitude());
            editor.apply();


//            Log.d(TAG, location.toString() + "\n address1: " + rgc.getAddress1() +
//                    "\n city: " + rgc.getCity() +
//                    "\n state: " + rgc.getState() +
//                    "\n zip " + rgc.getZIP() +
//                    "\n county: " + currentCounty);
        }
    }

}

