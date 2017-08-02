package mapp.com.sg.moiepicer.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by EternalFlames on 7/31/2017.
 */

public class Recipe implements Parcelable {
    private String name;
    private String dish;
    private String description;
    private long timeStamp;

    public Recipe() {
    }

    public Recipe(String name, String dish, String description, long timeStamp) {
        this.name = name;
        this.dish = dish;
        this.description = description;
        this.timeStamp = timeStamp;
    }

    public Recipe(String name, String dish, String description) {
        this.name = name;
        this.dish = dish;
        this.description = description;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        dish = in.readString();
        description = in.readString();
        timeStamp = in.readLong();
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dish);
        dest.writeString(description);
        dest.writeLong(timeStamp);
    }
}
