package model;

import java.util.ArrayList;

/**
 * Created by Peonsson on 18/03/16.
 */
public class MasterToResultPOST {

    private String pollId;
    private String owner;
    private String topic;
    private String pollStartTime;
    private String pollFinishTime;
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

    public String getPollStartTime() {
        return pollStartTime;
    }

    public void setPollStartTime(String pollStartTime) {
        this.pollStartTime = pollStartTime;
    }

    public String getPollFinishTime() {
        return pollFinishTime;
    }

    public void setPollFinishTime(String pollFinishTime) {
        this.pollFinishTime = pollFinishTime;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
