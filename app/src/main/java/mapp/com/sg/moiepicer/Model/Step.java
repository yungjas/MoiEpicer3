package mapp.com.sg.moiepicer.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EternalFlames on 7/31/2017.
 */

public class Step implements Parcelable{
    private String seq;
    private String name;
    private String description;
    private int time;

    public Step() {
    }

    public Step(String seq, String name, String description, int time) {
        this.seq = seq;
        this.name = name;
        this.description = description;
        this.time = time;
    }


    protected Step(Parcel in) {
        seq = in.readString();
        name = in.readString();
        description = in.readString();
        time = in.readInt();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public String getSeq() {
        return seq;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(seq);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(time);
    }
}
