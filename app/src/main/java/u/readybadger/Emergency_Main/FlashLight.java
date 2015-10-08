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

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;

/**
 * A Simple flashlight controller to handle flashlight usage in SDK >= 14?
 *
 * ** NOTE PERMISSION MUST BE GRANTED IN API 23 BEFORE USAGE! **
 */
@SuppressWarnings("deprecation")
public class FlashLight {
    private Context context;
    public boolean status = false;
    private static FlashLight instance = null;
    private Camera camera = null;

    public FlashLight(Context context) {
        this.context = context;
    }

    public static FlashLight getInstance(Context context) {
        if (instance == null) {
            instance = new FlashLight(context);
        }
        return instance;
    }

    public void toggle() {
        if (instance != null) {
            if (isOn()) {
                Log.i("Flashlight", "Turn Off");
                control(false);
                status = false;
            } else {
                Log.i("Flashlight", "Turn On");
                control(true);
                status = true;
            }
        } else {
            Log.e("FlashLight", "Flashlight not INITIALIZED");
        }
    }

    private void control(boolean state) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            CameraManager cameraManager = (CameraManager) context
                    .getSystemService(Context.CAMERA_SERVICE);
            try {
                for (String id : cameraManager.getCameraIdList()) {

                    // Turn on the flash if camera has one
                    if (cameraManager.getCameraCharacteristics(id)
                            .get(CameraCharacteristics.FLASH_INFO_AVAILABLE)) {
                        cameraManager.setTorchMode(id, state);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (safeCameraOpen()) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
            }
        }
    }

    private boolean safeCameraOpen() {
        boolean qOpened = false;

        try {
            releaseCamera();
            camera = Camera.open();
            qOpened = (camera != null);
        } catch (Exception e) {
            Log.e("FlashLight", "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    public void stop() {
        control(false);
        camera = null;
        instance = null;
    }

    public boolean isOn() {
        return status;
    }

}