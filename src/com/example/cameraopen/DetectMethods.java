package com.example.cameraopen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import android.util.Log;

public class DetectMethods {
    public static final String TAG = "DetectMethods";
    private static Pattern p = Pattern.compile("\\w+\\.");
    private static List<String> alist = new ArrayList<String>();

    public static List<String> detectMethods(String args) {
        String s = null;
        Log.v(TAG, "*******************detectMethods()*******************");
        alist.add("******detectMethods()*******");
        try {
            Class<?> c = Class.forName(args);
            Method[] methods = c.getMethods();
            Log.v(TAG, "======Methods=========");
            for (Method method : methods) {
                s = p.matcher(method.toString()).replaceAll("");
                Log.v(TAG, s);
                alist.add(s);
            }
            Log.v(TAG, "======Fields=========");
            Field[] fields = c.getFields();
            for (Field field : fields) {
                s = p.matcher(field.toString()).replaceAll("");
                Log.v(TAG, s);
                alist.add(s);
            }
            Log.v(TAG, "=========Annotations================");
            Annotation[] annotations = c.getAnnotations();
            for (Annotation annotation : annotations) {
                s = p.matcher(annotation.toString()).replaceAll("");
                Log.v(TAG, s);
                alist.add(s);
            }
            Log.v(TAG, "========= getClasses================");
            Class[] classes = c.getClasses();
            for (Class classe : classes) {
                s = p.matcher(classe.toString()).replaceAll("");
                Log.v(TAG, s);
                alist.add(s);
            }
            Log.v(TAG, "=========getDeclaredClasses================");
            Class[] declaredClasses = c.getDeclaredClasses();
            for (Class classe : declaredClasses) {
                s = p.matcher(classe.toString()).replaceAll("");
                Log.v(TAG, s);
                alist.add(s);
            }
        } catch (ClassNotFoundException e) {
            Log.v(TAG, "No such class: " + e);
        }
        return alist;
    }

    public static List<String> detectMethods2(List<String> ls, Class c) {
        alist = ls;
        detectMethods2(c);
        return alist;
    }
    public static List<String> detectMethods2(Class c) {

        String s = null;
        alist.add("******detectMethods2()*******");
        Log.v(TAG, "*******************detectMethods2()*******************");
        // try {
        // Class<?> c = Class.forName(args);

        Method[] methods = c.getMethods();
        Log.v(TAG, "======Methods=========");
        for (Method method : methods) {
            s = p.matcher(method.toString()).replaceAll("");
            Log.v(TAG, s);
            alist.add(s);
        }
        Log.v(TAG, "======Fields=========");
        Field[] fields = c.getFields();
        for (Field field : fields) {
            s = p.matcher(field.toString()).replaceAll("");
            Log.v(TAG, s);
            alist.add(s);
        }
        Log.v(TAG, "======getDeclaredFields=========");
        Field[] decfields = c.getDeclaredFields();
        for (Field decfield : decfields) {
            s = p.matcher(decfield.toString()).replaceAll("");
            Log.v(TAG, s);
            alist.add(s);
        }
        Log.v(TAG, "=========Annotations================");
        Annotation[] annotations = c.getAnnotations();
        for (Annotation annotation : annotations) {
            s = p.matcher(annotation.toString()).replaceAll("");
            Log.v(TAG, s);
            alist.add(s);
        }
        Log.v(TAG, "========= getClasses================");
        Class[] classes = c.getClasses();
        for (Class classe : classes) {
            s = p.matcher(classe.toString()).replaceAll("");
            Log.v(TAG, s);
            alist.add(s);
        }
        Log.v(TAG, "=========getDeclaredClasses================");
        Class[] declaredClasses = c.getDeclaredClasses();
        for (Class classe : declaredClasses) {
            s = p.matcher(classe.toString()).replaceAll("");
            Log.v(TAG, s);
            alist.add(s);
        }
        // } catch (ClassNotFoundException e) {
        // Log.v(TAG, "No such class: " + e);
        // }
        return alist;
    }
}