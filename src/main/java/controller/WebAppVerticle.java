package controller;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
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
    private final String VOTES_FROM_ADMIN = "votesFromAdmin";
    private final String VOTES_FROM_RECEIVER = "votesFromReceiver";

    // RECEIVER
    private int portToReceiver = 8080; // @TODO: change to receivers port
    private String addrToReceiver = "130.229.147.3"; // @TODO: change to receivers addr
    private String resourceToReceiver = "/v1/helloworld1/"; // @TODO: change to receivers resource addr

    // TRANSMITTER
    private int portToTransmitter = 8080;// @TODO: change to transmitters port
    private String addrToTransmitter = "130.229.147.3"; // @TODO: change to transmitters addr
    private String resourceToTransmitter = "/v1/helloworld2/"; // @TODO: change to transmitters resource addr

    @Override
    public void start(Future<Void> fut) {

        mongo = MongoClient.createShared(vertx, config());
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.route("/v1/addvote/").method(HttpMethod.POST).handler(BodyHandler.create());
        router.post("/v1/addvote/").handler(this::addVote);

        router.route("/v1/vote/").method(HttpMethod.POST).handler(BodyHandler.create());
        router.post("/v1/vote/").handler(this::vote);

        // For development purposes only. Not to be used in production.
        router.route("/v1/test/:id/").method(HttpMethod.GET).handler(BodyHandler.create());
        router.get("/v1/test/:id/").handler(this::test);

        server.requestHandler(router::accept).listen(8080);

        System.out.println("Server up and running..");
    }

    private void vote(RoutingContext routingContext) {

        /*
            Sets a 10 second timer and then send results to transmitter
         */
        vertx.setTimer(1000 * 10, handler -> {
            sendResult(routingContext.getBodyAsJson().getString("id"));
        });

        /*
            Persist votes from receiver(s)
         */
        try {
            mongo.save(VOTES_FROM_RECEIVER, routingContext.getBodyAsJson(), res -> {
                if (res.succeeded()) {

                    String id = res.result();
                    System.out.println("Saved votesFromReceiver with id " + id);

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
                Send BadRequest to client
             */
        } catch (Exception e) {
            badRequest(routingContext);
        }
    }

    private void addVote(RoutingContext routingContext) {

        /*
            Persist votes from admin
         */
        try {
            mongo.save(VOTES_FROM_ADMIN, routingContext.getBodyAsJson(), res -> {
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

    /*
        Send results to transmitter
     */
    // @TODO: Not a working implementation
    private void sendResult(String id) {

        JsonObject query = new JsonObject().put("id", id);

        mongo.find(VOTES_FROM_ADMIN, query, res -> {
            if (res.succeeded()) {
                for (JsonObject json : res.result()) {
                    JsonArray options = json.getJsonArray("options");
                    System.out.println(options.encodePrettily());
                }
            } else {
                res.cause().printStackTrace();
            }
        });
    }

    /*
        For development purposes only. Not to be used in production.
     */
    private void test(RoutingContext routingContext) {

        final String id = routingContext.request().getParam("id");
        System.out.println("id: " + id);
        JsonObject query = new JsonObject();

        mongo.find(VOTES_FROM_ADMIN, query, res -> {
            if (res.succeeded()) {
                for (JsonObject json : res.result()) {
                    JsonObject voting = json.getJsonObject("voting");
                    if(voting.getString("id").equals(id)) {
                        System.out.println("ID MATCH");
                        JsonArray options = voting.getJsonArray("options");
                        for (int i = 0; i < options.size(); i++) {
                            JsonObject obj = options.getJsonObject(i);
                            // @TODO: continue
                        }
                    }
                }
            } else {
                res.cause().printStackTrace();
                badRequest(routingContext);
            }
        });

        routingContext.response()
                .setStatusCode(HttpResponseStatus.OK.code())
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(routingContext.getBodyAsString());
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