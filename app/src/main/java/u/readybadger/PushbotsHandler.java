package u.readybadger;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

import com.pushbots.push.PBNotificationIntent;
import com.pushbots.push.Pushbots;
import com.pushbots.push.utils.PBConstants;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PushbotsHandler extends BroadcastReceiver {
    public PushbotsHandler() {
    }

    private static final String TAG = "customHandler";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "action=" + action);
        if (action.equals(PBConstants.EVENT_MSG_RECEIVE)) {
            //get msg and add to app shared pref
            HashMap<?, ?> PushdataOpen = (HashMap<?, ?>) intent.getExtras().get(PBConstants.EVENT_MSG_RECEIVE);
            if (PushdataOpen != null) {
                String msg = (String) PushdataOpen.get("message");
                addMessage(context, msg);
            }
        }
        // Handle Push Message when opened
        if (action.equals(PBConstants.EVENT_MSG_OPEN)) {
            //Check for Pushbots Instance
            Pushbots pushInstance = Pushbots.sharedInstance();
            if (!pushInstance.isInitialized()) {
                Log.d(TAG, "Initializing Pushbots.");
                Pushbots.sharedInstance().init(context.getApplicationContext());
            }

            //Clear Notification array
            if (PBNotificationIntent.notificationsArray != null) {
                PBNotificationIntent.notificationsArray = null;
            }

//            HashMap<?, ?> PushdataOpen = (HashMap<?, ?>) intent.getExtras().get(PBConstants.EVENT_MSG_OPEN);
//            Log.w(TAG, "User clicked notification with Message: " + PushdataOpen.get("message"));

            //Start launch Activity
            String packageName = context.getPackageName();
            Intent resultIntent = new Intent(context.getPackageManager().getLaunchIntentForPackage(packageName));
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            resultIntent.putExtras(intent.getBundleExtra("pushData"));
            Pushbots.sharedInstance().startActivity(resultIntent);

            // Handle Push Message when received
        }
    }

    private void addMessage(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("MyPrefsFile", 0);
        SharedPreferences.Editor editor = sp.edit();
        Set<String> messages = sp.getStringSet("messages", null);
        Set<String> messageTimes = sp.getStringSet("messageTimes", null);
        Time currentTime = new Time();
        currentTime.setToNow();
        if (msg != null && !msg.isEmpty()) {
            if (messages != null && messageTimes != null) {
                messages.add(msg);
                messageTimes.add(String.valueOf(currentTime.toMillis(false)));
            }
            //no previous messages
            else {
                messages = new HashSet<>();
                messageTimes = new HashSet<>();
                messages.add(msg);
                messageTimes.add(String.valueOf(currentTime.toMillis(false)));
            }
        }
        if (messages != null && messageTimes != null) {
            //must make sure there is a time for each message
            //and start server to handle clearing of message
            if (messages.size() == messageTimes.size()) {
                if (!isMyServiceRunning(MessageService.class, context)) {
                    Intent mServiceIntent = new Intent(context, MessageService.class);
                    context.startService(mServiceIntent);
                }
                editor.putStringSet("messages", messages);
                editor.putStringSet("messageTimes", messageTimes);
                editor.apply();
            }
        }
    }
    //checks to see if a service is running
    private boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}