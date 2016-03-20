package model.post_vote_results;

import java.util.ArrayList;

/**
 * Created by Peonsson on 18/03/16.
 */
public class Answer {

    private String answer;
    private int voteCount;
    private ArrayList<String> keysVoted;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public ArrayList<String> getKeysVoted() {
        return keysVoted;
    }

    public void setKeysVoted(ArrayList<String> keysVoted) {
        this.keysVoted = keysVoted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", voteCount=" + voteCount +
                ", keysVoted=" + keysVoted +
                '}';
    }
}
