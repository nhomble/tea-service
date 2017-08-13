package org.hombro.tea.question.code.test;

import net.openhft.compiler.CompilerUtils;
import org.hombro.tea.question.code.Argument;
import org.hombro.tea.question.code.Datatype;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nicolas on 8/13/2017.
 */
public class JavaSourceCode {
    private static final String testingInterface = "ClassUnderTest";

    public final String source;
    public final String name;

    private JavaSourceCode(String source, String name) {
        this.source = source;
        this.name = name;
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

    private static String generateMethod(Datatype type, String methodName, String args, String methodBody) {
        return String.format("private %s %s(%s){%s}", javaType(type), methodName, args, methodBody);
    }

    private static String className(){
        return "ClassUnderTest" + (int) (Math.random() * 100000); // TODO confirm if there is a better way;
    }

    public static JavaSourceCode createJavaSource(Datatype type, String methodName, List<Argument> args, String methodBody, List<String> parameters) {
        String className = className();
        String argString = args.stream().map(arg -> javaType(arg.getDatatype()) + " " + arg.getName()).collect(Collectors.joining(", "));
        String method = generateMethod(type, methodName, argString, methodBody);
        String paramString = String.join(", ", parameters);
        String source = String.format("" +
                "package %s;\n" +
                "class %s implements %s{\n" +
                "   %s\n" +
                "\n" +
                "   public %s call(){\n" +
                "       return %s(%s);\n" +
                "   }\n" +
                "}", JavaSourceCode.class.getPackage().getName(), className, testingInterface, method, javaType(type), methodName, paramString);
        return new JavaSourceCode(source, className);
    }

    public Boolean getResult(String out, JavaSourceCode code) {
        try {
            String name = this.getClass().getPackage().getName() + "." + code.name;
            Class clazz = CompilerUtils.CACHED_COMPILER.loadFromJava(name, code.source);
            ClassUnderTest toTest = (ClassUnderTest) clazz.newInstance();
            Object result = toTest.call();
            return out.equals(result.toString());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
