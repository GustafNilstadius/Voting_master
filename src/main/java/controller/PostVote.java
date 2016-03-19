//package controller;
//
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.json.Json;
//import model.got_new_vote.Duration;
//import model.got_new_vote.Option;
//import model.got_new_vote.Voting;
//
//import java.util.ArrayList;
//
///**
// * Created by Peonsson on 18/03/16.
// */
//
//public class PostVote extends AbstractVerticle {
//
//    private final int port = 8080;
//
//    private String addr = "localhost";
////    private String addr = "130.229.184.187";
//
//    private String pollid = "asd";
//    private String resource = "/v1/" + pollid + "/";
//
//    @Override
//    public void start() throws Exception {
//
//        Voting voting = new Voting();
//
//        voting.setOwner("owner");
//        voting.setId("Id");
//        voting.setTopic("Topic");
//
//        ArrayList<Duration> durations = new ArrayList<>();
//
//        Duration duration = new Duration();
//
//        duration.setBegin((long) 123);
//        duration.setEnd((long) 321);
//
//        durations.add(duration);
//
//        voting.setDuration(durations);
//
//        Option option = new Option();
//
//        option.setName("options name1");
//
//        ArrayList<String> values = new ArrayList<>();
//
//        values.add("value1");
//        values.add("value2");
//
//        option.setValues(values);
//
//        ArrayList<Option> options = new ArrayList<>();
//        options.add(option);
//
//        voting.setOptions(options);
//
//        //@TODO: create and send a voting to self to see proper JSON.
//
//        final String json = Json.encodePrettily(voting);
//
//        final String length = Integer.toString(json.length());
//
//        vertx.createHttpClient().post(port, addr, resource)
//                .putHeader("content-type", "application/json")
//                .putHeader("content-length", length)
//                .handler(response -> {
//                    System.out.println("response status code: " + response.statusCode());
//                })
//                .write(json)
//                .end();
//    }
//}