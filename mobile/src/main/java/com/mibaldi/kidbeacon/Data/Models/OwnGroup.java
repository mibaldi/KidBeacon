package com.mibaldi.kidbeacon.Data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mikelbalducieldiaz on 28/7/16.
 */

public class OwnGroup implements Parcelable {
    public String id;
    public String name;
    public String creation_date;
    public String photo;
    @Exclude
    public ArrayList<OwnBeacon> beaconArrayList = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.creation_date);
        dest.writeString(this.photo);
    }

    public OwnGroup() {
    }

    protected OwnGroup(Parcel in) {
        this.id = in.readString();
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
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id",id);
        result.put("name", name);
        result.put("photo", photo);
        result.put("creation_date",new Date().toString());
        return result;
    }

}
