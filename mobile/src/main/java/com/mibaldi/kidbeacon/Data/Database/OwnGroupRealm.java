package com.mibaldi.kidbeacon.Data.Database;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class OwnGroupRealm extends RealmObject  {
    @PrimaryKey
    private String name;
    private Date creation_date;
    public RealmList<OwnBeaconRealm> beaconsList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public RealmList<OwnBeaconRealm> getBeaconsList() {
        return beaconsList;
    }

    public void setBeaconsList(RealmList<OwnBeaconRealm> beaconsList) {
        this.beaconsList = beaconsList;
    }
}
