package control;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import model.create_vote.Vote;

/**
 * Created by Peonsson on 18/03/16.
 */
public class PostVote extends AbstractVerticle {

    private final int port = 8080;
    //    private String addr = "localhost";
    private String addr = "130.229.184.187";
    private String pollid = "asd";
    private String resource = "/v1/" + pollid + "/";

    @Override
    public void start() throws Exception {

        Vote vote = new Vote();

        //@TODO: create and send a vote to self to see proper JSON.

        final String json = Json.encodePrettily(vote);

        final String length = Integer.toString(json.length());

        vertx.createHttpClient().post(port, addr, resource)
                .putHeader("content-type", "application/json")
                .putHeader("content-length", length)
                .handler(response -> {
                    System.out.println("response status code: " +response.statusCode());
                })
                .write(json)
                .end();
    }
}
