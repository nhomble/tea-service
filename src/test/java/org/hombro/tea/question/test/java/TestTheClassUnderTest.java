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
}
