package org.hombro.tea.question.code.test.java;

import io.vertx.core.json.Json;
import org.hombro.tea.question.code.test.ClassUnderTestResponse;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * Created by nicolas on 9/4/2017.
 */
public abstract class ClassUnderTest {
    private ArrayList<String> prints = new ArrayList<>();

    abstract public Object call();

    public void print(Object o) {
        prints.add(o.toString());
    }

    public String printCall(ClassUnderTestResponse response) {
        return Json.encode(response);
    }

    public static Class getClassUnderTest(String path, String className) {
        System.out.println("Path: " + path);
        System.out.println("Class: " + className);
        File file = new File(path);
        try {
            URL[] urls = new URL[]{file.toURI().toURL()};
            ClassLoader cl = new URLClassLoader(urls);
            return cl.loadClass(className);
        } catch (ClassNotFoundException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void letsDoIt() {
        ClassUnderTestResponse response;
        try {
            Object o = call();
            response = ClassUnderTestResponse.fromTest(o);
        } catch (Exception e) {
            response = ClassUnderTestResponse.fromException(e);
        }
        response.setPrints(prints);
        System.out.println(printCall(response));
    }

    public static void main(String[] args) {
        System.out.println("Found: " + args.length + " arguments");
        Class clazz = getClassUnderTest(args[0], args[1]);
        try {
            ClassUnderTest classUnderTest = (ClassUnderTest) clazz.newInstance();
            classUnderTest.letsDoIt();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
