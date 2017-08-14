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
        if(args.length != 1){
            usage();
        }

        int port = Integer.valueOf(args[0]);

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new TeaVerticle(port));
        logger.info("TeaVerticle has been deployed");
    }

    private static void usage(){
        throw new IllegalArgumentException("usage: TeaRunner [PORT]");
    }
}
