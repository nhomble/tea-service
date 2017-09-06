package org.hombro.tea.question.code.test.java;

import io.vertx.core.json.Json;
import org.hombro.tea.question.code.test.SourceCode;
import org.hombro.tea.question.code.test.TestResponse;
import org.hombro.tea.question.code.test.TestResponseResult;

import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class JavaSourceCode implements SourceCode {
    private static final String testHarness = ClassUnderTest.class.getSimpleName();
    private final JvmSandbox jvmSandbox;
    public final String source;
    public final String name;

    private JavaSourceCode(String source, String name) {
        this.source = source;
        this.name = name;
        jvmSandbox = new JvmSandbox();
    }

    private static String className() {
        return testHarness + System.nanoTime();
    }

    public static SourceCode createJavaSource(String methodName, String method, List<String> parameters) {
        String className = className();
        String paramString = String.join(", ", parameters);
        String source = String.format("" +
                "import org.hombro.tea.question.code.test.java.ClassUnderTest;\n" +
                "public class %s extends %s {\n" +
                "   \n" +
                "   // Here is your code\n" +
                "   %s\n" +
                "   \n" +
                "" +
                "   // Here is my expected interface\n" +
                "   public Object call(){\n" +
                "       return %s(%s);\n" +
                "   }\n" +
                "}", className, testHarness, method, methodName, paramString);

        return new JavaSourceCode(source, className);
    }

    @Override
    public TestResponse getResult(Object expected) {
        ClassUnderTestResponse response;
        String out = jvmSandbox.run(name, source);
        if (out.isEmpty())
            response = ClassUnderTestResponse.noCompilation();
        else
            response = Json.decodeValue(out, ClassUnderTestResponse.class);

        TestResponseResult result;
        if (!response.isUnderstood())
            result = TestResponseResult.INVALID;
        else if (response.isThrew())
            result = TestResponseResult.THROW;
        else {
            result = (response.getOutput().toString().equals(expected.toString())) ? TestResponseResult.SUCCESS : TestResponseResult.DIFFERENCE;
        }
        return new TestResponse(response, result);
    }
}
