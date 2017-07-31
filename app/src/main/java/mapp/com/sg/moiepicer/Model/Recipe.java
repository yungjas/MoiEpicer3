package mapp.com.sg.moiepicer.Model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by EternalFlames on 7/31/2017.
 */
@IgnoreExtraProperties
public class Recipe {
    private String name;
    private String description;
    private ArrayList<RequiredIngredient> ingredientsList ;
    private ArrayList<Step> stepList;
    private int times;

    public Recipe() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
