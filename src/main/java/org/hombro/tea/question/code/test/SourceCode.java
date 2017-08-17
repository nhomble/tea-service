package org.hombro.tea.question.code.test;

import java.util.Collections;
import java.util.List;

/**
 * Created by nicolas on 8/17/2017.
 */
public interface SourceCode {
    default List<String> getPrints() {
        return Collections.emptyList();
    }

    public abstract TestResponseResult getResult(Object o);
}
