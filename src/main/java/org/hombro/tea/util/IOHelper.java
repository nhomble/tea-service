package org.hombro.tea.util;

import org.apache.commons.io.IOUtils;
import org.hombro.tea.question.code.Language;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nicolas on 8/12/2017.
 */
public class IOHelper {
    private static String QUESTION_PATH = "org/hombro/tea/question/";

    private IOHelper() {
    }

    private static String getFromFile(String path) {
        try (InputStream is = IOHelper.class.getClassLoader().getResourceAsStream(path)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * stop gap until I setup firebase
     *
     * @return
     */
    public static List<File> getQuestionsFromFile() {
        List<File> dirs = new ArrayList<>();
        for (Language lang : Language.values()) {
            URL url = IOHelper.class.getClassLoader().getResource(QUESTION_PATH + lang.toString().toLowerCase());
            if (url != null && url.getFile() != null) {
                dirs.add(new File(url.getFile()));
            }
        }
        List<File> files = new ArrayList<>();
        dirs.forEach(d -> {
            File[] list = d.listFiles();
            if (list != null)
                Collections.addAll(files, list);
        });
        return files;
    }

    public static String getQuestionFromFile(String path) {
        return getFromFile(QUESTION_PATH + path.toLowerCase() + ".json");
    }

}
