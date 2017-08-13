package org.hombro.tea.question.code.test;

/**
 * Created by nicolas on 8/13/2017.
 */
public interface ClassUnderTest {
    /**
     * I am meant to be overwritten by the runtime compiler
     * @return
     */
    default Object call(){
        return null;
    }
}
