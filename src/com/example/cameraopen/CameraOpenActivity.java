package com.example.cameraopen;

import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.HashSet;

public class CameraOpenActivity extends Activity {
    public static final String TAG = "CameraOpenActivity";

    private static final String MTK_METHOD_setBurstShotNum = "public void Camera$setBurstShotNum(int)";
    Camera mCamera;
    int numberOfCameras;
    int cameraCurrentlyLocked;

    // The first rear facing camera
    int defaultCameraId;
    TextView mTextView;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        mTextView = (TextView) findViewById(R.id.textview);
        mListView = (ListView) findViewById(R.id.listview);
        // Find the total number of cameras available
        numberOfCameras = Camera.getNumberOfCameras();
        mTextView.setText(Integer.toString(numberOfCameras));
        // Find the ID of the default camera
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraId = i;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera_open, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Open the default i.e. the first rear facing camera.
        mCamera = Camera.open();
        cameraCurrentlyLocked = defaultCameraId;
        // List<String> alist = DetectMethods
        // .detectMethods("android.hardware.Camera");
        List<String> alist = DetectMethods
                .detectMethods2(android.hardware.Camera.class);

        alist = DetectMethods.detectMethods2(alist,
                android.hardware.Camera.Parameters.class);
        long start = System.currentTimeMillis();
        Log.v(TAG, "start:" + start);
        HashSet<String> hashset = DetectProperty
                .detectMethods(android.hardware.Camera.class);
        Log.v(TAG,
                "hashset.contains(addCallbackBuffer):"
                        + hashset
                                .contains("public final void addCallbackBuffer(byte[])"));
        hashset = DetectProperty
                .detectMethods(android.hardware.Camera.Parameters.class);
        Log.v(TAG, "hashset.contains(public void Camera$setBurstShotNum(int)):"
                + hashset.contains("public void Camera$setBurstShotNum(int)"));
        long end = System.currentTimeMillis();
        Log.v(TAG, "end - start:" + (end - start));

        // ///////////// test second way start
        start = System.currentTimeMillis();

        end = System.currentTimeMillis();
        // ///////////// test second way end
        ArrayAdapter<String> adt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alist);
        mListView.setAdapter(adt);
        mListView.setTextFilterEnabled(true);

        //printListString(mCamera.getParameters().getSupportedCaptureMode());

        new AsuraCamcorderProfile();

    }

    static void printListString(List<String> l) {
        Log.e(TAG, "printListString(" + l + ") ENTER");
        for (String s : l) {
            Log.e(TAG, s);
        }
        Log.e(TAG, "printListString(" + l + ") EXIT");
    }

    static void printListCharSequence(CharSequence[] l) {
        Log.e(TAG, "printListString(" + l + ") ENTER");
        for (CharSequence s : l) {
            Log.e(TAG, s.toString());
        }
        Log.e(TAG, "printListString(" + l + ") EXIT");
    }

    static void printListSize(List<Size> l) {
        Log.e(TAG, "printListString(" + l + ") ENTER");
        for (Size s : l) {
            Log.e(TAG, s.width + "*" + s.height);
        }
        Log.e(TAG, "printListString(" + l + ") EXIT");
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Because the Camera object is a shared resource, it's very
        // important to release it when the activity is paused.
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
