package org.hombro.tea.question.code;

import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class TestCaseResult {
    private Boolean result;
    private Boolean isPublic;
    private List<String> argList;
    private List<String> prints;

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

    public Boolean getResult() {
        return result;
    }

    public TestCaseResult setResult(Boolean result) {
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
