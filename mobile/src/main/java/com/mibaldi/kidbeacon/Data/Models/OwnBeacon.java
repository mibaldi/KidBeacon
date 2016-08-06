package com.mibaldi.kidbeacon.Data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class OwnBeacon implements Parcelable {
    public String id;
    public String uuid;
    public String major;
    public String minor;
    public String name;
    @Exclude
    public double distance = 0 ;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.uuid);
        dest.writeString(this.major);
        dest.writeString(this.minor);
        dest.writeString(this.name);
        dest.writeDouble(this.distance);
    }

    public OwnBeacon() {
    }
    public OwnBeacon (String uuid,String major,String minor){
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
    }

    protected OwnBeacon(Parcel in) {
        this.id = in.readString();
        this.uuid = in.readString();
        this.major = in.readString();
        this.minor = in.readString();
        this.name = in.readString();
        this.distance = in.readDouble();
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
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id",id);
        result.put("uuid", uuid);
        result.put("major", uuid);
        result.put("minor", uuid);
        result.put("name", name);
        return result;
    }
    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof OwnBeacon)
        {
            boolean sameUUID = this.uuid.compareToIgnoreCase(((OwnBeacon) object).uuid) == 0;
            boolean sameMajor = this.major.compareToIgnoreCase(((OwnBeacon) object).major) == 0;
            boolean sameMinor =  this.minor.compareToIgnoreCase (((OwnBeacon) object).minor) == 0;
            sameSame = sameUUID && sameMajor && sameMinor;
        }

        return sameSame;
    }
}
