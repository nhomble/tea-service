package org.hombro.tea.util;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.hombro.tea.question.code.Language;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nicolas on 8/12/2017.
 */
public class IOHelper {
    private static Logger logger = LoggerFactory.getLogger(IOHelper.class);
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
            String path = QUESTION_PATH + lang.toString().toLowerCase();
            logger.info("Looking in path: " + path);
            URL url = IOHelper.class.getClassLoader().getResource(path);
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
        logger.info(String.format("Found %s files", files.size()));
        return files;
    }

    public static String getQuestionFromFile(String path) {
        return getFromFile(QUESTION_PATH + path.toLowerCase() + ".json");
    }

    public static File writeTempFile(String text){
        try {
            File tmp = Files.createTempDirectory("tmp_" + System.nanoTime()).toFile();
            tmp.deleteOnExit();
            FileWriter writer = new FileWriter(tmp);
            writer.write(text);
            writer.close();
            return tmp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
