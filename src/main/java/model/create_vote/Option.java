package model.create_vote;

import java.util.ArrayList;

/**
 * Created by Peonsson on 18/03/16.
 */
public class Option {
    private String name;
    private ArrayList<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", values=" + values +
                '}';
    }
}
