package org.hombro.tea.question.code.test;

/**
 * Created by nicolas on 8/17/2017.
 */
public enum TestResponseResult {
    SUCCESS,    // what you expect
    DIFFERENCE, // test ran, just equality difference
    THROW,      // exception thrown
    INVALID     // could not interpret/compile
}
