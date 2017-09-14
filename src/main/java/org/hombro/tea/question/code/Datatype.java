package org.hombro.tea.question.code;

/**
 * Created by nicolas on 8/13/2017.
 */
public enum Datatype {
    INTEGER,
    DOUBLE,
    STRING,
    BOOLEAN,
    NONE;

    public String forLanguage(Language l) {
        if (l.equals(Language.JAVA)) {
            String type;
            switch (this) {
                case DOUBLE:
                    type = "double";
                    break;
                case INTEGER:
                    type = "int";
                    break;
                case STRING:
                    type = "String";
                    break;
                case BOOLEAN:
                    type = "boolean";
                    break;
                default:
                    type = "void";
            }
            return type;
        }
        else{
            return "";
        }
    }
}
