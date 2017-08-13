package org.hombro.tea.question.code;

import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class TestCase {
    private List<String> in;
    private String out;

    public List<String> getIn() {
        return in;
    }

    public TestCase setIn(List<String> in) {
        this.in = in;
        return this;
    }

    public String getOut() {
        return out;
    }

    public TestCase setOut(String out) {
        this.out = out;
        return this;
    }
}
