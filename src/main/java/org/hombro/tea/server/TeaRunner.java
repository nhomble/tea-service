package org.hombro.tea.server;

import io.vertx.core.Vertx;

/**
 * Created by nicolas on 8/12/2017.
 */
public class TeaRunner {
    public static void main(String... args){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new TeaVerticle());
    }
}
