package org.hombro.tea.question.code.test.java;

import io.vertx.core.json.Json;
import org.hombro.tea.question.code.test.ClassUnderTestResponse;

import java.util.ArrayList;

/**
 * Created by nicolas on 9/4/2017.
 */
public abstract class ClassUnderTest {
    private ArrayList<String> prints = new ArrayList<>();

    abstract public Object call();

    public void print(Object o){
        prints.add(o.toString());
    }

    public String printCall(ClassUnderTestResponse response) {
        return Json.encode(response);
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
}
