package model.create_vote;

import java.time.Instant;

/**
 * Created by Peonsson on 18/03/16.
 */
public class Duration {
    private Instant begin;
    private Instant end;

    public Instant getBegin() {
        return begin;
    }

    public void setBegin(Instant begin) {
        this.begin = begin;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }
}
