package com.example.cameraopen;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import android.util.Log;

public class ShowMethods {
	public static final String TAG = "ShowMethods";
	private static String usage = "usage:\n"
			+ "ShowMethods qualified.class.name\n"
			+ "To show all methods in class or:\n"
			+ "ShowMethods qualified.class.name word\n"
			+ "To search for methods involving 'word'";
	private static Pattern p = Pattern.compile("\\w+\\.");

	public static void main(String args) {
		int lines = 0;
		try {
			Class<?> c = Class.forName(args);
			Method[] methods = c.getMethods();
			Constructor[] ctors = c.getConstructors();
			for (Method method : methods)
				Log.v(TAG, p.matcher(method.toString()).replaceAll(""));
			Log.v(TAG, "=======================================");
			for (Constructor ctor : ctors)
				Log.v(TAG, p.matcher(ctor.toString()).replaceAll(""));
			lines = methods.length + ctors.length;
		} catch (ClassNotFoundException e) {
			Log.v(TAG, "No such class: " + e);
		}
	}
}
