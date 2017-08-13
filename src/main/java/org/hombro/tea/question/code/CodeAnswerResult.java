package org.hombro.tea.question.code;

import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class CodeAnswerResult {
    private List<TestCaseResult> results;

    public List<TestCaseResult> getResults(){
        return results;
    }

    public CodeAnswerResult setResults(List<TestCaseResult> results){
        this.results = results;
        return this;
    }
}
