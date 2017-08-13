package org.hombro.tea.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nicolas on 8/12/2017.
 */
public class IOHelper {
    private static String getFromFile(String path) {
        try (InputStream is = IOHelper.class.getClassLoader().getResourceAsStream(path)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getQuestionFromFile(String path) {
        return getFromFile("tea/question/" + path.toLowerCase() + ".json");
    }
}
