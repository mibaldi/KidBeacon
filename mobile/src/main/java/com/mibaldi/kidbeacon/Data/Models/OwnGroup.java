package com.mibaldi.kidbeacon.Data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class OwnGroup implements Parcelable {
    public String name;
    public String creation_date;
    public String photo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.creation_date);
        dest.writeString(this.photo);
    }

    public OwnGroup() {
    }

    protected OwnGroup(Parcel in) {
        this.name = in.readString();
        this.creation_date = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<OwnGroup> CREATOR = new Parcelable.Creator<OwnGroup>() {
        @Override
        public OwnGroup createFromParcel(Parcel source) {
            return new OwnGroup(source);
        }

        @Override
        public OwnGroup[] newArray(int size) {
            return new OwnGroup[size];
        }
    };
}
