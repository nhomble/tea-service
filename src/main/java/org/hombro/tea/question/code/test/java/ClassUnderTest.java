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

    // up to me to implement in my runtime class
    abstract public Object call();

    public void print(Object o) {
        prints.add(o.toString());
    }

    private String printCall(ClassUnderTestResponse response) {
        return Json.encode(response);
    }

    private static ClassUnderTest getClassUnderTest(String path, String className) {
        File file = new File(path);
        try {
            URL[] urls = new URL[]{file.toURI().toURL()};
            ClassLoader cl = new URLClassLoader(urls);
            return (ClassUnderTest) cl.loadClass(className).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | MalformedURLException e) {
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
        ClassUnderTest classUnderTest = getClassUnderTest(args[0], args[1]);
        classUnderTest.letsDoIt();
    }
}
