package org.hombro.tea.server;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by nicolas on 8/12/2017.
 */
public class TeaRunner {
    private static final Logger logger = LoggerFactory.getLogger(TeaRunner.class);
    public static void main(String... args){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new TeaVerticle());
        logger.info("TeaVerticle has been deployed");
    }
}
