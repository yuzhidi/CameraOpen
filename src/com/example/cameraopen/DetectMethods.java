package com.example.cameraopen;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.util.Log;

public class DetectMethods {
	public static final String TAG = "ShowMethods";
	private static Pattern p = Pattern.compile("\\w+\\.");

	public static List<String> detectMethods(String args) {
		ArrayList<String> alist = new ArrayList<String>();
		String s = null;
		try {
			Class<?> c = Class.forName(args);
			Method[] methods = c.getMethods();
			for (Method method : methods) {
				s = p.matcher(method.toString()).replaceAll("");
				Log.v(TAG, s);
				alist.add(s);
			}
		} catch (ClassNotFoundException e) {
			Log.v(TAG, "No such class: " + e);
		}
		return alist;
	}
}