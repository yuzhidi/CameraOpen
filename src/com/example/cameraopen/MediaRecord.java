package com.example.cameraopen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;

public class MediaRecord {
    public static final String TAG = "MediaRecord";
    private int isPauseRecordSupported; // 0 not checked; 1 not supported;
                                        // 2 X supported; 3 XS supported;
    private MediaRecorder mMediaRecorder;
    private boolean mMediaRecoderRecordingPaused = false;
    private Method mMethodPause;
    private int mIsGetProfileDetect; // 0 not checked; 1 not supported;
                                     // 2 X supported; 3 XS supported;
    private Method mMethodGetProfile;

    public CamcorderProfile getCameraProfile(int cameraId, int quality) {
        Method m = null;
        if (mIsGetProfileDetect == 0) {
            // adapt X
            try {
                m = CamcorderProfile.class.getDeclaredMethod("getMtk",
                        int.class, int.class);
                m.setAccessible(true);
                mIsGetProfileDetect = 2;
                // getMtk is a static method
                Log.v(TAG, "getCameraProfile adapt x");
            } catch (SecurityException e) {
                Log.e(TAG, "getMtk SecurityException");
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "getMtk NoSuchMethodException");
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "getMtk IllegalArgumentException");
            }

            // adapt XS
            try {
                Class<?> c = Class
                        .forName("com.mediatek.camcorder.CamcorderProfileEx");
                m = c.getDeclaredMethod("getProfile", int.class, int.class);
                mIsGetProfileDetect = 3;
                // getProfile is a static method
                Log.v(TAG, "getCameraProfile adapt xs");
            } catch (ClassNotFoundException e) {
                // e.printStackTrace();
            } catch (SecurityException e) {
                Log.e(TAG, "getProfile SecurityException");
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "getProfile NoSuchMethodException");
            }
            mIsGetProfileDetect = 1;
        }

        try {
            if (mIsGetProfileDetect == 2 || mIsGetProfileDetect == 3) {
                return (CamcorderProfile) m.invoke(null, cameraId, quality);
            }
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getMtk IllegalAccessException");
        } catch (InvocationTargetException e) {
            Log.e(TAG, "getMtk InvocationTargetException");
        }
        return CamcorderProfile.get(cameraId, quality);
    }

    // if mediaRecord changed, should recheck
    public boolean isPauseRecordSupported() {
        if (isPauseRecordSupported == 1) {
            return false;
        }
        if (isPauseRecordSupported == 2 || isPauseRecordSupported == 3) {
            return true;
        }
        // adapt X
        // mt6589_x_hike/frameworks/base/media/java/android/media/MediaRecorder.java
        // 1083 public void pause() throws IllegalStateException {
        try {
            mMethodPause = android.media.MediaRecorder.class.getDeclaredMethod(
                    "pause", (Class[]) null);
            isPauseRecordSupported = 2;
            return true;
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "MediaRecorder NoSuchMethodException ");
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "MediaRecorder IllegalArgumentException ");
        }

        // adapt XS
        // mt6592_xs_hike/mediatek/frameworks/base/media/java/com/mediatek/media/MediaRecorderEx.java
        // 94 public static void pause(MediaRecorder recorder) throws
        // IllegalStateException {
        try {
            Class<?> c = Class.forName("com.mediatek.media.MediaRecorderEx");
            mMethodPause = c.getDeclaredMethod("pause", MediaRecorder.class);
            isPauseRecordSupported = 3;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "MediaRecorderEx ClassNotFoundException");
        } catch (SecurityException e) {
            Log.e(TAG, "MediaRecorderEx SecurityException");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "MediaRecorderEx NoSuchMethodException");
        }
        isPauseRecordSupported = 1;
        return false;
    }

    public void pauseRecord() {
        if (!mMediaRecoderRecordingPaused && isPauseRecordSupported()) {
            try {
                if (isPauseRecordSupported == 2) {
                    mMethodPause.invoke(null, (Object[]) null);
                } else if (isPauseRecordSupported == 3) {
                    mMethodPause.invoke(null, mMediaRecorder);
                }
                mMediaRecoderRecordingPaused = true;
            } catch (IllegalArgumentException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
            return;
        }
    }

    public void resumeRecord() {
        if (mMediaRecoderRecordingPaused) {
            mMediaRecorder.start();
            mMediaRecoderRecordingPaused = false;
        }
    }

    public void setMediaRecorder(MediaRecorder mediaRecorder) {
        mMediaRecorder = mediaRecorder;
    }
}
