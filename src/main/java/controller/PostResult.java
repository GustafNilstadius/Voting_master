package controller;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import model.post_vote_results.Answer;
import model.post_vote_results.Question;
import model.post_vote_results.VoteResults;

import java.time.Instant;
import java.util.ArrayList;

/**
 * Created by Peonsson on 18/03/16.
 */

public class PostResult extends AbstractVerticle {

    private final int port = 8080;
//    private String addr = "localhost";
    private String addr = "130.229.184.187";
    private String pollid = "asd";
    private String resource = "/v1/" + pollid + "/";

    @Override
    public void start() throws Exception {

        VoteResults post = new VoteResults();
        post.setOwner("johan");
        post.setPollStartTime(Instant.now());
        post.setPollFinishTime(Instant.now());
        post.setPollId(pollid);
        post.setTopic("Helgplz");

        ArrayList<Question> questions = new ArrayList<Question>();

        Question q1 = new Question();
        q1.setQuestion("How would a woodchuck chuck wood if a woodchuck would chuck wood");

        ArrayList<Answer> answers = new ArrayList<Answer>();

        Answer a1 = new Answer();
        a1.setAnswer("with an ax'");
        ArrayList<String> keysVoted = new ArrayList<String>();
        keysVoted.add("1111");
        keysVoted.add("2222");
        keysVoted.add("3333");

        a1.setKeysVoted(keysVoted);

        int count = keysVoted.size();
        a1.setVoteCount(count);
        answers.add(a1);

        q1.setAnswers(answers);
        questions.add(q1);
        post.setQuestions(questions);

        final String json = Json.encodePrettily(post);

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