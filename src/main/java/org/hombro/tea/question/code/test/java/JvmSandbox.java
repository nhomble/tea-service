package org.hombro.tea.question.code.test.java;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.*;
import java.nio.file.Files;

/**
 * Created by nicolas on 9/4/2017.
 */
public class JvmSandbox {
    private static final Logger logger = LoggerFactory.getLogger(JvmSandbox.class);

    private String separator = System.getProperty("file.separator");
    private String classPath = System.getProperty("java.class.path");

    private String getOutput(Process p) throws IOException, InterruptedException {
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String out = "";
        String temp;
        while ((temp = in.readLine()) != null ) {
            logger.info("Got: " + temp);
            if(temp.startsWith("{") && temp.endsWith("}"))
                out = temp;
        }
        logger.info("waiting on process");
        p.waitFor();
        in.close();
        return out;
    }

    public String run(String className, String code) {
        try {
            File tmp = Files.createTempDirectory("_delete_me_").toFile();
            tmp.deleteOnExit();
            File source = new File(String.valueOf(tmp.getAbsoluteFile()) + separator + className + ".java");
            source.deleteOnExit();
            if (!source.createNewFile()) {
                if (!source.delete()) {
                    throw new RuntimeException("no delete");
                }
                throw new RuntimeException("did not make file");
            }

            FileWriter writer = new FileWriter(source);
            writer.write(code);
            writer.close();
            /**
             * Here we compile our runtime source file into the filesystem. Our subsequent call to java will take the
             * tmp class path as an argument so we can control its loading via java.
             */
            ProcessBuilder processBuilder = new ProcessBuilder("javac", "-cp", classPath, source.getAbsolutePath());
            logger.info("compile command: " + String.join(" ", processBuilder.command()));
            processBuilder.redirectErrorStream(true);
            String error = getOutput(processBuilder.start());
            if (!error.isEmpty()) {
                logger.info(error);
                source.delete();
                tmp.delete();
                return "";
            }
            processBuilder = new ProcessBuilder("java", "-cp", classPath, ClassUnderTest.class.getCanonicalName(), tmp.getAbsolutePath() + separator, className);
            logger.info("run command: " + String.join(" ", processBuilder.command()));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            String out = getOutput(process);

            for (File f : tmp.listFiles()) {
                f.delete();
            }
            tmp.delete();
            return out;
        } catch (InterruptedException | IOException e) {
            logger.info("Some exception: " + e.getMessage());
            return "";
        }
    }
}
