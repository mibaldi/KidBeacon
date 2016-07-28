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
    public static void storeBeaconPosition(final BeaconPosition beaconPosition, final OwnBeaconRealm ownBeacon, final OnDatabaseTransaction listener){
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

    public static OwnGroupRealm getOwnGroup(String name) {

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<OwnGroupRealm> query = realm.where(OwnGroupRealm.class);
        query.equalTo("name", name);

        RealmResults<OwnGroupRealm> result = query.findAll();

        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public static void storeOwnGroup(final OwnGroupRealm ownGroupRealm, final OnDatabaseTransaction listener) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                OwnGroupRealm obj = bgRealm.createObject(OwnGroupRealm.class);
                obj.setName(ownGroupRealm.getName());
                obj.setCreation_date(ownGroupRealm.getCreation_date());
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



    public static void asignOwnBeacon2OwnGroup(final String groupName, final OwnBeaconRealm ownBeacon, final OnDatabaseTransaction listener) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                OwnGroupRealm ownGroupRealm = DataBaseManager.getOwnGroup(groupName);
                OwnBeaconRealm obj = bgRealm.createObject(OwnBeaconRealm.class);
                obj.setUuid(ownBeacon.getUuid());
                obj.setMajor(ownBeacon.getMajor());
                obj.setMinor(ownBeacon.getMinor());
                ownGroupRealm.beaconsList.add(obj);
                bgRealm.copyToRealmOrUpdate(ownGroupRealm);
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


    public static RealmResults<OwnBeaconRealm> getOwnBeacons() {

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<OwnBeaconRealm> query = realm.where(OwnBeaconRealm.class);

        RealmResults<OwnBeaconRealm> result = query.findAll();

        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public static OwnBeaconRealm getOwnBeacon(String uuid, String major, String minor) {

        Realm realm = Realm.getDefaultInstance();

        RealmQuery<OwnBeaconRealm> query = realm.where(OwnBeaconRealm.class);
        query.equalTo("uuid", uuid);
        query.equalTo("major", major);
        query.equalTo("minor", minor);

        RealmResults<OwnBeaconRealm> result = query.findAll();

        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public static void storeOwnBeacons(final OwnBeaconRealm ownBeacon, final OnDatabaseTransaction listener) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                OwnBeaconRealm obj = bgRealm.createObject(OwnBeaconRealm.class);
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
