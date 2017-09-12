package org.hombro.tea.question.code;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nicolas on 8/12/2017.
 */
public class CodingQuestion {
    private String name, functionName;
    private Language language;
    private List<Argument> arguments;
    private List<TestCase> tests;
    private String datatype;

    public String getEndpoint(){
        return (language.toString() + "/" + name).toLowerCase();
    }

    public String getBoilerPlate() {
        String type = getDatatype().forLanguage(getLanguage());
        return String.format("public %s %s(%s){\n}", type, getFunctionName(), getArguments().stream().map(a -> a.getDatatype().forLanguage(getLanguage()) + " " + a.getName()).collect(Collectors.joining(", ")));
    }

    public Datatype getDatatype() {
        return Datatype.valueOf(datatype.toUpperCase());
    }

    public CodingQuestion setDatatype(String datatype) {
        this.datatype = datatype;
        return this;
    }

    public List<TestCase> getTests() {
        return tests;
    }

    public CodingQuestion setTests(List<TestCase> tests) {
        this.tests = tests;
        return this;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public CodingQuestion setArguments(List<Argument> arguments) {
        this.arguments = arguments;
        return this;
    }

    public String getName() {
        return name;
    }

    public CodingQuestion setName(String name) {
        this.name = name;
        return this;
    }

    public String getFunctionName() {
        return functionName;
    }

    public CodingQuestion setFunctionName(String functionName) {
        this.functionName = functionName;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public CodingQuestion setLanguage(String language) {
        this.language = Language.valueOf(language.toUpperCase());
        return this;
    }
}
