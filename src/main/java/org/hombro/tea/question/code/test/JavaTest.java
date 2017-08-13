package org.hombro.tea.question.code.test;

import net.openhft.compiler.CompilerUtils;
import org.hombro.tea.question.code.Argument;
import org.hombro.tea.question.code.ArgumentType;
import org.hombro.tea.question.code.CodeAnswer;
import org.hombro.tea.question.code.CodeAnswerResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nicolas on 8/13/2017.
 */
public class JavaTest {
    public static JavaTest INSTANCE = new JavaTest();
    private final String className = "ClassUnderTest";
    private JavaTest() {
    }

    private String javaType(ArgumentType type) {
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

    private String generateMethod(String methodName, String args, String methodBody) {
        return String.format("private Object %s(%s){%s}", methodName, args, methodBody);
    }

    private String createJavaSource(String methodName, List<Argument> args, String methodBody, List<String> parameters) {
        String argString = args.stream().map(arg -> javaType(arg.getDatatype()) + " " + arg.getName()).collect(Collectors.joining(", "));
        String method = generateMethod(methodName, argString, methodBody);
        String paramString = String.join(", ", parameters);
        return String.format("" +
                "package %s;" +
                "public class %s {" +
                "%s" +
                "" +
                "   public Object call(){" +
                "       return %s(%s);" +
                "   }" +
                "}", this.getClass().getPackage().getName(), className, method, methodName, paramString);
    }

    private Boolean getResult(String out, String source) {
        try {
            String name = this.getClass().getPackage().getName() + "." + className;
            Class clazz = CompilerUtils.CACHED_COMPILER.loadFromJava(name, source);
            ClassUnderTest toTest = (ClassUnderTest) clazz.newInstance();
            Object result = toTest.call();
            return out.equals(result.toString());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public CodeAnswerResult isCorrect(CodeAnswer answer) {
        List<Boolean> results = answer.getCodingQuestion().getTests().stream().map(test -> {
            String source = createJavaSource(answer.getCodingQuestion().getFunctionName(), answer.getCodingQuestion().getArguments(), answer.getCode(), test.getIn());
            return getResult(test.getOut(), source);
        }).collect(Collectors.toList());
        return new CodeAnswerResult().setResults(results);
    }
}
