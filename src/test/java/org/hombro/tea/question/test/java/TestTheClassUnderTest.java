package org.hombro.tea.question.test.java;

import org.hombro.tea.question.code.test.java.ClassUnderTest;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nicolas on 9/6/2017.
 */
public class TestTheClassUnderTest {

    @Test
    public void return1() {
        class ChildClass extends ClassUnderTest {
            public Object call() {
                return 1;
            }
        }
        ChildClass c = new ChildClass();
        assertEquals("{\"output\":1,\"understood\":true,\"threw\":false,\"exceptionString\":\"\",\"prints\":[]}", c.letsDoIt());
    }

    @Test
    public void withPrints(){
        class ChildClass extends ClassUnderTest {
            public Object call(){
                print("print");
                return 1;
            }
        }
        ChildClass c = new ChildClass();
        assertEquals("{\"output\":1,\"understood\":true,\"threw\":false,\"exceptionString\":\"\",\"prints\":[\"print\"]}", c.letsDoIt());
    }

    @Test
    public void timeout(){
        class ChildClass extends ClassUnderTest {
            public Object call(){
                while(true){}
            }
        }
        ChildClass c = new ChildClass();
        assertEquals("{\"output\":null,\"understood\":true,\"threw\":true,\"exceptionString\":\"java.util.concurrent.CancellationException\",\"prints\":[]}", c.letsDoIt());
    }

    @Test
    public void stackoverflow(){
        class ChildClass extends ClassUnderTest {
            public Object call(){
                return call();
            }
        }
        ChildClass c = new ChildClass();
        assertEquals("{\"output\":null,\"understood\":true,\"threw\":true,\"exceptionString\":\"java.util.concurrent.ExecutionException: java.lang.StackOverflowError\",\"prints\":[]}", c.letsDoIt());
    }

    @Test
    public void throwRuntime(){
        class ChildClass extends ClassUnderTest {
            public Object call(){
                throw new RuntimeException("hey there");
            }
        }
        ChildClass c = new ChildClass();
        assertEquals("{\"output\":null,\"understood\":true,\"threw\":true,\"exceptionString\":\"java.util.concurrent.ExecutionException: java.lang.RuntimeException: hey there\",\"prints\":[]}", c.letsDoIt());
    }
}
