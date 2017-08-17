package org.hombro.tea.question.code.test;

import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;

/**
 * Created by nicolas on 8/17/2017.
 */
public interface CodeTest {
    CodeAnswerResult isCorrect(CodeAnswer answer);
}
