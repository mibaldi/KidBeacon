package com.mibaldi.kidbeacon.Features.Rastreator.Activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.mibaldi.kidbeacon.Data.FirebaseManager;
import com.mibaldi.kidbeacon.Data.Models.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Models.OwnGroup;
import com.mibaldi.kidbeacon.Features.Rastreator.Adapters.ListBeaconsRastreatorAdapter;
import com.mibaldi.kidbeacon.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import timber.log.Timber;

public class ListBeaconsRastreatorActivity extends AppCompatActivity implements BeaconConsumer {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BeaconManager beaconManager;

    @BindView(R.id.beaconsList)
    RecyclerView recyclerView;

    private List<OwnBeacon> items = new ArrayList<>();
    private OwnGroup ownGroup;
    private ListBeaconsRastreatorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_beacons_rastreator);
        ButterKnife.bind(this);
        ownGroup = getIntent().getParcelableExtra("ownGroup");
        permission_bluetooth();
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);

        items = new ArrayList<>();
        getBeacons();
        adapter = new ListBeaconsRastreatorAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                if (collection.size() > 0) {
                    for (Beacon b : collection) {
                        OwnBeacon o = new OwnBeacon(b.getIdentifier(0).toString(), b.getIdentifier(1).toString(), b.getIdentifier(2).toString());
                        if (items.contains(o)) {
                            int index = items.indexOf(o);
                            OwnBeacon ownBeacon = items.get(index);
                            ownBeacon.distance = b.getDistance();
                            items.set(index, ownBeacon);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

                            Log.i("ListBeaconsRastreator", "The first beacon I see is about " + b.getIdentifier(0));
                        } else {
                            Timber.i("Sin beacons en este grupo");
                        }
                    }

                }
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }

    }

    @Override
    public Context getApplicationContext() {
        return this;
    }

    public void permission_bluetooth() {
        verifyBluetooth();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check

            if (this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(23)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }

                });
                builder.show();
            }
        }
    }

    private void verifyBluetooth() {

        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                        System.exit(0);
                    }
                });
                builder.show();
            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                    System.exit(0);
                }

            });
            builder.show();

        }

    }

    public void getBeacons() {
        items.clear();
        SpotsDialog dialog = new SpotsDialog(this, "Cargando Beacons");
        dialog.show();
        RxFirebaseDatabase.observeValuesList(FirebaseManager.refGroups.child(ownGroup.id + "/beacons"), String.class)
                .subscribe(groupBeacons -> {
                    for (String key : groupBeacons) {
                        RxFirebaseDatabase.observeSingleValue(FirebaseManager.refBeacons.child(key), OwnBeacon.class)
                                .subscribe(ownBeacon -> {
                                    items.add(ownBeacon);
                                    dialog.hide();
                                    adapter.notifyDataSetChanged();
                                }, throwable -> {
                                    Timber.e("ListBeaconsFragment", throwable.toString());
                                });
                    }
                }, throwable -> {
                    Log.e("RxFirebaseSample", throwable.toString());
                });
    }
}
