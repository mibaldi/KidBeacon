package com.mibaldi.kidbeacon.Data.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class OwnBeacon implements Parcelable {
    public String uuid;
    public String major;
    public String minor;
    public String name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.major);
        dest.writeString(this.minor);
        dest.writeString(this.name);
    }

    public OwnBeacon() {
    }

    protected OwnBeacon(Parcel in) {
        this.uuid = in.readString();
        this.major = in.readString();
        this.minor = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<OwnBeacon> CREATOR = new Parcelable.Creator<OwnBeacon>() {
        @Override
        public OwnBeacon createFromParcel(Parcel source) {
            return new OwnBeacon(source);
        }

        @Override
        public OwnBeacon[] newArray(int size) {
            return new OwnBeacon[size];
        }
    };
}
