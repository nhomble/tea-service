package org.hombro.tea.question.code.test.java;

import io.vertx.core.json.Json;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by nicolas on 9/4/2017.
 */
public abstract class ClassUnderTest {
    private static final long TIMEOUT = 2 * 1000;
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

    public String letsDoIt() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        ClassUnderTestResponse response;
        try {
            Callable<Object> callable = this::call;
            final Future<Object> future = executorService.submit(callable);
            executorService.schedule((Runnable) () -> future.cancel(true),TIMEOUT, TimeUnit.MILLISECONDS);
            response = ClassUnderTestResponse.fromTest(future.get());
            executorService.shutdownNow();
        } catch (Exception e) {
            response = ClassUnderTestResponse.fromException(e);
        }
        response.setPrints(prints);
        return printCall(response);
    }

    public static void main(String[] args) {
        ClassUnderTest classUnderTest = getClassUnderTest(args[0], args[1]);
        System.out.println(classUnderTest.letsDoIt());
        System.exit(0); // do not linger, cause I am waiting
    }
}
