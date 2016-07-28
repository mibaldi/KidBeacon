package com.mibaldi.kidbeacon.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mibaldi.kidbeacon.Data.Database.DataBaseManager;
import com.mibaldi.kidbeacon.Data.Database.OwnBeacon;
import com.mibaldi.kidbeacon.Data.Database.OwnGroup;
import com.mibaldi.kidbeacon.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final OwnGroup ownGroup = new OwnGroup();
        ownGroup.setName("Grupo 4");
        ownGroup.setCreation_date(new Date());
        final OwnBeacon ownBeacon = new OwnBeacon();
        ownBeacon.setUuid("B9407F30-F5F8-466E-AFF9-25556B57FE6F");
        ownBeacon.setMajor("0");
        ownBeacon.setMinor("1");
       // List<OwnBeacon> list = new ArrayList<>(Arrays.asList(ownBeacon));
        //ownGroup.setBeaconsList(list);

        DataBaseManager.storeOwnGroup(ownGroup, new DataBaseManager.OnDatabaseTransaction() {
            @Override
            public void onSucces() {
                Timber.d("Grupo guardado");
                DataBaseManager.asignOwnBeacon2OwnGroup(ownGroup.getName(), ownBeacon, new DataBaseManager.OnDatabaseTransaction() {
                    @Override
                    public void onSucces() {
                        Timber.d("Beacon guardado en el grupo "+ownGroup.getName());
                    }

                    @Override
                    public void onError(Throwable error) {
                        Timber.e(error,"error al guardar el beacon en el grupo");
                    }
                });

            }

            @Override
            public void onError(Throwable error) {
                Timber.e(error,"error");
            }
        });
    }
}
