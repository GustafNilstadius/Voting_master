package model.create_vote;

/**
 * Created by Peonsson on 18/03/16.
 */
public class Duration {

    private long begin;
    private long end;

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Duration{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}
