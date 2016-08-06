package com.mibaldi.kidbeacon.Data;

import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Data.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by mikelbalducieldiaz on 3/8/16.
 */

public final class FirebaseManager {
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refRoot = database.getReference();
    public static DatabaseReference refUsers = refRoot.child("Users");
    public static DatabaseReference refGroups = refRoot.child("Groups");
    public static DatabaseReference refBeacons = refRoot.child("Beacons");
    public static DatabaseReference refUserGroups = refUsers.child(mAuth.getCurrentUser().getUid()).child("groups");

    private FirebaseManager() {
    }
    public static void generateUser(FirebaseUser currentUser){
        RxFirebaseDatabase.observeValue(FirebaseManager.refUsers.child(currentUser.getUid()), User.class)
                .subscribe(user -> {
                    if (user != null) {
                        String uid = currentUser.getUid();
                        String email = currentUser.getEmail();
                        String name = currentUser.getDisplayName();
                        User u = new User(uid, email, name);
                        FirebaseManager.refUsers.child(uid).setValue(u);
                    }
                }, throwable -> {
                    Timber.e("signInWithCredential: %s", throwable.toString());
                });
    }

    public static void generateGroup(OwnGroup ownGroup){
        String uid = mAuth.getCurrentUser().getUid();
        String key = FirebaseManager.refGroups.push().getKey();
        ownGroup.id = key;
        Map<String, Object> ownGroupMap = ownGroup.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Groups/"+key,ownGroupMap);
        childUpdates.put("/Users/"+uid+"/groups/"+key,key);
        FirebaseManager.refRoot.updateChildren(childUpdates);
    }
    public static void generateBeacon(OwnBeacon ownBeacon, String groupKey){

        String key = FirebaseManager.refBeacons.push().getKey();
        ownBeacon.id = key;
        Map<String, Object> ownBeaconMap = ownBeacon.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Beacons/"+key,ownBeaconMap);
        childUpdates.put("/Groups/"+groupKey+"/beacons/"+key,key);
        FirebaseManager.refRoot.updateChildren(childUpdates);
    }
    public static void editGroup(OwnGroup ownGroup){
        Map<String, Object> ownGroupMap = ownGroup.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Groups/"+ownGroup.id,ownGroupMap);
        FirebaseManager.refRoot.updateChildren(childUpdates);
    }
    public static void editBeacon(OwnBeacon ownBeacon){
        Map<String, Object> ownBeaconMap = ownBeacon.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Beacons/"+ownBeacon.id,ownBeaconMap);
        FirebaseManager.refRoot.updateChildren(childUpdates);
    }



    /*public static List<OwnGroup> getGroups() {

        List<OwnGroup> lista = new ArrayList<>();
        refUserGroups.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    refGroups.child(dataSnapshot1.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            OwnGroup ownGroup = dataSnapshot.getValue(OwnGroup.class);
                            lista.add(ownGroup);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return lista;
    }*/

}
