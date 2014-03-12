package com.android.camera;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;

public class MediaRecord {
    public static final String TAG = "MediaRecord";
    /*
     * the code has a defect using many static fields.
     */
    private static int mIsPauseRecordSupported; // 0 not;1 X supported; 2 XS
                                                // supported;
    private static int mIsGetProfileDetect; // 0 not checked; 1 not supported;
    private static int X_Supported = 1;
    private static int XS_Supported = 2;
    // 2 X supported; 3 XS supported;
    private static Method mMethodGetProfile;

    private MediaRecorder mMediaRecorder;
    private boolean mMediaRecoderRecordingPaused = false;
    private Method mMethodPause;

    public MediaRecord(MediaRecorder mediaRecorder) {
        mMediaRecorder = mediaRecorder;
    }

    /**
     * Returns the camcorder profile for the given camera at the given quality
     * level.
     * 
     * @param cameraId
     *            ID of the camera
     * @param quality
     *            Target quality level for the camcorder profile.
     */
    public static CamcorderProfile getCameraProfile(int cameraId, int quality) {
        // adapt X
        try {
            mMethodGetProfile = CamcorderProfile.class.getDeclaredMethod(
                    "getMtk", int.class, int.class);
            mMethodGetProfile.setAccessible(true);
            mIsGetProfileDetect = X_Supported;
            // getMtk is a static method
            // public static CamcorderProfile getMtk(int cameraId, int
            // quality) {
            Log.v(TAG, "getCameraProfile adapt X");
        } catch (SecurityException e) {
            Log.e(TAG, "X getMtk SecurityException");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "X getMtk NoSuchMethodException");
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "X getMtk IllegalArgumentException");
        }
        if (mIsGetProfileDetect == 0) {
            // adapt XS
            try {
                Class<?> c = Class
                        .forName("com.mediatek.camcorder.CamcorderProfileEx");
                mMethodGetProfile = c.getDeclaredMethod("getProfile",
                        int.class, int.class);
                mIsGetProfileDetect = XS_Supported;
                // getProfile is a static method
                // public static CamcorderProfile getProfile(int cameraId, int
                // quality) {
                Log.v(TAG, "getCameraProfile adapt XS");
            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
            } catch (SecurityException e) {
                Log.e(TAG, "XS getProfile SecurityException");
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "XS getProfile NoSuchMethodException");
            }
        }

        try {
            if (mIsGetProfileDetect == X_Supported
                    || mIsGetProfileDetect == XS_Supported) {
                Log.v(TAG, "mIsGetProfileDetect:" + mIsGetProfileDetect
                        + "mMethodGetProfile.invoke:" + cameraId + ","
                        + quality);
                return (CamcorderProfile) mMethodGetProfile.invoke(null,
                        cameraId, quality);
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "mMethodGetProfile.invoke IllegalAccessException");
        } catch (InvocationTargetException e) {
            Log.e(TAG, "mMethodGetProfile.invoke InvocationTargetException");
        }
        Log.v(TAG, "return CamcorderProfile.get()");
        // TODO add a protected here if the quality is not supported.
        return CamcorderProfile.get(cameraId, quality);
    }

    /**
     * return camcorder support pause
     * 
     * @return
     */
    public boolean isPauseRecordSupported() {
        Log.v(TAG, "isPauseRecordSupported ENTER");
        // adapt X
        // mt6589_x_hike/frameworks/base/media/java/android/media/MediaRecorder.java
        // 1083 public void pause() throws IllegalStateException {
        try {
            mMethodPause = android.media.MediaRecorder.class.getDeclaredMethod(
                    "pause", (Class[]) null);
            mIsPauseRecordSupported = X_Supported;
            Log.v(TAG, "X pause adapt");
            return true;
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "X MediaRecorder NoSuchMethodException ");
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "X MediaRecorder IllegalArgumentException ");
        }

        // adapt XS
        // mt6592_xs_hike/mediatek/frameworks/base/media/java/com/mediatek/media/MediaRecorderEx.java
        // 94 public static void pause(MediaRecorder recorder) throws
        // IllegalStateException {
        try {
            Class<?> c = Class.forName("com.mediatek.media.MediaRecorderEx");
            mMethodPause = c.getDeclaredMethod("pause", MediaRecorder.class);
            mIsPauseRecordSupported = XS_Supported;
            Log.v(TAG, "XS pause adapt");
            return true;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "XS MediaRecorderEx ClassNotFoundException");
        } catch (SecurityException e) {
            Log.e(TAG, "XS MediaRecorderEx SecurityException");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "XS MediaRecorderEx NoSuchMethodException");
        }

        Log.v(TAG, "isPauseRecordSupported EXIT");
        mIsPauseRecordSupported = 1;
        return false;
    }

    /**
     * pause camcoder
     */
    public void pauseRecord() {
        Log.v(TAG, "pauseRecord ENTER");
        if (!mMediaRecoderRecordingPaused && isPauseRecordSupported()) {
            Log.v(TAG, "IsPauseRecordSupported:" + mIsPauseRecordSupported);
            try {
                if (mIsPauseRecordSupported == X_Supported) {
                    mMethodPause.invoke(mMediaRecorder, (Object[]) null);
                } else if (mIsPauseRecordSupported == XS_Supported) {
                    mMethodPause.invoke(null, mMediaRecorder);
                }
                mMediaRecoderRecordingPaused = true;
            } catch (IllegalArgumentException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
            Log.v(TAG, "pauseRecord EXIT");
            return;
        }
    }

    /**
     * resume camcoder
     */
    public void resumeRecord() {
        Log.v(TAG, "resumeRecord ENTER," + mMediaRecoderRecordingPaused);
        if (mMediaRecoderRecordingPaused) {
            mMediaRecorder.start();
            mMediaRecoderRecordingPaused = false;
        }
        Log.v(TAG, "resumeRecord EXIT");
    }
}
