package org.hombro.tea.question.code;

import java.util.List;

/**
 * Created by nicolas on 8/13/2017.
 */
public class TestCase {
    private List<String> in;
    private String out;
    private String visibility;

    public Boolean isPublic(){
        return getVisibility() == Visibility.PUBLIC;
    }

    public Visibility getVisibility(){
        return Visibility.valueOf(visibility.toUpperCase());
    }

    public TestCase setVisibility(String visibility){
        this.visibility = visibility;
        return this;
    }

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
