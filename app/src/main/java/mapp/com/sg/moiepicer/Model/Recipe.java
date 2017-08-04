package mapp.com.sg.moiepicer.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by EternalFlames on 7/31/2017.
 */

public class Recipe implements Parcelable {
    private String uID;
    private String name;
    private String dish;
    private String description;
    private long duration;
    private ArrayList<Step> requiredSteps=null;
    private ArrayList<Ingredient> requiredIngredient=null;

    public Recipe() {
    }

    public Recipe(String uID,String name, String dish, String description, long duration) {
        this.uID=uID;
        this.name = name;
        this.dish = dish;
        this.description = description;
        this.duration = duration;
    }

    public Recipe(String name, String dish, String description) {
        this.name = name;
        this.dish = dish;
        this.description = description;
    }

    protected Recipe(Parcel in) {
        uID=in.readString();
        name = in.readString();
        dish = in.readString();
        description = in.readString();
        duration = in.readLong();
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
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
        dest.writeLong(duration);
    }

    public ArrayList<Step> getRequiredSteps() {
        return requiredSteps;
    }

    public ArrayList<Ingredient> getRequiredIngredient() {
        return requiredIngredient;
    }

    public void setRequiredIngredient(ArrayList<Ingredient> requiredIngredient) {
        this.requiredIngredient = requiredIngredient;
    }

    public void setRequiredSteps(ArrayList<Step> requiredSteps) {
        this.requiredSteps = requiredSteps;
    }

}
