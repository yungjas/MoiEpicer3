package mapp.com.sg.moiepicer.Model;

/**
 * Created by Acer on 31/7/2017.
 */

public class Ingredient {
    private String name;
    private String type;

    public Ingredient() {
    }

    public Ingredient(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}