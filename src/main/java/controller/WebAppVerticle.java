package controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by Peonsson on 18/03/16.
 */
public class WebAppVerticle extends AbstractVerticle {

    private MongoClient mongo;
    private int portToReceiver = 8080;// @TODO: Change to Receivers port
    private String addrToReceiver = "130.229.147.3"; // @TODO: Change to Receivers addr
    private String resourceToReceiver = "/v1/helloworld1/"; // @TODO: Change to Receivers resource addr

    @Override
    public void start(Future<Void> fut) {

        mongo = MongoClient.createShared(vertx, config());
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.route("/v1/addvote/").method(HttpMethod.POST).handler(BodyHandler.create());
        router.post("/v1/addvote/").handler(this::addVote);

        router.route("/v1/vote/").method(HttpMethod.POST).handler(BodyHandler.create());
        router.post("/v1/vote/").handler(this::vote);

        server.requestHandler(router::accept).listen(8080);

        System.out.println("Server up and running..");

    }

    private void vote(RoutingContext routingContext) {

        /*
            sets a 10 second timer and then send results to transmitter
         */
        vertx.setTimer(1000 * 10, handler -> {
            sendResult();
        });

        /*
            append database with new results
         */
        final String id = routingContext.getBodyAsJson().getString("id");
        JsonObject query = new JsonObject().put("id", id);

        // Set the author field
        JsonObject update = new JsonObject().put("$set", new JsonObject().put("author", "J. R. R. Tolkien"));

        mongo.update("books", query, update, res -> {

            if (res.succeeded()) {

                System.out.println("Book updated !");

            } else {

                res.cause().printStackTrace();
            }

        });
    }

    private void addVote(RoutingContext routingContext) {

        try {
            mongo.save("votes", routingContext.getBodyAsJson(), res -> {
                if (res.succeeded()) {

                    String id = res.result();
                    System.out.println("Saved voting with id " + id);

                } else {
                    res.cause().printStackTrace();
                }
            });
            /*
                Send ok to client
             */
            routingContext.response()
                    .setStatusCode(HttpResponseStatus.CREATED.code())
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(routingContext.getBodyAsString());
            /*
                Forward data to receiver(s)
             */
            vertx.createHttpClient().post(portToReceiver, addrToReceiver, resourceToReceiver)
                    .putHeader("content-type", "application/json")
                    .putHeader("content-length", Integer.toString(routingContext.getBody().length()))
                    .handler(response -> {
                        System.out.println("response status code: " + response.statusCode());
                    })
                    .write(routingContext.getBodyAsString())
                    .end();

        }
        /*
            Send bad request to client
         */ catch (Exception e) {
            badRequest(routingContext);
        }
    }

    private void sendResult() {

    }

    /*
        Reply a HTTP Bad Request to client
     */
    private void badRequest(RoutingContext routingContext) {
        routingContext.response()
                .putHeader("content-type", "text/plain")
                .setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
                .end("BAD REQUEST");
    }
}