package com.example.cameraopen;



import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.regex.Pattern;

import android.util.Log;

public class DetectProperty {
    public static final String TAG = "DetectProperty";
    private static Pattern p = Pattern.compile("\\w+\\.");

    /**
     * detect a class filed, memthods, inner class, and append the result in
     * argument hashset; TODO only get the mtk append methods,fileds, inner
     * classes.
     * 
     * @param hashset
     *            : save the result
     * 
     * @param c
     *            :Android Framework contained class
     */
    public static void detectMethods(HashSet<String> hashset, Class c) {
        if (hashset == null) {
            return;
        }

        String s = null;
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            s = p.matcher(method.toString()).replaceAll("");
            Log.v(TAG, s);
            hashset.add(s);
        }

        Field[] fields = c.getFields();
        for (Field field : fields) {
            s = p.matcher(field.toString()).replaceAll("");
            Log.v(TAG, s);
            hashset.add(s);
        }

        Class[] classes = c.getClasses();
        for (Class classe : classes) {
            s = p.matcher(classe.toString()).replaceAll("");
            Log.v(TAG, s);
            hashset.add(s);
        }
    }

    /**
     * detect a class filed, memthods, inner class
     * 
     * @param c
     *            : Android Framework contained class
     * @return : HashSet<String> store class methods, fileds, inner classes
     * 
     */
    public static HashSet<String> detectMethods(Class c) {
        HashSet<String> hashset = new HashSet<String>();
        if (c == null) {
            // return empty object is better than null
            return hashset;
        }
        detectMethods(hashset, c);

        return hashset;
    }
}