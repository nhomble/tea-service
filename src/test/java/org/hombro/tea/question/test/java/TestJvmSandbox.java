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
                "public class TestClass {\n" +
                "public static void main(String[] args){\n" +
                "   System.out.println(\"{hey}\");\n" +
                "}\n" +
                "}\n");
        assertEquals(out, "{hey}");
    }
}
