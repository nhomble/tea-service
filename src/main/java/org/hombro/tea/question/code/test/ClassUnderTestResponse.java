package org.hombro.tea.question.code.test;

import java.util.Optional;

/**
 * Created by nicolas on 8/15/2017.
 */
public class ClassUnderTestResponse {
    public static final ClassUnderTestResponse DEFAULT = new ClassUnderTestResponse(false, Optional.empty(), Optional.empty());

    private final boolean wasUnderstood;
    private final Optional<Object> ret;
    private final Optional<Exception> exception;

    private ClassUnderTestResponse(boolean wasUnderstood, Optional<Object> ret, Optional<Exception> exception) {
        this.wasUnderstood = wasUnderstood;
        this.ret = ret;
        this.exception = exception;
    }

    public static ClassUnderTestResponse fromException(Exception e){
        return new ClassUnderTestResponse(true, Optional.empty(), Optional.of(e));
    }

    public static ClassUnderTestResponse fromTest(Object o){
        return new ClassUnderTestResponse(true, Optional.of(o), Optional.empty());
    }

    public boolean didThrow(){
        return exception.isPresent();
    }

    public boolean didReturn(){
        return ret.isPresent();
    }

    public Object getReturn(){
        if(!didReturn())
            throw new RuntimeException("Guard with didReturn()");
        return ret.get();
    }

    public boolean wasUnderstood(){
        return wasUnderstood;
    }
}
