package org.hombro.tea.question.code.test.java;

import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;
import org.hombro.tea.question.code.TestCaseResult;
import org.hombro.tea.question.code.test.CodeTest;
import org.hombro.tea.question.code.test.SourceCode;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nicolas on 8/13/2017.
 */
public class JavaTest implements CodeTest {
    public static JavaTest INSTANCE = new JavaTest();

    private JavaTest() {
    }

    @Override
    public CodeAnswerResult isCorrect(CodeAnswer answer) {
        List<TestCaseResult> results = answer.getCodingQuestion().getTests().stream().map(test -> {
            SourceCode source = JavaSourceCode.createJavaSource(
                    answer.getCodingQuestion().getFunctionName(),
                    answer.getCode(),
                    test.getIn()
            );
            List<String> argsList = (test.isPublic())? test.getIn() : Collections.emptyList();
            return new TestCaseResult()
                    .setResult(source.getResult(test.getOut()))
                    .setPublic(test.isPublic())
                    .setArgList(argsList)
                    .setPrints(source.getPrints());
        }).collect(Collectors.toList());
        return new CodeAnswerResult().setResults(results);
    }
}
