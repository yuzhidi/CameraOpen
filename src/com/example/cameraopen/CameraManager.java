package com.example.cameraopen;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;

public class CameraManager {
    public static final String TAG = "CameraManager";

    private Camera mCamera;
    private android.hardware.Camera.ContinuousShotDone mMTKCSDoneCallback;
    private ContinuousShotDone mCSDoneCallback;
    private Parameters mParameters;

    private static final String KEY_CAMERA_MODE = "mtk-cam-mode";

    /**
     * @hide An interface which contains a callback for the continuous shot
     */
    public interface ContinuousShotDone {
        public void onConinuousShotDone(int capNum);
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
    }


    /**
     * detect camera profile
     */
    public void detectProfile() {
        if(mCamera == null) {
            return;
        }
    }

    /**
     * check MTK camera mode API available.
     * @return 
     */
    private boolean isCamptureModeAvilable() {
        //TODO check KEY_CAMERA_MODE
        return false;
    }

    /**
     * check MTK camera specific mode available.
     * @param value: CAPTURE_MODE_NORMAL, CAPTURE_MODE_BEST_SHOT,
     *        CAPTURE_MODE_EV_BRACKET_SHOT, CAPTURE_MODE_BURST_SHOT,
     *        CAPTURE_MODE_SMILE_SHOT, CAPTURE_MODE_PANORAMA_SHOT
     * @return true supported; false not supported.
     */
    public boolean isCaptureModeSurpported(String value) {
        return false;
    }

    /**
     * set MTK camera capture mode
         * @param value: CAPTURE_MODE_NORMAL, CAPTURE_MODE_BEST_SHOT,
         *        CAPTURE_MODE_EV_BRACKET_SHOT, CAPTURE_MODE_BURST_SHOT,
         *        CAPTURE_MODE_SMILE_SHOT, CAPTURE_MODE_PANORAMA_SHOT
     * @return true success; false fail.
     */
    public boolean setCaptureMode(String value) {
        if (!isCamptureModeAvilable() || !isCaptureModeSurpported(value)) {
            return false;
        }
        return false;
    }

    /**
     * Registers a callback to be invoked when continuous shot is done
     * 
     * @param ContinuousShotDone
     */
    public void setCSDoneCallback(ContinuousShotDone callback) {
        mCSDoneCallback = callback;
        if ((true)) {
            // TODO maybe the code could be improved, do not create new object
            // each time. for first version, keep this way.
            mMTKCSDoneCallback = new android.hardware.Camera.ContinuousShotDone() {
                public void onConinuousShotDone(int capNum) {
                    mCSDoneCallback.onConinuousShotDone(capNum);
                }
            };
        }
    }

    /**
     * 
     * @return android.hardware.Camera.Parameters. null if camera is not
     *         provided by {@link #setCamera(Camera camera)} or the provided
     *         camera is invalid;
     */
    public Parameters getParameters() {
        if (mCamera == null) {
            return null;
        }
        mParameters = mCamera.getParameters();
        return mParameters;
    }

    public class Parameter {
        // Values for capture mode settings.
        public static final String CAPTURE_MODE_NORMAL = "normal";
        public static final String CAPTURE_MODE_BEST_SHOT = "bestshot";
        public static final String CAPTURE_MODE_EV_BRACKET_SHOT = "evbracketshot";
        public static final String CAPTURE_MODE_BURST_SHOT = "burstshot";
        public static final String CAPTURE_MODE_SMILE_SHOT = "smileshot";
        public static final String CAPTURE_MODE_PANORAMA_SHOT = "autorama";
        public static final String CAPTURE_MODE_ASD = "asd";
        public static final String CAPTURE_MODE_FB = "face_beauty";
        public static final String KEY_MAX_NUM_DETECTED_OBJECT = "max-num-ot";
        public static final String CAPTURE_MODE_S3D = "single3d";
        public static final String CAPTURE_MODE_PANORAMA3D = "panorama3dmode";
        public static final String CAPTURE_MODE_CONTINUOUS_SHOT = "continuousshot";
    }

}
