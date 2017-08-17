package org.hombro.tea.question.test;

import org.hombro.tea.question.code.Argument;
import org.hombro.tea.question.code.Datatype;
import org.hombro.tea.question.code.test.TestResponseResult;
import org.hombro.tea.question.code.test.java.JavaSourceCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nicolas on 8/15/2017.
 */
@RunWith(Parameterized.class)
public class JavaSourceCodeSnippets {
    private Datatype type;
    private String methodName;
    private List<Argument> argumentList;
    private String solution;
    private List<String> params;
    private String expected;
    private TestResponseResult testResponseResult;

    public JavaSourceCodeSnippets(Datatype type, String methodName, List<Argument> argumentList, String solution, List<String> params, String expected, TestResponseResult testResponseResult) {
        this.type = type;
        this.methodName = methodName;
        this.argumentList = argumentList;
        this.solution = solution;
        this.params = params;
        this.expected = expected;
        this.testResponseResult = testResponseResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        List<Argument> addArguments = Arrays.asList(new Argument().setArgumentName("x").setArgumentType("Integer"), new Argument().setArgumentName("y").setArgumentType("Integer"));
        List<Argument> factorialArgs = Collections.singletonList(new Argument().setArgumentName("n").setArgumentType("Integer"));
        return Arrays.asList(new Object[][]{
                {Datatype.INTEGER, "add", addArguments, "return x + y;", Arrays.asList("1", "1"), "2", TestResponseResult.SUCCESS},
                {Datatype.INTEGER, "add", addArguments, "return x + y;", Arrays.asList("1", "2"), "3", TestResponseResult.SUCCESS},
                {Datatype.INTEGER, "add", addArguments, "return x + x;", Arrays.asList("4", "1"), "8", TestResponseResult.SUCCESS},
                {Datatype.INTEGER, "add", addArguments, "return 0;", Arrays.asList("1", "1"), "2", TestResponseResult.DIFFERENCE},
                {Datatype.INTEGER, "factorial", factorialArgs, "return ( n == 0 || n == 1 )? 1 : n * factorial( n - 1 );", Collections.singletonList("0"), "1", TestResponseResult.SUCCESS},
                {Datatype.INTEGER, "factorial", factorialArgs, "return ( n == 0 || n == 1 )? 1 : n * factorial( n - 1 );", Collections.singletonList("1"), "1", TestResponseResult.SUCCESS},
                {Datatype.INTEGER, "factorial", factorialArgs, "return ( n == 0 || n == 1 )? 1 : n * factorial( n - 1 );", Collections.singletonList("5"), "120", TestResponseResult.SUCCESS},
                {Datatype.INTEGER, "factorial", factorialArgs, "throw new RuntimeException();", Collections.singletonList("5"), "120", TestResponseResult.THROW}
        });
    }

    @Test
    public void test() {
        JavaSourceCode sourceCode = JavaSourceCode.createJavaSource(type, methodName, argumentList, solution, params);
        assertEquals(sourceCode.getResult(expected), testResponseResult);
    }
}
