package com.mibaldi.kidbeacon.Data.Database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OwnBeaconRealm extends RealmObject {
    @PrimaryKey
    private String uuid;
    private String major;
    private String minor;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }
}
