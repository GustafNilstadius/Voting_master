package control;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import model.create_vote.Vote;

/**
 * Created by Peonsson on 18/03/16.
 */

public class WebAppVerticle extends AbstractVerticle {

    private MongoClient mongo;

    @Override
    public void start(Future<Void> fut) {

        // Create a Mongo client
        mongo = MongoClient.createShared(vertx, config());

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.post("/v1/vote/").handler(this::vote);
        router.route("/v1/vote/").method(HttpMethod.POST).handler(BodyHandler.create());

        server.requestHandler(router::accept).listen(8080);

        System.out.println("server up and running..");

    }

    private void vote(RoutingContext routingContext) {

        try {
            final Vote vote = Json.decodeValue(routingContext.getBodyAsString(), Vote.class);

            mongo.save("votes", routingContext.getBodyAsJson(), res -> {
                if (res.succeeded()) {

                    String id = res.result();
                    System.out.println("Saved vote with id " + id);

                } else {
                    res.cause().printStackTrace();
                }
            });

            System.out.println(vote.toString());
            routingContext.response()
                    .setStatusCode(HttpResponseStatus.CREATED.code())
                    .putHeader("content-type", "application/json; charset=utf-8")
                    .end(Json.encodePrettily(vote));
        } catch (Exception e) {
            routingContext.response()
                    .putHeader("content-type", "text/plain")
                    .setStatusCode(HttpResponseStatus.BAD_REQUEST.code())
                    .end("BAD REQUEST");
        }
    }
}
