package org.hombro.tea.question.code;

import org.hombro.tea.question.code.test.TestResponseResult;

import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class TestCaseResult {
    private TestResponseResult result;
    private Boolean isPublic;
    private List<String> argList;
    private List<String> prints;
    private String expected;

    public String getExpected() {
        return expected;
    }

    public TestCaseResult setExpected(String expected) {
        this.expected = expected;
        return this;
    }

    public List<String> getPrints() {
        return prints;
    }

    public TestCaseResult setPrints(List<String> prints) {
        this.prints = prints;
        return this;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public TestCaseResult setPublic(Boolean aPublic) {
        isPublic = aPublic;
        return this;
    }

    public TestResponseResult getResult() {
        return result;
    }

    public TestCaseResult setResult(TestResponseResult result) {
        this.result = result;
        return this;
    }

    public List<String> getArgList() {
        return argList;
    }

    public TestCaseResult setArgList(List<String> argList) {
        this.argList = argList;
        return this;
    }
}
