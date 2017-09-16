package org.hombro.tea.question.code;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nicolas on 8/12/2017.
 */
public enum Language {
    JAVA;

    public List<String> supported(){
        return Arrays.stream(values()).map(Enum::name).collect(Collectors.toList());
    }
}
