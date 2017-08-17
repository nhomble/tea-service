package org.hombro.tea.question.code.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nicolas on 8/13/2017.
 */
public abstract class ClassUnderTest {
    private final List<String> prints = new ArrayList<>();

    /**
     * I am meant to be overwritten by the runtime compiler
     *
     * @return
     */
    public ClassUnderTestResponse call() {
        return ClassUnderTestResponse.DEFAULT;
    }

    public void print(Object... o) {
        prints.add(Arrays.stream(o).map(Object::toString).collect(Collectors.joining(" ")));
    }

    public List<String> getPrints() {
        return prints;
    }
}
