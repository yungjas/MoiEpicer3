package mapp.com.sg.moiepicer.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Acer on 31/7/2017.
 */
@IgnoreExtraProperties
public class Ingredient implements Parcelable{
    private String uID;
    private String name;
    private String type;
    public Ingredient() {
    }

    public Ingredient(String uID,String name, String type) {
        this.uID=uID;
        this.name = name;
        this.type = type;
    }

    protected Ingredient(Parcel in) {
        uID = in.readString();
        name = in.readString();
        type = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getUID() {
        return uID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uID);
        dest.writeString(name);
        dest.writeString(type);
    }
}
