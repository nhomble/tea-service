package org.hombro.tea.question.code;

/**
 * Created by nicolas on 8/13/2017.
 */
public class Argument {
    private String datatype;
    private String name;

    public Argument setArgumentType(String type){
        this.datatype = type;
        return this;
    }

    public Datatype getDatatype(){
        return Datatype.valueOf(datatype.toUpperCase());
    }

    public Argument setArgumentName(String name){
        this.name = name;
        return this;
    }

    public String getName(){
        return name;
    }
}
