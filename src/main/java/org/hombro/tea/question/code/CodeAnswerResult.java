package org.hombro.tea.question.code;

import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class CodeAnswerResult {
    private List<Boolean> results;

    public List<Boolean> getResults(){
        return results;
    }

    public CodeAnswerResult setResults(List<Boolean> results){
        this.results = results;
        return this;
    }
}
