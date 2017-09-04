package org.hombro.tea.question.code.test.java;

import io.vertx.core.json.Json;
import org.hombro.tea.question.code.Datatype;
import org.hombro.tea.question.code.test.ClassUnderTestResponse;
import org.hombro.tea.question.code.test.SourceCode;
import org.hombro.tea.question.code.test.TestResponseResult;

import java.util.Collections;
import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class JavaSourceCode implements SourceCode {
    private static final String testingInterface = "ClassUnderTest";
    private final JvmSandbox jvmSandbox;
    public final String source;
    public final String name;

    private JavaSourceCode(String source, String name) {
        this.source = source;
        this.name = name;
        jvmSandbox = new JvmSandbox();
    }

    private static String javaType(Datatype type) {
        switch (type) {
            case DOUBLE:
                return "Double";
            case INTEGER:
                return "Integer";
            case STRING:
                return "String";
            case NONE:
                return "void";
            default:
                throw new RuntimeException("unhandled type");
        }
    }

    private static String className() {
        return "ClassUnderTest" + (int) (Math.random() * 100000); // TODO confirm if there is a better way;
    }

    public static SourceCode createJavaSource(String methodName, String method, List<String> parameters) {
        String className = className();
        String paramString = String.join(", ", parameters);
        String source = String.format("" +
                "import org.hombro.tea.question.code.test.java.ClassUnderTest;\n" +
                "public class %s extends ClassUnderTest {\n" +
                "   \n" +
                "   // Here is your code\n" +
                "   %s\n" +
                "   \n" +
                "" +
                "   // Here is my expected interface\n" +
                "   public Object call(){\n" +
                "       return %s(%s);\n" +
                "   }\n" +
                "" +
                "   public static void main(String... args){\n" +
                "       %s mySelf = new %s();\n" +
                "       mySelf.letsDoIt();\n" +
                "   }\n" +
                "}", className, method, methodName, paramString, className, className);

        return new JavaSourceCode(source, className);
    }

    @Override
    public TestResponseResult getResult(Object expected) {
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
        return result;
    }

    @Override
    public List<String> getPrints() {
        return Collections.emptyList();
    }
}
