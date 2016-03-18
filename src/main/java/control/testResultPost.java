package control;

import io.vertx.core.Vertx;

/**
 * Created by Peonsson on 18/03/16.
 */
public class TestResultPost {

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new PostResult());

     }
}
