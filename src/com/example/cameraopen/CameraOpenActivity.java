package com.example.cameraopen;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CameraOpenActivity extends Activity {
	Camera mCamera;
	int numberOfCameras;
	int cameraCurrentlyLocked;

	// The first rear facing camera
	int defaultCameraId;
	TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		
		mTextView = (TextView)findViewById(R.id.textview);
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
		ShowMethods.main("android.hardware.Camera");
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
