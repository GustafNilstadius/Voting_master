package model.got_new_vote;

import java.util.ArrayList;

/**
 * Created by Peonsson on 18/03/16.
 */
public class Voting {

    private String id;
    private String topic;
    private String owner;

    private ArrayList<Duration> duration;
    private ArrayList<Option> options;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Duration> getDuration() {
        return duration;
    }

    public void setDuration(ArrayList<Duration> duration) {
        this.duration = duration;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Voting{" +
                "owner='" + owner + '\'' +
                ", topic='" + topic + '\'' +
                ", id='" + id + '\'' +
                ", duration=" + duration +
                ", options=" + options +
                '}';
    }
}
