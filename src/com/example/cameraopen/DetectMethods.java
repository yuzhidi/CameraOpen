package com.example.cameraopen;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

import android.util.Log;

public class DetectMethods {
	public static final String TAG = "ShowMethods";
	private static Pattern p = Pattern.compile("\\w+\\.");

	public static void detectMethods(String args) {
		try {
			Class<?> c = Class.forName(args);
			Method[] methods = c.getMethods();

			for (Method method : methods) {
				
				Log.v(TAG, p.matcher(method.toString()).replaceAll(""));
			}
		} catch (ClassNotFoundException e) {
			Log.v(TAG, "No such class: " + e);
		}
	}
}
