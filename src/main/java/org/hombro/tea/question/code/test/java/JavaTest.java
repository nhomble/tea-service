package org.hombro.tea.question.code.test.java;

import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;
import org.hombro.tea.question.code.TestCaseResult;
import org.hombro.tea.question.code.test.CodeTest;

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
            JavaSourceCode source = JavaSourceCode.createJavaSource(
                    answer.getCodingQuestion().getDatatype(),
                    answer.getCodingQuestion().getFunctionName(),
                    answer.getCodingQuestion().getArguments(),
                    answer.getCode(),
                    test.getIn()
            );
            List<String> argsList = (test.isPublic())? test.getIn() : Collections.emptyList();
            return new TestCaseResult()
                    .setResult(source.getResult(test.getOut()))
                    .setPublic(test.isPublic())
                    .setArgList(argsList)
                    .setPrints(source.classUnderTest.getPrints());
        }).collect(Collectors.toList());
        return new CodeAnswerResult().setResults(results);
    }
}
