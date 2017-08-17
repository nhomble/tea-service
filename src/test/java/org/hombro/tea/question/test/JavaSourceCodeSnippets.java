package org.hombro.tea.question.test;

import org.hombro.tea.question.code.test.SourceCode;
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
    private String methodName;
    private String solution;
    private List<String> params;
    private String expected;
    private TestResponseResult testResponseResult;

    public JavaSourceCodeSnippets(String methodName, String solution, List<String> params, String expected, TestResponseResult testResponseResult) {
        this.methodName = methodName;
        this.solution = solution;
        this.params = params;
        this.expected = expected;
        this.testResponseResult = testResponseResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        String addFormat = "public int add(int x, int y){ %s }";
        String factorialFormat = "public int factorial(int n){ %s }";
        return Arrays.asList(new Object[][]{
                {"add", "I am not java code", Arrays.asList("1", "1"), "2", TestResponseResult.INVALID},
                {"add", String.format(addFormat, "return x + y;"), Arrays.asList("1", "1"), "2", TestResponseResult.SUCCESS},
                {"add", String.format(addFormat, "return x + y;"), Arrays.asList("1", "2"), "3", TestResponseResult.SUCCESS},
                {"add", String.format(addFormat, "return x + x;"), Arrays.asList("4", "1"), "8", TestResponseResult.SUCCESS},
                {"add", String.format(addFormat, "return 0;"), Arrays.asList("1", "1"), "2", TestResponseResult.DIFFERENCE},
                {"factorial", String.format(factorialFormat, "return ( n == 0 || n == 1 )? 1 : n * factorial( n - 1 );"), Collections.singletonList("0"), "1", TestResponseResult.SUCCESS},
                {"factorial", String.format(factorialFormat, "return ( n == 0 || n == 1 )? 1 : n * factorial( n - 1 );"), Collections.singletonList("1"), "1", TestResponseResult.SUCCESS},
                {"factorial", String.format(factorialFormat, "return ( n == 0 || n == 1 )? 1 : n * factorial( n - 1 );"), Collections.singletonList("5"), "120", TestResponseResult.SUCCESS},
                {"factorial", String.format(factorialFormat, "throw new RuntimeException();"), Collections.singletonList("5"), "120", TestResponseResult.THROW},
                {"factorial", String.format(factorialFormat, "return ( n == 0 || n == 1 )? 1 : n * factorial( n - 1 );") + "private final Object randomField = null;", Collections.singletonList("5"), "120", TestResponseResult.SUCCESS},
        });
    }

    @Test
    public void test() {
        SourceCode sourceCode = JavaSourceCode.createJavaSource(methodName, solution, params);
        assertEquals(sourceCode.getResult(expected), testResponseResult);
    }
}
