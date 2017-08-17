package org.hombro.tea.question.code.test.java;

import net.openhft.compiler.CompilerUtils;
import org.hombro.tea.question.code.Datatype;
import org.hombro.tea.question.code.test.ClassUnderTest;
import org.hombro.tea.question.code.test.ClassUnderTestResponse;
import org.hombro.tea.question.code.test.SourceCode;
import org.hombro.tea.question.code.test.TestResponseResult;

import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class JavaSourceCode implements SourceCode {
    private static final String testingInterface = "ClassUnderTest";

    public final String source;
    public final String name;
    public final ClassUnderTest classUnderTest;

    private JavaSourceCode(String source, String name, ClassUnderTest classUnderTest) {
        this.source = source;
        this.name = name;
        this.classUnderTest = classUnderTest;
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
                "package %s;\n" +
                "public class %s extends %s{\n" +
                "   %s\n" +
                "\n" +
                "   public ClassUnderTestResponse call(){\n" +
                "       try {\n" +
                "           return ClassUnderTestResponse.fromTest(%s(%s));\n" +
                "       } catch (Exception e){\n" +
                "           return ClassUnderTestResponse.fromException(e);\n" +
                "       }\n" +
                "   }\n" +
                "}", ClassUnderTest.class.getPackage().getName(), className, testingInterface, method, methodName, paramString);

        String name = ClassUnderTest.class.getPackage().getName() + "." + className;
        try {
            Class clazz = CompilerUtils.CACHED_COMPILER.loadFromJava(name, source);
            ClassUnderTest toTest = (ClassUnderTest) clazz.newInstance();
            return new JavaSourceCode(source, className, toTest);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            return new JavaSourceCode(source, className, new ClassUnderTest());
        }
    }

    @Override
    public TestResponseResult getResult(Object expected) {
        ClassUnderTestResponse response = classUnderTest.call();
        if (!response.wasUnderstood())
            return TestResponseResult.INVALID;
        if (response.didThrow())
            return TestResponseResult.THROW;
        else {
            return (response.getReturn().toString().equals(expected.toString())) ? TestResponseResult.SUCCESS : TestResponseResult.DIFFERENCE;
        }
    }

    @Override
    public List<String> getPrints() {
        return classUnderTest.getPrints();
    }
}
