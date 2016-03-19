package model.post_vote_results;

import java.time.Instant;
import java.util.ArrayList;

/**
 * Created by Peonsson on 18/03/16.
 */
public class VoteResults {

    private String pollId;
    private String owner;
    private String topic;
    private Instant pollStartTime;
    private Instant pollFinishTime;
    private ArrayList<Question> questions;

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getPollStartTime() {
        return pollStartTime.toString();
    }

    public void setPollStartTime(Instant pollStartTime) {
        this.pollStartTime = pollStartTime;
    }

    public String getPollFinishTime() {
        return pollFinishTime.toString();
    }

    public void setPollFinishTime(Instant pollFinishTime) {
        this.pollFinishTime = pollFinishTime;
    }
}
