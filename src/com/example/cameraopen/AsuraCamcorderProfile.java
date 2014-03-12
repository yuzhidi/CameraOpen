package com.example.cameraopen;

import java.util.HashSet;

import android.media.CamcorderProfile;
import android.util.Log;

public class AsuraCamcorderProfile {
    public static final String TAG = "AsuraCamcorderProfile";

    private static final String FIELDS_QUALITY_MTK_1080P = "public static final int QUALITY_MTK_1080P";
    private static final String FIELDS_QUALITY_MTK_FINE = "public static final int QUALITY_MTK_FINE";
    private static final String FIELDS_QUALITY_MTK_HIGH = "public static final int QUALITY_MTK_HIGH";
    private static final String FIELDS_QUALITY_MTK_LOW = "public static final int QUALITY_MTK_LOW";
    private static final String FIELDS_QUALITY_MTK_MEDIUM = "public static final int QUALITY_MTK_MEDIUM";


    private static HashSet<String> mProperties = new HashSet<String>();

    // first time call AsuraCamcorderProfile.XXX() will do detectProfile() and
    // cost 14 - 22 ms
    static {
        detectProfile();
    }

    /**
     * detect camera profile
     */
    private static void detectProfile() {
        long start = System.currentTimeMillis();
        Log.v(TAG, "detectProfile ENTER," + start);
        DetectProperty.detectMethods(mProperties,
                android.media.CamcorderProfile.class);
        long duration = System.currentTimeMillis() - start;
        Log.v(TAG, "detectProfile EXIT," + duration);
    }

    /**
     * Quality level corresponding to the lowest available resolution.
     */
    public static int QUALITY_LOW() {
        if (mProperties.contains(FIELDS_QUALITY_MTK_LOW)) {
            return CamcorderProfile.QUALITY_MTK_LOW;
        }
        return CamcorderProfile.QUALITY_LOW;
    }

    public static int QUALITY_MTK_MEDIUM() {
        if (mProperties.contains(FIELDS_QUALITY_MTK_MEDIUM)) {
            return CamcorderProfile.QUALITY_MTK_MEDIUM;
        }
        // as android do NOT have medium, use -1.
        return -1;
    }

    /**
     * Quality level corresponding to the highest available resolution.
     */
    public static int QUALITY_HIGH() {
        if (mProperties.contains(FIELDS_QUALITY_MTK_HIGH)) {
            return CamcorderProfile.QUALITY_MTK_HIGH;
        }
        return -1;
    }

    public static int QUALITY_MTK_FINE() {
        if (mProperties.contains(FIELDS_QUALITY_MTK_FINE)) {
            return CamcorderProfile.QUALITY_MTK_FINE;
        }
        // as android do NOT have medium, use -1.
        return -1;
    }

    /**
     * Quality level corresponding to the qcif (176 x 144) resolution.
     */
    public static int QUALITY_QCIF() {
        return CamcorderProfile.QUALITY_QCIF;
    }

    /**
     * Quality level corresponding to the cif (352 x 288) resolution.
     */
    public static int QUALITY_CIF() {
        return CamcorderProfile.QUALITY_CIF;
    }

    /**
     * Quality level corresponding to the 480p (720 x 480) resolution. Note that
     * the horizontal resolution for 480p can also be other values, such as 640
     * or 704, instead of 720.(Since: API Level 11)
     */
    public static int QUALITY_480P() {
        return CamcorderProfile.QUALITY_480P;
    }

    /**
     * Quality level corresponding to the 720p (1280 x 720) resolution.
     */
    public static int QUALITY_720P() {
        return CamcorderProfile.QUALITY_720P;
    }

    /**
     * Quality level corresponding to the 1080p (1920 x 1080) resolution. Note
     * that the vertical resolution for 1080p can also be 1088, instead of 1080
     * (used by some vendors to avoid cropping during video playback).(Since:
     * API Level 11)
     */
    public static int QUALITY_1080P() {
        if (mProperties.contains(FIELDS_QUALITY_MTK_1080P)) {
            return CamcorderProfile.QUALITY_MTK_1080P;
        }
        return CamcorderProfile.QUALITY_1080P;
    }

    /**
     * Quality level corresponding to the QVGA (320x240) resolution.
     */
    public static int QUALITY_QVGA() {
        return CamcorderProfile.QUALITY_QVGA;
    }
}
