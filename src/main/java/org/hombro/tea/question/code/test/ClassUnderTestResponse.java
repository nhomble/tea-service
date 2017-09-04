package org.hombro.tea.question.code.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 8/15/2017.
 */
public class ClassUnderTestResponse {
    private Object output = null;
    private boolean understood = false;
    private boolean threw = false;
    private String exceptionString = "";
    private List<String> prints = new ArrayList<>();

    public static ClassUnderTestResponse fromException(Exception e) {
        ClassUnderTestResponse c = new ClassUnderTestResponse();
        c.understood = true;
        c.threw = true;
        c.exceptionString = e.getMessage();
        return c;
    }

    public static ClassUnderTestResponse noCompilation() {
        ClassUnderTestResponse c = new ClassUnderTestResponse();
        return c;
    }

    public static ClassUnderTestResponse fromTest(Object o) {
        ClassUnderTestResponse c = new ClassUnderTestResponse();
        c.output = o;
        c.understood = true;
        return c;
    }

    public Object getOutput() {
        return output;
    }

    public ClassUnderTestResponse setOutput(Object output) {
        this.output = output;
        return this;
    }

    public boolean isUnderstood() {
        return understood;
    }

    public ClassUnderTestResponse setUnderstood(boolean understood) {
        this.understood = understood;
        return this;
    }

    public boolean isThrew() {
        return threw;
    }

    public ClassUnderTestResponse setThrew(boolean threw) {
        this.threw = threw;
        return this;
    }

    public String getExceptionString() {
        return exceptionString;
    }

    public ClassUnderTestResponse setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
        return this;
    }

    public List<String> getPrints() {
        return prints;
    }

    public ClassUnderTestResponse setPrints(List<String> prints) {
        this.prints = prints;
        return this;
    }
}
