package mapp.com.sg.moiepicer.Model;

/**
 * Created by EternalFlames on 7/31/2017.
 */

public class Step {
    private String name;
    private String description;
    private int time;

    public Step() {
    }

    public Step(String name, String description, int time) {
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTime() {
        return time;
    }

}
