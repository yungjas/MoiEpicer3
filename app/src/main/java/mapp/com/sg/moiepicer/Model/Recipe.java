package mapp.com.sg.moiepicer.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import mapp.com.sg.moiepicer.Create;

/**
 * Created by EternalFlames on 7/31/2017.
 */

public class Recipe implements Parcelable {
    private String uID;
    private String name;
    private String dish;
    private String url;


    public String getUrl() {
        return url;
    }

    private String description;
    private String style;
    private String cuisine;
    private String level;
    private int duration;
    private ArrayList<Step> requiredSteps=null;
    private ArrayList<RequiredIngredient> requiredIngredient=null;

    public Recipe() {
    }
    //Need to change for main program

    public Recipe(String uID, String name, String dish, String description, String style, String cuisine, String level, int duration) {
        this.uID=uID;
        this.name = name;
        this.dish = dish;
        this.description = description;
        this.style = style;
        this.cuisine = cuisine;
        this.level = level;
        this.duration = duration;
    }
    public Recipe(String uID,String name, String dish, String description,int duration ){
        this.uID = uID;
        this.name = name;
        this.dish =dish;
        this.description = description;
        this.duration = duration;
    }
    public Recipe(Recipe recipe, ArrayList<RequiredIngredient> requiredIngredient, ArrayList<Step> requiredSteps) {
        this.uID = recipe.getUID();
        this.name = recipe.getName();
        this.dish = recipe.getDish();
        this.description = recipe.getDescription();
        this.style = recipe.getStyle();
        this.cuisine = recipe.getCuisine();
        this.level = recipe.getLevel();
        this.url = recipe.getUrl();
        this.duration = recipe.getDuration();
        this.requiredIngredient = requiredIngredient;
        this.requiredSteps = requiredSteps;
    }

    protected Recipe(Parcel in) {
        uID=in.readString();
        name = in.readString();
        dish = in.readString();
        description = in.readString();
        style = in.readString();
        cuisine = in.readString();
        level = in.readString();
        url=in.readString();
        duration = in.readInt();
        requiredSteps=new ArrayList<Step>();
        in.readList( requiredSteps, Step.class.getClassLoader());
        requiredIngredient=new ArrayList<RequiredIngredient>();
        in.readList( requiredIngredient, RequiredIngredient.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getUID() {
        return uID;
    }

    public void setUID(String uID) {
        this.uID = uID;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uID);
        dest.writeString(name);
        dest.writeString(dish);
        dest.writeString(description);
        dest.writeString(style);
        dest.writeString(cuisine);
        dest.writeString(level);
        dest.writeString(url);
        dest.writeInt(duration);
        dest.writeList(requiredSteps);
        dest.writeList(requiredIngredient);


    }

    public ArrayList<Step> getRequiredSteps() {
        return requiredSteps;
    }

    public ArrayList<RequiredIngredient> getRequiredIngredient() {
        return requiredIngredient;
    }

    public void setRequiredIngredient(ArrayList<RequiredIngredient> requiredIngredient) {
        this.requiredIngredient = requiredIngredient;
    }

    public void setRequiredSteps(ArrayList<Step> requiredSteps) {
        this.requiredSteps = requiredSteps;
    }

}
