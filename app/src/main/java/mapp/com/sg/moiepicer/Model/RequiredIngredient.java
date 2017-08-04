package mapp.com.sg.moiepicer.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EternalFlames on 7/31/2017.
 */

class RequiredIngredient implements Parcelable{
    private Ingredient ingredient;
    private int amount;
    private String Unit;

    public RequiredIngredient() {
    }

    public RequiredIngredient(Ingredient ingredient, int amount, String unit) {
        this.ingredient = ingredient;
        this.amount = amount;
        Unit = unit;
    }

    protected RequiredIngredient(Parcel in) {
        ingredient = in.readParcelable(Ingredient.class.getClassLoader());
        amount = in.readInt();
        Unit = in.readString();
    }

    public static final Creator<RequiredIngredient> CREATOR = new Creator<RequiredIngredient>() {
        @Override
        public RequiredIngredient createFromParcel(Parcel in) {
            return new RequiredIngredient(in);
        }

        @Override
        public RequiredIngredient[] newArray(int size) {
            return new RequiredIngredient[size];
        }
    };

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return Unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(ingredient, flags);
        dest.writeInt(amount);
        dest.writeString(Unit);
    }
}
