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

package u.ready_wisc;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.Time;
import android.util.Log;

import java.util.Set;

/**
 * Created by nathaneisner on 6/10/15.
 * Service to clear out messages from shared preferences after a set amount of time
 */
public class MessageService extends Service {
    public static final String ACTION = "u.ready_wisc.MessageService";
    //time to clear message - default to 1 hour = 3600000
    private static final int MESSAGE_TIME = 3600000;
    //time to loop through messages
    private static final int TIME_TO_CHECK = 30000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MessageService", "Message Service Started");
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("MyPrefsFile", 0);
                SharedPreferences.Editor editor = sp.edit();
                Set<String> messages = sp.getStringSet("messages", null);
                Set<String> messageTimes = sp.getStringSet("messageTimes", null);
                Time currentTime = new Time();
                currentTime.setToNow();
                if (messages != null && messageTimes != null) {
                    String[] mArr = messages.toArray(new String[messages.size()]);
                    String[] mtArr = messageTimes.toArray(new String[messageTimes.size()]);
                    if (mArr.length == mtArr.length) {
                        for (int i = 0; i < mArr.length; i++) {
                            String msg = mArr[i];
                            String mTime = mtArr[i];
                            //check duration of message
                            if (Long.valueOf(mTime) + MESSAGE_TIME < currentTime.toMillis(false)) {
                                Log.d("Message", msg + " has expired");
                                messages.remove(msg);
                                messageTimes.remove(mTime);
                                editor.putStringSet("messages", messages);
                                editor.putStringSet("messageTimes", messageTimes);
                                editor.apply();
                                //send broadcast to menu activity that message has timed-out
                                final Intent in = new Intent(ACTION);
                                in.putExtra("messageCleared", true);
                                LocalBroadcastManager.getInstance(MessageService.this)
                                        .sendBroadcast(in);
                            }
                        }
                    }
                    //stop service if no more message to wait for
                    if (messages.isEmpty()) {
                        MessageService.this.stopSelf();
                    }
                }
                //run again after a delay
                handler.postDelayed(this, TIME_TO_CHECK);
            }
        };
        handler.post(runnable);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MessageService", "Service Destroyed");
    }
}
