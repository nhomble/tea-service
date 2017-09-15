package org.hombro.tea.question.code.test.java;

import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;
import org.hombro.tea.question.code.TestCaseResult;
import org.hombro.tea.question.code.test.CodeTest;
import org.hombro.tea.question.code.test.SourceCode;
import org.hombro.tea.question.code.test.TestResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nicolas on 8/13/2017.
 */
public class JavaCodeTest implements CodeTest {
    public static JavaCodeTest INSTANCE = new JavaCodeTest();

    private JavaCodeTest() {
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
            TestResponse response = source.getResult(test.getOut());
            return new TestCaseResult()
                    .setResult(response.getTestResponseResult())
                    .setPublic(test.isPublic())
                    .setArgList(argsList)
                    .setPrints(response.getPrints())
                    .setExpected(test.getOut());
        }).collect(Collectors.toList());
        return new CodeAnswerResult().setResults(results);
    }
}
