package org.hombro.tea.question.code.test;

import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;

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
        List<Boolean> results = answer.getCodingQuestion().getTests().stream().map(test -> {
            JavaSourceCode source = JavaSourceCode.createJavaSource(
                    answer.getCodingQuestion().getDatatype(),
                    answer.getCodingQuestion().getFunctionName(),
                    answer.getCodingQuestion().getArguments(),
                    answer.getCode(),
                    test.getIn()
            );
            return source.getResult(test.getOut(), source);
        }).collect(Collectors.toList());
        return new CodeAnswerResult().setResults(results);
    }
}
