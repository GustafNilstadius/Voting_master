package model.vote_results;

import java.util.ArrayList;

/**
 * Created by Peonsson on 18/03/16.
 */
public class Question {
    private String question;
    private ArrayList<Answer> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
