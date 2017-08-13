package org.hombro.tea.question.code.test;

import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;
import org.hombro.tea.question.code.TestCaseResult;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nicolas on 8/13/2017.
 */
public class JavaTest {
    public static JavaTest INSTANCE = new JavaTest();


    private JavaTest() {
    }

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
                    .setResult(source.getResult(test.getOut(), source))
                    .setPublic(test.isPublic())
                    .setArgList(argsList);
        }).collect(Collectors.toList());
        return new CodeAnswerResult().setResults(results);
    }
}
