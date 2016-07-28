package com.mibaldi.kidbeacon.Data.Database;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DataBaseManager {
    public interface OnDatabaseTransaction {
        void onSucces();

        void onError(Throwable error);
    }
    public static BeaconPosition getBeaconPosition(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<BeaconPosition> results = realm.where(BeaconPosition.class).findAll();
        if (results.size() > 0 ) return results.get(0);
        else return null;
    }
    public static void storeBeaconPosition(final BeaconPosition beaconPosition,final OwnBeacon ownBeacon,final OnDatabaseTransaction listener){
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                BeaconPosition obj = bgRealm.createObject(BeaconPosition.class);
                obj.setUuid(ownBeacon.getUuid());
                obj.setLatitude(beaconPosition.getLatitude());
                obj.setLongitude(beaconPosition.getLongitude());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSucces();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onError(error);
            }
        });
    }
    public static void deleteBeaconPosition(final BeaconPosition beaconPosition,final OnDatabaseTransaction listener){
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

               beaconPosition.deleteFromRealm();

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSucces();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onError(error);
            }
        });
    }
    public static void updateBeaconPosition(final BeaconPosition beaconPosition,final OnDatabaseTransaction listener){
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealmOrUpdate(beaconPosition);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSucces();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onError(error);
            }
        });
    }

    public static OwnGroup getOwnGroup(String name) {

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<OwnGroup> query = realm.where(OwnGroup.class);
        query.equalTo("name", name);

        RealmResults<OwnGroup> result = query.findAll();

        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public static void storeOwnGroup(final OwnGroup ownGroup, final OnDatabaseTransaction listener) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                OwnGroup obj = bgRealm.createObject(OwnGroup.class);
                obj.setName(ownGroup.getName());
                obj.setCreation_date(ownGroup.getCreation_date());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSucces();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onError(error);
            }
        });
    }



    public static void asignOwnBeacon2OwnGroup(final String groupName, final OwnBeacon ownBeacon, final OnDatabaseTransaction listener) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                OwnGroup ownGroup = DataBaseManager.getOwnGroup(groupName);
                OwnBeacon obj = bgRealm.createObject(OwnBeacon.class);
                obj.setUuid(ownBeacon.getUuid());
                obj.setMajor(ownBeacon.getMajor());
                obj.setMinor(ownBeacon.getMinor());
                ownGroup.beaconsList.add(obj);
                bgRealm.copyToRealmOrUpdate(ownGroup);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSucces();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                listener.onError(error);
            }
        });

    }


    public static RealmResults<OwnBeacon> getOwnBeacons() {

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<OwnBeacon> query = realm.where(OwnBeacon.class);

        RealmResults<OwnBeacon> result = query.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public static OwnBeacon getOwnBeacon(String uuid, String major, String minor) {

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<OwnBeacon> query = realm.where(OwnBeacon.class);
        query.equalTo("uuid", uuid);
        query.equalTo("major", major);
        query.equalTo("minor", minor);

        RealmResults<OwnBeacon> result = query.findAll();

        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public static void storeOwnBeacons(final OwnBeacon ownBeacon, final OnDatabaseTransaction listener) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                OwnBeacon obj = bgRealm.createObject(OwnBeacon.class);
                obj.setUuid(ownBeacon.getUuid());
                obj.setMajor(ownBeacon.getMajor());
                obj.setMinor(ownBeacon.getMinor());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onSucces();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onError(error);
            }
        });

    }
}
