package org.hombro.tea.question.test.java;

import org.hombro.tea.question.code.test.java.JvmSandbox;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by nicolas on 9/4/2017.
 */
public class TestJvmSandbox {
    private JvmSandbox jvmSandbox;

    @Before
    public void setup() {
        jvmSandbox = new JvmSandbox();
    }

    @Test
    public void simple() {
        String out = jvmSandbox.run("TestClass", "" +
                "import org.hombro.tea.question.code.test.java.ClassUnderTest;\n" +
                "public class TestClass extends ClassUnderTest{\n" +
                "   public Object call(){ return 1; }\n" +
                "}\n");
        assertEquals(out, "{\"output\":1,\"understood\":true,\"threw\":false,\"exceptionString\":\"\",\"prints\":[]}");
    }
}
