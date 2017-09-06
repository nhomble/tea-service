package org.hombro.tea.question.code.test;

import org.hombro.tea.question.code.test.java.ClassUnderTestResponse;

import java.util.List;

/**
 * Created by nicolas on 9/6/2017.
 */
public class TestResponse {
    private final ClassUnderTestResponse classUnderTestResponse;
    private final TestResponseResult testResponseResult;

    public TestResponse(ClassUnderTestResponse classUnderTestResponse, TestResponseResult testResponseResult) {
        this.classUnderTestResponse = classUnderTestResponse;
        this.testResponseResult = testResponseResult;
    }

    public List<String> getPrints(){
        return classUnderTestResponse.getPrints();
    }

    public TestResponseResult getTestResponseResult(){
        return testResponseResult;
    }
}
